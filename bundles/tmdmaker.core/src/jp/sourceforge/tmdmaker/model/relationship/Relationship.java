/*
 * Copyright 2009-2018 TMD-Maker Project <https://tmdmaker.osdn.jp/>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jp.sourceforge.tmdmaker.model.relationship;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.AbstractRelationship;
import jp.sourceforge.tmdmaker.model.Event2EventRelationship;
import jp.sourceforge.tmdmaker.model.RecursiveRelationship;
import jp.sourceforge.tmdmaker.model.Resource2ResourceRelationship;
import jp.sourceforge.tmdmaker.model.SubsetEntity;
import jp.sourceforge.tmdmaker.model.TransfarReuseKeysToTargetRelationship;
import jp.sourceforge.tmdmaker.model.TurboFileRelationship;
import jp.sourceforge.tmdmaker.model.other.TurboFile;

/**
 * リレーションシップを生成する.
 * 
 * @author nakag
 *
 */
public class Relationship {
	/** logging */
	private static Logger logger = LoggerFactory.getLogger(Relationship.class);

	private AbstractEntityModel fromEntity;
	private AbstractEntityModel toEntity;

	/**
	 * コンストラクタは公開しない.
	 * 
	 * @param fromEntity
	 * @param toEntity
	 */
	protected Relationship(AbstractEntityModel fromEntity, AbstractEntityModel toEntity) {
		this.fromEntity = fromEntity;
		this.toEntity = toEntity;
	}

	/**
	 * モデル間のリレーションシップを生成して返す.
	 * 
	 * @param from
	 *            接続元
	 * @param to
	 *            接続先
	 * @return 関係の文法に則したリレーションシップを返す.
	 */
	public static AbstractRelationship of(AbstractEntityModel from, AbstractEntityModel to) {
		return new Relationship(from, to).build();
	}

	private AbstractRelationship build() {
		AbstractRelationship relationship = null;
		// 再帰
		if (isRecursive(fromEntity, toEntity)) {
			logger.debug("Recursive");
			relationship = new RecursiveRelationship(fromEntity);
		} else if (isR2E(fromEntity, toEntity)) {
			logger.debug("RESOURCE:EVENT");
			relationship = new TransfarReuseKeysToTargetRelationship(fromEntity, toEntity);
		} else if (isR2R(fromEntity, toEntity) && !isSameOriginal(fromEntity, toEntity)) {
			logger.debug("RESOURCE:RESOURCE");
			/* 対照表作成 */
			relationship = new Resource2ResourceRelationship(fromEntity, toEntity);
		} else if (isE2E(fromEntity, toEntity) && !isSameOriginal(fromEntity, toEntity)) {
			logger.debug("EVENT:EVENT");
			/* 通常コネクション */
			relationship = new Event2EventRelationship(fromEntity, toEntity);
		} else {
			logger.debug("Not TM Relationship Rule");
			if (isTurboFile(fromEntity, toEntity)) {
				relationship = new TurboFileRelationship(fromEntity, toEntity);
			}
		}
		return relationship;

	}

	/**
	 * エンティティの関連がR:Eかを判定する。
	 * 
	 * @param source
	 * @param target
	 * @return RESOURCE:Eの場合にtrueを返す。
	 */
	private static boolean isR2E(AbstractEntityModel source, AbstractEntityModel target) {
		return (source.isEvent() && target.isResource())
				|| (source.isResource() && target.isEvent());
	}

	/**
	 * エンティティの関連がR:Rかを判定する。
	 * 
	 * @param source
	 * @param target
	 * @return RESOURCE:Rの場合にtrueを返す。
	 */
	private static boolean isR2R(AbstractEntityModel source, AbstractEntityModel target) {
		return source.isResource() && target.isResource();
	}

	/**
	 * エンティティの関連が再帰かを判定する。
	 * 
	 * @param source
	 * @param target
	 * @return エンティティが同一インスタンスの場合にtrueを返す。
	 */
	private static boolean isRecursive(AbstractEntityModel source, AbstractEntityModel target) {
		return source == target;
	}

	/**
	 * エンティティの関連がE:Eかを判定する。
	 * 
	 * @param source
	 * @param target
	 * @return EVENT:Eの場合にtrueを返す。
	 */
	private static boolean isE2E(AbstractEntityModel source, AbstractEntityModel target) {
		return source.isEvent() && target.isEvent();
	}

	/**
	 * エンティティの関連が同じエンティティを起源としたサブセット同士かを判定する。
	 * 
	 * @param source
	 * @param target
	 * @return 同じエンティティを起源としたサブセット同士の場合にtrueを返す。
	 */
	private static boolean isSameOriginal(AbstractEntityModel source, AbstractEntityModel target) {
		if (source instanceof SubsetEntity && target instanceof SubsetEntity) {
			SubsetEntity sourceSubset = (SubsetEntity) source;
			SubsetEntity targetSubset = (SubsetEntity) target;

			return sourceSubset.getSuperset().equals(targetSubset.getSuperset());
		} else {
			return false;
		}
	}

	/**
	 * エンティティがターボファイルかを判定する。
	 *
	 * @param source
	 * @param target
	 *
	 * @return ターボファイルの場合にtrueを返す
	 */
	private static boolean isTurboFile(AbstractEntityModel source, AbstractEntityModel target) {
		return (source instanceof TurboFile) || (target instanceof TurboFile);
	}
}

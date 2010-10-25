/*
 * Copyright 2009-2010 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
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
package jp.sourceforge.tmdmaker.model.rule;

import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.AbstractRelationship;
import jp.sourceforge.tmdmaker.model.Event2EventRelationship;
import jp.sourceforge.tmdmaker.model.RecursiveRelationship;
import jp.sourceforge.tmdmaker.model.Resource2ResourceRelationship;
import jp.sourceforge.tmdmaker.model.SubsetEntity;
import jp.sourceforge.tmdmaker.model.TransfarReuseKeysToTargetRelationship;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 関係の文法に関するルールをまとめたクラス
 * 
 * @author nakaG
 * 
 */
public class RelationshipRule {
	/** logging */
	private static Logger logger = LoggerFactory.getLogger(RelationshipRule.class);

	public static AbstractRelationship createRelationship(AbstractEntityModel source, AbstractEntityModel target) {
		AbstractRelationship relationship = null;
		// 再帰
		if (isRecursive(source, target)) {
			logger.debug("Recursive");
			relationship = new RecursiveRelationship(source);
		} else if (isR2E(source, target)) {
			logger.debug("RESOURCE:EVENT");
			relationship = new TransfarReuseKeysToTargetRelationship(source, target);
		} else if (isR2R(source, target) && !isSameOriginal(source, target)) {
			logger.debug("RESOURCE:RESOURCE");
			/* 対照表作成 */
			relationship = new Resource2ResourceRelationship(source, target);
		} else if (isE2E(source, target) && !isSameOriginal(source, target)) {
			logger.debug("EVENT:EVENT");
			/* 通常コネクション */
			relationship = new Event2EventRelationship(source, target);
		}
		return relationship;
	}
	/**
	 * エンティティの関連が再帰かを判定する。
	 * 
	 * @param source
	 * @param target
	 * @return エンティティが同一インスタンスの場合にtrueを返す。
	 */
	private static boolean isRecursive(AbstractEntityModel source,
			AbstractEntityModel target) {
		return source == target;
	}

	/**
	 * エンティティの関連がR:Rかを判定する。
	 * 
	 * @param source
	 * @param target
	 * @return RESOURCE:Rの場合にtrueを返す。
	 */
	private static boolean isR2R(AbstractEntityModel source,
			AbstractEntityModel target) {
		return EntityTypeRule.isResource(source)
				&& EntityTypeRule.isResource(target);
	}
	/**
	 * エンティティの関連が同じエンティティを起源としたサブセット同士かを判定する。
	 * @param source
	 * @param target
	 * @return 同じエンティティを起源としたサブセット同士の場合にtrueを返す。
	 */
	private static boolean isSameOriginal(AbstractEntityModel source,
			AbstractEntityModel target) {
		if (source instanceof SubsetEntity && target instanceof SubsetEntity) {
			SubsetEntity sourceSubset = (SubsetEntity) source;
			SubsetEntity targetSubset = (SubsetEntity) target;
			
			return sourceSubset.getSuperset().equals(targetSubset.getSuperset());
		} else {
			return false;
		}
	}
	/**
	 * エンティティの関連がR:Eかを判定する。
	 * 
	 * @param source
	 * @param target
	 * @return RESOURCE:Eの場合にtrueを返す。
	 */
	private static boolean isR2E(AbstractEntityModel source,
			AbstractEntityModel target) {
		return (EntityTypeRule.isEvent(source) && EntityTypeRule
				.isResource(target))
				|| (EntityTypeRule.isResource(source) && EntityTypeRule
						.isEvent(target));
	}

	/**
	 * エンティティの関連がE:Eかを判定する。
	 * 
	 * @param source
	 * @param target
	 * @return EVENT:Eの場合にtrueを返す。
	 */
	private static boolean isE2E(AbstractEntityModel source,
			AbstractEntityModel target) {
		return EntityTypeRule.isEvent(source) && EntityTypeRule.isEvent(target);
	}

}

/*
 * Copyright 2009-2015 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.AbstractRelationship;
import jp.sourceforge.tmdmaker.model.CombinationTable;
import jp.sourceforge.tmdmaker.model.EntityType;
import jp.sourceforge.tmdmaker.model.Event2EventRelationship;
import jp.sourceforge.tmdmaker.model.MappingList;
import jp.sourceforge.tmdmaker.model.RecursiveRelationship;
import jp.sourceforge.tmdmaker.model.RecursiveTable;
import jp.sourceforge.tmdmaker.model.Resource2ResourceRelationship;
import jp.sourceforge.tmdmaker.model.SubsetEntity;
import jp.sourceforge.tmdmaker.model.TransfarReuseKeysToTargetRelationship;
import jp.sourceforge.tmdmaker.model.TurboFileRelationship;
import jp.sourceforge.tmdmaker.model.other.TurboFile;

/**
 * 関係の文法に関するルールをまとめたクラス
 * 
 * @author nakaG
 * 
 */
public class RelationshipRule {
	/** logging */
	private static Logger logger = LoggerFactory.getLogger(RelationshipRule.class);

	public static AbstractRelationship createRelationship(AbstractEntityModel source,
			AbstractEntityModel target) {
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
		} else {
			logger.debug("Not TM Relationship Rule");
			if (isTurboFile(source, target)) {
				relationship = new TurboFileRelationship(source, target);
			}
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
	private static boolean isRecursive(AbstractEntityModel source, AbstractEntityModel target) {
		return source == target;
	}

	/**
	 * エンティティの関連がR:Rかを判定する。
	 * 
	 * @param source
	 * @param target
	 * @return RESOURCE:Rの場合にtrueを返す。
	 */
	private static boolean isR2R(AbstractEntityModel source, AbstractEntityModel target) {
		return EntityTypeRule.isResource(source) && EntityTypeRule.isResource(target);
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
	 * エンティティの関連がR:Eかを判定する。
	 * 
	 * @param source
	 * @param target
	 * @return RESOURCE:Eの場合にtrueを返す。
	 */
	private static boolean isR2E(AbstractEntityModel source, AbstractEntityModel target) {
		return (EntityTypeRule.isEvent(source) && EntityTypeRule.isResource(target))
				|| (EntityTypeRule.isResource(source) && EntityTypeRule.isEvent(target));
	}

	/**
	 * エンティティの関連がE:Eかを判定する。
	 * 
	 * @param source
	 * @param target
	 * @return EVENT:Eの場合にtrueを返す。
	 */
	private static boolean isE2E(AbstractEntityModel source, AbstractEntityModel target) {
		return EntityTypeRule.isEvent(source) && EntityTypeRule.isEvent(target);
	}

	/**
	 * 対照表を作成する
	 * 
	 * @param source
	 *            接続元
	 * @param target
	 *            接続先
	 * @return 対照表
	 */
	public static CombinationTable createCombinationTable(AbstractEntityModel source,
			AbstractEntityModel target) {
		CombinationTable table = new CombinationTable();
		table.setEntityType(EntityType.RESOURCE);
		table.setName(createCombinationTableName(source, target));
		ImplementRule.setModelDefaultValue(table);

		return table;
	}

	/**
	 * 対照表の名前を作成する。
	 * 
	 * @param source
	 *            接続元
	 * @param target
	 *            接続先
	 * @return 対照表名
	 */
	private static String createCombinationTableName(AbstractEntityModel source,
			AbstractEntityModel target) {
		return source.getName().replace(CombinationTable.COMBINATION_TABLE_SUFFIX, "") + "."
				+ target.getName().replace(CombinationTable.COMBINATION_TABLE_SUFFIX, "")
				+ CombinationTable.COMBINATION_TABLE_SUFFIX;

	}

	/**
	 * 対応表を作成する。
	 * 
	 * @param source
	 *            接続元
	 * @param target
	 *            接続先
	 * @return 対応表
	 */
	public static MappingList createMappingList(AbstractEntityModel source,
			AbstractEntityModel target) {
		MappingList table = new MappingList();
		table.setName(createMappingListName(source, target));
		ImplementRule.setModelDefaultValue(table);

		return table;
	}

	/**
	 * 対応表の名前を作成する。
	 * 
	 * @param source
	 *            接続元
	 * @param target
	 *            接続先
	 * @return 対応表名
	 */
	private static String createMappingListName(AbstractEntityModel source,
			AbstractEntityModel target) {
		return source.getName() + "." + target.getName() + "." + "対応表";
	}

	/**
	 * 再帰表を作成する
	 * 
	 * @param model
	 *            再帰元モデル
	 * @return 再帰表
	 */
	public static RecursiveTable createRecursiveTable(AbstractEntityModel model) {
		RecursiveTable table = new RecursiveTable();
		table.setEntityType(model.getEntityType());
		table.setName(createRecursiveTableName(model));
		ImplementRule.setModelDefaultValue(table);
		table.addCreationIdentifier(model);

		return table;
	}

	/**
	 * 再帰表の名称を作成する
	 * 
	 * @param model
	 *            再帰元モデル
	 * @return 再帰表名
	 */
	private static String createRecursiveTableName(AbstractEntityModel model) {
		String name = model.getName();
		return name + "." + name + "." + "再帰表";
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

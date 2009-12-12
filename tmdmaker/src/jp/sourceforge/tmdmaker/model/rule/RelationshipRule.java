/*
 * Copyright 2009 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
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

/**
 * 関係の文法に関するルールをまとめたクラス
 * 
 * @author nakaG
 * 
 */
public class RelationshipRule {
	/**
	 * エンティティの関連が再帰かを判定する。
	 * 
	 * @param source
	 * @param target
	 * @return エンティティが同一インスタンスの場合にtrueを返す。
	 */
	public static boolean isRecursive(AbstractEntityModel source,
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
	public static boolean isR2R(AbstractEntityModel source,
			AbstractEntityModel target) {
		return EntityTypeRule.isResource(source)
				&& EntityTypeRule.isResource(target);
	}

	/**
	 * エンティティの関連がR:Eかを判定する。
	 * 
	 * @param source
	 * @param target
	 * @return RESOURCE:Eの場合にtrueを返す。
	 */
	public static boolean isR2E(AbstractEntityModel source,
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
	public static boolean isE2E(AbstractEntityModel source,
			AbstractEntityModel target) {
		return EntityTypeRule.isEvent(source) && EntityTypeRule.isEvent(target);
	}

}

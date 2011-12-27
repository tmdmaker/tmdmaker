/*
 * Copyright 2009-2011 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
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
import jp.sourceforge.tmdmaker.model.Attribute;
import jp.sourceforge.tmdmaker.model.Entity;
import jp.sourceforge.tmdmaker.model.EntityType;
import jp.sourceforge.tmdmaker.model.IAttribute;

/**
 * 個体の性質・関係の性質に関するルールをまとめたクラス
 * 
 * @author nakaG
 * 
 */
public class EntityTypeRule {
	private static final String EVENT_DATE_LITERAL = "日";

	/**
	 * エンティティ系モデルの種類がリソースかを判定する
	 * 
	 * @param model
	 * @return リソースの場合にtrueを返す
	 */
	public static boolean isResource(AbstractEntityModel model) {
		return model.getEntityType().equals(EntityType.RESOURCE);
	}

	/**
	 * エンティティ系モデルの種類がイベントかを判定する
	 * 
	 * @param model
	 * @return イベントの場合にtrueを返す
	 */
	public static boolean isEvent(AbstractEntityModel model) {
		return model.getEntityType().equals(EntityType.EVENT);
	}

	/**
	 * エンティティ種類によりエンティティ作成時に保持するアトリビュートを追加する
	 * 
	 * @param model
	 *            作成対象エンティティ
	 */
	public static void addDefaultAttribute(Entity model) {
		Attribute defaultAttribute = new Attribute();
		if (isEvent(model)) {
			defaultAttribute.setName(model.getName() + EVENT_DATE_LITERAL);
			ImplementRule.setEventDefaultAttributeValue(defaultAttribute);
		} else {
			defaultAttribute.setName(model.getName() + "名称");
			ImplementRule.setResourceDefaultAttributeValue(defaultAttribute);
		}
		model.addAttribute(defaultAttribute);
	}

	/**
	 * エンティティ作成時に保持するアトリビュートが既に追加されているか
	 * 
	 * @param model
	 *            チェック対象エンティティ
	 * @return アトリビュートが追加されている場合にtrueを返す
	 */
	public static boolean hasDefaultAttributeAlreadyAdded(Entity model) {
		return model.getAttributes().size() > 0;
	}

	/**
	 * エンティティ系モデルがイベントを表すアトリビュートを持っているか
	 * 
	 * @param model
	 *            チェック対象エンティティ
	 * @return エンティティ系モデルがイベントを表すアトリビュートを持っている場合にtrueを返す
	 */
	public static boolean hasEventAttribute(AbstractEntityModel model) {
		for (IAttribute a : model.getAttributes()) {
			if (a.getName().endsWith(EVENT_DATE_LITERAL)) {
				return true;
			}
		}
		return false;
	}
}

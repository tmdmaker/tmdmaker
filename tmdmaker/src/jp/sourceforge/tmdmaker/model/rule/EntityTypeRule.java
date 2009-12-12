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
import jp.sourceforge.tmdmaker.model.EntityType;

/**
 * 個体の性質・関係の性質に関するルールをまとめたクラス
 * 
 * @author nakaG
 * 
 */
public class EntityTypeRule {
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
}

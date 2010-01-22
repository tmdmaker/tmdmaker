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
import jp.sourceforge.tmdmaker.model.SubsetEntity;
import jp.sourceforge.tmdmaker.model.SubsetType;

/**
 * データの周延に関するルールをまとめたクラス
 * 
 * @author nakaG
 * 
 */
public class SubsetRule {
	/**
	 * サブセット作成のためにサブセット種類を用意する。
	 * 
	 * @param model
	 *            サブセット作成元モデル
	 * @return サブセット種類
	 */
	public static SubsetType setupSubsetType(AbstractEntityModel model) {
		SubsetType subsetType = model.findSubsetType();
		// サブセット未作成の場合は初期値を用意
		if (subsetType == null) {
			subsetType = new SubsetType();
			subsetType.setSubsetType(SubsetType.SubsetTypeValue.SAME);
			subsetType.setExceptNull(false);
		}
		return subsetType;
	}

	/**
	 * サブセットを作成する。
	 * 
	 * @param model
	 *            サブセット作成元モデル
	 * @param subsetEntityName
	 *            サブセット名
	 * @return サブセット
	 */
	public static SubsetEntity createSubsetEntity(AbstractEntityModel model,
			String subsetEntityName) {
		SubsetEntity subsetEntity = new SubsetEntity();
		subsetEntity.setName(subsetEntityName);
		subsetEntity
				.setOriginalReusedIdentifier(model.createReusedIdentifier());
		subsetEntity.setEntityType(model.getEntityType());
		return subsetEntity;
	}
}

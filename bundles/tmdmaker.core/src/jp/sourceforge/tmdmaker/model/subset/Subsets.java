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
package jp.sourceforge.tmdmaker.model.subset;

import java.util.Collections;
import java.util.List;

import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.Entity2SubsetTypeRelationship;
import jp.sourceforge.tmdmaker.model.SubsetEntity;
import jp.sourceforge.tmdmaker.model.SubsetType;

/**
 * サブセットの集約クラス.
 * 
 * サブセット操作の起点になる.
 * 
 * @author nakag
 *
 */
public class Subsets {
	private AbstractEntityModel parent;

	/**
	 * コンストラクタ.
	 * 
	 * @param parent
	 *            サブセット元
	 */
	public Subsets(AbstractEntityModel parent) {
		this.parent = parent;
	}

	/**
	 * サブセットを操作する際のビルダーを返す.
	 * 
	 * @return
	 */
	public SubsetBuilder builder() {
		return new SubsetBuilder(parent);
	}

	/**
	 * サブセットを検索する際のクエリを返す.
	 * 
	 * @return
	 */
	public SubsetQuery query() {
		return new SubsetQuery(parent);
	}

	/**
	 * 全サブセットを返す.
	 * 
	 * @return
	 */
	public List<SubsetEntity> all() {
		SubsetType type = parent.findSubsetType();
		if (type != null) {
			return type.getSubsetList();
		}
		return Collections.emptyList();
	}

	/**
	 * サブセットがあるか？
	 * 
	 * @return サブセットがある場合はtrueを返す.
	 */
	public boolean hasSubset() {
		SubsetType type = subsetType();
		return type != null && type.hasSubsetEntity();
	}

	/**
	 * サブセット種類を返す.
	 * 
	 * @return
	 */
	public SubsetType subsetType() {
		return this.parent.findSubsetType();
	}

	/**
	 * サブセット種類へのリレションシップを返す.
	 * 
	 * @return
	 */
	protected Entity2SubsetTypeRelationship subsetTypeRelationship() {
		SubsetType type = parent.findSubsetType();
		if (type != null) {
			return type.getEntityRelationship();
		}
		return new Entity2SubsetTypeRelationship(parent);
	}
}

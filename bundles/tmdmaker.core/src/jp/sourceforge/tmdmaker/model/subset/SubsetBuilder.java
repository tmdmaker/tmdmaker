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

import java.util.ArrayList;
import java.util.List;

import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.Entity2SubsetTypeRelationship;
import jp.sourceforge.tmdmaker.model.IAttribute;
import jp.sourceforge.tmdmaker.model.SubsetEntity;
import jp.sourceforge.tmdmaker.model.SubsetType;
import jp.sourceforge.tmdmaker.model.SubsetType.SubsetTypeValue;
import jp.sourceforge.tmdmaker.model.SubsetType2SubsetRelationship;
import jp.sourceforge.tmdmaker.model.parts.ModelName;

/**
 * サブセットを生成するクラス.
 * 
 * @author nakag
 *
 */
public class SubsetBuilder {
	private boolean partitionChanged = false;
	private IAttribute partition;
	private boolean subsetTypeChanged = false;
	private SubsetTypeValue subsetTypeValue = SubsetTypeValue.SAME;
	private boolean exceptNullChanged = false;
	private boolean exceptNull = false;
	private List<ModelName> addSubsetNameList = new ArrayList<ModelName>();
	private List<SubsetType2SubsetRelationship> addedSubsetRelationship = new ArrayList<SubsetType2SubsetRelationship>();
	private List<SubsetType2SubsetRelationship> deletedSubsetRelationship = new ArrayList<SubsetType2SubsetRelationship>();
	private Entity2SubsetTypeRelationship entity2SubsetTypeRelationship;

	/**
	 * コンストラクタ.
	 * 
	 * @param model
	 *            サブセット生成元
	 */
	protected SubsetBuilder(AbstractEntityModel model) {
		this.entity2SubsetTypeRelationship = model.subsets().subsetTypeRelationship();
	}

	/**
	 * サブセットを生成する.
	 */
	public void build() {
		SubsetType subsetType = buildSubsetType();

		removeSubsetEntities();

		buildSubsetEntities(subsetType);
		changeSubsetTypeState(subsetType);
		if (!subsetType.hasSubsetEntity()) {
			this.entity2SubsetTypeRelationship.disconnect();
		}
	}

	private void changeSubsetTypeState(SubsetType subsetType) {
		if (this.partitionChanged) {
			subsetType.setPartitionAttribute(this.partition);
		}
		if (this.subsetTypeChanged) {
			subsetType.setSubsetType(this.subsetTypeValue);
		}
		if (this.exceptNullChanged) {
			subsetType.setExceptNull(this.exceptNull);
		}
	}

	private void buildSubsetEntities(SubsetType subsetType) {
		if (!this.addSubsetNameList.isEmpty()) {
			if (this.addedSubsetRelationship.isEmpty()) {
				for (ModelName name : this.addSubsetNameList) {
					SubsetType2SubsetRelationship relationship = new SubsetType2SubsetRelationship(
							subsetType, name);
					this.addedSubsetRelationship.add(relationship);
				}
			}
			for (SubsetType2SubsetRelationship r : this.addedSubsetRelationship) {
				r.connect();
			}
		}
	}

	private void removeSubsetEntities() {
		for (SubsetType2SubsetRelationship r : this.deletedSubsetRelationship) {
			r.disconnect();
		}
	}

	private SubsetType buildSubsetType() {
		if (!this.entity2SubsetTypeRelationship.isConnected()) {
			this.entity2SubsetTypeRelationship.connect();
		}
		SubsetType subsetType = this.entity2SubsetTypeRelationship.getSubsetType();
		return subsetType;
	}

	/**
	 * 同一のサブセットに設定する.
	 * 
	 * @return
	 */
	public SubsetBuilder same() {
		this.subsetTypeChanged = true;
		this.subsetTypeValue = SubsetTypeValue.SAME;
		return this;
	}

	/**
	 * 相違のサブセットに設定する.
	 * 
	 * @return
	 */
	public SubsetBuilder different() {
		this.subsetTypeChanged = true;
		this.subsetTypeValue = SubsetTypeValue.DIFFERENT;
		return this;
	}

	/**
	 * 区分コードのアトリビュートを設定する.
	 * 
	 * @param partition
	 *            区分コードのアトリビュート
	 * @return
	 */
	public SubsetBuilder partition(IAttribute partition) {
		this.partitionChanged = true;
		this.partition = partition;
		return this;
	}

	/**
	 * NULLを排除する.
	 * 
	 * @return
	 */
	public SubsetBuilder expectNull() {
		this.exceptNullChanged = true;
		this.exceptNull = true;
		return this;
	}

	/**
	 * NULLを排除しない.
	 * 
	 * @return
	 */
	public SubsetBuilder notExpectNull() {
		this.exceptNullChanged = true;
		this.exceptNull = false;
		return this;
	}

	/**
	 * サブセットを追加する.
	 * 
	 * @param subsetName
	 *            サブセット名
	 * @return
	 */
	public SubsetBuilder add(ModelName subsetName) {
		this.addSubsetNameList.add(subsetName);
		return this;
	}

	/**
	 * サブセットを削除する.
	 * 
	 * @param removeSubset
	 * @return
	 */
	public SubsetBuilder remove(SubsetEntity removeSubset) {
		this.deletedSubsetRelationship.add(removeSubset.getSubsetTypeRelationship());
		return this;
	}

	/**
	 * 本インスタンスで追加したサブセットを返す.
	 * 
	 * @return 追加したサブセット
	 */
	public List<SubsetEntity> getAddedSubsetList() {
		List<SubsetEntity> addedSubsetList = new ArrayList<SubsetEntity>();
		for (SubsetType2SubsetRelationship r : this.addedSubsetRelationship) {
			addedSubsetList.add(r.getSubsetEntity());
		}
		return addedSubsetList;
	}

	public void rollbackAdd() {
		for (SubsetType2SubsetRelationship r : this.addedSubsetRelationship) {
			r.disconnect();
		}
		if (!this.entity2SubsetTypeRelationship.getSubsetType().hasSubsetEntity()) {
			this.entity2SubsetTypeRelationship.disconnect();
		}
	}

	public void rollbackRemove() {
		if (!this.entity2SubsetTypeRelationship.isConnected()) {
			this.entity2SubsetTypeRelationship.connect();
		}
		for (SubsetType2SubsetRelationship r : this.deletedSubsetRelationship) {
			r.connect();
		}
	}
}

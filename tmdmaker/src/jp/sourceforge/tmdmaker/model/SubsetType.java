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
package jp.sourceforge.tmdmaker.model;

import java.util.ArrayList;
import java.util.List;

/**
 * サブセット種類
 * 
 * @author nakaG
 * 
 */
@SuppressWarnings("serial")
public class SubsetType extends ConnectableElement {

	/** サブセット種類（同一、相違） */
	public enum SubsetTypeValue {
		/** 同一 */
		SAME,
		/** 相違 */
		DIFFERENT
	};

	/** サブセット種類 */
	private SubsetTypeValue subsetType = SubsetTypeValue.SAME;
	/** 区分コードプロパティ定数 */
	public static final String PROPERTY_PARTITION = "_property_partition";
	/** サブセットタイプ */
	public static final String PROPERTY_TYPE = "_property_type";
	/** 区分コードの属性 */
	private IAttribute partitionAttribute;
	/** NULLを排除（形式的サブセット）するか？ */
	private boolean exceptNull;

	/**
	 * サブセットエンティティ取得
	 * 
	 * @return サブセットエンティティのリスト
	 */
	public List<SubsetEntity> findSubsetEntityList() {
		List<SubsetEntity> results = new ArrayList<SubsetEntity>();
		for (AbstractConnectionModel c : getModelSourceConnections()) {
			if (c instanceof RelatedRelationship) {
				results.add((SubsetEntity) c.getTarget());
			}
		}
		return results;
	}

	/**
	 * @return the subsetType
	 */
	public SubsetTypeValue getSubsetType() {
		return subsetType;
	}

	/**
	 * @param subsetType
	 *            the subsetType to set
	 */
	public void setSubsetType(SubsetTypeValue subsetType) {
		SubsetTypeValue oldValue = this.subsetType;
		this.subsetType = subsetType;
		firePropertyChange(PROPERTY_TYPE, oldValue, this.subsetType);
	}

	/**
	 * @return the partitionAttribute
	 */
	public IAttribute getPartitionAttribute() {
		return partitionAttribute;
	}

	/**
	 * @param partitionAttribute
	 *            the partitionAttribute to set
	 */
	public void setPartitionAttribute(IAttribute partitionAttribute) {
		IAttribute oldValue = this.partitionAttribute;
		this.partitionAttribute = partitionAttribute;
		firePropertyChange(PROPERTY_PARTITION, oldValue, partitionAttribute);
		firePartitionChanged();
	}

	/**
	 * @return the exceptNull
	 */
	public boolean isExceptNull() {
		return exceptNull;
	}

	/**
	 * @param exceptNull
	 *            the exceptNull to set
	 */
	public void setExceptNull(boolean exceptNull) {
		boolean oldValue = this.exceptNull;
		this.exceptNull = exceptNull;
		firePropertyChange(PROPERTY_PARTITION, oldValue, partitionAttribute);
		firePartitionChanged();
	}

	/**
	 * 区分コード変更時処理
	 */
	public void firePartitionChanged() {
		if (getModelTargetConnections().size() > 0) {
			((Entity2SubsetTypeRelationship) getModelTargetConnections().get(0))
					.firePartitionChanged();
		}
	}

	/**
	 * 同一のサブセットか？
	 * 
	 * @return 同一サブセットの場合にtrueを返す。
	 */
	public boolean isSameType() {
		return subsetType.equals(SubsetTypeValue.SAME);
	}

	/**
	 * オブジェクト破棄
	 */
	public void dispose() {
		// TODO 必要な処理を記述
	}

}

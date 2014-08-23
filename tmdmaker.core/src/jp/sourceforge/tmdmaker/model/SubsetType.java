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
	/** サブセットタイプの向き */
	public static final String PROPERTY_DIRECTION = "_property_direction";

	/** 区分コードの属性 */
	private IAttribute partitionAttribute;
	/** NULLを排除（形式的サブセット）するか？ */
	private boolean exceptNull;

	/** モデルの向き（縦） */
	private boolean vertical = false;

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
		firePartitionChanged();
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
		// スーパーセット
		notifySuperset();
		// スーパーセットとのリレーションシップ
		nofityRelationship();
		// サブセット
		notifySubsetEntity();
	}

	private Entity2SubsetTypeRelationship getEntity2SubsetTypeRelationship() {
		if (getModelTargetConnections().size() > 0) {
			return (Entity2SubsetTypeRelationship) getModelTargetConnections().get(0);
		}
		return null;
	}

	public AbstractEntityModel getSuperset() {
		Entity2SubsetTypeRelationship r = getEntity2SubsetTypeRelationship();
		if (r != null) {
			return (AbstractEntityModel) r.getSource();
		}
		return null;
	}

	private void notifySuperset() {
		AbstractEntityModel superset = getSuperset();
		if (superset != null) {
			superset.firePropertyChange(PROPERTY_PARTITION, null, getSubsetType());
		}
	}

	private void nofityRelationship() {
		Entity2SubsetTypeRelationship r = getEntity2SubsetTypeRelationship();
		if (r != null) {
			r.firePartitionChanged();
		}
	}

	private void notifySubsetEntity() {
		for (SubsetEntity s : findSubsetEntityList()) {
			s.firePropertyChange(PROPERTY_PARTITION, null, getSubsetType());
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

	/**
	 * 向き（縦）
	 * 
	 * @return
	 */
	public boolean isVertical() {
		return vertical;
	}

	/**
	 * 向き（縦）
	 * 
	 * @param vertical
	 */
	public void setVertical(boolean vertical) {
		boolean oldValue = this.vertical;
		this.vertical = vertical;
		firePropertyChange(PROPERTY_DIRECTION, oldValue, this.vertical);
	}

	public boolean isNew() {
		// 接続前の場合は新規作成
		return getModelTargetConnections().size() == 0;
	}

	@Override
	public void accept(IVisitor visitor) {
		visitor.visit(this);
	}
}

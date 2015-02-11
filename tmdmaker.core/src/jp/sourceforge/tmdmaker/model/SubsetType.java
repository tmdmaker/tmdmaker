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
package jp.sourceforge.tmdmaker.model;

import java.util.ArrayList;
import java.util.List;

/**
 * サブセット種類.
 *
 * @author nakaG
 *
 */
@SuppressWarnings("serial")
public class SubsetType extends AbstractSubsetType {

	/** サブセット種類（同一、相違）. */
	public enum SubsetTypeValue {
		/* 同一 */
		SAME,
		/* 相違 */
		DIFFERENT
	};

	/** サブセット種類. */
	private SubsetTypeValue subsetType = SubsetTypeValue.SAME;
	/** 区分コードプロパティ定数. */
	public static final String PROPERTY_PARTITION = "_property_partition";
	/** サブセットタイプ. */
	public static final String PROPERTY_TYPE = "_property_type";
	/** 区分コードの属性. */
	private IAttribute partitionAttribute;
	/** NULLを排除（形式的サブセット）するか？. */
	private boolean exceptNull;

	/**
	 * サブセットエンティティ取得.
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

	/**
	 * スーパーセットを返す
	 * @return  スーパーセット。存在しない場合はnullを返す。
	 */
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
	 * 新規作成か？
	 * @return 新規作成の場合はtrueを返す
	 */
	public boolean isNew() {
		// 接続前の場合は新規作成
		return getModelTargetConnections().size() == 0;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.ModelElement#accept(jp.sourceforge.tmdmaker.model.IVisitor)
	 */
	@Override
	public void accept(IVisitor visitor) {
		visitor.visit(this);
	}
	
	public boolean removeSubsetEntity(SubsetEntity subsetEntity) {
		if (!subsetEntity.isDeletable()) {
			return false;
		}
		for (SubsetEntity s : findSubsetEntityList()) {
			if (s.equals(subsetEntity)) {
				Diagram diagram = subsetEntity.getDiagram();
				SubsetType2SubsetRelationship r = (SubsetType2SubsetRelationship) s.findRelationshipFromTargetConnections(SubsetType2SubsetRelationship.class)
				.get(0);
				r.disconnect();
				diagram.removeChild(subsetEntity);
				return true;
			}
		}
		return false;
	}
	
	public boolean hasSubsetEntity() {
		return getModelSourceConnections().size() != 0;
	}
}

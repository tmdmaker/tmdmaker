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
public class SubsetType extends AbstractSubsetType<AbstractEntityModel> {

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
	 * コンストラクタは公開しない.
	 */
	protected SubsetType() {
		super();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.model.AbstractSubsetType#getSubsetList()
	 */
	@Override
	public List<SubsetEntity> getSubsetList() {
		List<SubsetEntity> results = new ArrayList<SubsetEntity>();
		for (AbstractConnectionModel c : getModelSourceConnections()) {
			ConnectableElement m = c.getTarget();
			if (m instanceof SubsetEntity) {
				results.add((SubsetEntity) m);
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

	/**
	 * エンティティとのリレーションシップを返す.
	 * 
	 * @return エンティティとのリレーションシップ
	 */
	public Entity2SubsetTypeRelationship getEntityRelationship() {
		if (getModelTargetConnections().size() > 0) {
			return (Entity2SubsetTypeRelationship) getModelTargetConnections().get(0);
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.model.AbstractSubsetType#getSuperset()
	 */
	@Override
	public AbstractEntityModel getSuperset() {
		Entity2SubsetTypeRelationship r = getEntityRelationship();
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
		Entity2SubsetTypeRelationship r = getEntityRelationship();
		if (r != null) {
			r.firePartitionChanged();
		}
	}

	private void notifySubsetEntity() {
		for (SubsetEntity s : getSubsetList()) {
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
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.ModelElement#accept(jp.sourceforge.tmdmaker.model.IVisitor)
	 */
	@Override
	public void accept(IVisitor visitor) {
		visitor.visit(this);
	}

	/**
	 * サブセットを保持しているか?
	 * 
	 * @return サブセットを保持している場合にtrueを返す.
	 */
	public boolean hasSubsetEntity() {
		return getModelSourceConnections().size() != 0;
	}
}

/*
 * Copyright 2009-2019 TMD-Maker Project <https://www.tmdmaker.org>
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
package org.tmdmaker.core.model;

/**
 * リレーションシップの基底クラス
 * 
 * @author nakaG
 * 
 */
@SuppressWarnings("serial")
public abstract class AbstractRelationship extends AbstractConnectionModel
		implements IdentifierChangeListener {
	/** 接続元とのカーディナリティ */
	private Cardinality sourceCardinality = Cardinality.ONE;
	/** 接続先とのカーディナリティ */
	private Cardinality targetCardinality = Cardinality.ONE;
	private boolean sourceNoInstance = false;
	private boolean targetNoInstance = false;
	private boolean centerMark = false;

	/**
	 * @return the sourceCardinality
	 */
	public Cardinality getSourceCardinality() {
		return sourceCardinality;
	}

	/**
	 * @param sourceCardinality
	 *            the sourceCardinality to set
	 */
	public void setSourceCardinality(Cardinality sourceCardinality) {
		Cardinality oldValue = this.sourceCardinality;
		this.sourceCardinality = sourceCardinality;
		firePropertyChange(PROPERTY_SOURCE_CARDINALITY, oldValue, sourceCardinality);
	}

	/**
	 * @return the targetCardinality
	 */
	public Cardinality getTargetCardinality() {
		return targetCardinality;
	}

	/**
	 * @param targetCardinality
	 *            the targetCardinality to set
	 */
	public void setTargetCardinality(Cardinality targetCardinality) {
		Cardinality oldValue = this.targetCardinality;
		this.targetCardinality = targetCardinality;
		firePropertyChange(PROPERTY_TARGET_CARDINALITY, oldValue, targetCardinality);
	}

	/**
	 * @return the sourceNoInstance
	 */
	public boolean isSourceNoInstance() {
		return sourceNoInstance;
	}

	/**
	 * @param sourceNoInstance
	 *            the sourceNoInstance to set
	 */
	public void setSourceNoInstance(boolean sourceNoInstance) {
		boolean oldValue = this.sourceNoInstance;
		this.sourceNoInstance = sourceNoInstance;
		firePropertyChange(PROPERTY_SOURCE_CARDINALITY, oldValue, sourceNoInstance);
	}

	/**
	 * @return the targetNoInstance
	 */
	public boolean isTargetNoInstance() {
		return targetNoInstance;
	}

	/**
	 * @param targetNoInstance
	 *            the targetNoInstance to set
	 */
	public void setTargetNoInstance(boolean targetNoInstance) {
		boolean oldValue = this.targetNoInstance;
		this.targetNoInstance = targetNoInstance;
		firePropertyChange(PROPERTY_TARGET_CARDINALITY, oldValue, targetNoInstance);
	}

	/**
	 * @return the centerMark
	 */
	public boolean isCenterMark() {
		return centerMark;
	}

	/**
	 * @param centerMark
	 *            the centerMark to set
	 */
	public void setCenterMark(boolean centerMark) {
		boolean oldValue = this.centerMark;
		this.centerMark = centerMark;
		firePropertyChange(PROPERTY_CONNECTION, oldValue, centerMark);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.tmdmaker.core.model.IdentifierChangeListener#identifierChanged()
	 */
	@Override
	public void identifierChanged() {

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.tmdmaker.core.model.AbstractConnectionModel#isDeletable()
	 */
	@Override
	public boolean isDeletable() {
		return false;
	}

	@Override
	public AbstractEntityModel getSource() {
		return (AbstractEntityModel) super.getSource();
	}

	@Override
	public AbstractEntityModel getTarget() {
		return (AbstractEntityModel) super.getTarget();
	}

	/**
	 * 多値のリレーションシップ判定
	 * 
	 * @return 多値の場合にtrueを返す
	 */
	public boolean isMultiValue() {
		return getSourceCardinality().equals(Cardinality.MANY)
				&& getTargetCardinality().equals(Cardinality.MANY);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.tmdmaker.core.model.AbstractConnectionModel#getSourceName()
	 */
	@Override
	public String getSourceName() {
		return getSource().getName();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.tmdmaker.core.model.AbstractConnectionModel#getTargetName()
	 */
	@Override
	public String getTargetName() {
		return getTarget().getName();
	}

	@Override
	public void accept(IVisitor visitor) {
		visitor.visit(this);
	}

	/**
	 * リレーションシップで表が生成されたかを返す.
	 * 
	 * @return 表が生成されている場合にtrueを返す.
	 */
	public abstract boolean hasTable();

	/**
	 * リレーションシップで生成された表を返す.
	 * 
	 * @return リレーションシップで生成された表.生成していない場合はnullを返す.
	 */
	public abstract AbstractEntityModel getTable();
}

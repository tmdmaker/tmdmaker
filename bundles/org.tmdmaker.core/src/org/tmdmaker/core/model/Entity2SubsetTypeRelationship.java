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
 * エンティティ系モデルとサブセット種類とのリレーションシップ
 * 
 * @author nakaG
 * 
 */
@SuppressWarnings("serial")
public class Entity2SubsetTypeRelationship extends AbstractConnectionModel
		implements IdentifierChangeListener {

	/**
	 * コンストラクタ
	 * 
	 * @param source
	 *            サブセット作成元モデル
	 */
	public Entity2SubsetTypeRelationship(AbstractEntityModel source) {
		setSource(source);
		setTarget(new SubsetType());
	}

	/**
	 * @return the partitionAttributeName
	 */
	public IAttribute getPartitionAttribute() {
		return ((SubsetType) getTarget()).getPartitionAttribute();
	}

	public String getPartitionAttributeName() {
		IAttribute partitionAttribute = getPartitionAttribute();
		if (partitionAttribute == null) {
			return "";
		}
		if (isExceptNull()) {
			return "NULL(" + partitionAttribute.getName() + ")"; //$NON-NLS-1$ //$NON-NLS-2$
		}
		return partitionAttribute.getName();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.tmdmaker.core.model.IdentifierChangeListener#identifierChanged()
	 */
	@Override
	public void identifierChanged() {
		for (AbstractConnectionModel con : getTarget().getModelSourceConnections()) {
			if (con instanceof IdentifierChangeListener) {
				((IdentifierChangeListener) con).identifierChanged();
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.tmdmaker.core.model.AbstractConnectionModel#isDeletable()
	 */
	@Override
	public boolean isDeletable() {
		return true;
	}

	/**
	 * @return the exceptNull
	 */
	public boolean isExceptNull() {
		return ((SubsetType) getTarget()).isExceptNull();
	}

	/**
	 * 区分コード変更時処理
	 */
	public void firePartitionChanged() {
		firePropertyChange(SubsetType.PROPERTY_PARTITION, null, null);
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
		StringBuilder builder = new StringBuilder();
		boolean first = true;
		for (AbstractConnectionModel c : getTarget().getModelSourceConnections()) {
			if (first) {
				first = false;
			} else {
				builder.append(',');
			}
			builder.append(c.getTarget().getName());
		}
		return builder.toString();
	}

	/**
	 * 
	 * {@inheritDoc}
	 *
	 * @see org.tmdmaker.core.model.AbstractConnectionModel#connect()
	 */
	@Override
	public void connect() {
		super.connect();
		Diagram diagram = getSuperset().getDiagram();
		if (diagram != null) {
			diagram.addChild(getSubsetType());
		}
	}

	/**
	 * 
	 * {@inheritDoc}
	 *
	 * @see org.tmdmaker.core.model.AbstractConnectionModel#disconnect()
	 */
	@Override
	public void disconnect() {
		Diagram diagram = getSubsetType().getSuperset().getDiagram();
		if (diagram != null) {
			diagram.removeChild(getSubsetType());
		}
		super.disconnect();
	}

	/**
	 * 
	 * {@inheritDoc}
	 *
	 * @see org.tmdmaker.core.model.ModelElement#accept(org.tmdmaker.core.model.IVisitor)
	 */
	@Override
	public void accept(IVisitor visitor) {
		visitor.visit(this);
	}

	/**
	 * サブセット種別を返す.
	 * 
	 * @return サブセット種別
	 */
	public SubsetType getSubsetType() {
		return (SubsetType) getTarget();
	}

	/**
	 * スーパセットを返す.
	 * 
	 * @return スーパーセット
	 */
	private AbstractEntityModel getSuperset() {
		return (AbstractEntityModel) getSource();
	}
}

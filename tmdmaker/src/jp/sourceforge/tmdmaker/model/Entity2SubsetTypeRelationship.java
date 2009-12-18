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

/**
 * エンティティ系モデルとサブセット種類とのリレーションシップ
 * 
 * @author nakaG
 * 
 */
@SuppressWarnings("serial")
public class Entity2SubsetTypeRelationship extends AbstractConnectionModel
		implements IdentifierChangeListener {
	/** 区分コードプロパティ定数 */
	public static final String PROPERTY_PARTITION = "_property_partition";

	/**
	 * コンストラクタ
	 * 
	 * @param source
	 *            サブセット作成元モデル
	 * @param target
	 *            サブセット種類
	 */
	public Entity2SubsetTypeRelationship(AbstractEntityModel source,
			ConnectableElement target) {
		setSource(source);
		setTarget(target);
	}

	/**
	 * @return the partitionAttributeName
	 */
	public Attribute getPartitionAttribute() {
		return ((SubsetType) getTarget()).getPartitionAttribute();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.IdentifierChangeListener#identifierChanged()
	 */
	@Override
	public void identifierChanged() {
		for (AbstractConnectionModel con : getTarget()
				.getModelSourceConnections()) {
			if (con instanceof IdentifierChangeListener) {
				((IdentifierChangeListener) con).identifierChanged();
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.AbstractConnectionModel#isDeletable()
	 */
	@Override
	public boolean isDeletable() {
		// TODO Auto-generated method stub
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
		firePropertyChange(PROPERTY_PARTITION, null, null);
	}
}

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
package org.tmdmaker.core.model;

import org.tmdmaker.core.model.parts.ModelName;

/**
 * サブセット種類とサブセットとのリレーションシップ
 * 
 * @author nakaG
 * 
 */
@SuppressWarnings("serial")
public class SubsetType2SubsetRelationship extends RelatedRelationship
		implements IdentifierChangeListener {

	/**
	 * コンストラクタ.
	 * 
	 * @param subsetType
	 *            サブセット種類
	 * @param subsetName
	 *            サブセット名.
	 */
	public SubsetType2SubsetRelationship(SubsetType subsetType, ModelName subsetName) {
		super(subsetType, SubsetEntity.build(subsetType.getSuperset(), subsetName));
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
		Diagram diagram = getSubsetType().getSuperset().getDiagram();
		if (diagram != null) {
			diagram.addChild(getSubsetEntity());
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
			diagram.removeChild(getSubsetEntity());
		}
		super.disconnect();
	}

	/**
	 * 接続元のサブセット種別を返す.
	 * 
	 * @return サブセット種別
	 */
	public SubsetType getSubsetType() {
		return (SubsetType) getSource();
	}

	/**
	 * 接続先のサブセットを返す.
	 * 
	 * @return サブセット
	 */
	public SubsetEntity getSubsetEntity() {
		return (SubsetEntity) getTarget();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.tmdmaker.core.model.IdentifierChangeListener#identifierChanged()
	 */
	@Override
	public void identifierChanged() {
		SubsetEntity subset = (SubsetEntity) getTarget();
		subset.setOriginalReusedIdentifier(getOriginal().createReusedIdentifier());
		subset.fireIdentifierChanged(this);
	}

	private AbstractEntityModel getOriginal() {
		return (AbstractEntityModel) getSource().getModelTargetConnections().get(0).getSource();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.tmdmaker.core.model.RelatedRelationship#getSourceName()
	 */
	@Override
	public String getSourceName() {
		return getOriginal().getName();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.tmdmaker.core.model.RelatedRelationship#getTargetName()
	 */
	@Override
	public String getTargetName() {
		return getTarget().getName();
	}

}

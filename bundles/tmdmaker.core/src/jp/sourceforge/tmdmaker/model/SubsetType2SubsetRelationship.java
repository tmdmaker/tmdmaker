/*
 * Copyright 2009-2011 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
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
 * サブセット種類とサブセットとのリレーションシップ
 * 
 * @author nakaG
 * 
 */
@SuppressWarnings("serial")
public class SubsetType2SubsetRelationship extends RelatedRelationship
		implements IdentifierChangeListener {
	/**
	 * コンストラクタ
	 * 
	 * @param source
	 *            接続元
	 * @param target
	 *            接続先
	 */
	public SubsetType2SubsetRelationship(ConnectableElement source,
			ConnectableElement target) {
		super(source, target);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.IdentifierChangeListener#identifierChanged()
	 */
	@Override
	public void identifierChanged() {
		SubsetEntity subset = (SubsetEntity) getTarget();
		subset.setOriginalReusedIdentifier(getOriginal().createReusedIdentifier());
		subset.fireIdentifierChanged(this);
	}

	private AbstractEntityModel getOriginal() {
		return (AbstractEntityModel) getSource().getModelTargetConnections()
				.get(0).getSource();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.RelatedRelationship#getSourceName()
	 */
	@Override
	public String getSourceName() {
		return getOriginal().getName();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.RelatedRelationship#getTargetName()
	 */
	@Override
	public String getTargetName() {
		return getTarget().getName();
	}

}

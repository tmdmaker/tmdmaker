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
 * エンティティ系モデルとみなしスーパーセット種類とのリレーションシップ
 * 
 * @author nakaG
 * 
 */
@SuppressWarnings("serial")
public class Entity2VirtualSupersetTypeRelationship extends RelatedRelationship
		implements IdentifierChangeListener {
	/** 移送先から削除したReused */
	private ReusedIdentifier targetIdentifier;

	/**
	 * コンストラクタ
	 * 
	 * @param source
	 *            エンティティ系モデル
	 * @param target
	 *            みなしスーパーセット種類
	 */
	public Entity2VirtualSupersetTypeRelationship(AbstractEntityModel source,
			ConnectableElement target) {
		super(source, target);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.IdentifierChangeListener#identifierChanged()
	 */
	@Override
	public void identifierChanged() {
		((AbstractEntityModel) getTarget().getModelSourceConnections().get(0)
				.getTarget()).fireIdentifierChanged(this);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.AbstractConnectionModel#attachTarget()
	 */
	@Override
	public void attachTarget() {
		super.attachTarget();
		if (targetIdentifier == null) {
			((VirtualSupersetType) getTarget())
					.addReusedIdentifier((AbstractEntityModel) getSource());
		} else {
			((VirtualSupersetType) getTarget()).addReusedIdentifier(
					(AbstractEntityModel) getSource(), targetIdentifier);
			targetIdentifier = null;
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.AbstractConnectionModel#detachTarget()
	 */
	@Override
	public void detachTarget() {
		targetIdentifier = ((VirtualSupersetType) getTarget())
				.removeReusedIdentifier((AbstractEntityModel) getSource());
		super.detachTarget();
	}
	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.RelatedRelationship#getSourceName()
	 */
	@Override
	public String getSourceName() {
		// 各エンティティ -> スーパーセットタイプ -> スーパーセットの順で接続しているため表示順序が逆になっている
		return getTarget().getModelSourceConnections().get(0).getTarget().getName();
	}
	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.RelatedRelationship#getTargetName()
	 */
	@Override
	public String getTargetName() {
		return getSource().getName();
	}

	@Override
	public void accept(IVisitor visitor) {
		visitor.visit(this);
	}
}

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

import org.tmdmaker.core.model.SubsetType.SubsetTypeValue;
import org.tmdmaker.core.model.parts.ModelName;
import org.tmdmaker.core.model.rule.ImplementRule;

/**
 * サブセット
 * 
 * @author nakaG
 * 
 */
@SuppressWarnings("serial")
public class SubsetEntity extends AbstractEntityModel {
	/** サブセットの親のRe-usedキー */
	private ReusedIdentifier originalReusedIdentifier;

	/**
	 * コンストラクタは公開しない.
	 */
	protected SubsetEntity() {
		super();
	}

	/**
	 * サブセット生成.
	 * 
	 * 外部から生成させない.
	 * 
	 * @param parent
	 *            親モデル
	 * @param subsetName
	 *            サブセット名
	 * @return サブセット
	 */
	protected static SubsetEntity build(AbstractEntityModel parent, ModelName subsetName) {
		SubsetEntity subsetEntity = new SubsetEntity();
		subsetEntity.setName(subsetName.getValue());
		subsetEntity.setOriginalReusedIdentifier(parent.createReusedIdentifier());
		subsetEntity.setEntityType(parent.getEntityType());
		ImplementRule.setModelDefaultValue(subsetEntity);
		return subsetEntity;
	}

	/**
	 * @return the originalReusedIdentifier
	 */
	public ReusedIdentifier getOriginalReusedIdentifier() {
		return originalReusedIdentifier;
	}

	/**
	 * @param originalReusedIdentifier
	 *            the originalReusedIdentifier to set
	 */
	public void setOriginalReusedIdentifier(ReusedIdentifier originalReusedIdentifier) {
		this.originalReusedIdentifier = originalReusedIdentifier;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.tmdmaker.core.model.AbstractEntityModel#createReusedIdentifier()
	 */
	@Override
	public ReusedIdentifier createReusedIdentifier() {
		ReusedIdentifier returnValue = new ReusedIdentifier(keyModels.getSurrogateKey());
		returnValue.addAll(this.originalReusedIdentifier.getIdentifiers());

		return returnValue;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.tmdmaker.core.model.AbstractEntityModel#isEntityTypeEditable()
	 */
	@Override
	public boolean isEntityTypeEditable() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.tmdmaker.core.model.AbstractEntityModel#isDeletable()
	 */
	@Override
	public boolean isDeletable() {
		return getModelTargetConnections().size() == 1 && getModelSourceConnections().isEmpty();
	}

	/**
	 * サブセット元がエンティティか？
	 * 
	 * @return サブセット元がエンティティの場合にtrueを返す。
	 */
	public boolean isSupersetAnEntity() {
		return this.originalReusedIdentifier.getIdentifiers().size() == 1;
	}

	/**
	 * 同一のサブセットかを判定する。
	 * 
	 * @return 同一のサブセットの場合にtrueを返す。
	 */
	public boolean isSameSubset() {
		SubsetType type = getSubsetType();

		if (type == null) {
			return false;
		}
		return type.getSubsetType().equals(SubsetTypeValue.SAME);
	}

	private SubsetType getSubsetType() {
		if (getModelTargetConnections().isEmpty()) {
			return null;
		}
		SubsetType2SubsetRelationship r = getSubsetTypeRelationship();
		if (r != null) {
			return (SubsetType) r.getSource();
		}
		return null;
	}

	/**
	 * サブセット種類とのリレーションシップを返す.
	 * 
	 * @return サブセット種類とのリレーションシップ
	 */
	public SubsetType2SubsetRelationship getSubsetTypeRelationship() {
		return (SubsetType2SubsetRelationship) getModelTargetConnections().get(0);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.tmdmaker.core.model.AbstractEntityModel#getCopy()
	 */
	@Override
	public SubsetEntity getCopy() {
		SubsetEntity copy = new SubsetEntity();
		copyTo(copy);
		return copy;
	}

	/**
	 * サブセットの派生元（スーパーセット）を取得する
	 * 
	 * @return 派生元モデル（スーパーセット）
	 */
	public AbstractEntityModel getSuperset() {
		SubsetType type = getSubsetTypeRelationship().getSubsetType();
		return type.getSuperset();
	}

	/**
	 * 
	 * {@inheritDoc}
	 *
	 * @see org.tmdmaker.core.model.AbstractEntityModel#accept(org.tmdmaker.core.model.IVisitor)
	 */
	@Override
	public void accept(IVisitor visitor) {
		visitor.visit(this);
	}

	/**
	 * 
	 * {@inheritDoc}
	 *
	 * @see org.tmdmaker.core.model.ConnectableElement#hasRelationship()
	 */
	@Override
	public boolean hasRelationship() {
		return !getModelSourceConnections().isEmpty() || getModelTargetConnections().size() > 1;
	}
}

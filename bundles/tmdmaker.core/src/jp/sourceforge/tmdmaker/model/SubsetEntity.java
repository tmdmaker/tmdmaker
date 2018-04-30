/*
 * Copyright 2009-2016 TMD-Maker Project <http://tmdmaker.osdn.jp/>
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

import jp.sourceforge.tmdmaker.model.SubsetType.SubsetTypeValue;

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
	 * @see jp.sourceforge.tmdmaker.model.AbstractEntityModel#createReusedIdentifier()
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
	 * @see jp.sourceforge.tmdmaker.model.AbstractEntityModel#isEntityTypeEditable()
	 */
	@Override
	public boolean isEntityTypeEditable() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.AbstractEntityModel#isDeletable()
	 */
	@Override
	public boolean isDeletable() {
		return getModelTargetConnections().size() == 1 && getModelSourceConnections().size() == 0;
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
		if (getModelTargetConnections().size() == 0) {
			return false;
		}
		SubsetType2SubsetRelationship r = (SubsetType2SubsetRelationship) getModelTargetConnections()
				.get(0);
		if (r != null) {
			SubsetType type = (SubsetType) r.getSource();
			return type.getSubsetType().equals(SubsetTypeValue.SAME);
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.AbstractEntityModel#getCopy()
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
		SubsetType type = (SubsetType) getModelTargetConnections().get(0).getSource();
		return (AbstractEntityModel) type.getModelTargetConnections().get(0).getSource();
	}

	@Override
	public void accept(IVisitor visitor) {
		visitor.visit(this);
	}
}

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

import jp.sourceforge.tmdmaker.model.rule.ImplementRule;

/**
 * みなしエンティティ
 * 
 * @author nakaG
 * 
 */
@SuppressWarnings("serial")
public class VirtualEntity extends AbstractEntityModel {
	/** VEの元モデルRe-used */
	private ReusedIdentifier originalReusedIdentifier;

	/** みなしエンティティの種類 */
	private VirtualEntityType virtualEntityType = VirtualEntityType.NORMAL;

	/**
	 * コンストラクタは非公開.
	 */
	protected VirtualEntity() {
	}

	/**
	 * みなしエンティティを作成する
	 * 
	 * @param source
	 *            派生元モデル
	 * @param virtualEntityName
	 *            みなしエンティティ名
	 * @return みなしエンティティ
	 */
	protected static VirtualEntity build(AbstractEntityModel source, String virtualEntityName, VirtualEntityType type) {
		VirtualEntity ve = new VirtualEntity();
		ve.setName(virtualEntityName);
		ve.setOriginalReusedIdentifier(source.createReusedIdentifier());
		ve.setVirtualEntityType(type);
		ImplementRule.setModelDefaultValue(ve);
		return ve;
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
		firePropertyChange(PROPERTY_REUSED, null, null);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.AbstractEntityModel#isEntityTypeEditable()
	 */
	@Override
	public boolean isEntityTypeEditable() {
		return false;
	}

	/**
	 * @return the virtualEntityType
	 */
	public VirtualEntityType getVirtualEntityType() {
		return virtualEntityType;
	}

	/**
	 * @param virtualEntityType
	 *            the virtualEntityType to set
	 */
	public void setVirtualEntityType(VirtualEntityType virtualEntityType) {
		this.virtualEntityType = virtualEntityType;
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
	 * @see jp.sourceforge.tmdmaker.model.AbstractEntityModel#isDeletable()
	 */
	@Override
	public boolean isDeletable() {
		return getModelSourceConnections().size() == 0;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.AbstractEntityModel#getCopy()
	 */
	@Override
	public VirtualEntity getCopy() {
		VirtualEntity copy = new VirtualEntity();
		copyTo(copy);
		return copy;
	}

	/**
	 * 派生元のモデルを取得する
	 * 
	 * @return 派生元モデル
	 */
	public AbstractEntityModel getRealEntity() {
		return (AbstractEntityModel) getModelTargetConnections().get(0).getSource();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.AbstractEntityModel#copyTo(jp.sourceforge.tmdmaker.model.AbstractEntityModel)
	 */
	@Override
	public void copyTo(AbstractEntityModel to) {
		if (to instanceof VirtualEntity) {
			((VirtualEntity) to).setVirtualEntityType(getVirtualEntityType());
		}
		super.copyTo(to);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.model.AbstractEntityModel#accept(jp.sourceforge.tmdmaker.model.IVisitor)
	 */
	@Override
	public void accept(IVisitor visitor) {
		visitor.visit(this);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.model.AbstractEntityModel#canCreateSubset()
	 */
	@Override
	public boolean canCreateSubset() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.model.AbstractEntityModel#canCreateMultivalueOr()
	 */
	@Override
	public boolean canCreateMultivalueOr() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.model.AbstractEntityModel#canCreateVirtualEntity()
	 */
	@Override
	public boolean canCreateVirtualEntity() {
		return false;
	}
}

/*
 * Copyright 2009-2019 TMD-Maker Project <https://tmdmaker.osdn.jp/>
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
 * 多値のANDの概念的スーパーセット
 * 
 * @author nakaG
 * 
 */
@SuppressWarnings("serial")
public class MultivalueAndSuperset extends AbstractEntityModel {
	/** DTL */
	private Detail detail;

	/**
	 * コンストラクタは非公開.
	 */
	protected MultivalueAndSuperset() {
		super();
	}

	/**
	 * 多値のANDのSupersetを作成する。
	 * 
	 * @param header
	 *            派生元のモデル
	 * @return 多値のANDのSuperset
	 */
	protected static MultivalueAndSuperset build(AbstractEntityModel header) {
		MultivalueAndSuperset superset = new MultivalueAndSuperset();
		superset.setEntityType(header.getEntityType());
		superset.setName(header.getName());
		superset.addReusedIdentifier(header);

		return superset;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isNotImplement() {
		return true;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.tmdmaker.core.model.AbstractEntityModel#isEntityTypeEditable()
	 */
	@Override
	public boolean isEntityTypeEditable() {
		return false;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.tmdmaker.core.model.AbstractEntityModel#createReusedIdentifier()
	 */
	@Override
	public ReusedIdentifier createReusedIdentifier() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.tmdmaker.core.model.AbstractEntityModel#isDeletable()
	 */
	@Override
	public boolean isDeletable() {
		return detail.isDeletable() && getModelSourceConnections().size() == 1;
	}

	/**
	 * @return the detail
	 */
	public Detail getDetail() {
		return detail;
	}

	/**
	 * @param detail
	 *            the detail to set
	 */
	public void setDetail(Detail detail) {
		this.detail = detail;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.tmdmaker.core.model.AbstractEntityModel#getCopy()
	 */
	@Override
	public MultivalueAndSuperset getCopy() {
		MultivalueAndSuperset copy = new MultivalueAndSuperset();
		copyTo(copy);
		return copy;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.tmdmaker.core.model.AbstractEntityModel#accept(org.tmdmaker.core.model.IVisitor)
	 */
	@Override
	public void accept(IVisitor visitor) {
		visitor.visit(this);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.tmdmaker.core.model.AbstractEntityModel#canCreateSubset()
	 */
	@Override
	public boolean canCreateSubset() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.tmdmaker.core.model.AbstractEntityModel#canCreateMultivalueOr()
	 */
	@Override
	public boolean canCreateMultivalueOr() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.tmdmaker.core.model.AbstractEntityModel#canCreateVirtualEntity()
	 */
	@Override
	public boolean canCreateVirtualEntity() {
		return false;
	}
}

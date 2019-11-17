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
package org.tmdmaker.core.model.other;

import java.util.Map;

import org.tmdmaker.core.model.AbstractEntityModel;
import org.tmdmaker.core.model.EntityType;
import org.tmdmaker.core.model.IVisitor;
import org.tmdmaker.core.model.ReusedIdentifier;

/**
 * ターボファイル
 *
 * @author nakag
 *
 */
@SuppressWarnings("serial")
public class TurboFile extends AbstractEntityModel {

	/**
	 * {@inheritDoc}
	 *
	 * @see org.tmdmaker.core.model.AbstractEntityModel#createReusedIdentifier()
	 */
	@Override
	public ReusedIdentifier createReusedIdentifier() {
		ReusedIdentifier returnValue = new ReusedIdentifier(keyModels.getSurrogateKey());
		for (Map.Entry<AbstractEntityModel, ReusedIdentifier> rk : this.reusedIdentifiers
				.entrySet()) {
			returnValue.addAll(rk.getValue().getIdentifiers());
		}
		return returnValue;	}

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
		return true;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.tmdmaker.core.model.AbstractEntityModel#getCopy()
	 */
	@Override
	public AbstractEntityModel getCopy() {
		TurboFile turbo = new TurboFile();
		copyTo(turbo);
		return turbo;
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
	 * @see org.tmdmaker.core.model.AbstractEntityModel#getEntityType()
	 */
	@Override
	public EntityType getEntityType() {
		return EntityType.TURBO;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.tmdmaker.core.model.ModelElement#canCreateVirtualEntity()
	 */
	@Override
	public boolean canCreateVirtualEntity() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.tmdmaker.core.model.ModelElement#canCreateMultivalueOr()
	 */
	@Override
	public boolean canCreateMultivalueOr() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.tmdmaker.core.model.ModelElement#canCreateSubset()
	 */
	@Override
	public boolean canCreateSubset() {
		return false;
	}
}

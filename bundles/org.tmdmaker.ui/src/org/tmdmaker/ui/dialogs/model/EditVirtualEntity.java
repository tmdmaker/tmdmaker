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
package org.tmdmaker.ui.dialogs.model;

import org.tmdmaker.core.model.AbstractEntityModel;
import org.tmdmaker.core.model.VirtualEntity;
import org.tmdmaker.core.model.VirtualEntityType;

/**
 * みなしエンティティの編集用
 * 
 * @author nakaG
 * 
 */
public class EditVirtualEntity extends EditTable {
	/** みなしエンティティの種類 */
	private VirtualEntityType virtualEntityType;

	/**
	 * コンストラクタ
	 * 
	 * @param model
	 *            編集対象のみなしエンティティ
	 */
	public EditVirtualEntity(VirtualEntity model) {
		super(model);
		this.virtualEntityType = model.getVirtualEntityType();
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
	 * {@inheritDoc}
	 * 
	 * @see org.tmdmaker.ui.dialogs.model.EditTable#copySpecialTo(org.tmdmaker.core.model.AbstractEntityModel)
	 */
	@Override
	protected void copySpecialTo(AbstractEntityModel to) {
		VirtualEntity ve = (VirtualEntity) to;
		ve.setVirtualEntityType(virtualEntityType);
	}

}

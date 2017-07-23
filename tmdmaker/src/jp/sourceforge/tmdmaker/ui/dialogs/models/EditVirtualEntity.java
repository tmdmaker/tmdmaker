/*
 * Copyright 2009-2012 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
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
package jp.sourceforge.tmdmaker.ui.dialogs.models;

import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.VirtualEntity;
import jp.sourceforge.tmdmaker.model.VirtualEntityType;

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
	 * @see jp.sourceforge.tmdmaker.ui.dialogs.models.EditTable#copySpecialTo(jp.sourceforge.tmdmaker.model.AbstractEntityModel)
	 */
	@Override
	protected void copySpecialTo(AbstractEntityModel to) {
		VirtualEntity ve = (VirtualEntity) to;
		ve.setVirtualEntityType(virtualEntityType);
	}

}

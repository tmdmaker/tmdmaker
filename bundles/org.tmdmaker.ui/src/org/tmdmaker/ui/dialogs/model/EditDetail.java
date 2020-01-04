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
import org.tmdmaker.core.model.Detail;
import org.tmdmaker.core.model.Identifier;

/**
 * エンティティの編集用
 * 
 * @author nakaG
 * 
 */
public class EditDetail extends EditEntity {
	
	private boolean editedIsDetailIdentifierEnabled;

	/**
	 * コンストラクタ
	 * 
	 * @param entity
	 *            編集対象のエンティティ
	 */
	public EditDetail(Detail entity) {
		super(entity);
		editedIsDetailIdentifierEnabled = entity.isDetailIdentifierEnabled();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.tmdmaker.ui.dialogs.model.EditTable#copySpecialTo(org.tmdmaker.core.model.AbstractEntityModel)
	 */
	@Override
	protected void copySpecialTo(AbstractEntityModel to) {
		Detail edited = (Detail) to;
		Identifier newIdentifier = new Identifier();
		getEditIdentifier().copyTo(newIdentifier);
		edited.setDetailIdentifier(newIdentifier);
		edited.setEntityType(getType());
		edited.setDetailIdentifierEnabled(isDetailIdentifierEnabled());
	}

	public boolean isDetailIdentifierEnabled() {
		return editedIsDetailIdentifierEnabled;
	}

	public boolean canDisableDetailIdentifierEnabled() {
		return ((Detail)this.entity).canDisableDetailIdentifierEnabled();
	}

	public void setDetailIdentifierEnabled(boolean enabled)
	{
		editedIsDetailIdentifierEnabled = enabled;
	}
}

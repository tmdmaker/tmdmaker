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
package org.tmdmaker.ui.dialogs.model;

import org.tmdmaker.core.model.AbstractEntityModel;
import org.tmdmaker.core.model.Detail;
import org.tmdmaker.core.model.Entity;
import org.tmdmaker.core.model.EntityType;
import org.tmdmaker.core.model.Identifier;
import org.tmdmaker.core.model.Laputa;
import org.tmdmaker.core.model.parts.ModelName;

/**
 * エンティティの編集用
 * 
 * @author nakaG
 * 
 */
public class EditEntity extends EditTable {
	public static final String PROPERTY_IDENTIFIER = "_edit_property_identifier"; //$NON-NLS-1$
	public static final String PROPERTY_UP_IDENTIFIER = "_edit_property_up_identifier"; //$NON-NLS-1$
	/** 編集対象の個体指定子 */
	protected EditAttribute editIdentifier;
	private boolean latuta;

	/**
	 * コンストラクタ
	 * 
	 * @param entity
	 *            編集対象のエンティティ
	 */
	public EditEntity(Entity entity) {
		super(entity);
		this.editIdentifier = new EditAttribute(entity.getIdentifier());
	}

	public EditEntity(Detail entity) {
		super(entity);
		this.editIdentifier = new EditAttribute(entity.getDetailIdentifier());
	}

	public EditEntity(Laputa entity) {
		super(entity);
		this.editIdentifier = new EditAttribute(entity.getIdentifier());
		this.latuta = true;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.tmdmaker.ui.dialogs.model.EditTable#uptoIdentifier(int)
	 */
	@Override
	public void uptoIdentifier(int index) {
		EditAttribute toIdentifier = attributes.get(index);

		EditAttribute tempIdentifier = new EditAttribute();
		editIdentifier.copyTo(tempIdentifier);
		toIdentifier.copyTo(editIdentifier);
		tempIdentifier.copyTo(toIdentifier);
		firePropertyChange(PROPERTY_UP_IDENTIFIER, tempIdentifier, toIdentifier);
	}

	/**
	 * @return the editIdentifier;
	 */
	public EditAttribute getEditIdentifier() {
		return editIdentifier;
	}

	/**
	 * 編集用の個体指定子を更新する
	 * 
	 * @param identifier
	 *            更新後の個体指定子の値
	 */
	public void updateEditIdentifier(EditAttribute identifier) {
		String oldIndentifierName = editIdentifier.getName();
		identifier.copyTo(editIdentifier);
		firePropertyChange(PROPERTY_IDENTIFIER, oldIndentifierName, editIdentifier);
	}

	/**
	 * 個体指定子名称を設定する
	 * 
	 * @param name
	 *            個体指定子名称
	 */
	public void setIdentifierName(String name) {
		String oldIndentifierName = editIdentifier.getName();
		editIdentifier.setName(name);
		firePropertyChange(PROPERTY_IDENTIFIER, oldIndentifierName, editIdentifier);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.tmdmaker.ui.dialogs.model.EditTable#canUpToIdentifier()
	 */
	@Override
	public boolean canUpToIdentifier() {
		return true;
	}

	/**
	 * 
	 * @param editIdentifier
	 */
	protected void setEditIdentifier(EditAttribute editIdentifier) {
		this.editIdentifier = editIdentifier;
	}

	public boolean isLaputa() {
		return latuta;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.tmdmaker.ui.dialogs.model.EditTable#isValid()
	 */
	@Override
	public boolean isValid() {
		return super.isValid() && this.getEditIdentifier().getName().length() > 0;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.tmdmaker.ui.dialogs.model.EditTable#copySpecialTo(org.tmdmaker.core.model.AbstractEntityModel)
	 */
	@Override
	protected void copySpecialTo(AbstractEntityModel to) {
		if (!isLaputa()) {
			Entity edited = (Entity) to;
			Identifier newIdentifier = new Identifier();
			getEditIdentifier().copyTo(newIdentifier);
			edited.setIdentifier(newIdentifier);
		}
	}

	private EditAttribute findAttributeByName(String attributeName) {
		if (attributeName == null || attributeName.length() == 0) {
			return null;
		}
		for (EditAttribute ea : getAttributes()) {
			if (attributeName.equals(ea.getName())) {
				return ea;
			}
		}
		return null;
	}

	public void updateTypeAttribute(String oldEntityName, String newEntityName) {
		String oldAttributeName = null;
		String newAttribteName = null;
		if (isEvent()) {
			oldAttributeName = Entity.getDefaultEventAttributeName(oldEntityName);
			newAttribteName = Entity.getDefaultEventAttributeName(newEntityName);
		} else {
			oldAttributeName = Entity.getDefaultResourceAttributeName(oldEntityName);
			newAttribteName = Entity.getDefaultResourceAttributeName(newEntityName);
		}

		EditAttribute ea = findAttributeByName(oldAttributeName);
		if (ea != null) {
			ea.setName(newAttribteName);
			firePropertyChange(PROPERTY_ATTRIBUTES, null, ea);
		}
	}

	private ModelName createEntityNameFromIdentifier() {
		return new Identifier(editIdentifier.getName()).createEntityName();
	}

	public boolean hasAutoCreateEntityName() {
		ModelName autoCreationName = createEntityNameFromIdentifier();
		return autoCreationName.equals(new ModelName(name));
	}

	public String generateEntityNameFromIdentifier() {
		return createEntityNameFromIdentifier().getValue();
	}

	private boolean isEvent() {
		return getType().equals(EntityType.EVENT);
	}
}

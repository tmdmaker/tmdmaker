/*
 * Copyright 2009-2019 TMD-Maker Project <https://www.tmdmaker.org/>
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
package org.tmdmaker.ui.views.properties.gef3;

import org.eclipse.gef.commands.CommandStack;
import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;
import org.tmdmaker.ui.Messages;

import jp.sourceforge.tmdmaker.model.Entity;
import jp.sourceforge.tmdmaker.model.EntityType;

/**
 * Entity property source.
 *
 */
public class EntityPropertySource extends AbstractEntityModelPropertySource {
	private static final String IDENTIFIER_NAME = "identifierName"; //$NON-NLS-1$
	private static final String[] ENTITY_TYPE_LABELS = { Messages.Resource, Messages.Event };
	private static final int ENTITY_TYPE_RESOURCE_INDEX = 0;
	private static final int ENTITY_TYPE_EVENT_INDEX = 1;

	public EntityPropertySource(CommandStack commandStack, Entity model) {
		super(commandStack, model);
	}

	@Override
	protected IPropertyDescriptor[] buildPropertyDescriptors() {
		IPropertyDescriptor entityTypeDescriptor = null;
		if (this.model.isEntityTypeEditable()) {
			entityTypeDescriptor = new ComboBoxPropertyDescriptor(ENTITY_TYPE, Messages.EntityType, ENTITY_TYPE_LABELS);
		} else {
			entityTypeDescriptor = new PropertyDescriptor(ENTITY_TYPE, Messages.EntityType);
		}
		return new IPropertyDescriptor[] { new TextPropertyDescriptor(NAME, Messages.ModelName),
				new TextPropertyDescriptor(IMPLEMENT_NAME, Messages.ImplementationName),
				new TextPropertyDescriptor(IDENTIFIER_NAME, Messages.Identifier), entityTypeDescriptor,
				new ComboBoxPropertyDescriptor(IMPLEMENT, Messages.Implementation, IMPLEMENT_LABELS), };
	}

	@Override
	public Object getPropertyValue(Object id) {
		if (id.equals(IDENTIFIER_NAME)) {
			return this.getModel().getIdentifier().getName();
		}
		return super.getPropertyValue(id.toString());
	}

	@Override
	protected Object getEntityType() {
		if (this.model.isEntityTypeEditable()) {
			return this.entityTypeToIndex();
		}
		return this.getEntityTypeName();
	}

	private String getEntityTypeName() {
		if (this.getModel().isEvent()) {
			return Messages.Event;
		}
		return Messages.Resource;
	}

	private int entityTypeToIndex() {
		if (this.getModel().isEvent()) {
			return ENTITY_TYPE_EVENT_INDEX;
		}
		return ENTITY_TYPE_RESOURCE_INDEX;
	}

	private boolean isResourceIndex(Object value) {
		return ((Integer) value) == ENTITY_TYPE_RESOURCE_INDEX;
	}

	@Override
	protected Object handleSetNewPropertyValue(String propertyName, Object oldValue, Object newValue) {
		if (propertyName.equals(ENTITY_TYPE)) {
			return this.isResourceIndex(newValue) ? EntityType.RESOURCE : EntityType.EVENT;
		}
		return super.handleSetNewPropertyValue(propertyName, oldValue, newValue);
	}

	@Override
	protected Object handleOldPropertyValue(String propertyName, Object oldValue, Object newValue) {
		if (propertyName.equals(IDENTIFIER_NAME)) {
			return this.getModel().getIdentifier().getName();
		}
		return super.handleOldPropertyValue(propertyName, oldValue, newValue);
	}

	private Entity getModel() {
		return (Entity) this.model;
	}
}

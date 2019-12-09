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
package org.tmdmaker.ui.views.properties.gef3;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;
import org.tmdmaker.core.model.AbstractEntityModel;
import org.tmdmaker.core.model.CombinationTable;
import org.tmdmaker.core.model.Detail;
import org.tmdmaker.core.model.Laputa;
import org.tmdmaker.core.model.MappingList;
import org.tmdmaker.core.model.MultivalueOrEntity;
import org.tmdmaker.core.model.RecursiveTable;
import org.tmdmaker.core.model.SubsetEntity;
import org.tmdmaker.core.model.VirtualEntity;
import org.tmdmaker.ui.Messages;
import org.tmdmaker.ui.views.properties.gef3.commands.PropertyChangeCommand;

/**
 * Abstract class of entity model's property source.
 */
public class AbstractEntityModelPropertySource extends AbstractPropertySource {
	protected static final String[] IMPLEMENT_LABELS = { Messages.Implementation, Messages.NotToImplement };
	protected static final int IMPLEMENTATION_INDEX = 0;
	protected static final int NOT_IMPLEMEN_INDEX = 1;

	protected static final String IMPLEMENT = "notImplement"; //$NON-NLS-1$
	protected static final String ENTITY_TYPE = "entityType"; //$NON-NLS-1$
	protected static final String IMPLEMENT_NAME = "implementName"; //$NON-NLS-1$
	protected static final String NAME = "name"; //$NON-NLS-1$
	protected AbstractEntityModel model;
	protected IPropertyDescriptor[] propertyFields;

	public AbstractEntityModelPropertySource(CommandStack commandStack, AbstractEntityModel model) {
		super(commandStack);
		this.model = model;
		this.propertyFields = buildPropertyDescriptors();
	}

	@Override
	public Object getEditableValue() {
		return this.model;
	}

	protected IPropertyDescriptor[] buildPropertyDescriptors() {
		return new IPropertyDescriptor[] { new TextPropertyDescriptor(NAME, Messages.ModelName),
				new TextPropertyDescriptor(IMPLEMENT_NAME, Messages.ImplementationName),
				new PropertyDescriptor(ENTITY_TYPE, Messages.EntityType),
				new ComboBoxPropertyDescriptor(IMPLEMENT, Messages.Implementation, IMPLEMENT_LABELS), };
	}

	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() {
		return propertyFields;
	}

	@Override
	public Object getPropertyValue(Object id) {
		if (id.equals(ENTITY_TYPE)) {
			return getEntityType();
		}
		if (id.equals(IMPLEMENT)) {
			return model.isNotImplement() ? NOT_IMPLEMEN_INDEX : IMPLEMENTATION_INDEX;
		}
		return getPropertyValue(this.model, id.toString());
	}

	protected Object getEntityType() {
		if (model instanceof CombinationTable) {
			return Messages.CombinationTable;
		} else if (model instanceof SubsetEntity) {
			return Messages.Subset;
		} else if (model instanceof VirtualEntity) {
			return Messages.VirtualEntity;
		} else if (model instanceof MultivalueOrEntity) {
			return Messages.MultivalueOR;
		} else if (model instanceof Detail) {
			return Messages.MultivalueAndDetail;
		} else if (model instanceof RecursiveTable) {
			return Messages.RecursiveTable;
		} else if (model instanceof MappingList) {
			return Messages.MappingList;
		} else if (model instanceof Laputa) {
			return Messages.Laputa;
		}
		return null;
	}

	@Override
	protected Command createSetPropertyCommand(Object id, Object value) {
		String propertyName = id.toString();
		Object oldValue = getPropertyValue(this.model, propertyName);
		oldValue = handleOldPropertyValue(propertyName, oldValue, value);
		value = handleSetNewPropertyValue(propertyName, oldValue, value);
		return new PropertyChangeCommand(this.model, propertyName, oldValue, value);
	}

	protected Object handleOldPropertyValue(String propertyName, Object oldValue, Object newValue) {
		if (propertyName.equals(IMPLEMENT)) {
			return this.model.isNotImplement();
		}
		return oldValue;
	}

	protected Object handleSetNewPropertyValue(String propertyName, Object oldValue, Object newValue) {
		if (propertyName.equals(IMPLEMENT)) {
			return ((Integer) newValue) == NOT_IMPLEMEN_INDEX;
		}
		return newValue;
	}

}

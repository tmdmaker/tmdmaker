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

import org.eclipse.gef.commands.CommandStack;
import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;
import org.tmdmaker.core.model.AbstractEntityModel;
import org.tmdmaker.core.model.Detail;
import org.tmdmaker.ui.Messages;

/**
 * Detail property source.
 *
 */
public class DetailPropertySource extends AbstractEntityModelPropertySource {
	private static final String DETAIL_IDENTIFIER_NAME = "detailIdentifierName"; //$NON-NLS-1$

	public DetailPropertySource(CommandStack commandStack, AbstractEntityModel model) {
		super(commandStack, model);
	}

	@Override
	protected IPropertyDescriptor[] buildPropertyDescriptors() {

		return new IPropertyDescriptor[] { new TextPropertyDescriptor(NAME, Messages.ModelName),
				new TextPropertyDescriptor(IMPLEMENT_NAME, Messages.ImplementationName),
				new TextPropertyDescriptor(DETAIL_IDENTIFIER_NAME, Messages.Identifier),
				new PropertyDescriptor(ENTITY_TYPE, Messages.EntityType),
				new ComboBoxPropertyDescriptor(IMPLEMENT, Messages.Implementation, IMPLEMENT_LABELS), };
	}

	@Override
	public Object getPropertyValue(Object id) {
		if (id.equals(DETAIL_IDENTIFIER_NAME)) {
			return this.getModel().getDetailIdentifier().getName();
		}
		return super.getPropertyValue(id.toString());
	}

	@Override
	protected Object handleOldPropertyValue(String propertyName, Object oldValue, Object newValue) {
		if (propertyName.equals(DETAIL_IDENTIFIER_NAME)) {
			return this.getModel().getDetailIdentifier().getName();
		}
		return super.handleOldPropertyValue(propertyName, oldValue, newValue);
	}

	private Detail getModel() {
		return (Detail) this.model;
	}
}

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

import org.apache.commons.lang.ArrayUtils;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;
import org.tmdmaker.ui.Messages;
import org.tmdmaker.ui.views.properties.gef3.commands.PropertyChangeCommand;

import jp.sourceforge.tmdmaker.extension.DialectProviderFactory;
import jp.sourceforge.tmdmaker.model.Diagram;

/**
 * Diagram property source.
 *
 */
public class DiagramPropertySource extends AbstractPropertySource {

	private static final String NAME = "name"; //$NON-NLS-1$
	private static final String DATABASE_NAME = "databaseName"; //$NON-NLS-1$
	private static final String DESCRIPTION = "description"; //$NON-NLS-1$
	private Diagram diagram;
	private IPropertyDescriptor[] propertyFields;
	private String[] dataBaseList;

	public DiagramPropertySource(CommandStack commandStack, Diagram diagram) {
		super(commandStack);
		this.diagram = diagram;
		this.dataBaseList = DialectProviderFactory.getDialectProvider().getDatabaseList().toArray(new String[0]);
		this.propertyFields = buildPropertyDescriptors();
	}

	@Override
	public Object getEditableValue() {
		return this.diagram;
	}

	protected IPropertyDescriptor[] buildPropertyDescriptors() {
		return new IPropertyDescriptor[] { new TextPropertyDescriptor(NAME, Messages.ModelName),
				new ComboBoxPropertyDescriptor(DATABASE_NAME, Messages.DatabaseName, dataBaseList),
				new TextPropertyDescriptor(DESCRIPTION, Messages.Description) };
	}

	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() {
		return this.propertyFields;
	}

	@Override
	public Object getPropertyValue(Object id) {
		if (id.equals(DATABASE_NAME)) {
			return ArrayUtils.indexOf(dataBaseList, diagram.getDatabaseName());
		}
		return this.canonicalize((String) getPropertyValue(this.diagram, id.toString()));
	}

	@Override
	public void resetPropertyValue(Object id) {
		// do nothing
	}

	@Override
	protected Command createSetPropertyCommand(Object id, Object newValue) {
		String propertyName = id.toString();
		Object oldValue = getPropertyValue(this.diagram, propertyName);
		if (id.equals(DATABASE_NAME)) {
			newValue = dataBaseList[(Integer) newValue];
		}
		return new PropertyChangeCommand(this.diagram, propertyName, oldValue, newValue);
	}
}

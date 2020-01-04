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
import org.tmdmaker.core.model.KeyModel;
import org.tmdmaker.ui.Messages;
import org.tmdmaker.ui.views.properties.gef3.commands.PropertyChangeCommand;

/**
 * Keymodel property source.
 *
 */
public class KeyModelPropertySource extends AbstractPropertySource {

	private static final String UNIQUE = "unique"; //$NON-NLS-1$
	private static final String MASTER_KEY = "masterKey"; //$NON-NLS-1$
	private static final String NAME = "name"; //$NON-NLS-1$
	private static final String[] BOOLEAN_LABELS = { Messages.Yes, Messages.No };
	private static final int BOOLEAN_YES_INDEX = 0;
	private static final int BOOLEAN_NO_INDEX = 1;
	private KeyModel keymodel;

	public KeyModelPropertySource(CommandStack commandStack, KeyModel keymodel) {
		super(commandStack);
		this.keymodel = keymodel;
	}

	@Override
	public Object getEditableValue() {
		return this.keymodel;
	}

	protected IPropertyDescriptor[] buildPropertyDescriptors() {
		IPropertyDescriptor masterKeyPropertyDescriptor = null;
		if (this.keymodel.isUnique()) {
			masterKeyPropertyDescriptor = new ComboBoxPropertyDescriptor(MASTER_KEY, Messages.MasterKey,
					BOOLEAN_LABELS);
		} else {
			masterKeyPropertyDescriptor = new PropertyDescriptor(MASTER_KEY, Messages.MasterKey);
		}
		return new IPropertyDescriptor[] { new TextPropertyDescriptor(NAME, Messages.ModelName),
				new ComboBoxPropertyDescriptor(UNIQUE, Messages.UniqueConstraint, BOOLEAN_LABELS),
				masterKeyPropertyDescriptor };
	}

	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() {
		return buildPropertyDescriptors();
	}

	@Override
	public Object getPropertyValue(Object id) {
		if (id.equals(NAME)) {
			return canonicalize(keymodel.getName());
		}
		if (id.equals(UNIQUE)) {
			return keymodel.isUnique() ? BOOLEAN_YES_INDEX : BOOLEAN_NO_INDEX;
		}
		if (id.equals(MASTER_KEY)) {
			if (this.keymodel.isUnique()) {
				return keymodel.isMasterKey() ? BOOLEAN_YES_INDEX : BOOLEAN_NO_INDEX;
			}
			return keymodel.isMasterKey() ? Messages.Yes : Messages.No;
		}
		return null;
	}

	@Override
	protected Command createSetPropertyCommand(Object id, Object value) {
		String propertyName = id.toString();
		Object oldValue = getPropertyValue(this.keymodel, propertyName);
		if (propertyName.equals(UNIQUE)) {
			value = ((Integer) value) == BOOLEAN_YES_INDEX;
		}
		if (propertyName.equals(MASTER_KEY)) {
			if (this.keymodel.isUnique()) {
				value = ((Integer) value) == BOOLEAN_YES_INDEX;
			}
		}
		return new PropertyChangeCommand(this.keymodel, propertyName, oldValue, value);
	}

}

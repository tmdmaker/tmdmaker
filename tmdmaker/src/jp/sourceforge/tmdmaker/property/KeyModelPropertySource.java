/*
 * Copyright 2009-2016 TMD-Maker Project <http://tmdmaker.osdn.jp/>
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
package jp.sourceforge.tmdmaker.property;

import jp.sourceforge.tmdmaker.Messages;
import jp.sourceforge.tmdmaker.TMDEditor;
import jp.sourceforge.tmdmaker.model.KeyModel;

import org.eclipse.gef.commands.Command;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

/**
 * Keymodel property source.
 *
 */
public class KeyModelPropertySource extends AbstractPropertySource {

	private static final String IS_UNIQUE = "IsUnique"; // $NON-NLS-1$
	private static final String IS_MASTER_KEY = "IsMasterKey"; //$NON-NLS-1$
	private static final String NAME = "Name"; //$NON-NLS-1$
	private KeyModel keymodel;

	public KeyModelPropertySource(TMDEditor editor, KeyModel keymodel) {
		super(editor);
		this.keymodel = keymodel;
	}

	@Override
	public Object getEditableValue() {
		return this.keymodel;
	}

	static private IPropertyDescriptor[] propertyFields;
	static {
		propertyFields = new IPropertyDescriptor[] {
				new TextPropertyDescriptor(NAME, Messages.ModelName),
				new TextPropertyDescriptor(IS_MASTER_KEY, Messages.MasterKey),
				new TextPropertyDescriptor(IS_UNIQUE, Messages.UniqueConstraint) };
	}

	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() {
		return propertyFields;
	}

	@Override
	public Object getPropertyValue(Object id) {
		if (id.equals(NAME)) {
			return canonicalize(keymodel.getName());
		}
		if (id.equals(IS_MASTER_KEY)) {
			return keymodel.isMasterKey() ? Messages.Yes : Messages.No;
		}
		if (id.equals(IS_UNIQUE)) {
			return keymodel.isUnique() ? Messages.Yes : Messages.No;
		}
		return null;
	}

	@Override
	protected Command createSetPropertyCommand(Object id, Object value) {
		return null;
	}

}

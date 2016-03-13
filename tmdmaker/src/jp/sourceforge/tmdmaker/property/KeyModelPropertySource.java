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
				new TextPropertyDescriptor("Name", Messages.KeyModelPropertySource_0), //$NON-NLS-1$
				new TextPropertyDescriptor("IsMasterKey", Messages.KeyModelPropertySource_1), //$NON-NLS-1$
				new TextPropertyDescriptor("IsUnique", Messages.KeyModelPropertySource_2) //$NON-NLS-1$
		};
	}

	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() {
		return propertyFields;
	}

	@Override
	public Object getPropertyValue(Object id) {
		if (id.equals("Name")) { //$NON-NLS-1$
			return canonicalize(keymodel.getName());
		}
		if (id.equals("IsMasterKey")) { //$NON-NLS-1$
			return keymodel.isMasterKey() ? Messages.KeyModelPropertySource_3
					: Messages.KeyModelPropertySource_4;
		}
		if (id.equals("IsUnique")) { //$NON-NLS-1$
			return keymodel.isUnique() ? Messages.KeyModelPropertySource_5
					: Messages.KeyModelPropertySource_6;
		}
		return null;
	}

	@Override
	protected Command createSetPropertyCommand(Object id, Object value) {
		return null;
	}

}

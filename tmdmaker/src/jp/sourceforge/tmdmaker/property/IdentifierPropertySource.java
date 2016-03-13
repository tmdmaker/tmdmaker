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
import jp.sourceforge.tmdmaker.model.Identifier;

import org.eclipse.gef.commands.Command;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

/**
 * Identifier property source.
 *
 */
public class IdentifierPropertySource extends AbstractPropertySource {

	private Identifier identifier;

	public IdentifierPropertySource(TMDEditor editor, Identifier identifier) {
		super(editor);
		this.identifier = identifier;
	}

	@Override
	public Object getEditableValue() {
		return this.identifier;
	}

	static private IPropertyDescriptor[] propertyFields;
	static {
		propertyFields = new IPropertyDescriptor[] {
				new TextPropertyDescriptor("Name", Messages.IdentifierPropertySource_0), //$NON-NLS-1$
				new TextPropertyDescriptor("ImplementName", Messages.IdentifierPropertySource_1), //$NON-NLS-1$
				new TextPropertyDescriptor("DataTypeDeclaration", //$NON-NLS-1$
						Messages.IdentifierPropertySource_2),
				new TextPropertyDescriptor("Size", Messages.IdentifierPropertySource_3), //$NON-NLS-1$
				new TextPropertyDescriptor("Scale", Messages.IdentifierPropertySource_4) //$NON-NLS-1$
		};
	}

	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() {
		return propertyFields;
	}

	@Override
	public Object getPropertyValue(Object id) {
		if (id.equals("Name")) { //$NON-NLS-1$
			return canonicalize(identifier.getName());
		}
		if (id.equals("ImplementName")) { //$NON-NLS-1$
			return canonicalize(identifier.getImplementName());
		}
		if (id.equals("DataTypeDeclaration")) { //$NON-NLS-1$
			return identifier.getDataTypeDeclaration() != null
					? identifier.getDataTypeDeclaration().getLogicalType().toString() : ""; //$NON-NLS-1$
		}
		if (id.equals("Size")) { //$NON-NLS-1$
			return identifier.getDataTypeDeclaration() != null
					? identifier.getDataTypeDeclaration().getSize() : ""; //$NON-NLS-1$
		}
		if (id.equals("Scale")) { //$NON-NLS-1$
			return identifier.getDataTypeDeclaration() != null
					? identifier.getDataTypeDeclaration().getScale() : ""; //$NON-NLS-1$
		}
		return null;
	}

	@Override
	protected Command createSetPropertyCommand(Object id, Object value) {
		return null;
	}
}

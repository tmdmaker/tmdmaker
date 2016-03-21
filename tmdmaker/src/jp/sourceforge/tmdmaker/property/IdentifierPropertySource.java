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

	private static final String SCALE = "Scale"; //$NON-NLS-1$
	private static final String SIZE = "Size"; //$NON-NLS-1$
	private static final String DATA_TYPE_DECLARATION = "DataTypeDeclaration"; //$NON-NLS-1$
	private static final String IMPLEMENT_NAME = "ImplementName"; //$NON-NLS-1$
	private static final String NAME = "Name"; //$NON-NLS-1$
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
				new TextPropertyDescriptor(NAME, Messages.ModelName),
				new TextPropertyDescriptor(IMPLEMENT_NAME, Messages.ImplementationName),
				new TextPropertyDescriptor(DATA_TYPE_DECLARATION, Messages.DataType),
				new TextPropertyDescriptor(SIZE, Messages.Size),
				new TextPropertyDescriptor(SCALE, Messages.Scale) };
	}

	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() {
		return propertyFields;
	}

	@Override
	public Object getPropertyValue(Object id) {
		if (id.equals(NAME)) {
			return canonicalize(identifier.getName());
		}
		if (id.equals(IMPLEMENT_NAME)) {
			return canonicalize(identifier.getImplementName());
		}
		if (id.equals(DATA_TYPE_DECLARATION)) {
			return identifier.getDataTypeDeclaration() != null
					? identifier.getDataTypeDeclaration().getLogicalType().toString() : ""; //$NON-NLS-1$
		}
		if (id.equals(SIZE)) {
			return identifier.getDataTypeDeclaration() != null
					? identifier.getDataTypeDeclaration().getSize() : ""; //$NON-NLS-1$
		}
		if (id.equals(SCALE)) {
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

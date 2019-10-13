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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;
import org.tmdmaker.ui.Messages;
import org.tmdmaker.ui.views.properties.gef3.commands.PropertyChangeCommand;

import jp.sourceforge.tmdmaker.model.DataTypeDeclaration;
import jp.sourceforge.tmdmaker.model.IAttribute;
import jp.sourceforge.tmdmaker.model.StandardSQLDataType;

/**
 * IAttribute property source.
 *
 */
public class IAttributePropertySource extends AbstractPropertySource {

	private static final String SCALE = "scale"; //$NON-NLS-1$
	private static final String SIZE = "size"; //$NON-NLS-1$
	private static final String DATA_TYPE_DECLARATION = "dataTypeDeclaration"; //$NON-NLS-1$
	private static final String IMPLEMENT_NAME = "implementName"; //$NON-NLS-1$
	private static final String NAME = "name"; //$NON-NLS-1$
	private IAttribute attribute;

	public IAttributePropertySource(CommandStack commandStack, IAttribute attribute) {
		super(commandStack);
		this.attribute = attribute;
	}

	@Override
	public Object getEditableValue() {
		return this.attribute;
	}

	private static String[] getDataTypeDeclarationLabels() {
		List<String> labels = new ArrayList<String>();
		labels.add(""); //$NON-NLS-1$
		for (StandardSQLDataType dt : StandardSQLDataType.values()) {
			labels.add(dt.getName());
		}
		return labels.toArray(new String[labels.size()]);
	}

	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() {
		List<IPropertyDescriptor> propertyDescriptordList = new ArrayList<IPropertyDescriptor>();

		DataTypeDeclaration dtd = this.attribute.getDataTypeDeclaration();
		StandardSQLDataType dt = null;
		if (dtd != null) {
			dt = dtd.getLogicalType();
		}
		propertyDescriptordList.add(new TextPropertyDescriptor(NAME, Messages.ModelName));
		propertyDescriptordList.add(new TextPropertyDescriptor(IMPLEMENT_NAME, Messages.ImplementationName));
		ComboBoxPropertyDescriptor cbpd = new ComboBoxPropertyDescriptor(DATA_TYPE_DECLARATION, Messages.DataType,
				getDataTypeDeclarationLabels());
		propertyDescriptordList.add(cbpd);
		if (dt != null && dt.isSupportSize()) {
			propertyDescriptordList.add(new TextPropertyDescriptor(SIZE, Messages.Size));
		}
		if (dt != null && dt.isSupportScale()) {
			propertyDescriptordList.add(new TextPropertyDescriptor(SCALE, Messages.Scale));
		}
		return propertyDescriptordList.toArray(new IPropertyDescriptor[propertyDescriptordList.size()]);
	}

	@Override
	public Object getPropertyValue(Object id) {
		if (id.equals(NAME)) {
			return canonicalize(attribute.getName());
		}
		if (id.equals(IMPLEMENT_NAME)) {
			return canonicalize(attribute.getImplementName());
		}
		DataTypeDeclaration dt = this.attribute.getDataTypeDeclaration();
		if (id.equals(DATA_TYPE_DECLARATION)) {
			if (dt == null) {
				return 0;
			}
			return StandardSQLDataType.getStandardSQLDataType(dt.getLogicalType().getName()).ordinal();
		}
		if (id.equals(SIZE)) {
			if (dt == null) {
				return ""; //$NON-NLS-1$
			}
			return dt.getSize() != null ? String.valueOf(dt.getSize()) : ""; //$NON-NLS-1$
		}
		if (id.equals(SCALE)) {
			if (dt == null) {
				return ""; //$NON-NLS-1$
			}
			return dt.getScale() != null ? String.valueOf(dt.getScale()) : ""; //$NON-NLS-1$
		}
		return null;
	}

	@Override
	protected Command createSetPropertyCommand(Object id, Object value) {
		Object oldValue = null;
		Object newValue = value;
		String propertyName = (String) id;
		oldValue = getPropertyValue(this.attribute, propertyName);
		if (id.equals(DATA_TYPE_DECLARATION)) {
			propertyName = DATA_TYPE_DECLARATION;
			DataTypeDeclaration oldDt = (DataTypeDeclaration) oldValue;
			StandardSQLDataType sdt = StandardSQLDataType.values()[(Integer) newValue];
			newValue = oldDt.newLogicalType(sdt);
		}
		if (id.equals(SIZE)) {
			propertyName = DATA_TYPE_DECLARATION;
			DataTypeDeclaration oldDt = attribute.getDataTypeDeclaration();
			oldValue = oldDt;
			newValue = oldDt.newSize(Integer.valueOf((String) newValue));
		}
		if (id.equals(SCALE)) {
			propertyName = DATA_TYPE_DECLARATION;
			DataTypeDeclaration oldDt = attribute.getDataTypeDeclaration();
			oldValue = oldDt;
			newValue = oldDt.newScale(Integer.valueOf((String) newValue));
		}
		return new PropertyChangeCommand(this.attribute, propertyName, oldValue, newValue);
	}

}

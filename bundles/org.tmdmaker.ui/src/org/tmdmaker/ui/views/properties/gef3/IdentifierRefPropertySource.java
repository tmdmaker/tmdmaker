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

import org.eclipse.gef.commands.CommandStack;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;
import org.tmdmaker.core.model.DataTypeDeclaration;
import org.tmdmaker.core.model.IAttribute;
import org.tmdmaker.core.model.StandardSQLDataType;
import org.tmdmaker.ui.Messages;

/**
 * IdentifierRef property source.
 *
 */
public class IdentifierRefPropertySource extends IAttributePropertySource {


	public IdentifierRefPropertySource(CommandStack commandStack, IAttribute attribute) {
		super(commandStack, attribute);
	}

	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() {
		List<IPropertyDescriptor> propertyDescriptordList = new ArrayList<IPropertyDescriptor>();

		DataTypeDeclaration dtd = this.attribute.getDataTypeDeclaration();
		StandardSQLDataType dt = null;
		if (dtd != null) {
			dt = dtd.getLogicalType();
		}
		propertyDescriptordList.add(new PropertyDescriptor(NAME, Messages.ModelName));
		propertyDescriptordList.add(new TextPropertyDescriptor(IMPLEMENT_NAME, Messages.ImplementationName));
		propertyDescriptordList.add(new PropertyDescriptor(DATA_TYPE_DECLARATION, Messages.DataType));
		if (dt != null && dt.isSupportSize()) {
			propertyDescriptordList.add(new PropertyDescriptor(SIZE, Messages.Size));
		}
		if (dt != null && dt.isSupportScale()) {
			propertyDescriptordList.add(new PropertyDescriptor(SCALE, Messages.Scale));
		}
		return propertyDescriptordList.toArray(new IPropertyDescriptor[propertyDescriptordList.size()]);
	}

	@Override
	public Object getPropertyValue(Object id) {
		if (id.equals(DATA_TYPE_DECLARATION)) {
			DataTypeDeclaration dt = this.attribute.getDataTypeDeclaration();
			if (dt != null) {
				return dt.getLogicalType().getName();
			}
			return "";
		}
		return super.getPropertyValue(id);
	}
}

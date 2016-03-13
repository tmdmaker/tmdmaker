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

import org.eclipse.gef.commands.Command;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

import jp.sourceforge.tmdmaker.Messages;
import jp.sourceforge.tmdmaker.TMDEditor;
import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.CombinationTable;
import jp.sourceforge.tmdmaker.model.Detail;
import jp.sourceforge.tmdmaker.model.Laputa;
import jp.sourceforge.tmdmaker.model.MappingList;
import jp.sourceforge.tmdmaker.model.MultivalueOrEntity;
import jp.sourceforge.tmdmaker.model.RecursiveTable;
import jp.sourceforge.tmdmaker.model.SubsetEntity;
import jp.sourceforge.tmdmaker.model.VirtualEntity;

/**
 * Abstract class of entity model's property source.
 */
public class AbstractEntityModelPropertySource extends AbstractPropertySource {

	private AbstractEntityModel model;

	public AbstractEntityModelPropertySource(TMDEditor editor, AbstractEntityModel model) {
		super(editor);
		this.model = model;
	}

	@Override
	public Object getEditableValue() {
		return this.model;
	}

	static private IPropertyDescriptor[] propertyFields;
	static {
		propertyFields = new IPropertyDescriptor[] {
				new TextPropertyDescriptor("Name", Messages.AbstractEntityModelPropertySource_0), //$NON-NLS-1$
				new TextPropertyDescriptor("ImplementName", //$NON-NLS-1$
						Messages.AbstractEntityModelPropertySource_1),
				new TextPropertyDescriptor("EntityType", //$NON-NLS-1$
						Messages.AbstractEntityModelPropertySource_2),
				new TextPropertyDescriptor("Implement", //$NON-NLS-1$
						Messages.AbstractEntityModelPropertySource_3) };
	}

	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() {
		return propertyFields;
	}

	@Override
	public Object getPropertyValue(Object id) {
		if (id.equals("Name")) { //$NON-NLS-1$
			return canonicalize(model.getName());
		}
		if (id.equals("ImplementName")) { //$NON-NLS-1$
			return canonicalize(model.getImplementName());
		}
		if (id.equals("Implement")) { //$NON-NLS-1$
			return model.isNotImplement() ? Messages.AbstractEntityModelPropertySource_4
					: Messages.AbstractEntityModelPropertySource_5;
		}
		if (id.equals("EntityType")) { //$NON-NLS-1$
			if (model instanceof CombinationTable) {
				return Messages.AbstractEntityModelPropertySource_6;
			} else if (model instanceof SubsetEntity) {
				return Messages.AbstractEntityModelPropertySource_7;
			} else if (model instanceof VirtualEntity) {
				return Messages.AbstractEntityModelPropertySource_8;
			} else if (model instanceof MultivalueOrEntity) {
				return Messages.AbstractEntityModelPropertySource_9;
			} else if (model instanceof Detail) {
				return Messages.AbstractEntityModelPropertySource_10;
			} else if (model instanceof RecursiveTable) {
				return Messages.AbstractEntityModelPropertySource_11;
			} else if (model instanceof MappingList) {
				return Messages.AbstractEntityModelPropertySource_12;
			} else if (model instanceof Laputa) {
				return Messages.AbstractEntityModelPropertySource_13;
			}
		}
		return null;
	}

	@Override
	protected Command createSetPropertyCommand(Object id, Object value) {
		return null;
	}

}

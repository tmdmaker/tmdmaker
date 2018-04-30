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
package jp.sourceforge.tmdmaker.ui.views.properties;

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

	private static final String IMPLEMENT = "Implement"; //$NON-NLS-1$
	private static final String ENTITY_TYPE = "EntityType"; //$NON-NLS-1$
	private static final String IMPLEMENT_NAME = "ImplementName"; //$NON-NLS-1$
	private static final String NAME = "Name"; //$NON-NLS-1$
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
				new TextPropertyDescriptor(NAME, Messages.ModelName),
				new TextPropertyDescriptor(IMPLEMENT_NAME, Messages.ImplementationName),
				new TextPropertyDescriptor(ENTITY_TYPE, Messages.EntityType),
				new TextPropertyDescriptor(IMPLEMENT, Messages.Implementation) };
	}

	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() {
		return propertyFields;
	}

	@Override
	public Object getPropertyValue(Object id) {
		if (id.equals(NAME)) {
			return canonicalize(model.getName());
		}
		if (id.equals(IMPLEMENT_NAME)) {
			return canonicalize(model.getImplementName());
		}
		if (id.equals(IMPLEMENT)) {
			return model.isNotImplement() ? Messages.NotToImplement : Messages.Implementation;
		}
		if (id.equals(ENTITY_TYPE)) {
			if (model instanceof CombinationTable) {
				return Messages.CombinationTable;
			} else if (model instanceof SubsetEntity) {
				return Messages.Subset;
			} else if (model instanceof VirtualEntity) {
				return Messages.VirtualEntity;
			} else if (model instanceof MultivalueOrEntity) {
				return Messages.MultivalueOR;
			} else if (model instanceof Detail) {
				return Messages.MultivalueAndDetail;
			} else if (model instanceof RecursiveTable) {
				return Messages.RecursiveTable;
			} else if (model instanceof MappingList) {
				return Messages.MappingList;
			} else if (model instanceof Laputa) {
				return Messages.Laputa;
			}
		}
		return null;
	}

	@Override
	protected Command createSetPropertyCommand(Object id, Object value) {
		return null;
	}

}

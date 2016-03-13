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
import jp.sourceforge.tmdmaker.model.Diagram;

import org.eclipse.gef.commands.Command;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

/**
 * Diagram property source.
 *
 */
public class DiagramPropertySource extends AbstractPropertySource {

	Diagram diagram;

	public DiagramPropertySource(TMDEditor editor, Diagram diagram) {
		super(editor);
		this.diagram = diagram;
	}

	@Override
	public Object getEditableValue() {
		return this.diagram;
	}

	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() {
		return new IPropertyDescriptor[] {
				new TextPropertyDescriptor("DatabaseName", Messages.DiagramPropertySource_0) }; //$NON-NLS-1$
	}

	@Override
	public Object getPropertyValue(Object id) {
		if (id.equals("DatabaseName")) { //$NON-NLS-1$
			return diagram.getDatabaseName() != null ? diagram.getDatabaseName() : ""; //$NON-NLS-1$
		}
		return null;
	}

	@Override
	public boolean isPropertySet(Object id) {
		if (id.equals("DatabaseName")) { //$NON-NLS-1$
			return true;
		}
		return false;
	}

	@Override
	public void resetPropertyValue(Object id) {
	}

	@Override
	public void setPropertyValue(Object id, Object value) {
	}

	@Override
	protected Command createSetPropertyCommand(Object id, Object value) {
		return null;
	}

}

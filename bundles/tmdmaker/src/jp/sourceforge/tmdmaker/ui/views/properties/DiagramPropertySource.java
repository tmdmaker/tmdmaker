/*
 * Copyright 2009-2019 TMD-Maker Project <https://tmdmaker.osdn.jp/>
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

import jp.sourceforge.tmdmaker.Messages;
import jp.sourceforge.tmdmaker.TMDEditor;
import jp.sourceforge.tmdmaker.extension.DialectProviderFactory;
import jp.sourceforge.tmdmaker.model.Diagram;

import org.apache.commons.lang.ArrayUtils;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

/**
 * Diagram property source.
 *
 */
public class DiagramPropertySource extends AbstractPropertySource {

	private static final String NAME = "Name"; //$NON-NLS-1$
	private static final String DATABASE_NAME = "DatabaseName"; //$NON-NLS-1$
	private static final String DESCRIPTION = "Description"; //$NON-NLS-1$
	Diagram diagram;
	CommandStack stack;
	
	private String[] dataBaseList;

	public DiagramPropertySource(TMDEditor editor, Diagram diagram) {
		super(editor);
		this.stack = editor.getViewer().getEditDomain().getCommandStack();
		this.diagram = diagram;
		this.dataBaseList = DialectProviderFactory.getDialectProvider().getDatabaseList().toArray(new String[0]);
	}

	@Override
	public Object getEditableValue() {
		return this.diagram;
	}

	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() {
		return new IPropertyDescriptor[] {
				new TextPropertyDescriptor(NAME, Messages.ModelName),
				new ComboBoxPropertyDescriptor(DATABASE_NAME, Messages.DatabaseName,dataBaseList),
				new TextPropertyDescriptor(DESCRIPTION, Messages.Description)};
	}

	@Override
	public Object getPropertyValue(Object id) {
		if (id.equals(NAME)) {
			return diagram.getName() != null ? diagram.getName() : "";
		}
		else if (id.equals(DATABASE_NAME)) {
			return ArrayUtils.indexOf(dataBaseList,diagram.getDatabaseName());
		}
		else if (id.equals(DESCRIPTION)){
		    return diagram.getDescription() != null ? diagram.getDescription() : "";
		}
		return null;
	}

	@Override
	public boolean isPropertySet(Object id) {
		return id.equals(NAME) || id.equals(DATABASE_NAME) || id.equals(DESCRIPTION);
	}

	@Override
	public void resetPropertyValue(Object id) {
		// do nothing
	}

	@Override
	public void setPropertyValue(Object id, Object value) {
		stack.execute(createSetPropertyCommand(id, value));
	}

	@Override
	protected Command createSetPropertyCommand(Object id, Object value) {
		return new DiagramPropertyChangeCommand(this.diagram, id, value);
	}
	
	private static class DiagramPropertyChangeCommand extends Command {
		
		/** 変更対象 */
		private Diagram diagram;
		private Object  newValue;
		private Object  oldValue;
		private Object  id;
		private String[] dataBaseList;

		public DiagramPropertyChangeCommand(Diagram diagram, Object id, Object newValue){
			this.id = id;
			this.diagram = diagram;
			this.newValue = newValue;
			this.dataBaseList = DialectProviderFactory.getDialectProvider().getDatabaseList().toArray(new String[0]);
			if (id.equals(NAME)) {
				oldValue = diagram.getName();
			}
			if (id.equals(DESCRIPTION)) {
				oldValue = diagram.getDescription();
			}
			if (id.equals(DATABASE_NAME)){
				oldValue = diagram.getDatabaseName();
			}
		}
		
		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.gef.commands.Command#execute()
		 */
		@Override
		public void execute() {
			if (id.equals(NAME)) {
				diagram.setName((String) newValue);
			}
			if (id.equals(DESCRIPTION)) {
				diagram.setDescription((String) newValue);
			}
			if (id.equals(DATABASE_NAME)){
				diagram.setDatabaseName(dataBaseList[(Integer) newValue]);
			}
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.gef.commands.Command#undo()
		 */
		@Override
		public void undo() {
			if (id.equals(NAME)) {
				diagram.setName((String) oldValue);
			}
			if (id.equals(DESCRIPTION)) {
				diagram.setDescription((String) oldValue);
			}
			if (id.equals(DATABASE_NAME)){
				diagram.setDatabaseName((String) oldValue);
			}
		}
	}
}

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

import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.commands.CommandStackEvent;
import org.eclipse.gef.commands.CommandStackEventListener;
import org.eclipse.ui.views.properties.IPropertySheetEntry;
import org.eclipse.ui.views.properties.PropertySheetPage;
import org.eclipse.ui.views.properties.PropertySheetSorter;
import org.tmdmaker.ui.views.properties.gef3.commands.PropertyChangeCommand;

/**
 * Undoable and No sort PropertySheetPage
 * 
 * @author nakag
 *
 */
public class TMDPropertySheetPage extends PropertySheetPage {
	private final CommandStack commandStack;
	private final CommandStackEventListener commandStackEventListener;

	public TMDPropertySheetPage(CommandStack commandStack) {
		this.commandStack = commandStack;
		this.commandStackEventListener = new CommandStackEventListener() {

			public void stackChanged(CommandStackEvent event) {
				if (this.executeUndoRedo(event) || this.executeNotPropertySheetCommand(event)) {
					refresh();
				}
			}

			protected boolean executeNotPropertySheetCommand(CommandStackEvent event) {
				return event.getDetail() == CommandStack.POST_EXECUTE
						&& !(event.getCommand() instanceof PropertyChangeCommand);
			}

			protected boolean executeUndoRedo(CommandStackEvent event) {
				return event.getDetail() == CommandStack.POST_UNDO || event.getDetail() == CommandStack.POST_REDO;
			}
		};
		commandStack.addCommandStackEventListener(commandStackEventListener);
		this.setSorter(new TMDPropertySheetSorter());
		this.setPropertySourceProvider(new TMDEditorPropertySourceProvider(this.commandStack));
	}

	@Override
	public void dispose() {
		if (commandStack != null)
			commandStack.removeCommandStackEventListener(commandStackEventListener);
		super.dispose();
	}

	private class TMDPropertySheetSorter extends PropertySheetSorter {
		@Override
		public void sort(IPropertySheetEntry[] entries) {
			// not sort
		}
	}

}

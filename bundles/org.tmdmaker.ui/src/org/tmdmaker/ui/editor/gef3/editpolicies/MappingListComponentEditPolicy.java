/*
 * Copyright 2009-2019 TMD-Maker Project <https://www.tmdmaker.org>
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
package org.tmdmaker.ui.editor.gef3.editpolicies;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.GroupRequest;
import org.tmdmaker.core.model.AbstractConnectionModel;
import org.tmdmaker.core.model.MappingList;
import org.tmdmaker.ui.Messages;
import org.tmdmaker.ui.dialogs.ModelEditDialog;
import org.tmdmaker.ui.dialogs.TableEditDialog;
import org.tmdmaker.ui.editor.gef3.commands.TableDeleteCommand;

/**
 * 
 * @author nakaG
 * 
 */
public class MappingListComponentEditPolicy extends AbstractEntityModelEditPolicy<MappingList> {
	@Override
	protected ModelEditDialog<MappingList> getDialog() {
		return new TableEditDialog<MappingList>(getControllShell(), Messages.EditMappingList,
				getModel());
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.editpolicies.ComponentEditPolicy#createDeleteCommand(org.eclipse.gef.requests.GroupRequest)
	 */
	@Override
	protected Command createDeleteCommand(GroupRequest deleteRequest) {
		AbstractConnectionModel creationRelationship = (AbstractConnectionModel) getModel()
				.findCreationRelationship().getSource();
		return new TableDeleteCommand(getModel(), creationRelationship);
	}
}
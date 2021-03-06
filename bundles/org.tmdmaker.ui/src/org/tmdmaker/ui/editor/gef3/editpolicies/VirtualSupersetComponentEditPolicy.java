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

import java.util.List;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.GroupRequest;
import org.tmdmaker.core.model.AbstractEntityModel;
import org.tmdmaker.core.model.VirtualSuperset;
import org.tmdmaker.ui.dialogs.ModelEditDialog;
import org.tmdmaker.ui.dialogs.SupersetEditDialog;
import org.tmdmaker.ui.dialogs.model.EditAttribute;
import org.tmdmaker.ui.editor.gef3.commands.ConnectionDeleteCommand;
import org.tmdmaker.ui.editor.gef3.commands.ModelEditCommand;

/**
 * みなしスーパーセットのComponentEditPolicy
 * 
 * @author nakaG
 * 
 */
public class VirtualSupersetComponentEditPolicy extends AbstractEntityModelEditPolicy<VirtualSuperset> {
	
	ModelEditDialog<VirtualSuperset> dialog;
	
	@Override
	protected ModelEditDialog<VirtualSuperset> getDialog() {
		dialog = new SupersetEditDialog(getControllShell(), getModel());
		return dialog;
	}

	@Override
	protected Command createEditCommand(List<EditAttribute> editAttributeList, AbstractEntityModel editedValue) {
		return new ModelEditCommand(getModel(), dialog.getEditedValue());
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.editpolicies.ComponentEditPolicy#createDeleteCommand(org.eclipse.gef.requests.GroupRequest)
	 */
	@Override
	protected Command createDeleteCommand(GroupRequest deleteRequest) {
		VirtualSuperset model = (VirtualSuperset) getHost().getModel();
		return new ConnectionDeleteCommand(model.getCreationRelationship());
	}
}
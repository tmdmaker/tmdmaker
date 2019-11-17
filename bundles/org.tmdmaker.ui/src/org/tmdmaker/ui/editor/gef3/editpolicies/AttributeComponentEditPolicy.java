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
package org.tmdmaker.ui.editor.gef3.editpolicies;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.jface.dialogs.Dialog;
import org.tmdmaker.core.model.AbstractEntityModel;
import org.tmdmaker.core.model.Attribute;
import org.tmdmaker.core.model.IAttribute;
import org.tmdmaker.ui.dialogs.AttributeDialog;
import org.tmdmaker.ui.dialogs.model.EditAttribute;
import org.tmdmaker.ui.editor.gef3.commands.AttributeEditCommand;
import org.tmdmaker.ui.editor.gef3.editparts.IAttributeEditPart;

public class AttributeComponentEditPolicy extends AbstractTMDComponentEditPolicy {

	@Override
	protected Command createEditCommand() {
		EditAttribute edited = ((AttributeDialog) dialog).getEditedValue();
		if (!edited.isEdited()) {
			return null;
		}
		Attribute editedValueAttribute = new Attribute();
		edited.copyTo(editedValueAttribute);
		IAttribute original = edited.getOriginalAttribute();
		EditPart part = getHost();
		if (!(part instanceof IAttributeEditPart)) {
			return null;
		}
		AbstractEntityModel entity = ((IAttributeEditPart) part).getParentModel();
		return new AttributeEditCommand(original, editedValueAttribute, entity);
	}

	@Override
	protected Dialog getDialog() {
		return new AttributeDialog(getHost().getViewer().getControl().getShell(),
				new EditAttribute(getModel()));
	}

	private IAttribute getModel() {
		return (IAttribute) getHost().getModel();
	}
}

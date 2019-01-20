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
package jp.sourceforge.tmdmaker.ui.editor.gef3.editpolicies;

import org.eclipse.gef.commands.Command;
import org.eclipse.jface.dialogs.Dialog;

import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.Attribute;
import jp.sourceforge.tmdmaker.model.IAttribute;
import jp.sourceforge.tmdmaker.ui.dialogs.AttributeDialog;
import jp.sourceforge.tmdmaker.ui.dialogs.models.EditAttribute;
import jp.sourceforge.tmdmaker.ui.editor.gef3.commands.AttributeEditCommand;

public class AttributeComponentEditPolicy extends AbstractTMDComponentEditPolicy {

	@Override
	protected Command createEditCommand() {
		EditAttribute edited = ((AttributeDialog) dialog).getEditedValue();
		if (edited.isEdited()) {
			Attribute editedValueAttribute = new Attribute();
			edited.copyTo(editedValueAttribute);
			IAttribute original = edited.getOriginalAttribute();
			AbstractEntityModel entity = (AbstractEntityModel) getHost().getParent().getModel();
			return new AttributeEditCommand(original, editedValueAttribute, entity);
		} else {
			return null;
		}
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

/*
 * Copyright 2009-2018 TMD-Maker Project <https://tmdmaker.osdn.jp/>
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
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.requests.GroupRequest;
import org.tmdmaker.core.model.AbstractEntityModel;
import org.tmdmaker.core.model.SubsetEntity;
import org.tmdmaker.core.model.rule.ImplementRule;
import org.tmdmaker.ui.Messages;
import org.tmdmaker.ui.dialogs.ModelEditDialog;
import org.tmdmaker.ui.dialogs.TableEditDialog;
import org.tmdmaker.ui.dialogs.model.EditAttribute;
import org.tmdmaker.ui.editor.gef3.commands.ImplementDerivationModelsDeleteCommand;
import org.tmdmaker.ui.editor.gef3.commands.SubsetDeleteCommand;

/**
 * サブセットEditPolicy
 * 
 * @author nakaG
 * 
 */
public class SubsetEntityComponentEditPolicy extends AbstractEntityModelEditPolicy<SubsetEntity> {
	@Override
	protected ModelEditDialog<SubsetEntity> getDialog() {
		return new TableEditDialog<SubsetEntity>(getControllShell(), Messages.EditSubset,
				getModel());
	}

	/**
	 * 自分自身が実装対象でない場合に実行するコマンドを生成する。
	 * 
	 * @param editedValue
	 * @return
	 */
	private Command getDeleteCommand(AbstractEntityModel editedValue) {
		AbstractEntityModel table = getModel();
		if (table.isNotImplement() && !editedValue.isNotImplement()) {
			AbstractEntityModel original = ImplementRule.findOriginalImplementModel(table);
			return new ImplementDerivationModelsDeleteCommand(table, original);
		}
		return null;
	}

	/**
	 * 
	 * {@inheritDoc}
	 *
	 * @see org.tmdmaker.ui.editor.gef3.editpolicies.AbstractEntityModelEditPolicy#createEditCommand(java.util.List,
	 *      org.tmdmaker.core.model.AbstractEntityModel)
	 */
	@Override
	protected Command createEditCommand(List<EditAttribute> editAttributeList,
			AbstractEntityModel editedValue) {
		CompoundCommand ccommand = new CompoundCommand();
		ccommand.add(super.createEditCommand(editAttributeList, editedValue));
		Command deleteCommand = getDeleteCommand(editedValue);
		if (deleteCommand != null) {
			ccommand.add(deleteCommand);
		}
		return ccommand.unwrap();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.editpolicies.ComponentEditPolicy#createDeleteCommand(org.eclipse.gef.requests.GroupRequest)
	 */
	@Override
	protected Command createDeleteCommand(GroupRequest deleteRequest) {
		CompoundCommand ccommand = new CompoundCommand();
		SubsetDeleteCommand command1 = new SubsetDeleteCommand(getModel());
		ccommand.add(command1);
		if (getModel().isNotImplement()) {
			AbstractEntityModel original = ImplementRule.findOriginalImplementModel(getModel());
			ccommand.add(new ImplementDerivationModelsDeleteCommand(getModel(), original));
		}
		return ccommand;
	}
}
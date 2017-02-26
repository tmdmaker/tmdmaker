/*
 * Copyright 2009-2017 TMD-Maker Project <http://tmdmaker.osdn.jp/>
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
package jp.sourceforge.tmdmaker.editpolicy;

import java.util.List;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.requests.GroupRequest;

import jp.sourceforge.tmdmaker.dialog.ModelEditDialog;
import jp.sourceforge.tmdmaker.dialog.VirtualEntityEditDialog;
import jp.sourceforge.tmdmaker.dialog.model.EditAttribute;
import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.VirtualEntity;
import jp.sourceforge.tmdmaker.model.rule.ImplementRule;
import jp.sourceforge.tmdmaker.ui.editor.gef3.commands.ImplementDerivationModelsDeleteCommand;
import jp.sourceforge.tmdmaker.ui.editor.gef3.commands.TableDeleteCommand;

/**
 * 
 * @author nakaG
 * 
 */
public class VirtualEntityComponentEditPolicy extends AbstractEntityModelEditPolicy<VirtualEntity> {

	@Override
	protected ModelEditDialog<VirtualEntity> getDialog() {
		return new VirtualEntityEditDialog(getControllShell(), getModel());
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
	
	@Override
	protected CompoundCommand createEditCommand(List<EditAttribute> editAttributeList,
			AbstractEntityModel editedValue) {
		CompoundCommand ccommand = super.createEditCommand(editAttributeList, editedValue);
		Command deleteCommand = getDeleteCommand(editedValue);
		if (deleteCommand != null) {
			ccommand.add(deleteCommand);
		}
		return ccommand;
	}

	
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.editpolicies.ComponentEditPolicy#createDeleteCommand(org.eclipse.gef.requests.GroupRequest)
	 */
	@Override
	protected Command createDeleteCommand(GroupRequest deleteRequest) {
		CompoundCommand ccommand = new CompoundCommand();
		if (getModel().isNotImplement()) {
			AbstractEntityModel original = ImplementRule.findOriginalImplementModel(getModel());
			ccommand.add(new ImplementDerivationModelsDeleteCommand(getModel(), original));
		}

		ccommand.add(new TableDeleteCommand(getModel(),getModel().getModelTargetConnections().get(0)));

		return ccommand;
	}

}
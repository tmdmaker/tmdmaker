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

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.GroupRequest;

import jp.sourceforge.tmdmaker.dialog.LaputaEditDialog;
import jp.sourceforge.tmdmaker.dialog.ModelEditDialog;
import jp.sourceforge.tmdmaker.model.Laputa;
import jp.sourceforge.tmdmaker.ui.editor.gef3.commands.EntityDeleteCommand;

/**
 * エンティティ削除系EditPolicy
 * 
 * @author nakaG
 * 
 */
public class LaputaComponentEditPolicy extends AbstractEntityModelEditPolicy<Laputa> {
	@Override
	protected ModelEditDialog<Laputa> getDialog() {
		return new LaputaEditDialog(getControllShell(), getModel());
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.editpolicies.ComponentEditPolicy#createDeleteCommand(org.eclipse.gef.requests.GroupRequest)
	 */
	@Override
	protected Command createDeleteCommand(GroupRequest deleteRequest) {
		return new EntityDeleteCommand<Laputa>(getDiagram(), getModel());
	}
}
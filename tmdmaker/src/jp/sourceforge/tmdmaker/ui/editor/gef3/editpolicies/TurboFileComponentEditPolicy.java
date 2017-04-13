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
package jp.sourceforge.tmdmaker.ui.editor.gef3.editpolicies;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.GroupRequest;

import jp.sourceforge.tmdmaker.Messages;
import jp.sourceforge.tmdmaker.model.other.TurboFile;
import jp.sourceforge.tmdmaker.ui.dialogs.ModelEditDialog;
import jp.sourceforge.tmdmaker.ui.dialogs.TableEditDialog;
import jp.sourceforge.tmdmaker.ui.editor.gef3.commands.EntityDeleteCommand;

/**
 * エンティティ削除系EditPolicy
 * 
 * @author nakaG
 * 
 */
public class TurboFileComponentEditPolicy extends AbstractEntityModelEditPolicy<TurboFile> {
	@Override
	protected ModelEditDialog<TurboFile> getDialog() {
		return new TableEditDialog<TurboFile>(getControllShell(), Messages.EditTurboFile,
				getModel());
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.editpolicies.ComponentEditPolicy#createDeleteCommand(org.eclipse.gef.requests.GroupRequest)
	 */
	@Override
	protected Command createDeleteCommand(GroupRequest deleteRequest) {
		return new EntityDeleteCommand<TurboFile>(getDiagram(), getModel());
	}
}
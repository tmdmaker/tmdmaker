/*
 * Copyright 2009-2010 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
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
package jp.sourceforge.tmdmaker.tool;

import jp.sourceforge.tmdmaker.dialog.EntityCreateDialog;
import jp.sourceforge.tmdmaker.model.command.ModelAddCommand;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.tools.CreationTool;
import org.eclipse.jface.dialogs.Dialog;

/**
 * エンティティ作成ツール
 * 
 * @author nakaG
 * 
 */
public class EntityCreationTool extends CreationTool {
	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.tools.CreationTool#handleButtonUp(int)
	 */
	@Override
	protected boolean handleButtonUp(int button) {

		boolean added = false;
		if (stateTransition(STATE_DRAG | STATE_DRAG_IN_PROGRESS, STATE_TERMINAL)) {
			System.out.println(getState());
			eraseTargetFeedback();
			unlockTargetEditPart();

			Command curCommand = getCurrentCommand();
			if (curCommand instanceof ModelAddCommand) {
				ModelAddCommand addCommand = (ModelAddCommand) curCommand;

				EntityCreateDialog dialog = new EntityCreateDialog(
						getCurrentViewer().getControl().getShell());
				if (dialog.open() == Dialog.OK) {
					added = true;
					addCommand.setModel(dialog.getCreateModel());
					performCreation(button);
				} else {
					added = false;
				}
			}
		}

		setState(STATE_TERMINAL);
		handleFinished();

		return added;
	}

}
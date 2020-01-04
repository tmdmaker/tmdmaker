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
package org.tmdmaker.ui.actions.gef3;

import org.eclipse.gef.commands.Command;
import org.eclipse.ui.IWorkbenchPart;
import org.tmdmaker.core.model.AbstractEntityModel;
import org.tmdmaker.core.model.Header2DetailRelationship;
import org.tmdmaker.ui.Messages;

/**
 * HDR-DTLのスーパーセットを非表示にするアクション
 * 
 * @author nakaG
 *
 */
public class MultivalueAndSupersetHideAction extends AbstractEntitySelectionAction {
	public static final String ID = "_MVA_SE_HIDE"; //$NON-NLS-1$

	/**
	 * コンストラクタ
	 * 
	 * @param part
	 *            エディター
	 */
	public MultivalueAndSupersetHideAction(IWorkbenchPart part) {
		super(part);
		setText(Messages.HideMultivalueAndSuperset);
		setId(ID);
	}

	@Override
	protected boolean calculateEnabled() {
		if (super.calculateEnabled()) {
			AbstractEntityModel entity = getModel();
			if (!entity.isHeaderDetail()) {
				return false;
			}
			Header2DetailRelationship r = entity.getHeader2DetailRelationship();
			return r != null && r.isSupersetConnected();
		}
		return false;
	}

	@Override
	public void run() {
		MultivalueAndSupersetHideCommand command = new MultivalueAndSupersetHideCommand(
				getModel().getHeader2DetailRelationship());
		execute(command);
	}

	private static class MultivalueAndSupersetHideCommand extends Command {
		private Header2DetailRelationship relationship;

		public MultivalueAndSupersetHideCommand(Header2DetailRelationship relationship) {
			this.relationship = relationship;
		}

		@Override
		public void execute() {
			relationship.disconnectSuperset();
		}

		@Override
		public void undo() {
			relationship.connectSuperset();
		}
	}
}

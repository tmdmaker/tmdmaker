/*
 * Copyright 2009,2014 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
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
package jp.sourceforge.tmdmaker.action;

import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.Header2DetailRelationship;

import org.eclipse.gef.commands.Command;
import org.eclipse.ui.IWorkbenchPart;

/**
 * HDR-DTLのスーパーセットを（再）表示するアクション
 * 
 * @author nakaG
 *
 */
public class MultivalueAndSupersetShowAction extends AbstractEntitySelectionAction {
	public static final String ID = "_MVA_SE_SHOW";

	/**
	 * コンストラクタ
	 * 
	 * @param part
	 *            エディター
	 */
	public MultivalueAndSupersetShowAction(IWorkbenchPart part) {
		super(part);
		setText("多値のANDのスーパーセットを表示");
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
			return r != null && !r.isSupersetConnected();
		}
		return false;
	}

	@Override
	public void run() {
		MultivalueAndSupersetShowCommand command = new MultivalueAndSupersetShowCommand(getModel()
				.getHeader2DetailRelationship());
		execute(command);
	}

	private static class MultivalueAndSupersetShowCommand extends Command {
		private Header2DetailRelationship relationship;

		public MultivalueAndSupersetShowCommand(Header2DetailRelationship relationship) {
			this.relationship = relationship;
		}

		@Override
		public void execute() {
			relationship.connectSuperset();
		}

		@Override
		public void undo() {
			relationship.disconnectSuperset();
		}
	}
}

/*
 * Copyright 2009-2012 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
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

import jp.sourceforge.tmdmaker.editpart.AbstractEntityEditPart;
import jp.sourceforge.tmdmaker.editpart.SubsetTypeEditPart;
import jp.sourceforge.tmdmaker.model.SubsetType;

import org.eclipse.gef.commands.Command;
import org.eclipse.ui.IWorkbenchPart;

/**
 * サブセット種類を回転アクション
 * 
 * @author nakaG
 * 
 */
public class SubsetTypeTurnAction extends AbstractEntitySelectionAction {

	/** サブセット種類を回転アクションを表す定数 */
	public static final String ID = "_SUBSETTYPE";

	/**
	 * コンストラクタ
	 * 
	 * @param part
	 *            エディター
	 */
	public SubsetTypeTurnAction(IWorkbenchPart part) {
		super(part);
		setText("サブセット種類を回転");
		setId(ID);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.action.AbstractEntitySelectionAction#calculateEnabled()
	 */
	@Override
	protected boolean calculateEnabled() {
		if (getSelectedObjects().size() == 1) {
			Object selection = getSelectedObjects().get(0);
			return selection instanceof SubsetTypeEditPart;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
		AbstractEntityEditPart part = getPart();
		SubsetType model = (SubsetType) part.getModel();

		execute(new SubsetTypeChangeCommand(model));
	}

	private static class SubsetTypeChangeCommand extends Command {
		private SubsetType model;
		private boolean oldVertical;
		private boolean newVertical;

		public SubsetTypeChangeCommand(SubsetType model) {
			this.model = model;
			oldVertical = model.isVertical();
			newVertical = !oldVertical;
		}

		@Override
		public void execute() {
			model.setVertical(newVertical);
		}

		@Override
		public void undo() {
			model.setVertical(oldVertical);
		}
	}
}

/*
 * Copyright 2009-2015 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
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

import jp.sourceforge.tmdmaker.dialog.MultivalueOrEntityCreateDialog;
import jp.sourceforge.tmdmaker.editpart.AbstractModelEditPart;
import jp.sourceforge.tmdmaker.editpart.AbstractSubsetTypeEditPart;
import jp.sourceforge.tmdmaker.editpart.LaputaEditPart;
import jp.sourceforge.tmdmaker.editpart.MultivalueAndSupersetEditPart;
import jp.sourceforge.tmdmaker.editpart.VirtualSupersetEditPart;
import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.MultivalueOrRelationship;

import org.eclipse.gef.commands.Command;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.ui.IWorkbenchPart;

/**
 * 多値のOR作成アクション.
 * 
 * @author nakaG
 * 
 */
public class MultivalueOrCreateAction extends AbstractEntitySelectionAction {
	/** 多値のOR作成アクションを表す定数 */
	public static final String ID = "_MO";

	/**
	 * コンストラクタ.
	 * 
	 * @param part
	 *            エディター.
	 */
	public MultivalueOrCreateAction(IWorkbenchPart part) {
		super(part);
		setText("多値のOR作成");
		setId(ID);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
		// AbstractEntityEditPart part = getPart();
		MultivalueOrEntityCreateDialog dialog = new MultivalueOrEntityCreateDialog(getPart()
				.getViewer().getControl().getShell());
		if (dialog.open() == Dialog.OK) {
			MultivalueOrCreateCommand command = new MultivalueOrCreateCommand(getModel(),
					dialog.getInputTypeName());

			execute(command);
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.action.AbstractEntitySelectionAction#calculateEnabled()
	 */
	@Override
	protected boolean calculateEnabled() {
		if (getSelectedObjects().size() == 1) {
			Object selection = getSelectedObjects().get(0);
			return selection instanceof AbstractModelEditPart
					&& !(selection instanceof AbstractSubsetTypeEditPart)
					&& !(selection instanceof MultivalueAndSupersetEditPart)
					&& !(selection instanceof VirtualSupersetEditPart)
					&& !(selection instanceof LaputaEditPart);
		} else {
			return false;
		}
	}

	/**
	 * 多値のOR作成Command.
	 *
	 * @author nakaG
	 *
	 */
	private static class MultivalueOrCreateCommand extends Command {
		/** 多値のORとのリレーションシップ */
		private MultivalueOrRelationship relationship;

		/**
		 * コンストラクタ.
		 *
		 * @param model
		 *            多値のOR作成対象.
		 * @param typeName
		 *            多値のORの名称.
		 */
		public MultivalueOrCreateCommand(AbstractEntityModel model, String typeName) {
			this.relationship = new MultivalueOrRelationship(model, typeName);
		}

		/**
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.gef.commands.Command#execute()
		 */
		@Override
		public void execute() {
			relationship.connect();
		}

		/**
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.gef.commands.Command#undo()
		 */
		@Override
		public void undo() {
			relationship.disconnect();
		}
	}
}

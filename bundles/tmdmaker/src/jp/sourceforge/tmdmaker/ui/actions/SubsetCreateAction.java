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
package jp.sourceforge.tmdmaker.ui.actions;

import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.ui.IWorkbenchPart;

import jp.sourceforge.tmdmaker.Messages;
import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.ui.dialogs.SubsetCreateDialog;
import jp.sourceforge.tmdmaker.ui.editor.gef3.editparts.node.AbstractModelEditPart;

/**
 * サブセット作成アクション.
 *
 * @author nakaG
 * 
 */
public class SubsetCreateAction extends AbstractEntitySelectionAction {
	/** サブセット作成アクションを表す定数 */
	public static final String ID = "_SUBSET"; //$NON-NLS-1$

	/**
	 * コンストラクタ.
	 *
	 * @param part
	 *            エディター.
	 */
	public SubsetCreateAction(IWorkbenchPart part) {
		super(part);
		setText(Messages.CreateSubset);
		setId(ID);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.ui.actions.AbstractEntitySelectionAction#calculateEnabled()
	 */
	@Override
	protected boolean calculateEnabled() {
		if (getSelectedObjects().size() != 1) {
			return false;
		}
		Object selection = getSelectedObjects().get(0);
		if (selection instanceof AbstractModelEditPart<?>) {
			return getPart().canCreateSubset();
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
		AbstractModelEditPart<? extends AbstractEntityModel> part = getPart();
		AbstractEntityModel model = getModel();

		SubsetCreateDialog dialog = new SubsetCreateDialog(part.getViewer().getControl().getShell(),
				model);
		if (dialog.open() == Dialog.OK) {
			CompoundCommand ccommand = dialog.getCcommand();
			execute(ccommand);
		}
	}
}

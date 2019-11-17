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
package org.tmdmaker.ui.actions.gef3;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.ui.IWorkbenchPart;
import org.tmdmaker.ui.Messages;
import org.tmdmaker.ui.dialogs.MultivalueOrEntityCreateDialog;
import org.tmdmaker.ui.editor.gef3.commands.MultivalueOrCreateCommand;
import org.tmdmaker.ui.editor.gef3.editparts.node.AbstractModelEditPart;

/**
 * 多値のOR作成アクション.
 * 
 * @author nakaG
 * 
 */
public class MultivalueOrCreateAction extends AbstractEntitySelectionAction {
	/** 多値のOR作成アクションを表す定数 */
	public static final String ID = "_MO"; //$NON-NLS-1$

	/**
	 * コンストラクタ.
	 * 
	 * @param part
	 *            エディター.
	 */
	public MultivalueOrCreateAction(IWorkbenchPart part) {
		super(part);
		setText(Messages.CreateMultivalueOr);
		setId(ID);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
		MultivalueOrEntityCreateDialog dialog = new MultivalueOrEntityCreateDialog(
				getPart().getViewer().getControl().getShell());
		if (dialog.open() == Dialog.OK) {
			execute(new MultivalueOrCreateCommand(getModel(), dialog.getInputTypeName()));
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.tmdmaker.ui.actions.gef3.AbstractEntitySelectionAction#calculateEnabled()
	 */
	@Override
	protected boolean calculateEnabled() {
		if (getSelectedObjects().size() != 1) {
			return false;
		}
		Object selection = getSelectedObjects().get(0);
		if (selection instanceof AbstractModelEditPart<?>) {
			return getPart().canCreateMultivalueOr();
		} else {
			return false;
		}
	}
}

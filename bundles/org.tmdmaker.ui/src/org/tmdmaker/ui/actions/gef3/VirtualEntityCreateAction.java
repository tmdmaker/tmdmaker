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

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.ui.IWorkbenchPart;
import org.tmdmaker.core.model.parts.ModelName;
import org.tmdmaker.ui.Messages;
import org.tmdmaker.ui.dialogs.VirtualEntityCreateDialog;
import org.tmdmaker.ui.editor.gef3.commands.VirtualEntityCreateCommand;
import org.tmdmaker.ui.editor.gef3.editparts.node.AbstractModelEditPart;

/**
 * みなしエンティティ作成アクション.
 * 
 * @author nakaG
 * 
 */
public class VirtualEntityCreateAction extends AbstractEntitySelectionAction {
	/** みなしエンティティ作成アクションを表す定数 */
	public static final String ID = "_VE"; //$NON-NLS-1$

	/**
	 * コンストラクタ.
	 * 
	 * @param part
	 *            エディター
	 */
	public VirtualEntityCreateAction(IWorkbenchPart part) {
		super(part);
		setText(Messages.CreateVirtualEntity);
		setId(ID);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
		VirtualEntityCreateDialog dialog = new VirtualEntityCreateDialog(
				getPart().getViewer().getControl().getShell());
		if (dialog.open() == Dialog.OK) {
			execute(new VirtualEntityCreateCommand(getModel(),
					new ModelName(dialog.getInputVirtualEntityName()),
					dialog.getInputVirtualEntityType()));
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
			return getPart().canCreateVirtualEntity();
		} else {
			return false;
		}
	}
}

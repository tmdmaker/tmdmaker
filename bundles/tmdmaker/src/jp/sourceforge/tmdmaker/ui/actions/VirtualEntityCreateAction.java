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
package jp.sourceforge.tmdmaker.ui.actions;

import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.ui.IWorkbenchPart;

import jp.sourceforge.tmdmaker.Messages;
import jp.sourceforge.tmdmaker.model.Entity2VirtualEntityRelationship;
import jp.sourceforge.tmdmaker.ui.dialogs.VirtualEntityCreateDialog;
import jp.sourceforge.tmdmaker.ui.editor.gef3.commands.ConstraintAdjusterCommand;
import jp.sourceforge.tmdmaker.ui.editor.gef3.commands.RelationshipConnectionCommand;
import jp.sourceforge.tmdmaker.ui.editor.gef3.editparts.node.AbstractModelEditPart;

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
			CompoundCommand ccommand = new CompoundCommand();
			Entity2VirtualEntityRelationship relationship = new Entity2VirtualEntityRelationship(
					getModel(), dialog.getInputVirtualEntityName(),
					dialog.getInputVirtualEntityType());
			ccommand.add(new RelationshipConnectionCommand(relationship));
			ccommand.add(new ConstraintAdjusterCommand(relationship, 100, 0));
			execute(ccommand);
		}
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
			return getPart().canCreateVirtualEntity();
		} else {
			return false;
		}
	}
}

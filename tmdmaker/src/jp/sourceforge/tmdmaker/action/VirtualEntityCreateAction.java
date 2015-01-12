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

import jp.sourceforge.tmdmaker.dialog.VirtualEntityCreateDialog;
import jp.sourceforge.tmdmaker.editpart.AbstractModelEditPart;
import jp.sourceforge.tmdmaker.editpart.LaputaEditPart;
import jp.sourceforge.tmdmaker.editpart.MultivalueAndAggregatorEditPart;
import jp.sourceforge.tmdmaker.editpart.MultivalueAndSupersetEditPart;
import jp.sourceforge.tmdmaker.editpart.SubsetTypeEditPart;
import jp.sourceforge.tmdmaker.editpart.VirtualSupersetEditPart;
import jp.sourceforge.tmdmaker.editpart.VirtualSupersetTypeEditPart;
import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.Entity2VirtualEntityRelationship;
import jp.sourceforge.tmdmaker.model.VirtualEntityType;

import org.eclipse.gef.commands.Command;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.ui.IWorkbenchPart;

/**
 * みなしエンティティ作成アクション
 * 
 * @author nakaG
 * 
 */
public class VirtualEntityCreateAction extends AbstractEntitySelectionAction {
	/** みなしエンティティ作成アクションを表す定数 */
	public static final String ID = "_VE";

	/**
	 * コンストラクタ
	 * 
	 * @param part
	 *            エディター
	 */
	public VirtualEntityCreateAction(IWorkbenchPart part) {
		super(part);
		setText("みなしEntity作成");
		setId(ID);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
		VirtualEntityCreateDialog dialog = new VirtualEntityCreateDialog(getPart().getViewer().getControl().getShell());
		if (dialog.open() == Dialog.OK) {
			execute(new VirtualEntityCreateCommand(getModel(), dialog.getInputVirtualEntityName(), dialog.getInputVirtualEntityType()));
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
					&& !(selection instanceof SubsetTypeEditPart)
					&& !(selection instanceof MultivalueAndSupersetEditPart)
					&& !(selection instanceof MultivalueAndAggregatorEditPart)
					&& !(selection instanceof VirtualSupersetEditPart)
					&& !(selection instanceof VirtualSupersetTypeEditPart)
					&& !(selection instanceof LaputaEditPart);
		} else {
			return false;
		}
	}


	/**
	 * みなしエンティティ作成
	 * 
	 * @author nakaG
	 * 
	 */
	private static class VirtualEntityCreateCommand extends Command {
		/** みなしエンティティへのリレーションシップ */
		private Entity2VirtualEntityRelationship relationship;

		/**
		 * コンストラクタ
		 * 
		 * @param model
		 *            みなしエンティティ作成対象
		 */
		public VirtualEntityCreateCommand(AbstractEntityModel model, String virtualEntityName, VirtualEntityType type) {
			this.relationship = new Entity2VirtualEntityRelationship(model, virtualEntityName, type);
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

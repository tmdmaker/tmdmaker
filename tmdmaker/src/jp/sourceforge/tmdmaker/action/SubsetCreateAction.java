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

import jp.sourceforge.tmdmaker.dialog.SubsetCreateDialog;
import jp.sourceforge.tmdmaker.editpart.AbstractModelEditPart;
import jp.sourceforge.tmdmaker.editpart.LaputaEditPart;
import jp.sourceforge.tmdmaker.editpart.MultivalueAndAggregatorEditPart;
import jp.sourceforge.tmdmaker.editpart.MultivalueAndSupersetEditPart;
import jp.sourceforge.tmdmaker.editpart.SubsetTypeEditPart;
import jp.sourceforge.tmdmaker.editpart.VirtualSupersetEditPart;
import jp.sourceforge.tmdmaker.editpart.VirtualSupersetTypeEditPart;
import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.SubsetType;
import jp.sourceforge.tmdmaker.model.rule.SubsetRule;

import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.ui.IWorkbenchPart;

/**
 * サブセット作成アクション
 * 
 * @author nakaG
 * 
 */
public class SubsetCreateAction extends AbstractEntitySelectionAction {
	/** サブセット作成アクションを表す定数 */
	public static final String ID = "_SUBSET";

	/**
	 * コンストラクタ
	 * 
	 * @param part
	 *            エディター
	 */
	public SubsetCreateAction(IWorkbenchPart part) {
		super(part);
		setText("サブセット作成");
		setId(ID);
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
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
		AbstractModelEditPart<? extends AbstractEntityModel> part = getPart();
		AbstractEntityModel model = getModel();
		SubsetType subsetType = SubsetRule.setupSubsetType(model);

		SubsetCreateDialog dialog = new SubsetCreateDialog(
				part.getViewer().getControl().getShell(), subsetType, model);
		if (dialog.open() == Dialog.OK) {
			CompoundCommand ccommand = dialog.getCcommand();
			execute(ccommand);
		}
	}

}

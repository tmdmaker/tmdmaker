/*
 * Copyright 2009-2011 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
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

import jp.sourceforge.tmdmaker.editpart.LaputaEditPart;
import jp.sourceforge.tmdmaker.editpart.MultivalueAndAggregatorEditPart;
import jp.sourceforge.tmdmaker.editpart.SubsetTypeEditPart;
import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.command.ModelConstraintChangeCommand;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.ui.IWorkbenchPart;

/**
 * モデルのサイズを自動調整に設定するAction
 * 
 * @author nakaG
 * 
 */
public class AutoSizeSettingAction extends AbstractMultipleSelectionAction {
	public static final String ID = "AutoSizeSettingAction";

	/**
	 * コンストラクタ
	 * 
	 * @param part
	 *            エディター
	 */
	public AutoSizeSettingAction(IWorkbenchPart part) {
		super(part);
		setText("サイズの自動調整");
		setId(ID);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
		CompoundCommand ccommand = new CompoundCommand();
		for (AbstractEntityModel m : getSelectedModelList()) {
			Rectangle constraint = m.getConstraint().getCopy();
			constraint.height = -1;
			constraint.width = -1;
			ccommand.add(new ModelConstraintChangeCommand(m, constraint));
		}
		if (!ccommand.isEmpty()) {
			execute(ccommand.unwrap());
		}
	}

	@Override
	protected boolean isTargetModel(Object selection) {
		if (super.isTargetModel(selection)) {
			return !(selection instanceof SubsetTypeEditPart)
					&& !(selection instanceof MultivalueAndAggregatorEditPart)
					&& !(selection instanceof LaputaEditPart);
		} else {
			return false;
		}
	}

}

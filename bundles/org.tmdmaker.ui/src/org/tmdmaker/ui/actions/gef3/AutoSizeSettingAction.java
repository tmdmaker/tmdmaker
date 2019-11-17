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
package org.tmdmaker.ui.actions.gef3;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.ui.IWorkbenchPart;
import org.tmdmaker.core.model.ConnectableElement;
import org.tmdmaker.ui.Messages;
import org.tmdmaker.ui.editor.draw2d.ConstraintConverter;
import org.tmdmaker.ui.editor.gef3.commands.ConstraintChangeCommand;
import org.tmdmaker.ui.editor.gef3.editparts.node.AbstractModelEditPart;

/**
 * モデルのサイズを自動調整に設定するAction
 * 
 * @author nakaG
 * 
 */
public class AutoSizeSettingAction extends AbstractMultipleSelectionAction {
	public static final String ID = "AutoSizeSettingAction"; //$NON-NLS-1$

	/**
	 * コンストラクタ
	 * 
	 * @param part
	 *            エディター
	 */
	public AutoSizeSettingAction(IWorkbenchPart part) {
		super(part);
		setText(Messages.AutomaticSizeAdjustment);
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
		for (ConnectableElement m : getSelectedElementList()) {
			ccommand.add(new ConstraintChangeCommand(m, ConstraintConverter.getResetRectangle(m)));
		}
		if (!ccommand.isEmpty()) {
			execute(ccommand.unwrap());
		}
	}

	@Override
	protected boolean isTargetModel(Object selection) {
		if (selection instanceof AbstractModelEditPart) {
			return getPart().canAutoSize();
		} else {
			return false;
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.tmdmaker.ui.actions.gef3.AbstractMultipleSelectionAction#calculateEnabled()
	 */
	@Override
	protected boolean calculateEnabled() {
		return getSelectedElementList().size() >= 1;
	}

	protected List<ConnectableElement> getSelectedElementList() {
		List<ConnectableElement> list = new ArrayList<ConnectableElement>();
		for (Object selection : getSelectedObjects()) {
			if (isTargetModel(selection)) {
				Object model = getPart().getModel();
				if (model instanceof ConnectableElement) {
					list.add((ConnectableElement) model);
				}
			}
		}
		return list;
	}
}

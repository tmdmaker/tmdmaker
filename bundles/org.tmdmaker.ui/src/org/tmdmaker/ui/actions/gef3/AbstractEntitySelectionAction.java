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

import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.IWorkbenchPart;
import org.tmdmaker.core.model.AbstractEntityModel;
import org.tmdmaker.ui.editor.gef3.editparts.node.AbstractModelEditPart;

/**
 * SelectionAction系クラスの基底クラス.
 *
 * @author nakaG
 *
 */
public abstract class AbstractEntitySelectionAction extends SelectionAction {

	/**
	 * コンストラクタ.
	 *
	 * @param part
	 *            エディター.
	 */
	public AbstractEntitySelectionAction(IWorkbenchPart part) {
		super(part);
	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.gef.ui.actions.WorkbenchPartAction#calculateEnabled()
	 */
	@Override
	protected boolean calculateEnabled() {
		if (getSelectedObjects().size() != 1) {
			return false;
		}
		Object selection = getSelectedObjects().get(0);
		if (selection instanceof AbstractModelEditPart<?>) {
			return getPart().canCallSelectionAction();
		} else {
			return false;
		}
	}

	/**
	 * コントローラ取得
	 *
	 * @return コントローラ(EditPart).
	 */
	@SuppressWarnings("unchecked")
	protected AbstractModelEditPart<? extends AbstractEntityModel> getPart() {
		return (AbstractModelEditPart<? extends AbstractEntityModel>) getSelectedObjects().get(0);
	}

	/**
	 * モデル取得.
	 *
	 * @return モデルを返す.
	 */
	protected AbstractEntityModel getModel() {
		return getPart().getModel();
	}
}

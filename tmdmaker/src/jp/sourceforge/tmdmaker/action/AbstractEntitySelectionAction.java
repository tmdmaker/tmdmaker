/*
 * Copyright 2009-2014 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
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

import jp.sourceforge.tmdmaker.editpart.AbstractModelEditPart;
import jp.sourceforge.tmdmaker.editpart.AbstractSubsetTypeEditPart;
import jp.sourceforge.tmdmaker.editpart.LaputaEditPart;
import jp.sourceforge.tmdmaker.model.AbstractEntityModel;

import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.IWorkbenchPart;

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
		if (getSelectedObjects().size() == 1) {
			Object selection = getSelectedObjects().get(0);
			return selection instanceof AbstractModelEditPart
					&& !(selection instanceof AbstractSubsetTypeEditPart)
					&& !(selection instanceof LaputaEditPart);
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

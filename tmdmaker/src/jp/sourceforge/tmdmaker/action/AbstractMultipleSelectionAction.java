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

import java.util.ArrayList;
import java.util.List;

import jp.sourceforge.tmdmaker.editpart.AbstractEntityEditPart;
import jp.sourceforge.tmdmaker.model.AbstractEntityModel;

import org.eclipse.ui.IWorkbenchPart;

/**
 * 複数モデルを選択可能なSelectionAction系クラスの基底クラス
 * 
 * @author nakaG
 * 
 */
public abstract class AbstractMultipleSelectionAction extends
		AbstractEntitySelectionAction {
	/**
	 * コンストラクタ
	 * 
	 * @param part
	 *            エディター
	 */
	public AbstractMultipleSelectionAction(IWorkbenchPart part) {
		super(part);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.action.AbstractEntitySelectionAction#calculateEnabled()
	 */
	@Override
	protected boolean calculateEnabled() {
		return getSelectedModelList().size() >= 1;
	}

	/**
	 * 選択したエンティティ系モデルを取得する
	 * 
	 * @return 選択したエンティティ系モデルのリスト。未選択の場合は空のリストを返す。
	 */
	protected List<AbstractEntityModel> getSelectedModelList() {
		List<AbstractEntityModel> list = new ArrayList<AbstractEntityModel>();
		for (Object selection : getSelectedObjects()) {
			if (isTargetModel(selection)) {
				AbstractEntityModel model = (AbstractEntityModel) ((AbstractEntityEditPart) selection)
						.getModel();
				list.add(model);
			}
		}
		return list;
	}

	/**
	 * 選択したEditPartから対象を制限する
	 * 
	 * @param selection
	 *            選択したEditPart
	 * @return 本Actionで使用する対象の場合にtrueを返す
	 */
	protected boolean isTargetModel(Object selection) {
		return selection instanceof AbstractEntityEditPart;
	}
}
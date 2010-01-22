/*
 * Copyright 2009-2010 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
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

import jp.sourceforge.tmdmaker.editpart.EntityEditPart;
import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.Cardinality;
import jp.sourceforge.tmdmaker.model.Header2DetailRelationship;

import org.eclipse.gef.commands.Command;
import org.eclipse.ui.IWorkbenchPart;

/**
 * 多値のAND(HDR-DTL)作成アクション
 * 
 * @author nakaG
 * 
 */
public class MultivalueAndCreateAction extends AbstractEntitySelectionAction {
	/** 多値のAND作成アクションを表す定数 */
	public static final String ID = "_MA";

	/**
	 * コンストラクタ
	 * 
	 * @param part
	 *            エディター
	 */
	public MultivalueAndCreateAction(IWorkbenchPart part) {
		super(part);
		setText("多値のAND(HDR-DTL)作成");
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
			return selection instanceof EntityEditPart;
		} else {
			return false;
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
		AbstractEntityModel model = getModel();

		// Detailとスーパーセットを追加してHeaderと接続
		HeaderDetailCreateCommand command = new HeaderDetailCreateCommand(model);

		execute(command);
	}

	/**
	 * HDR-DTLを作成するコマンド
	 */
	private static class HeaderDetailCreateCommand extends Command {
		/** リレーションシップ */
		private Header2DetailRelationship relationship;

		/**
		 * コンストラクタ
		 * 
		 * @param header
		 *            ヘッダーとなるモデル
		 */
		public HeaderDetailCreateCommand(AbstractEntityModel header) {
			relationship = new Header2DetailRelationship(header);
			relationship.setTargetCardinality(Cardinality.MANY);
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

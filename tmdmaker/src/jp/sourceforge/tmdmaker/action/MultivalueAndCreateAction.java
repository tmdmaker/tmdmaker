/*
 * Copyright 2009-2016 TMD-Maker Project <http://tmdmaker.osdn.jp/>
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

import jp.sourceforge.tmdmaker.Messages;
import jp.sourceforge.tmdmaker.editpart.EntityEditPart;
import jp.sourceforge.tmdmaker.model.AbstractConnectionModel;
import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.AbstractRelationship;
import jp.sourceforge.tmdmaker.model.Cardinality;
import jp.sourceforge.tmdmaker.model.Detail;
import jp.sourceforge.tmdmaker.model.Header2DetailRelationship;
import jp.sourceforge.tmdmaker.model.rule.EntityTypeRule;
import jp.sourceforge.tmdmaker.model.rule.RelationshipRule;
import jp.sourceforge.tmdmaker.ui.command.ConnectionCreateCommand;
import jp.sourceforge.tmdmaker.ui.command.ConnectionDeleteCommand;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.ui.IWorkbenchPart;

/**
 * 多値のAND(HDR-DTL)作成アクション
 * 
 * @author nakaG
 * 
 */
public class MultivalueAndCreateAction extends AbstractEntitySelectionAction {
	/** 多値のAND作成アクションを表す定数 */
	public static final String ID = "_MA"; //$NON-NLS-1$

	/**
	 * コンストラクタ
	 * 
	 * @param part
	 *            エディター
	 */
	public MultivalueAndCreateAction(IWorkbenchPart part) {
		super(part);
		setText(Messages.CreateMultivalueAnd);
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
		CompoundCommand ccommand = new CompoundCommand();
		// Detailとスーパーセットを追加してHeaderと接続
		HeaderDetailCreateCommand command = new HeaderDetailCreateCommand(model);
		ccommand.add(command);
		// 多値のリレーションをHeaderから削除してDetailと再接続
		if (EntityTypeRule.isEvent(model)) {
			for (AbstractConnectionModel con : model.getModelTargetConnections()) {
				if (con instanceof AbstractRelationship) {
					AbstractRelationship relation = (AbstractRelationship) con;
					if (relation.isMultiValue()) {
						AbstractEntityModel source = relation.getSource();
						ConnectionDeleteCommand command2 = new ConnectionDeleteCommand(relation);
						ccommand.add(command2);
						Detail detail = (Detail) command.getRelationship().getTarget();
						AbstractRelationship newRelation = RelationshipRule
								.createRelationship(source, detail);
						newRelation.setTargetCardinality(Cardinality.MANY);
						ConnectionCreateCommand command3 = new ConnectionCreateCommand(newRelation,
								source, detail);
						ccommand.add(command3);
					}
				}
			}
		}
		execute(ccommand.unwrap());
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

		public Header2DetailRelationship getRelationship() {
			return relationship;
		}

	}
}

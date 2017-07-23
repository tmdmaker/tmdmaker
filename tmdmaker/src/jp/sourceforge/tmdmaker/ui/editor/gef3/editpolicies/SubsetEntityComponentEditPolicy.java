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
package jp.sourceforge.tmdmaker.ui.editor.gef3.editpolicies;

import java.util.List;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.requests.GroupRequest;

import jp.sourceforge.tmdmaker.Messages;
import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.Diagram;
import jp.sourceforge.tmdmaker.model.SubsetEntity;
import jp.sourceforge.tmdmaker.model.SubsetType;
import jp.sourceforge.tmdmaker.model.SubsetType2SubsetRelationship;
import jp.sourceforge.tmdmaker.model.rule.ImplementRule;
import jp.sourceforge.tmdmaker.ui.dialogs.ModelEditDialog;
import jp.sourceforge.tmdmaker.ui.dialogs.TableEditDialog;
import jp.sourceforge.tmdmaker.ui.dialogs.models.EditAttribute;
import jp.sourceforge.tmdmaker.ui.editor.gef3.commands.ImplementDerivationModelsDeleteCommand;
import jp.sourceforge.tmdmaker.ui.editor.gef3.commands.SubsetTypeDeleteCommand;

/**
 * 
 * @author nakaG
 * 
 */
public class SubsetEntityComponentEditPolicy extends AbstractEntityModelEditPolicy<SubsetEntity> {
	@Override
	protected ModelEditDialog<SubsetEntity> getDialog() {
		return new TableEditDialog<SubsetEntity>(getControllShell(), Messages.EditSubset,
				getModel());
	}

	
	/**
	 * 自分自身が実装対象でない場合に実行するコマンドを生成する。
	 * 
	 * @param editedValue
	 * @return
	 */
	private Command getDeleteCommand(AbstractEntityModel editedValue) {
		AbstractEntityModel table = getModel();
		if (table.isNotImplement() && !editedValue.isNotImplement()) {
			AbstractEntityModel original = ImplementRule.findOriginalImplementModel(table);
			return new ImplementDerivationModelsDeleteCommand(table, original);
		}
		return null;
	}
	
	@Override
	protected Command createEditCommand(List<EditAttribute> editAttributeList,
			AbstractEntityModel editedValue) {
		CompoundCommand ccommand = new CompoundCommand();
		ccommand.add(super.createEditCommand(editAttributeList, editedValue));
		Command deleteCommand = getDeleteCommand(editedValue);
		if (deleteCommand != null) {
			ccommand.add(deleteCommand);
		}
		return ccommand.unwrap();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.editpolicies.ComponentEditPolicy#createDeleteCommand(org.eclipse.gef.requests.GroupRequest)
	 */
	@Override
	protected Command createDeleteCommand(GroupRequest deleteRequest) {
		CompoundCommand ccommand = new CompoundCommand();
		SubsetEntityDeleteCommand command1 = new SubsetEntityDeleteCommand(getDiagram(), getModel());
		ccommand.add(command1);
		if (getModel().isNotImplement()) {
			AbstractEntityModel original = ImplementRule.findOriginalImplementModel(getModel());
			ccommand.add(new ImplementDerivationModelsDeleteCommand(getModel(), original));
		}
		SubsetType2SubsetRelationship relationship = (SubsetType2SubsetRelationship) getModel()
				.findRelationshipFromTargetConnections(SubsetType2SubsetRelationship.class)
				.get(0);
		SubsetTypeDeleteCommand command2 = new SubsetTypeDeleteCommand(getDiagram(),
				(SubsetType) relationship.getSource());
		ccommand.add(command2);
		return ccommand;
	}
	/**
	 * 
	 * @author nakaG
	 * 
	 */
	private static class SubsetEntityDeleteCommand extends Command {
		/** 親 */
		private Diagram diagram;
		/** 削除対象 */
		private SubsetEntity model;
		/** サブセットタイプとのコネクション */
		private SubsetType2SubsetRelationship subsetType2SubsetEntityRelationship;

		/**
		 * コンストラクタ
		 * 
		 * @param diagram
		 *            親
		 * @param model
		 *            削除対象
		 */
		public SubsetEntityDeleteCommand(Diagram diagram, SubsetEntity model) {
			super();
			this.diagram = diagram;
			this.model = model;
			this.subsetType2SubsetEntityRelationship = (SubsetType2SubsetRelationship) this.model
					.findRelationshipFromTargetConnections(SubsetType2SubsetRelationship.class)
					.get(0);
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.gef.commands.Command#canExecute()
		 */
		@Override
		public boolean canExecute() {
			return model.isDeletable();
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.gef.commands.Command#execute()
		 */
		@Override
		public void execute() {
			this.subsetType2SubsetEntityRelationship.disconnect();
			this.diagram.removeChild(this.model);

		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.gef.commands.Command#undo()
		 */
		@Override
		public void undo() {
			this.diagram.addChild(this.model);
			this.subsetType2SubsetEntityRelationship.connect();
		}

	}

}
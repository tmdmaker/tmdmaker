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

import java.util.List;

import jp.sourceforge.tmdmaker.dialog.SubsetCreateDialog;
import jp.sourceforge.tmdmaker.dialog.model.EditSubsetEntity;
import jp.sourceforge.tmdmaker.editpart.AbstractEntityEditPart;
import jp.sourceforge.tmdmaker.editpart.LaputaEditPart;
import jp.sourceforge.tmdmaker.editpart.MultivalueAndAggregatorEditPart;
import jp.sourceforge.tmdmaker.editpart.SubsetTypeEditPart;
import jp.sourceforge.tmdmaker.editpart.VirtualSupersetEditPart;
import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.Constraint;
import jp.sourceforge.tmdmaker.model.Diagram;
import jp.sourceforge.tmdmaker.model.Entity2SubsetTypeRelationship;
import jp.sourceforge.tmdmaker.model.IAttribute;
import jp.sourceforge.tmdmaker.model.RelatedRelationship;
import jp.sourceforge.tmdmaker.model.SubsetEntity;
import jp.sourceforge.tmdmaker.model.SubsetType;
import jp.sourceforge.tmdmaker.model.SubsetType.SubsetTypeValue;
import jp.sourceforge.tmdmaker.model.SubsetType2SubsetRelationship;
import jp.sourceforge.tmdmaker.model.command.ImplementDerivationModelsDeleteCommand;
import jp.sourceforge.tmdmaker.model.command.SubsetTypeDeleteCommand;
import jp.sourceforge.tmdmaker.model.rule.ImplementRule;
import jp.sourceforge.tmdmaker.model.rule.SubsetRule;

import org.eclipse.gef.commands.Command;
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
			return selection instanceof AbstractEntityEditPart
					&& !(selection instanceof SubsetTypeEditPart)
					&& !(selection instanceof MultivalueAndAggregatorEditPart)
					&& !(selection instanceof VirtualSupersetEditPart)
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
		AbstractEntityEditPart part = getPart();
		AbstractEntityModel model = getModel();
		SubsetType subsetType = SubsetRule.setupSubsetType(model);

		SubsetCreateDialog dialog = new SubsetCreateDialog(
				part.getViewer().getControl().getShell(), subsetType.getSubsetType(),
				subsetType.isExceptNull(), model.getAttributes(),
				subsetType.findSubsetEntityList(), subsetType.getPartitionAttribute());
		// dialog.initializeValue(true, false, null);
		if (dialog.open() == Dialog.OK) {
			CompoundCommand ccommand = new CompoundCommand();
			SubsetType.SubsetTypeValue newSubsetType = dialog.getEditedSubsetType();
			IAttribute selectedPartitionAttribute = dialog.getEditedPartitionAttribute();
			boolean newExceptNull = dialog.isEditedExceptNull();
			ccommand.add(createSuitableSubsetTypeCommand(model, subsetType, newSubsetType,
					selectedPartitionAttribute, newExceptNull));

			List<EditSubsetEntity> editSubsets = dialog.getEditedSubsetEntities();
			addSuitableSubsetEntityCommand(model, subsetType, ccommand, editSubsets);

			List<EditSubsetEntity> deleteSubsets = dialog.getDeletedSubsetEntities();
			addSubsetDeleteCommand(model, subsetType, ccommand, deleteSubsets);
			execute(ccommand);
		}
	}

	private void addSubsetDeleteCommand(AbstractEntityModel model, SubsetType subsetType,
			CompoundCommand ccommand, List<EditSubsetEntity> deleteSubsets) {
		AbstractEntityModel original = null;
		for (EditSubsetEntity e : deleteSubsets) {
			SubsetEntity subset = e.getOriginal();
			if (original == null) {
				original = ImplementRule.findOriginalImplementModel(subset);
			}
			SubsetDeleteCommand command = new SubsetDeleteCommand(subset);
			ccommand.add(command);
			if (subset.isNotImplement()) {
				ccommand.add(new ImplementDerivationModelsDeleteCommand(subset, original));
			}
		}
		if (deleteSubsets.size() > 0) {
			SubsetTypeDeleteCommand command = new SubsetTypeDeleteCommand(model.getDiagram(),
					subsetType);
			ccommand.add(command);
		}
	}

	private void addSuitableSubsetEntityCommand(AbstractEntityModel model, SubsetType subsetType,
			CompoundCommand ccommand, List<EditSubsetEntity> editSubsets) {
		for (EditSubsetEntity e : editSubsets) {
			if (e.isAdded()) {
				Command command = new SubsetCreateCommand(model, subsetType,
						SubsetRule.createSubsetEntity(model, e.getName()));
				ccommand.add(command);
			} else if (e.isNameChanged()) {
				SubsetEntity subsetEntity = e.getOriginal();
				SubsetNameChangeCommand command = new SubsetNameChangeCommand(subsetEntity,
						e.getName());
				ccommand.add(command);
			}
		}
	}

	private Command createSuitableSubsetTypeCommand(AbstractEntityModel model,
			SubsetType subsetType, SubsetTypeValue newSubsetType,
			IAttribute selectedPartitionAttribute, boolean newExceptNull) {
		if (subsetType.getConstraint() == null) {
			// entityとpartitionCodeModelの接続
			Constraint constraint = model.getConstraint().getTranslated(0, 50);
			subsetType.setConstraint(constraint);
			subsetType.setExceptNull(newExceptNull);
			subsetType.setSubsetType(newSubsetType);
			return new Entity2SubsetTypeCreateCommand(model, subsetType, selectedPartitionAttribute);
		} else {

			return new SubsetTypeChangeCommand(subsetType, newSubsetType,
					selectedPartitionAttribute, newExceptNull);
		}
	}

	/**
	 * 
	 * @author nakaG
	 * 
	 */
	private static class SubsetTypeChangeCommand extends Command {
		private SubsetType subsetType;
		private SubsetType.SubsetTypeValue newSubsetTypeValue;
		private SubsetType.SubsetTypeValue oldSubsetTypeValue;
		private IAttribute oldPartitionAttribute;
		private IAttribute newPartitionAttribute;
		private boolean oldExceptNull;
		private boolean newExceptNull;
		private Entity2SubsetTypeRelationship relationship;

		/**
		 * コンストラクタ
		 * 
		 * @param subsetType
		 * @param newSubsetTypeValue
		 * @param selectedPartitionAttribute
		 * @param newExceptNull
		 */
		public SubsetTypeChangeCommand(SubsetType subsetType, SubsetTypeValue newSubsetTypeValue,
				IAttribute selectedPartitionAttribute, boolean newExceptNull) {
			this.subsetType = subsetType;
			this.oldSubsetTypeValue = subsetType.getSubsetType();
			this.newSubsetTypeValue = newSubsetTypeValue;
			this.newPartitionAttribute = selectedPartitionAttribute;
			this.newExceptNull = newExceptNull;
			this.oldExceptNull = subsetType.isExceptNull();
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.gef.commands.Command#execute()
		 */
		@Override
		public void execute() {
			this.subsetType.setSubsetType(newSubsetTypeValue);
			if (this.subsetType.getModelTargetConnections() != null
					&& this.subsetType.getModelTargetConnections().size() > 0) {
				this.relationship = (Entity2SubsetTypeRelationship) this.subsetType
						.getModelTargetConnections().get(0);
				this.oldPartitionAttribute = this.relationship.getPartitionAttribute();
				this.subsetType.setExceptNull(newExceptNull);
				this.subsetType.setPartitionAttribute(newPartitionAttribute);

				// this.relationship.setPartitionAttribute(newPartitionAttribute);
			}
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.gef.commands.Command#undo()
		 */
		@Override
		public void undo() {
			this.subsetType.setSubsetType(oldSubsetTypeValue);
			if (this.relationship != null) {
				this.subsetType.setExceptNull(oldExceptNull);
				this.subsetType.setPartitionAttribute(oldPartitionAttribute);
				// this.relationship.setPartitionAttribute(oldPartitionAttribute);
			}
		}

	}

	/**
	 * エンティティ系モデルとサブセット種類を接続するCommand
	 * 
	 * @author nakaG
	 * 
	 */
	private static class Entity2SubsetTypeCreateCommand extends Command {
		private Diagram diagram;
		private AbstractEntityModel model;
		private SubsetType subsetType;
		private Entity2SubsetTypeRelationship model2subsetRelationship;

		public Entity2SubsetTypeCreateCommand(AbstractEntityModel model, SubsetType subsetType,
				IAttribute partitionAttribute) {
			this.diagram = model.getDiagram();
			this.model = model;
			this.subsetType = subsetType;
			this.model2subsetRelationship = new Entity2SubsetTypeRelationship(this.model,
					this.subsetType);
			this.subsetType.setPartitionAttribute(partitionAttribute);
			// this.model2subsetRelationship
			// .setPartitionAttribute(partitionAttribute);
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.gef.commands.Command#execute()
		 */
		@Override
		public void execute() {
			diagram.addChild(subsetType);
			model2subsetRelationship.connect();
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.gef.commands.Command#undo()
		 */
		@Override
		public void undo() {
			model2subsetRelationship.disconnect();
			diagram.removeChild(subsetType);
		}
	}

	/**
	 * サブセット作成Command
	 * 
	 * @author nakaG
	 * 
	 */
	private static class SubsetCreateCommand extends Command {
		private Diagram diagram;
		private AbstractEntityModel model;
		private SubsetType subsetType;
		private Entity2SubsetTypeRelationship model2subsetTypeRelationship;
		private SubsetEntity subsetEntity;
		private SubsetType2SubsetRelationship subsetType2subsetEntityRelationship;

		/**
		 * コンストラクタ
		 * 
		 * @param model
		 * @param subsetType
		 * @param subsetEntity
		 */
		public SubsetCreateCommand(AbstractEntityModel model, SubsetType subsetType,
				SubsetEntity subsetEntity) {
			super();
			this.model = model;
			this.subsetType = subsetType;
			this.diagram = model.getDiagram();
			this.subsetEntity = subsetEntity;
			subsetType2subsetEntityRelationship = new SubsetType2SubsetRelationship(
					this.subsetType, this.subsetEntity);
			Constraint constraint = subsetType.getConstraint().getTranslated(0, 50);
			subsetEntity.setConstraint(constraint);
		}

		/**
		 * 
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.gef.commands.Command#execute()
		 */
		@Override
		public void execute() {
			if (subsetType.getModelTargetConnections().size() == 0) {
				this.model2subsetTypeRelationship = new Entity2SubsetTypeRelationship(this.model,
						this.subsetType);
			} else {
				this.model2subsetTypeRelationship = (Entity2SubsetTypeRelationship) subsetType
						.getModelTargetConnections().get(0);
			}
			model2subsetTypeRelationship.connect();
			diagram.addChild(subsetEntity);
			subsetType2subsetEntityRelationship.connect();
		}

		/**
		 * 
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.gef.commands.Command#undo()
		 */
		@Override
		public void undo() {
			subsetType2subsetEntityRelationship.disconnect();
			diagram.removeChild(subsetEntity);
			model2subsetTypeRelationship.disconnect();
		}
	}

	/**
	 * サブセット名変更Command
	 * 
	 * @author nakaG
	 * 
	 */
	private static class SubsetNameChangeCommand extends Command {
		private SubsetEntity model;
		private String oldName;
		private String newName;

		/**
		 * コンストラクタ
		 * 
		 * @param model
		 * @param name
		 */
		public SubsetNameChangeCommand(SubsetEntity model, String name) {
			this.model = model;
			this.oldName = model.getName();
			this.newName = name;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.gef.commands.Command#execute()
		 */
		@Override
		public void execute() {
			this.model.setName(newName);
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.gef.commands.Command#undo()
		 */
		@Override
		public void undo() {
			this.model.setName(oldName);
		}

	}

	/**
	 * サブセット削除Command
	 * 
	 * @author nakaG
	 * 
	 */
	private static class SubsetDeleteCommand extends Command {
		private SubsetEntity model;
		private Diagram diagram;
		private RelatedRelationship relationship;

		/**
		 * コンストラクタ
		 * 
		 * @param model
		 * @param subsetType
		 */
		public SubsetDeleteCommand(SubsetEntity model) {
			this.model = model;
			this.diagram = model.getDiagram();
			this.relationship = (RelatedRelationship) model.getModelTargetConnections().get(0);
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.gef.commands.Command#execute()
		 */
		@Override
		public void execute() {
			this.relationship.disconnect();
			this.diagram.removeChild(model);
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.gef.commands.Command#undo()
		 */
		@Override
		public void undo() {
			this.diagram.addChild(model);
			this.relationship.connect();
		}

	}
}

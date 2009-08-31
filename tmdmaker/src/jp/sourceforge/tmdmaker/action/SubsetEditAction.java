package jp.sourceforge.tmdmaker.action;

import java.util.ArrayList;
import java.util.List;

import jp.sourceforge.tmdmaker.dialog.EditSubsetEntity;
import jp.sourceforge.tmdmaker.dialog.SubsetEditDialog2;
import jp.sourceforge.tmdmaker.editpart.AbstractEntityEditPart;
import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.Attribute;
import jp.sourceforge.tmdmaker.model.Diagram;
import jp.sourceforge.tmdmaker.model.Entity2SubsetTypeRelationship;
import jp.sourceforge.tmdmaker.model.RelatedRelationship;
import jp.sourceforge.tmdmaker.model.SubsetEntity;
import jp.sourceforge.tmdmaker.model.SubsetType;
import jp.sourceforge.tmdmaker.model.SubsetType2SubsetRelationship;
import jp.sourceforge.tmdmaker.model.SubsetType.SubsetTypeValue;
import jp.sourceforge.tmdmaker.model.command.SubsetTypeDeleteCommand;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.ui.IWorkbenchPart;

/**
 * 
 * @author nakaG
 * 
 */
public class SubsetEditAction extends AbstractEntitySelectionAction {
	/** サブセット編集アクションを表す定数 */
	public static final String ID = "_SUBSET";

	/**
	 * コンストラクタ
	 * 
	 * @param part
	 *            エディター
	 */
	public SubsetEditAction(IWorkbenchPart part) {
		super(part);
		setText("サブセット編集");
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
		AbstractEntityEditPart part = getPart();
		AbstractEntityModel model = getModel();
		SubsetType subsetType = model.findSubset();
		SubsetType.SubsetTypeValue subsetTypeValue;
		boolean exceptNull;
		List<SubsetEntity> subsetEntities;
		Attribute selectedAttribute;
		if (subsetType == null) {
			subsetType = new SubsetType();;
			subsetTypeValue = SubsetType.SubsetTypeValue.SAME;
			exceptNull = false;
			subsetEntities = new ArrayList<SubsetEntity>();
			selectedAttribute = null;
		} else {
			subsetTypeValue = subsetType.getSubsetType();
			exceptNull = subsetType.isExceptNull();
			subsetEntities = subsetType.findSubsetEntityList();
			selectedAttribute = ((Entity2SubsetTypeRelationship) subsetType
					.getModelTargetConnections().get(0))
					.getPartitionAttribute();
		}
		SubsetEditDialog2 dialog = new SubsetEditDialog2(part.getViewer().getControl().getShell(), subsetTypeValue, exceptNull, model.getAttributes(), subsetEntities, selectedAttribute);
//		dialog.initializeValue(true, false, null);
		if (dialog.open() == Dialog.OK) {
			CompoundCommand ccommand = new CompoundCommand();
			SubsetType.SubsetTypeValue newSubsetType = dialog.getEditedSubsetType();
			Attribute selectedPartitionAttribute = dialog
			.getEditedPartitionAttribute();
			boolean newExceptNull = dialog.isEditedExceptNull();
			ccommand.add(createPreferSubsetTypeCommand(model, subsetType, newSubsetType, selectedPartitionAttribute, newExceptNull));
			
			List<EditSubsetEntity> editSubsets = dialog.getEditedSubsetEntities();
			addPreferSubsetEntityCommand(model, subsetType, ccommand,
					editSubsets);
			
			List<EditSubsetEntity> deleteSubsets = dialog
			.getDeletedSubsetEntities();
			addDeleteSubsetEntityCommand(model, subsetType, ccommand,
					deleteSubsets);
			execute(ccommand);
		}
//		SubsetEditDialog dialog = new SubsetEditDialog(part.getViewer()
//				.getControl().getShell(), sameType, model.getAttributes(),
//				subsetEntities, selectedAttribute);
//		if (dialog.open() == Dialog.OK) {
//			CompoundCommand ccommand = new CompoundCommand();
//			SubsetType.SubsetTypeValue newSubsetType;
//			if (dialog.isSubsetSameType()) {
//				newSubsetType = SubsetType.SubsetTypeValue.SAME;
//			} else {
//				newSubsetType = SubsetType.SubsetTypeValue.DIFFERENT;
//			}
//			Attribute selectedPartitionAttribute = dialog
//					.getSelectedPartitionAttribute();
//
//			if (subsetType == null) {
//				// entityとpartitionCodeModelの接続
//				subsetType = new SubsetType();
//				Rectangle constraint = model.getConstraint().getTranslated(0,
//						50);
//				subsetType.setConstraint(constraint);
//
//				Entity2SubsetCreateCommand createCmd = new Entity2SubsetCreateCommand(
//						model, subsetType, selectedPartitionAttribute);
//				ccommand.add(createCmd);
//			} else {
//
//				SubsetChangeCommand changeCmd = new SubsetChangeCommand(
//						subsetType, newSubsetType, selectedPartitionAttribute);
//				ccommand.add(changeCmd);
//			}
//			// 追加更新分
//			List<EditSubsetEntity> editSubsets = dialog.getSubsets();
//			for (EditSubsetEntity e : editSubsets) {
//				if (e.isAdded()) {
//					SubsetEntity subsetEntity = new SubsetEntity();
//					subsetEntity.setName(e.getName());
//					subsetEntity.setOriginalReuseKey(model.getMyReuseKey());
//					subsetEntity.setEntityType(model.getEntityType());
//					// subsetEntity.setOriginal(model);
//					Command command = new SubsetCreateCommand(model,
//							subsetType, subsetEntity);
//					ccommand.add(command);
//				} else if (e.isNameChanged()) {
//					SubsetEntity subsetEntity = e.getSubsetEntity();
//					SubsetEditCommand command = new SubsetEditCommand(
//							subsetEntity, e.getName());
//					ccommand.add(command);
//				}
//			}
//			List<SubsetEntity> deleteSubsets = dialog
//					.getDeletedSubsetEntities();
//			for (SubsetEntity e : deleteSubsets) {
//				SubsetDeleteCommand command = new SubsetDeleteCommand(e,
//						subsetType);
//				ccommand.add(command);
//			}
//			if (deleteSubsets.size() > 0) {
//				SubsetTypeDeleteCommand command = new SubsetTypeDeleteCommand(
//						model.getDiagram(), subsetType);
//				ccommand.add(command);
//			}
//			execute(ccommand);
//		}
	}

	private void addDeleteSubsetEntityCommand(AbstractEntityModel model,
			SubsetType subsetType, CompoundCommand ccommand,
			List<EditSubsetEntity> deleteSubsets) {
		for (EditSubsetEntity e : deleteSubsets) {
			SubsetDeleteCommand command = new SubsetDeleteCommand(e.getOriginal(),
					subsetType);
			ccommand.add(command);
		}
		if (deleteSubsets.size() > 0) {
			SubsetTypeDeleteCommand command = new SubsetTypeDeleteCommand(
					model.getDiagram(), subsetType);
			ccommand.add(command);
		}
	}

	private void addPreferSubsetEntityCommand(AbstractEntityModel model,
			SubsetType subsetType, CompoundCommand ccommand,
			List<EditSubsetEntity> editSubsets) {
		for (EditSubsetEntity e : editSubsets) {
			if (e.isAdded()) {
				SubsetEntity subsetEntity = new SubsetEntity();
				subsetEntity.setName(e.getName());
				subsetEntity.setOriginalReuseKey(model.createReusedIdentifier());
				subsetEntity.setEntityType(model.getEntityType());
				// subsetEntity.setOriginal(model);
				Command command = new SubsetCreateCommand(model,
						subsetType, subsetEntity);
				ccommand.add(command);
			} else if (e.isNameChanged()) {
				SubsetEntity subsetEntity = e.getOriginal();
				SubsetEditCommand command = new SubsetEditCommand(
						subsetEntity, e.getName());
				ccommand.add(command);
			}
		}
	}

	private Command createPreferSubsetTypeCommand(AbstractEntityModel model,
			SubsetType subsetType, SubsetTypeValue newSubsetType,
			Attribute selectedPartitionAttribute, boolean newExceptNull) {
			if (subsetType.getConstraint() == null) {
			// entityとpartitionCodeModelの接続
			Rectangle constraint = model.getConstraint().getTranslated(0,
					50);
			subsetType.setConstraint(constraint);
			subsetType.setExceptNull(newExceptNull);
			subsetType.setSubsetType(newSubsetType);
			Entity2SubsetCreateCommand createCmd = new Entity2SubsetCreateCommand(
					model, subsetType, selectedPartitionAttribute);
			return createCmd;
		} else {
	
			SubsetChangeCommand changeCmd = new SubsetChangeCommand(
					subsetType, newSubsetType, selectedPartitionAttribute, newExceptNull);
			return changeCmd;
		}
	}

	/**
	 * 
	 * @author nakaG
	 *
	 */
	private static class SubsetChangeCommand extends Command {
		private SubsetType subset;
		private SubsetType.SubsetTypeValue newSubsetType;
		private SubsetType.SubsetTypeValue oldSubsetType;
		private Attribute oldPartitionAttribute;
		private Attribute newPartitionAttribute;
		private boolean oldExceptNull;
		private boolean newExceptNull;
		private Entity2SubsetTypeRelationship relationship;

		public SubsetChangeCommand(SubsetType subset,
				SubsetTypeValue newSubsetType,
				Attribute selectedPartitionAttribute, boolean newExceptNull) {
			this.subset = subset;
			this.oldSubsetType = subset.getSubsetType();
			this.newSubsetType = newSubsetType;
			this.newPartitionAttribute = selectedPartitionAttribute;
			this.newExceptNull = newExceptNull;
			this.oldExceptNull = subset.isExceptNull();
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.gef.commands.Command#execute()
		 */
		@Override
		public void execute() {
			this.subset.setSubsetType(newSubsetType);
			if (this.subset.getModelTargetConnections() != null
					&& this.subset.getModelTargetConnections().size() > 0) {
				this.relationship = (Entity2SubsetTypeRelationship) this.subset
						.getModelTargetConnections().get(0);
				this.oldPartitionAttribute = this.relationship
						.getPartitionAttribute();
				this.subset.setExceptNull(newExceptNull);
				this.subset.setPartitionAttribute(newPartitionAttribute);

//				this.relationship.setPartitionAttribute(newPartitionAttribute);
			}
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.gef.commands.Command#undo()
		 */
		@Override
		public void undo() {
			this.subset.setSubsetType(oldSubsetType);
			if (this.relationship != null) {
				this.subset.setExceptNull(oldExceptNull);
				this.subset.setPartitionAttribute(oldPartitionAttribute);
//				this.relationship.setPartitionAttribute(oldPartitionAttribute);
			}
		}

	}

	private static class Entity2SubsetCreateCommand extends Command {
		private Diagram diagram;
		private AbstractEntityModel model;
		private SubsetType subset;
		private Entity2SubsetTypeRelationship model2subsetRelationship;

		public Entity2SubsetCreateCommand(AbstractEntityModel model,
				SubsetType subset, Attribute partitionAttribute) {
			this.diagram = model.getDiagram();
			this.model = model;
			this.subset = subset;
			this.model2subsetRelationship = new Entity2SubsetTypeRelationship();
			this.model2subsetRelationship.setSource(this.model);
			this.model2subsetRelationship.setTarget(this.subset);
			this.subset.setPartitionAttribute(partitionAttribute);
//			this.model2subsetRelationship
//					.setPartitionAttribute(partitionAttribute);
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.gef.commands.Command#execute()
		 */
		@Override
		public void execute() {
			diagram.addChild(subset);
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
			diagram.removeChild(subset);
		}
	}

	/**
	 * 
	 */
	private static class SubsetCreateCommand extends Command {
		private Diagram diagram;
		private AbstractEntityModel model;
		private SubsetType subsetType;
		private Entity2SubsetTypeRelationship model2subsetRelationship;
		private SubsetEntity subsetEntity;
		private SubsetType2SubsetRelationship subset2entityRelationship;

		/**
		 * 
		 * @param model
		 * @param subsetType
		 * @param subsetEntity
		 */
		public SubsetCreateCommand(AbstractEntityModel model,
				SubsetType subsetType, SubsetEntity subsetEntity) {
			super();
			this.model = model;
			this.subsetType = subsetType;
			// Rectangle constraint = model.getConstraint().getTranslated(0,
			// 50);
			// this.subset.setConstraint(constraint);
			this.diagram = model.getDiagram();
			this.subsetEntity = subsetEntity;
			subset2entityRelationship = new SubsetType2SubsetRelationship();
			subset2entityRelationship.setSource(subsetType);
			subset2entityRelationship.setTarget(this.subsetEntity);
			Rectangle constraint = subsetType.getConstraint().getTranslated(0,
					50);
			subsetEntity.setConstraint(constraint);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.gef.commands.Command#execute()
		 */
		@Override
		public void execute() {
			if (subsetType.getModelTargetConnections().size() == 0) {
				this.model2subsetRelationship = new Entity2SubsetTypeRelationship();
				this.model2subsetRelationship.setSource(this.model);
				this.model2subsetRelationship.setTarget(this.subsetType);
			} else {
				this.model2subsetRelationship = (Entity2SubsetTypeRelationship) subsetType
						.getModelTargetConnections().get(0);
			}
			// diagram.addChild(subset);
			// subset.setDiagram(diagram);
			model2subsetRelationship.connect();
			// 変更後サブセットを追加
			diagram.addChild(subsetEntity);
			// subsetType.getSubsetEntityList().add(subsetEntity);
			// model.setSubset(subset);
//			subsetEntity.setDiagram(diagram);
			subset2entityRelationship.connect();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.gef.commands.Command#undo()
		 */
		@Override
		public void undo() {
			subset2entityRelationship.disconnect();
//			subsetEntity.setDiagram(null);
			// model.setSubset(null);
			// subsetType.getSubsetEntityList().remove(subsetEntity);
			diagram.removeChild(subsetEntity);
			model2subsetRelationship.disconnect();
			// diagram.removeChild(subset);
			// subset.setDiagram(null);
		}
	}

	/**
	 * 
	 * @author nakaG
	 * 
	 */
	private static class SubsetEditCommand extends Command {
		private SubsetEntity model;
		private String oldName;
		private String newName;

		public SubsetEditCommand(SubsetEntity model, String name) {
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

	private static class SubsetDeleteCommand extends Command {
		private SubsetEntity model;
		private Diagram diagram;
		private RelatedRelationship relationship;

		public SubsetDeleteCommand(SubsetEntity model, SubsetType subset) {
			this.model = model;
			this.diagram = model.getDiagram();
			this.relationship = (RelatedRelationship) model
					.getModelTargetConnections().get(0);
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.gef.commands.Command#execute()
		 */
		@Override
		public void execute() {
			this.relationship.disconnect();
			// this.subset.getSubsetEntityList().remove(model);
			this.diagram.removeChild(model);
//			this.model.setDiagram(null);
			// if (subset.getSubsetEntityList().size() == 0) {
			// this.model2subsetRelationship = (RelatedRelationship) subset
			// .getModelTargetConnections().get(0);
			// this.model2subsetRelationship.disConnect();
			// this.diagram.removeChild(this.subset);
			// this.subset.setDiagram(null);
			// }
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.gef.commands.Command#undo()
		 */
		@Override
		public void undo() {
			// this.subset.getSubsetEntityList().add(model);
			this.diagram.addChild(model);
//			this.model.setDiagram(diagram);
			this.relationship.connect();
			// if (this.model2subsetRelationship != null) {
			// this.diagram.addChild(this.subset);
			// this.subset.setDiagram(this.diagram);
			// this.model2subsetRelationship.connect();
			// }
		}

	}
}

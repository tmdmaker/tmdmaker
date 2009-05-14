package jp.sourceforge.tmdmaker.action;

import java.util.List;

import jp.sourceforge.tmdmaker.dialog.SubsetEditDialog;
import jp.sourceforge.tmdmaker.dialog.SubsetEditDialog.EditSubsetEntity;
import jp.sourceforge.tmdmaker.editpart.AbstractEntityEditPart;
import jp.sourceforge.tmdmaker.editpart.SubsetEditPart;
import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.Attribute;
import jp.sourceforge.tmdmaker.model.Diagram;
import jp.sourceforge.tmdmaker.model.Entity2SubsetRelationship;
import jp.sourceforge.tmdmaker.model.RelatedRelationship;
import jp.sourceforge.tmdmaker.model.Subset;
import jp.sourceforge.tmdmaker.model.SubsetEntity;
import jp.sourceforge.tmdmaker.model.Subset.SubsetType;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.ui.IWorkbenchPart;

/**
 * 
 * @author nakaG
 * 
 */
public class SubsetCreateAction extends SelectionAction {
	public static final String SUBSET = "_SUBSET";

	public SubsetCreateAction(IWorkbenchPart part) {
		super(part);
		setText("サブセット編集");
		setId(SUBSET);
	}

	@Override
	protected boolean calculateEnabled() {
		System.out.println("selection is :" + getSelection());
		System.out.println("count=" + getSelectedObjects().size());
		if (getSelectedObjects().size() == 1) {
			Object selection = getSelectedObjects().get(0);
			if (selection instanceof AbstractEntityEditPart && (selection instanceof SubsetEditPart) == false) {
				System.out
						.println("selection instanceof AbstractEntityEditPart");
				return true;
			} else {
				System.out
						.println("! selection instanceof AbstractEntityEditPart");
				return false;
			}
		} else {
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
		AbstractEntityEditPart part = (AbstractEntityEditPart) getSelectedObjects()
				.get(0);
		AbstractEntityModel model = (AbstractEntityModel) part.getModel();
		Subset subset = model.getSubset();
		boolean sameType;
		List<SubsetEntity> subsetEntities;
		if (subset == null) {
			sameType = true;
			subsetEntities = null;
		} else {
			sameType = subset.subsettype.equals(Subset.SubsetType.SAME);
			subsetEntities = subset.getSubsetEntityList();
		}
		
		SubsetEditDialog dialog = new SubsetEditDialog(part.getViewer()
				.getControl().getShell(), sameType, model.getAttributes(),
				subsetEntities);
		if (dialog.open() == Dialog.OK) {
			CompoundCommand ccommand = new CompoundCommand();
			Subset.SubsetType newSubsetType;
			if (dialog.isSubsetSameType()) {
				newSubsetType = Subset.SubsetType.SAME;
			} else {
				newSubsetType = Subset.SubsetType.DIFFERENT;
			}
			Attribute selectedPartitionAttribute = dialog
					.getSelectedPartitionAttribute();
			
			if (subset == null) {
				// entityとpartitionCodeModelの接続
				subset = new Subset(model);
				Rectangle constraint = model.getConstraint().getTranslated(0, 50);
				subset.setConstraint(constraint);
				
				Entity2SubsetCreateCommand createCmd = new Entity2SubsetCreateCommand(model, subset, selectedPartitionAttribute);
				ccommand.add(createCmd);
			} else {

				SubsetChangeCommand changeCmd = new SubsetChangeCommand(subset,
						newSubsetType, selectedPartitionAttribute);
				ccommand.add(changeCmd);
			}
			// 追加更新分
			List<EditSubsetEntity> editSubsets = dialog.getSubsets();
			for (EditSubsetEntity e : editSubsets) {
				if (e.isAdded()) {
					SubsetEntity subsetEntity = new SubsetEntity();
					subsetEntity.setName(e.getName());
					Command command = new SubsetCreateCommand(model, subset,
							subsetEntity);
					ccommand.add(command);
				} else if (e.isNameChanged()) {
					SubsetEntity subsetEntity = e.getSubsetEntity();
					SubsetEditCommand command = new SubsetEditCommand(
							subsetEntity, e.getName());
					ccommand.add(command);
				}
			}
			List<SubsetEntity> deleteSubsets = dialog
					.getDeletedSubsetEntities();
			for (SubsetEntity e : deleteSubsets) {
				SubsetDeleteCommand command = new SubsetDeleteCommand(e, subset);
				ccommand.add(command);
			}
			execute(ccommand);
		}
	}

	private static class SubsetChangeCommand extends Command {
		private Subset subset;
		private Subset.SubsetType newSubsetType;
		private Subset.SubsetType oldSubsetType;
		private Attribute oldPartitionAttribute;
		private Attribute newPartitionAttribute;
		private Entity2SubsetRelationship relationship;

		public SubsetChangeCommand(Subset subset, SubsetType newSubsetType,
				Attribute selectedPartitionAttribute) {
			this.subset = subset;
			this.oldSubsetType = subset.getSubsettype();
			this.newSubsetType = newSubsetType;
			this.newPartitionAttribute = selectedPartitionAttribute;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.gef.commands.Command#execute()
		 */
		@Override
		public void execute() {
			this.subset.setSubsettype(newSubsetType);
			if (this.subset.getModelTargetConnections() != null
					&& this.subset.getModelTargetConnections().size() > 0) {
				this.relationship = (Entity2SubsetRelationship) this.subset
						.getModelTargetConnections().get(0);
				this.oldPartitionAttribute = this.relationship
						.getPartitionAttribute();
				this.relationship.setPartitionAttribute(newPartitionAttribute);
			}
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.gef.commands.Command#undo()
		 */
		@Override
		public void undo() {
			this.subset.setSubsettype(oldSubsetType);
			if (this.relationship != null) {
				this.relationship.setPartitionAttribute(oldPartitionAttribute);
			}
		}

	}

	private static class Entity2SubsetCreateCommand extends Command {
		private Diagram diagram;
		private AbstractEntityModel model;
		private Subset subset;
		private Entity2SubsetRelationship model2subsetRelationship;
//		private Attribute selectedPartitionAttribute;
		
		public Entity2SubsetCreateCommand(AbstractEntityModel model, Subset subset, Attribute partitionAttribute) {
			this.diagram = model.getDiagram();
			this.model = model;
			this.subset = subset;
			this.model2subsetRelationship = new Entity2SubsetRelationship();
			this.model2subsetRelationship.setSource(this.model);
			this.model2subsetRelationship.setTarget(this.subset);
			this.model2subsetRelationship.setPartitionAttribute(partitionAttribute);
//			this.selectedPartitionAttribute = selectedPartitionAttribute;
		}

		/**
		 * {@inheritDoc}
		 * @see org.eclipse.gef.commands.Command#execute()
		 */
		@Override
		public void execute() {
			diagram.addChild(subset);
			subset.setDiagram(diagram);
			model2subsetRelationship.connect();			
		}

		/**
		 * {@inheritDoc}
		 * @see org.eclipse.gef.commands.Command#undo()
		 */
		@Override
		public void undo() {
			model2subsetRelationship.disConnect();
			subset.setDiagram(null);
			diagram.removeChild(subset);
		}
		
	}
	/**
	 * 
	 */
	private static class SubsetCreateCommand extends Command {
		private Diagram diagram;
		private AbstractEntityModel model;
		private Subset subset;
		private Entity2SubsetRelationship model2subsetRelationship;
		private SubsetEntity subsetEntity;
		private RelatedRelationship subset2entityRelationship;

		/**
		 * 
		 * @param model
		 */
		public SubsetCreateCommand(AbstractEntityModel model, Subset subset,
				SubsetEntity subsetEntity) {
			super();
			this.model = model;
			this.subset = subset;
			// Rectangle constraint = model.getConstraint().getTranslated(0,
			// 50);
			// this.subset.setConstraint(constraint);
			this.diagram = model.getDiagram();
			this.subsetEntity = subsetEntity;
			subset2entityRelationship = new RelatedRelationship();
			subset2entityRelationship.setSource(subset);
			subset2entityRelationship.setTarget(this.subsetEntity);
			Rectangle constraint = subset.getConstraint().getTranslated(0, 50);
			subsetEntity.setConstraint(constraint);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.gef.commands.Command#execute()
		 */
		@Override
		public void execute() {
			if (subset.getModelTargetConnections().size() == 0) {
				this.model2subsetRelationship = new Entity2SubsetRelationship();
				this.model2subsetRelationship.setSource(this.model);
				this.model2subsetRelationship.setTarget(this.subset);
			} else {
				this.model2subsetRelationship = (Entity2SubsetRelationship) subset
						.getModelTargetConnections().get(0);
			}
			diagram.addChild(subset);
			subset.setDiagram(diagram);
			model2subsetRelationship.connect();
			// 変更後サブセットを追加
			diagram.addChild(subsetEntity);
			subset.getSubsetEntityList().add(subsetEntity);
			model.setSubset(subset);
			subsetEntity.setDiagram(diagram);
			subset2entityRelationship.connect();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.gef.commands.Command#undo()
		 */
		@Override
		public void undo() {
			subset2entityRelationship.disConnect();
			subsetEntity.setDiagram(null);
			model.setSubset(null);
			subset.getSubsetEntityList().remove(subsetEntity);
			diagram.removeChild(subsetEntity);
			model2subsetRelationship.disConnect();
			diagram.removeChild(subset);
			subset.setDiagram(null);
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
		private Subset subset;
		private Diagram diagram;
		private RelatedRelationship relationship;
		private RelatedRelationship model2subsetRelationship;

		public SubsetDeleteCommand(SubsetEntity model, Subset subset) {
			this.model = model;
			this.diagram = model.getDiagram();
			this.subset = subset;
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
			this.relationship.disConnect();
			this.subset.getSubsetEntityList().remove(model);
			this.diagram.removeChild(model);
			this.model.setDiagram(null);
			if (subset.getSubsetEntityList().size() == 0) {
				this.model2subsetRelationship = (RelatedRelationship) subset
						.getModelTargetConnections().get(0);
				this.model2subsetRelationship.disConnect();
				this.diagram.removeChild(this.subset);
				this.subset.setDiagram(null);
			}
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.gef.commands.Command#undo()
		 */
		@Override
		public void undo() {
			this.subset.getSubsetEntityList().add(model);
			this.diagram.addChild(model);
			this.model.setDiagram(diagram);
			this.relationship.connect();
			if (this.model2subsetRelationship != null) {
				this.diagram.addChild(this.subset);
				this.subset.setDiagram(this.diagram);
				this.model2subsetRelationship.connect();
			}
		}

	}
}

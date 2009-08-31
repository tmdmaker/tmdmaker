package jp.sourceforge.tmdmaker.editpart;

import java.util.List;
import java.util.Map;

import jp.sourceforge.tmdmaker.dialog.EditAttribute;
import jp.sourceforge.tmdmaker.dialog.TableEditDialog2;
import jp.sourceforge.tmdmaker.editpolicy.AbstractEntityGraphicalNodeEditPolicy;
import jp.sourceforge.tmdmaker.figure.EntityFigure;
import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.Attribute;
import jp.sourceforge.tmdmaker.model.Diagram;
import jp.sourceforge.tmdmaker.model.Identifier;
import jp.sourceforge.tmdmaker.model.ReusedIdentifier;
import jp.sourceforge.tmdmaker.model.RelatedRelationship;
import jp.sourceforge.tmdmaker.model.SubsetEntity;
import jp.sourceforge.tmdmaker.model.SubsetType;
import jp.sourceforge.tmdmaker.model.command.SubsetTypeDeleteCommand;
import jp.sourceforge.tmdmaker.model.command.TableEditCommand;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;
import org.eclipse.jface.dialogs.Dialog;

/**
 * サブセットのコントローラ
 * 
 * @author nakaG
 * 
 */
public class SubsetEntityEditPart extends AbstractEntityEditPart {
	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#createFigure()
	 */
	@Override
	protected IFigure createFigure() {
		EntityFigure figure = new EntityFigure();
		updateFigure(figure);
		return figure;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.editpart.AbstractEntityEditPart#propertyChange(java.beans.PropertyChangeEvent)
	 */
	// @Override
	// public void propertyChange(PropertyChangeEvent evt) {
	// if (evt.getPropertyName().equals(AbstractEntityModel.PROPERTY_REUSEKEY))
	// {
	// logger.debug(getClass() + "#propertyChange().PROPERTY_REUSEKEY");
	// refreshVisuals();
	// SubsetEntity model = (SubsetEntity) getModel();
	// if (model.getEntityType().equals(EntityType.RESOURCE)) {
	// for (AbstractConnectionModel<?> con : model.getModelTargetConnections())
	// {
	// logger.debug("RESOURCE.source = " + con.getSource().getName());
	// //
	// con.getSource().firePropertyChange(AbstractEntityModel.PROPERTY_REUSEKEY,
	// null, null);
	// if (con instanceof IdentifierChangeListener && !(con instanceof
	// RelatedRelationship)) {
	// ((IdentifierChangeListener) con).awareReUseKeysChanged();
	// }
	// }
	// }
	// for (AbstractConnectionModel<?> con : model.getModelSourceConnections())
	// {
	// logger.debug("target = " + con.getTarget().getName());
	// //
	// con.getTarget().firePropertyChange(AbstractEntityModel.PROPERTY_REUSEKEY,
	// null, null);
	// if (con instanceof IdentifierChangeListener) {
	// ((IdentifierChangeListener) con).awareReUseKeysChanged();
	// }
	// }
	// } else {
	// super.propertyChange(evt);
	// }
	// }
	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.editpart.AbstractEntityEditPart#updateFigure(org.eclipse.draw2d.IFigure)
	 */
	@Override
	protected void updateFigure(IFigure figure) {
		logger.debug(getClass() + "#updateFigure()");

		EntityFigure entityFigure = (EntityFigure) figure;
		SubsetEntity entity = (SubsetEntity) getModel();
		entityFigure.setNotImplement(entity.isNotImplement());

		// List<Identifier> ids = entity.getReuseKeys().;

		List<Attribute> atts = entity.getAttributes();
		entityFigure.removeAllRelationship();
		entityFigure.removeAllAttributes();

		entityFigure.setEntityName(entity.getName());
		// entityFigure.setEntityType(entity.getEntityType().toString());
		// figure.setIdentifier(entity.getIdentifier().getName());
		for (Map.Entry<AbstractEntityModel, ReusedIdentifier> rk : entity
				.getReuseKey().entrySet()) {
			for (Identifier i : rk.getValue().getIdentifires()) {
				entityFigure.addRelationship(i.getName());
			}
		}
		for (Attribute a : atts) {
			entityFigure.addAttribute(a.getName());
		}
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#createEditPolicies()
	 */
	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE,
				new SubsetEntityComponentEditPolicy());
		installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE,
				new AbstractEntityGraphicalNodeEditPolicy());
	}

	/**
	 * サブセットタイプと接続しているリレーションシップモデルを取得する
	 * 
	 * @param connections
	 *            サブセットが保持している全リレーションシップ
	 * @return RelatedRelationship
	 */
	// protected static RelatedRelationship findRelatedRelationship(
	// List<AbstractConnectionModel> connections) {
	// for (AbstractConnectionModel<?> c : connections) {
	// if (c instanceof RelatedRelationship) {
	// return (RelatedRelationship) c;
	// }
	// }
	// return null;
	// }
	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.editpart.AbstractEntityEditPart#onDoubleClicked()
	 */
	@Override
	protected void onDoubleClicked() {
		logger.debug(getClass() + "#onDoubleClicked()");
		SubsetEntity table = (SubsetEntity) getModel();
		// TableEditDialog dialog = new TableEditDialog(getViewer().getControl()
		// .getShell(), table.getName(), table.getReuseKeys(), table
		// .getAttributes());
		TableEditDialog2 dialog = new TableEditDialog2(getViewer().getControl()
				.getShell(), "サブセット表編集", table);
		if (dialog.open() == Dialog.OK) {
			CompoundCommand ccommand = new CompoundCommand();

			List<EditAttribute> editAttributeList = dialog
					.getEditAttributeList();
			addAttributeEditCommands(ccommand, table, editAttributeList);

			TableEditCommand<SubsetEntity> command = new TableEditCommand<SubsetEntity>(
					table, (SubsetEntity) dialog.getEditedValue());
			ccommand.add(command);
			getViewer().getEditDomain().getCommandStack().execute(ccommand);
			// TableEditCommand<SubsetEntity> command = new
			// TableEditCommand<SubsetEntity>(
			// table, dialog.getEntityName(), dialog.getReuseKeys(),
			// dialog.getAttributes());
			// getViewer().getEditDomain().getCommandStack().execute(command);
		}
	}

	/**
	 * 
	 * @author nakaG
	 * 
	 */
	private static class SubsetEntityComponentEditPolicy extends
			ComponentEditPolicy {

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.gef.editpolicies.ComponentEditPolicy#createDeleteCommand(org.eclipse.gef.requests.GroupRequest)
		 */
		@Override
		protected Command createDeleteCommand(GroupRequest deleteRequest) {
			CompoundCommand ccommand = new CompoundCommand();
			Diagram diagram = (Diagram) getHost().getParent().getModel();
			SubsetEntity model = (SubsetEntity) getHost().getModel();
			SubsetEntityDeleteCommand command1 = new SubsetEntityDeleteCommand(
					diagram, model);
			ccommand.add(command1);
			// RelatedRelationship relationship = findRelatedRelationship(model
			// .getModelTargetConnections());
			RelatedRelationship relationship = (RelatedRelationship) model
					.findRelationshipFromTargetConnections(RelatedRelationship.class);
			SubsetTypeDeleteCommand command2 = new SubsetTypeDeleteCommand(
					diagram, (SubsetType) relationship.getSource());
			ccommand.add(command2);
			return ccommand;
		}

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
		private RelatedRelationship subsetType2SubsetEntityRelationship;

		// private SubsetType subsetType;

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
			this.subsetType2SubsetEntityRelationship = (RelatedRelationship) this.model
					.findRelationshipFromTargetConnections(RelatedRelationship.class);
			// findRelatedRelationship(this.model.getModelTargetConnections());
			// this.subsetType = (SubsetType)
			// subsetType2SubsetEntityRelationship
			// .getSource();
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
			// this.model.setDiagram(null);
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
			// this.model.setDiagram(this.diagram);
			this.subsetType2SubsetEntityRelationship.connect();
		}

	}
}

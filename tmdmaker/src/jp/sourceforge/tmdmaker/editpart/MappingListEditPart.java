package jp.sourceforge.tmdmaker.editpart;

import java.util.List;
import java.util.Map;

import jp.sourceforge.tmdmaker.dialog.TableEditDialog;
import jp.sourceforge.tmdmaker.figure.EntityFigure;
import jp.sourceforge.tmdmaker.model.AbstractConnectionModel;
import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.Attribute;
import jp.sourceforge.tmdmaker.model.Diagram;
import jp.sourceforge.tmdmaker.model.Identifier;
import jp.sourceforge.tmdmaker.model.MappingList;
import jp.sourceforge.tmdmaker.model.RelatedRelationship;
import jp.sourceforge.tmdmaker.model.Relationship;
import jp.sourceforge.tmdmaker.model.ReUseKeys;
import jp.sourceforge.tmdmaker.model.command.TableEditCommand;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;
import org.eclipse.jface.dialogs.Dialog;

public class MappingListEditPart extends AbstractEntityEditPart {
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
	 * 
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.editpart.AbstractEntityEditPart#updateFigure(org.eclipse.draw2d.IFigure)
	 */
	@Override
	protected void updateFigure(IFigure figure) {
		EntityFigure entityFigure = (EntityFigure) figure;
		MappingList table = (MappingList) getModel();
		// List<Identifier> ids = table.getReuseKeys();
		List<Attribute> atts = table.getAttributes();
		entityFigure.removeAllRelationship();
		entityFigure.removeAllAttributes();

		entityFigure.setEntityName(table.getName());
		for (Map.Entry<AbstractEntityModel, ReUseKeys> rk : table.getReuseKeys()
				.entrySet()) {
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
				new MappingListComponentEditPolicy());
	}

	
	/**
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.editpart.AbstractEntityEditPart#onDoubleClicked()
	 */
	@Override
	protected void onDoubleClicked() {
		logger.debug(getClass() + "#onDoubleClicked()");
		MappingList table = (MappingList) getModel();
		TableEditDialog dialog = new TableEditDialog(getViewer().getControl()
				.getShell(), table.getName(), table.getReuseKeys(), table
				.getAttributes());
		if (dialog.open() == Dialog.OK) {
			TableEditCommand<MappingList> command = new TableEditCommand<MappingList>(
					table, dialog.getEntityName(), dialog.getReuseKeys(),
					dialog.getAttributes());
//			MappingListEditCommand command = new MappingListEditCommand(
//					table, dialog.getEntityName(), dialog.getReuseKeys(),
//					dialog.getAttributes());
			getViewer().getEditDomain().getCommandStack().execute(command);
		}
	}


	private static class MappingListComponentEditPolicy extends
			ComponentEditPolicy {

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.eclipse.gef.editpolicies.ComponentEditPolicy#createDeleteCommand
		 * (org.eclipse.gef.requests.GroupRequest)
		 */
		@Override
		protected Command createDeleteCommand(GroupRequest deleteRequest) {
			MappingListDeleteCommand command = new MappingListDeleteCommand(
					(MappingList) getHost().getModel());
			return command;
		}

	}

	private static class MappingListDeleteCommand extends Command {
		private Diagram diagram;
		private MappingList model;
		private RelatedRelationship relatedRelationship;
		private Relationship relationship;

		public MappingListDeleteCommand(MappingList model) {
			this.model = model;
			this.diagram = model.getDiagram();
			this.relatedRelationship = findRelatedRelationship(model);
			this.relationship = (Relationship) relatedRelationship.getSource();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.gef.commands.Command#execute()
		 */
		@Override
		public void execute() {
//			diagram.removeChild(model);
//			model.setDiagram(null);
			relationship.disConnect();
		}

		private RelatedRelationship findRelatedRelationship(MappingList model) {
			this.model = model;
			for (AbstractConnectionModel c : this.model
					.getModelTargetConnections()) {
				if (c instanceof RelatedRelationship) {
					return (RelatedRelationship) c;
				}
			}
			return null;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.gef.commands.Command#undo()
		 */
		@Override
		public void undo() {
//			diagram.addChild(model);
//			model.setDiagram(diagram);
			relationship.connect();
		}
	}
}

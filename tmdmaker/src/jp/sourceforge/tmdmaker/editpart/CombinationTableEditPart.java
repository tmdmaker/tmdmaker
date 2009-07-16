package jp.sourceforge.tmdmaker.editpart;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jp.sourceforge.tmdmaker.dialog.TableEditDialog;
import jp.sourceforge.tmdmaker.editpolicy.AbstractEntityGraphicalNodeEditPolicy;
import jp.sourceforge.tmdmaker.figure.EntityFigure;
import jp.sourceforge.tmdmaker.model.AbstractConnectionModel;
import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.Attribute;
import jp.sourceforge.tmdmaker.model.CombinationTable;
import jp.sourceforge.tmdmaker.model.Diagram;
import jp.sourceforge.tmdmaker.model.Identifier;
import jp.sourceforge.tmdmaker.model.RelatedRelationship;
import jp.sourceforge.tmdmaker.model.AbstractRelationship;
import jp.sourceforge.tmdmaker.model.ReUseKeys;
import jp.sourceforge.tmdmaker.model.command.TableEditCommand;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;
import org.eclipse.jface.dialogs.Dialog;

/**
 * 
 * @author nakaG
 * 
 */
public class CombinationTableEditPart extends AbstractEntityEditPart {

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
		logger.debug(getClass() + "updateFigure()");
		EntityFigure entityFigure = (EntityFigure) figure;
		CombinationTable table = (CombinationTable) getModel();
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
				new CombinationTableComponentEditPolicy());
		installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE,
				new AbstractEntityGraphicalNodeEditPolicy());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.editpart.AbstractEntityEditPart#onDoubleClicked()
	 */
	@Override
	protected void onDoubleClicked() {
		logger.debug(getClass() + "#onDoubleClicked()");
		CombinationTable table = (CombinationTable) getModel();
		TableEditDialog dialog = new TableEditDialog(getViewer().getControl()
				.getShell(), table.getName(), table.getReuseKeys(), table
				.getAttributes());
		if (dialog.open() == Dialog.OK) {
			TableEditCommand<CombinationTable> command = new TableEditCommand<CombinationTable>(
					table, dialog.getEntityName(), dialog.getReuseKeys(),
					dialog.getAttributes());
			getViewer().getEditDomain().getCommandStack().execute(command);
		}

	}

	/**
	 * 
	 * @author nakaG
	 * 
	 */
	private static class CombinationTableComponentEditPolicy extends
			ComponentEditPolicy {
		/**
		 * 
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.gef.editpolicies.ComponentEditPolicy#createDeleteCommand(org.eclipse.gef.requests.GroupRequest)
		 */
		@Override
		protected Command createDeleteCommand(GroupRequest deleteRequest) {
			CombinationTableDeleteCommand command = new CombinationTableDeleteCommand(
					(Diagram) getHost().getParent().getModel(),
					(CombinationTable) getHost().getModel());
			return command;
		}
	}

	/**
	 * 
	 * @author nakaG
	 * 
	 */
	private static class CombinationTableDeleteCommand extends Command {
		private Diagram diagram;
		private CombinationTable model;
		private RelatedRelationship relatedRelationship;
		private AbstractRelationship relationship;
		private List<AbstractConnectionModel> sourceConnections = new ArrayList<AbstractConnectionModel>();
		private List<AbstractConnectionModel> targetConnections = new ArrayList<AbstractConnectionModel>();

		/**
		 * コンストラクタ
		 * 
		 * @param diagram
		 * @param model
		 */
		public CombinationTableDeleteCommand(Diagram diagram,
				CombinationTable model) {
			this.diagram = diagram;
			this.model = model;
			this.relatedRelationship = model.findCreationRelationship();
			sourceConnections.addAll(model.getModelSourceConnections());
			targetConnections.addAll(model.getModelTargetConnections());
			relationship = (AbstractRelationship) relatedRelationship.getSource();
		}

		@Override
		public boolean canExecute() {
			return model.canDeletable();
		}

		@Override
		public void execute() {
			// System.out.println(getClass().toString() + "#execute()");
			// diagram.removeChild(model);
			// model.setDiagram(null);
			// detachConnections(sourceConnections);
			// detachConnections(targetConnections);
			// relationship.detachSource();
			// relationship.detachTarget();
			relationship.disconnect();
		}

		@Override
		public void undo() {
			// diagram.addChild(model);
			// model.setDiagram(diagram);
			// attathConnections(sourceConnections);
			// attathConnections(targetConnections);
			// relationship.attachSource();
			// relationship.attachTarget();
			relationship.connect();
		}

//		private void setModel(Object model) {
//			this.model = (CombinationTable) model;
//			for (AbstractConnectionModel c : this.model
//					.getModelTargetConnections()) {
//				if (c instanceof RelatedRelationship) {
//					this.relatedRelationship = (RelatedRelationship) c;
//					break;
//				}
//			}
//		}

		private void detachConnections(List<AbstractConnectionModel> connections) {
			for (AbstractConnectionModel model : connections) {
				model.detachSource();
				model.detachTarget();
			}
		}

		private void attathConnections(List<AbstractConnectionModel> connections) {
			for (AbstractConnectionModel model : connections) {
				model.attachSource();
				model.attachTarget();
			}
		}
	}
}

package jp.sourceforge.tmdmaker.editpart;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jp.sourceforge.tmdmaker.editpolicy.AbstractEntityGraphicalNodeEditPolicy;
import jp.sourceforge.tmdmaker.figure.EntityFigure;
import jp.sourceforge.tmdmaker.model.AbstractConnectionModel;
import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.Attribute;
import jp.sourceforge.tmdmaker.model.CombinationTable;
import jp.sourceforge.tmdmaker.model.Diagram;
import jp.sourceforge.tmdmaker.model.Identifier;
import jp.sourceforge.tmdmaker.model.RelatedRelationship;
import jp.sourceforge.tmdmaker.model.Relationship;
import jp.sourceforge.tmdmaker.model.ReuseKey;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;
import org.eclipse.gef.requests.ReconnectRequest;

public class CombinationTableEditPart extends AbstractEntityEditPart {

	@Override
	protected IFigure createFigure() {
		EntityFigure figure = new EntityFigure();
		updateFigure(figure);

		return figure;
	}

	@Override
	protected void updateFigure(IFigure figure) {
		EntityFigure entityFigure = (EntityFigure) figure;
		CombinationTable table = (CombinationTable) getModel();
		// List<Identifier> ids = table.getReuseKeys();
		List<Attribute> atts = table.getAttributes();
		entityFigure.removeAllRelationship();
		entityFigure.removeAllAttributes();

		entityFigure.setEntityName(table.getName());
		for (Map.Entry<AbstractEntityModel, ReuseKey> rk : table.getReuseKeys()
				.entrySet()) {
			for (Identifier i : rk.getValue().getIdentifires()) {
				entityFigure.addRelationship(i.getName());
			}
		}
		for (Attribute a : atts) {
			entityFigure.addAttribute(a.getName());
		}
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE,
				new CombinationTableComponentEditPolicy());
		installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE,
				new AbstractEntityGraphicalNodeEditPolicy());
	}

	private static class CombinationTableComponentEditPolicy extends
			ComponentEditPolicy {

		@Override
		protected Command createDeleteCommand(GroupRequest deleteRequest) {
			CombinationTableDeleteCommand command = new CombinationTableDeleteCommand(
					(Diagram) getHost().getParent().getModel(),
					(CombinationTable) getHost().getModel());
			return command;
		}
	}

	private static class CombinationTableDeleteCommand extends Command {
		private Diagram diagram;
		private CombinationTable model;
		private RelatedRelationship relatedRelationship;
		private Relationship relationship;
		private List<AbstractConnectionModel> sourceConnections = new ArrayList<AbstractConnectionModel>();
		private List<AbstractConnectionModel> targetConnections = new ArrayList<AbstractConnectionModel>();

		public CombinationTableDeleteCommand(Diagram diagram,
				CombinationTable model) {
			this.diagram = diagram;
			setModel(model);
			sourceConnections.addAll(model.getModelSourceConnections());
			targetConnections.addAll(model.getModelTargetConnections());
			relationship = (Relationship) relatedRelationship.getSource();
		}

		@Override
		public boolean canExecute() {
			return model.getModelSourceConnections().size() == 0;
		}

		@Override
		public void execute() {
//			System.out.println(getClass().toString() + "#execute()");
//			diagram.removeChild(model);
//			model.setDiagram(null);
//			detachConnections(sourceConnections);
//			detachConnections(targetConnections);
//			relationship.detachSource();
//			relationship.detachTarget();
			relationship.disConnect();
		}

		@Override
		public void undo() {
//			diagram.addChild(model);
//			model.setDiagram(diagram);
//			attathConnections(sourceConnections);
//			attathConnections(targetConnections);
//			relationship.attachSource();
//			relationship.attachTarget();
			relationship.connect();
		}

		private void setModel(Object model) {
			this.model = (CombinationTable) model;
			for (AbstractConnectionModel c : this.model
					.getModelTargetConnections()) {
				if (c instanceof RelatedRelationship) {
					this.relatedRelationship = (RelatedRelationship) c;
					break;
				}
			}
		}

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

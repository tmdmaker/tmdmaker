package jp.sourceforge.tmdmaker.editpart;

import java.util.List;
import java.util.Map;

import jp.sourceforge.tmdmaker.figure.EntityFigure;
import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.Attribute;
import jp.sourceforge.tmdmaker.model.Diagram;
import jp.sourceforge.tmdmaker.model.Identifier;
import jp.sourceforge.tmdmaker.model.RecursiveTable;
import jp.sourceforge.tmdmaker.model.ReuseKey;
import jp.sourceforge.tmdmaker.model.command.ConnectableElementDeleteCommand;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

/**
 * 
 * @author nakaG
 *
 */
public class RecursiveTableEditPart extends AbstractEntityEditPart {

	@Override
	protected IFigure createFigure() {
		EntityFigure figure = new EntityFigure();
		updateFigure(figure);

		return figure;
	}
	private void updateFigure(EntityFigure figure) {
		RecursiveTable table = (RecursiveTable)getModel();
//		List<Identifier> ids = table.getReuseKeys();
		List<Attribute> atts = table.getAttributes();
		figure.removeAllRelationship();
		figure.removeAllAttributes();

		figure.setEntityName(table.getName());
		for (Map.Entry<AbstractEntityModel, ReuseKey> rk : table.getReuseKeys().entrySet()) {
			for (Identifier i : rk.getValue().getIdentifires()) {
				figure.addRelationship(i.getName());
			}
		}
		for (Attribute a : atts) {
			figure.addAttribute(a.getName());
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.editparts.AbstractEditPart#refreshVisuals()
	 */
	@Override
	protected void refreshVisuals() {
		System.out.println(getClass().toString() + "#refreshVisuals()");
		super.refreshVisuals();
		Object model = getModel();
		Rectangle bounds = new Rectangle(((AbstractEntityModel) model).getConstraint());
		((GraphicalEditPart) getParent()).setLayoutConstraint(this, getFigure(), bounds);

		updateFigure((EntityFigure) getFigure());
		refreshChildren();
	}
	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new RecursiveTableComponentEditPolicy());

	}
	private class RecursiveTableComponentEditPolicy extends ComponentEditPolicy {

		@Override
		protected Command createDeleteCommand(GroupRequest deleteRequest) {
			RecursiveTableDeleteCommand command = 
				new RecursiveTableDeleteCommand(
						(Diagram) getHost().getParent().getModel(),
						(RecursiveTable) getHost().getModel());
			return command;
		}
		
	}
	private class RecursiveTableDeleteCommand extends ConnectableElementDeleteCommand {
		private RecursiveTable model;

		public RecursiveTableDeleteCommand(Diagram diagram,
				RecursiveTable model) {
			super();
			this.diagram = diagram;
			this.model = model;
			this.sourceConnections.addAll(model.getModelSourceConnections());
			this.targetConnections.addAll(model.getModelTargetConnections());
		}

		@Override
		public void execute() {

			diagram.removeChild(model);

			detachConnections(sourceConnections);
			detachConnections(targetConnections);
		}

		@Override
		public void undo() {
			diagram.addChild(model);
			attathConnections(sourceConnections);
			attathConnections(targetConnections);
		}
	}
}

package jp.sourceforge.tmdmaker.editpart;

import java.util.List;
import java.util.Map;

import jp.sourceforge.tmdmaker.editpolicy.AbstractEntityGraphicalNodeEditPolicy;
import jp.sourceforge.tmdmaker.figure.EntityFigure;
import jp.sourceforge.tmdmaker.model.AbstractConnectionModel;
import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.Attribute;
import jp.sourceforge.tmdmaker.model.Diagram;
import jp.sourceforge.tmdmaker.model.Identifier;
import jp.sourceforge.tmdmaker.model.RelatedRelationship;
import jp.sourceforge.tmdmaker.model.ReuseKey;
import jp.sourceforge.tmdmaker.model.SubsetEntity;
import jp.sourceforge.tmdmaker.model.SubsetType;
import jp.sourceforge.tmdmaker.model.command.SubsetTypeDeleteCommand;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

/**
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
	 * 
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.editpart.AbstractEntityEditPart#updateFigure(org.eclipse.draw2d.IFigure)
	 */
	@Override
	protected void updateFigure(IFigure figure) {
		EntityFigure entityFigure = (EntityFigure) figure;
		SubsetEntity entity = (SubsetEntity) getModel();

		// List<Identifier> ids = entity.getReuseKeys().;

		List<Attribute> atts = entity.getAttributes();
		entityFigure.removeAllRelationship();
		entityFigure.removeAllAttributes();

		entityFigure.setEntityName(entity.getName());
		// entityFigure.setEntityType(entity.getEntityType().toString());
		// figure.setIdentifier(entity.getIdentifier().getName());
		for (Map.Entry<AbstractEntityModel, ReuseKey> rk : entity
				.getReuseKeys().entrySet()) {
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
				new SubsetEntityComponentEditPolicy());
		installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE,
				new AbstractEntityGraphicalNodeEditPolicy());
	}

	protected static RelatedRelationship findRelatedRelationship(
			List<AbstractConnectionModel> connections) {
		for (AbstractConnectionModel<?> c : connections) {
			if (c instanceof RelatedRelationship) {
				return (RelatedRelationship) c;
			}
		}
		return null;
	}

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
			RelatedRelationship relationship = findRelatedRelationship(model
					.getModelTargetConnections());
			SubsetTypeDeleteCommand command2 = new SubsetTypeDeleteCommand(diagram, (SubsetType)relationship.getSource());
			ccommand.add(command2);
			return ccommand;
		}

	}

	private static class SubsetEntityDeleteCommand extends Command {
		private Diagram diagram;
		private SubsetEntity model;
		private RelatedRelationship subsetType2SubsetEntityRelationship;
		private SubsetType subsetType;

		public SubsetEntityDeleteCommand(Diagram diagram, SubsetEntity model) {
			super();
			this.diagram = diagram;
			this.model = model;
			this.subsetType2SubsetEntityRelationship = findRelatedRelationship(this.model
					.getModelTargetConnections());
			this.subsetType = (SubsetType) subsetType2SubsetEntityRelationship
					.getSource();
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.gef.commands.Command#canExecute()
		 */
		@Override
		public boolean canExecute() {
			System.out.println(model.getModelTargetConnections().size());
			System.out.println(model.getModelSourceConnections().size());

			return model.getModelTargetConnections().size() == 1
					&& model.getModelSourceConnections().size() == 0;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.gef.commands.Command#execute()
		 */
		@Override
		public void execute() {
			this.subsetType2SubsetEntityRelationship.disConnect();
			this.model.setDiagram(null);
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
			this.model.setDiagram(this.diagram);
			this.subsetType2SubsetEntityRelationship.connect();
		}

	}
}

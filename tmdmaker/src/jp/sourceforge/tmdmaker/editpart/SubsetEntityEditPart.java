package jp.sourceforge.tmdmaker.editpart;

import java.util.List;
import java.util.Map;

import jp.sourceforge.tmdmaker.editpart.EntityEditPart.EntityComponentEditPolicy;
import jp.sourceforge.tmdmaker.editpolicy.AbstractEntityGraphicalNodeEditPolicy;
import jp.sourceforge.tmdmaker.figure.EntityFigure;
import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.Attribute;
import jp.sourceforge.tmdmaker.model.Identifier;
import jp.sourceforge.tmdmaker.model.ReuseKey;
import jp.sourceforge.tmdmaker.model.SubsetEntity;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.ReconnectRequest;

public class SubsetEntityEditPart extends AbstractEntityEditPart {

	@Override
	protected IFigure createFigure() {
		EntityFigure figure = new EntityFigure();
		updateFigure(figure);
		return figure;
	}

	private void updateFigure(EntityFigure figure) {
		SubsetEntity entity = (SubsetEntity) getModel();
		
//		List<Identifier> ids = entity.getReuseKeys().;
		
		List<Attribute> atts = entity.getAttributes();
		figure.removeAllRelationship();
		figure.removeAllAttributes();

		figure.setEntityName(entity.getName());
		figure.setEntityType(entity.getEntityType().toString());
//		figure.setIdentifier(entity.getIdentifier().getName());
		for (Map.Entry<AbstractEntityModel, ReuseKey> rk : entity.getReuseKeys().entrySet()) {
			for (Identifier i : rk.getValue().getIdentifires()) {
				figure.addRelationship(i.getName());
			}
		}
		for (Attribute a : atts) {
			figure.addAttribute(a.getName());
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.gef.editparts.AbstractEditPart#refreshVisuals()
	 */
	@Override
	protected void refreshVisuals() {
		Object model = getModel();
		Rectangle bounds = new Rectangle(((AbstractEntityModel) model)
				.getConstraint());
		((GraphicalEditPart) getParent()).setLayoutConstraint(this,
				getFigure(), bounds);
		updateFigure((EntityFigure) getFigure());
		refreshChildren();
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE,
				new EntityComponentEditPolicy());
		installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE,
				new SubsetEntityGraphicalNodeEditPolicy());
	}
	private static class SubsetEntityGraphicalNodeEditPolicy extends AbstractEntityGraphicalNodeEditPolicy {

		/**
		 * {@inheritDoc}
		 * @see org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy#getReconnectSourceCommand(org.eclipse.gef.requests.ReconnectRequest)
		 */
		@Override
		protected Command getReconnectSourceCommand(ReconnectRequest request) {
			// TODO Auto-generated method stub
			return null;
		}

		/**
		 * {@inheritDoc}
		 * @see org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy#getReconnectTargetCommand(org.eclipse.gef.requests.ReconnectRequest)
		 */
		@Override
		protected Command getReconnectTargetCommand(ReconnectRequest request) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
}

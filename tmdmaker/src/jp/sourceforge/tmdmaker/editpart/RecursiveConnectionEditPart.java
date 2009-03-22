package jp.sourceforge.tmdmaker.editpart;

import java.beans.PropertyChangeEvent;

import org.eclipse.draw2d.BendpointConnectionRouter;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PolylineConnection;

public class RecursiveConnectionEditPart extends AbstractRelationshipEditPart {

	/* (non-Javadoc)
	 * @see org.eclipse.gef.editparts.AbstractConnectionEditPart#createFigure()
	 */
	@Override
	protected IFigure createFigure() {
		PolylineConnection connection = new PolylineConnection();
	    connection.setConnectionRouter(new BendpointConnectionRouter());
	    return connection;
	}

	/* (non-Javadoc)
	 * @see tm.tmdiagram.tmdeditor.editpart.AbstractRelationshipEditPart#propertyChange(java.beans.PropertyChangeEvent)
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
//		if (evt.getPropertyName().equals(RecursiveMarkConnection.P_BEND_POINT)) {
//			refleshBendpoints();
//		}
		super.propertyChange(evt);
	}
//	protected void refleshBendpoints() {
//		List<ConnectionBendpoint> bendpoints = ((RecursiveMarkConnection)getModel()).getBendpoints();
//		List<Bendpoint> constraint = new ArrayList<Bendpoint>();
//		
////		for (int i = 0; i < bendpoints.size(); i++) {
////			ConnectionBendpoint wbp = bendpoints.get(i);
////			RelativeBendpoint rbp = new RelativeBendpoint(getConnectionFigure());
////			rbp.setRelativeDimensions(wbp.getFirstRelativeDimension(), wbp.getSecondRelativeDimension());
////			rbp.setWeight((i + 1) / ((float) constraint.size() + 1));
////			constraint.add(rbp);
////		}
//		RelativeBendpoint rbp = new RelativeBendpoint(getConnectionFigure());
//		Dimension d1 = new Dimension(0, 0);
//		Dimension d2 = new Dimension(0, 10);
//		
//		rbp.setRelativeDimensions(d1, d2);
//		constraint.add(rbp);
//		getConnectionFigure().setRoutingConstraint(constraint);
//		Ellipse figure = new Ellipse();
//		figure.setFill(false);
//		figure.setBounds(new Rectangle(-1, -1, 15, 15));
//		BendpointLocator locator = new BendpointLocator(getConnectionFigure(),1);
//		getConnectionFigure().add(figure, locator);
//	}
	
	/* (non-Javadoc)
	 * @see tm.tmdiagram.tmdeditor.editpart.AbstractRelationshipEditPart#refreshVisuals()
	 */
//	@Override
//	protected void refreshVisuals() {
////		refleshBendpoints();
//	}

	@Override
	protected void createEditPolicies() {
//		installEditPolicy(EditPolicy.CONNECTION_BENDPOINTS_ROLE, new RecursiveBendpointEditPolicy());

	}
//	private class RecursiveBendpointEditPolicy extends BendpointEditPolicy {
//
//		/* (non-Javadoc)
//		 * @see org.eclipse.gef.editpolicies.BendpointEditPolicy#getCreateBendpointCommand(org.eclipse.gef.requests.BendpointRequest)
//		 */
//		@Override
//		protected Command getCreateBendpointCommand(BendpointRequest request) {
//			// TODO Auto-generated method stub
//			return null;
//		}
//
//		/* (non-Javadoc)
//		 * @see org.eclipse.gef.editpolicies.BendpointEditPolicy#getDeleteBendpointCommand(org.eclipse.gef.requests.BendpointRequest)
//		 */
//		@Override
//		protected Command getDeleteBendpointCommand(BendpointRequest request) {
//			// TODO Auto-generated method stub
//			return null;
//		}
//
//		/* (non-Javadoc)
//		 * @see org.eclipse.gef.editpolicies.BendpointEditPolicy#getMoveBendpointCommand(org.eclipse.gef.requests.BendpointRequest)
//		 */
//		@Override
//		protected Command getMoveBendpointCommand(BendpointRequest request) {
//			// TODO Auto-generated method stub
//			return null;
//		}
//		
//	}
}

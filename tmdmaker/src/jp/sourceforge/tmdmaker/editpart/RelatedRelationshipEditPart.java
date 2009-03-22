package jp.sourceforge.tmdmaker.editpart;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.ManhattanConnectionRouter;
import org.eclipse.draw2d.PolylineConnection;

public class RelatedRelationshipEditPart extends AbstractRelationshipEditPart {

	/* (non-Javadoc)
	 * @see org.eclipse.gef.editparts.AbstractConnectionEditPart#createFigure()
	 */
	@Override
	protected IFigure createFigure() {
		PolylineConnection connection = new PolylineConnection();
		ManhattanConnectionRouter router = new ManhattanConnectionRouter();
		connection.setConnectionRouter(router);
		
//		Ellipse figure = new Ellipse();
//		figure.setFill(false);
//		figure.setBounds(new Rectangle(-1, -1, 16, 16));
//		ConnectionEndpointLocator locator = new ConnectionEndpointLocator(connection,false);
//		locator.setUDistance(-8);
//		locator.setVDistance(0);
//		connection.add(figure, locator);
		return connection;
	}

	@Override
	protected void createEditPolicies() {
		// TODO Auto-generated method stub

	}
}

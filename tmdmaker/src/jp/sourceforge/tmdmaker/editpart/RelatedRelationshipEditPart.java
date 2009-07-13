package jp.sourceforge.tmdmaker.editpart;

import jp.sourceforge.tmdmaker.figure.RelationshipFigure;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.ManhattanConnectionRouter;
import org.eclipse.draw2d.PolylineConnection;
/**
 * 
 * @author nakaG
 *
 */
public class RelatedRelationshipEditPart extends AbstractRelationshipEditPart {

	/**
	 * 
	 * {@inheritDoc}
	 *
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

	/**
	 * 
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.gef.editparts.AbstractEditPart#createEditPolicies()
	 */
	@Override
	protected void createEditPolicies() {
		// TODO Auto-generated method stub

	}

	/**
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.editpart.AbstractRelationshipEditPart#refreshVisuals()
	 */
	@Override
	protected void refreshVisuals() {
		IFigure figure = getFigure();
		updateFigure(figure);
		super.refreshVisuals();
	}

	private void updateFigure(IFigure figure) {
		// TODO Auto-generated method stub
		
	}
	
}

package jp.sourceforge.tmdmaker.editpart;

import jp.sourceforge.tmdmaker.figure.RelationshipFigure;

import org.eclipse.draw2d.ConnectionEndpointLocator;
import org.eclipse.draw2d.Ellipse;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.geometry.Rectangle;

/**
 * 
 * @author nakaG
 *
 */
public class RecursiveRelationshipEditPart extends RelationshipEditPart {

	/* (non-Javadoc)
	 * @see tm.tmdiagram.tmdeditor.editpart.RelationshipEditPart#createFigure()
	 */
	@Override
	protected IFigure createFigure() {
		RelationshipFigure connection = new RelationshipFigure();

		RectangleFigure rf = new RectangleFigure();
		rf.setSize(20, 20);
		rf.setOpaque(false);
		rf.setFill(false);
		
		Ellipse ef = new Ellipse();
		ef.setOpaque(false);
		ef.setFill(false);
		ef.setSize(15, 15);
		Figure l = new Figure();
		l.setOpaque(false);
		l.setBorder(new LineBorder());
		Rectangle rect = new Rectangle(-1, -1, 20, 20);
		l.setBounds(rect);
		
		ConnectionEndpointLocator  locator = new ConnectionEndpointLocator(connection, false);
		locator.setUDistance(-1);
		locator.setVDistance(1);
		connection.add(rf, locator);

		ConnectionEndpointLocator  locator2 = new ConnectionEndpointLocator(connection, false);
		locator2.setUDistance(11);
		locator2.setVDistance(0);
		connection.add(ef, locator2);
		
		return connection;
	}
}

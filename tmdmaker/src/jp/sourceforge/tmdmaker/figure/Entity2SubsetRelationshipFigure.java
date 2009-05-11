package jp.sourceforge.tmdmaker.figure;

import org.eclipse.draw2d.ConnectionEndpointLocator;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.ManhattanConnectionRouter;
import org.eclipse.draw2d.PolylineConnection;

public class Entity2SubsetRelationshipFigure extends PolylineConnection {
	private Figure partitionAttributeNameFigure;
	
	public Entity2SubsetRelationshipFigure() {
		setConnectionRouter(new ManhattanConnectionRouter());
	}

	public void createPartitionAttributeNameDecoration(String partitionAttributeName) {
		if (partitionAttributeName == null) {
			return;
		}
		if (partitionAttributeNameFigure != null) {
			remove(partitionAttributeNameFigure);
		}
		partitionAttributeNameFigure = new Label(partitionAttributeName);
		ConnectionEndpointLocator locator = new ConnectionEndpointLocator(this, true);
		locator.setUDistance(-10);
		locator.setVDistance(20);
		add(partitionAttributeNameFigure, locator);
	}
}

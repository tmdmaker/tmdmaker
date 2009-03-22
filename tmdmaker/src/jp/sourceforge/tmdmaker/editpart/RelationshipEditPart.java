package jp.sourceforge.tmdmaker.editpart;

import java.beans.PropertyChangeEvent;

import jp.sourceforge.tmdmaker.figure.RelationshipFigure;
import jp.sourceforge.tmdmaker.model.AbstractConnectionModel;
import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.Relationship;
import jp.sourceforge.tmdmaker.model.command.ConnectionDeleteCommand;
import jp.sourceforge.tmdmaker.model.command.RelationshipEditCommand;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ConnectionEditPolicy;
import org.eclipse.gef.editpolicies.ConnectionEndpointEditPolicy;
import org.eclipse.gef.requests.GroupRequest;
import org.eclipse.jface.dialogs.Dialog;

public class RelationshipEditPart extends AbstractRelationshipEditPart implements DoubleClickSupport {
	@Override
	protected IFigure createFigure() {
		RelationshipFigure connection = new RelationshipFigure();
//		PolylineConnection connection = new PolylineConnection();
//		ManhattanConnectionRouter router = new ManhattanConnectionRouter();
//		connection.setConnectionRouter(router);
		updateFigure(connection);
//			PolylineDecoration sourceDecoration = new PolylineDecoration();
//			// 1のカーディナリティ
////			PointList onePointList = new PointList();
////			onePointList.addPoint(-2, 2);
////			onePointList.addPoint(-2, -2);
////			sourceDecoration.setTemplate(onePointList);
////			connection.setSourceDecoration(sourceDecoration);
//			
//			// 0のカーディナリティ
//			Ellipse figure = new Ellipse();
//			figure.setFill(false);
//			figure.setBounds(new Rectangle(-1, -1, 8, 8));
//			ConnectionEndpointLocator locator = new ConnectionEndpointLocator(connection,false);
//			locator.setUDistance(14);
//			locator.setVDistance(0);
//			connection.add(figure, locator);
//
//			// Nのカーディナリティ
//			PointList manyPointList = new PointList();
//			manyPointList.addPoint(0, 2);
//			manyPointList.addPoint(-2, 0);
//			manyPointList.addPoint(0, -2);
//			sourceDecoration.setTemplate(manyPointList);
//			connection.setSourceDecoration(sourceDecoration);		



//		connection.setTargetDecoration(new PolygonDecoration());
//		PolylineDecoration sourceDecoration = new PolylineDecoration();
//		PolylineDecoration targetDecoration = new PolylineDecoration();
//		PointList pointList = new PointList();
//		pointList.addPoint(-2, 2);
//		pointList.addPoint(-2, -2);
//		pointList.addPoint(0, 0);
//		sourceDecoration.setTemplate(pointList);
//		targetDecoration.setTemplate(pointList);
//		connection.setSourceDecoration(sourceDecoration);
//		connection.setTargetDecoration(targetDecoration);
		
		// 対照表・対応表の中央デコレーション
//		MidpointLocator locator = new MidpointLocator(connection, 1);
//		Ellipse figure = new Ellipse();
//		figure.setFill(false);
//		figure.setBounds(new Rectangle(-1, -1, 15, 15));
//		connection.add(figure, locator);

		return connection;
	}
	protected void updateFigure(RelationshipFigure connection) {
		Relationship relationship = (Relationship) getModel();

		connection.createSourceDecoration(relationship.getSourceCardinality().equals("N"));
		if (relationship.isSourceNoInstance()) {
			connection.createSourceZeroCardinalityDecoration();
		} else {
			connection.removeSourceZeroCardinalityDecoration();
		}

		connection.createTargetDecoration(relationship.getTargetCardinality().equals("N"));
		if (relationship.isTargetNoInstance()) {
			connection.createTargetZeroCardinalityDecoration();
		} else {
			connection.removeTargetZeroCardinalityDecoration();
		}

		if (relationship.isCenterMard()) {
			connection.createCenterDecoration();
		}
		
		if (relationship.getSource() == relationship.getTarget()) {
			connection.createRecursiveDecoration();
		}
	}
	
	/* (non-Javadoc)
	 * @see tm.tmdiagram.tmdeditor.editpart.AbstractRelationshipEditPart#refreshVisuals()
	 */
	@Override
	protected void refreshVisuals() {
		super.refreshVisuals();
		updateFigure((RelationshipFigure) getFigure());
	}
	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.CONNECTION_ENDPOINTS_ROLE,
				new ConnectionEndpointEditPolicy());

		installEditPolicy(EditPolicy.CONNECTION_ROLE,
				new ConnectionEditPolicy() {
					protected Command getDeleteCommand(GroupRequest request) {
						ConnectionDeleteCommand cmd = new ConnectionDeleteCommand((AbstractConnectionModel) getModel());
						return cmd;
					}
				});
	}
	/* (non-Javadoc)
	 * @see tm.tmdiagram.tmdeditor.editpart.DoubleClickSupport#doubleClicked()
	 */
	@Override
	public void doubleClicked() {
		Relationship model = (Relationship)getModel();
		AbstractEntityModel source = (AbstractEntityModel) model.getSource();
		AbstractEntityModel target = (AbstractEntityModel) model.getTarget();
		RelationshipEditDialog dialog = new RelationshipEditDialog(getViewer().getControl().getShell(), source.getName(), target.getName(), model.getSourceCardinality(), model.getTargetCardinality(), model.isSourceNoInstance(), model.isTargetNoInstance());
		if (dialog.open() == Dialog.OK) {
			RelationshipEditCommand command = new RelationshipEditCommand();
			command.setModel(model);
			command.setSourceCardinality(dialog.getSourceCardinality());
			command.setSourceNoInstance(dialog.isSourceNoInstance());
			command.setTargetCardinality(dialog.getTargetCardinality());
			command.setTargetNoInstance(dialog.isTargetNoInstance());
			GraphicalViewer viewer = (GraphicalViewer) getViewer();
			viewer.getEditDomain().getCommandStack().execute(command);
//			model.setSourceCardinality(dialog
//					.getSourceCardinality());
//			model.setTargetCardinality(dialog
//					.getTargetCardinality());
//			model.setSourceNoInstance(dialog.isSourceNoInstance());
//			model.setTargetNoInstance(dialog.isTargetNoInstance());
		}
	}
	/* (non-Javadoc)
	 * @see tm.tmdiagram.tmdeditor.editpart.AbstractRelationshipEditPart#propertyChange(java.beans.PropertyChangeEvent)
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals(Relationship.P_SOURCE_CARDINALITY)) {
			refreshVisuals();
		} else if (evt.getPropertyName().equals(Relationship.P_TARGET_CARDINALITY)) {
			refreshVisuals();
		} else {
			super.propertyChange(evt);
		}
	}
	
}

package jp.sourceforge.tmdmaker.editpart;

import java.beans.PropertyChangeEvent;

import jp.sourceforge.tmdmaker.dialog.RelationshipEditDialog;
import jp.sourceforge.tmdmaker.figure.RelationshipFigure;
import jp.sourceforge.tmdmaker.model.AbstractConnectionModel;
import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.AbstractRelationship;
import jp.sourceforge.tmdmaker.model.command.ConnectionDeleteCommand;
import jp.sourceforge.tmdmaker.model.command.RelationshipEditCommand;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ConnectionEditPolicy;
import org.eclipse.gef.editpolicies.ConnectionEndpointEditPolicy;
import org.eclipse.gef.requests.GroupRequest;
import org.eclipse.jface.dialogs.Dialog;

public class RelationshipEditPart extends AbstractRelationshipEditPart {
	@Override
	protected IFigure createFigure() {
		RelationshipFigure connection = new RelationshipFigure();
		// PolylineConnection connection = new PolylineConnection();
		// ManhattanConnectionRouter router = new ManhattanConnectionRouter();
		// connection.setConnectionRouter(router);
		updateFigure(connection);
		// PolylineDecoration sourceDecoration = new PolylineDecoration();
		// // 1のカーディナリティ
		// // PointList onePointList = new PointList();
		// // onePointList.addPoint(-2, 2);
		// // onePointList.addPoint(-2, -2);
		// // sourceDecoration.setTemplate(onePointList);
		// // connection.setSourceDecoration(sourceDecoration);
		//			
		// // 0のカーディナリティ
		// Ellipse figure = new Ellipse();
		// figure.setFill(false);
		// figure.setBounds(new Rectangle(-1, -1, 8, 8));
		// ConnectionEndpointLocator locator = new
		// ConnectionEndpointLocator(connection,false);
		// locator.setUDistance(14);
		// locator.setVDistance(0);
		// connection.add(figure, locator);
		//
		// // Nのカーディナリティ
		// PointList manyPointList = new PointList();
		// manyPointList.addPoint(0, 2);
		// manyPointList.addPoint(-2, 0);
		// manyPointList.addPoint(0, -2);
		// sourceDecoration.setTemplate(manyPointList);
		// connection.setSourceDecoration(sourceDecoration);

		// connection.setTargetDecoration(new PolygonDecoration());
		// PolylineDecoration sourceDecoration = new PolylineDecoration();
		// PolylineDecoration targetDecoration = new PolylineDecoration();
		// PointList pointList = new PointList();
		// pointList.addPoint(-2, 2);
		// pointList.addPoint(-2, -2);
		// pointList.addPoint(0, 0);
		// sourceDecoration.setTemplate(pointList);
		// targetDecoration.setTemplate(pointList);
		// connection.setSourceDecoration(sourceDecoration);
		// connection.setTargetDecoration(targetDecoration);

		// 対照表・対応表の中央デコレーション
		// MidpointLocator locator = new MidpointLocator(connection, 1);
		// Ellipse figure = new Ellipse();
		// figure.setFill(false);
		// figure.setBounds(new Rectangle(-1, -1, 15, 15));
		// connection.add(figure, locator);

		return connection;
	}

	protected void updateFigure(RelationshipFigure connection) {
		AbstractRelationship model = (AbstractRelationship) getModel();

		connection.createSourceDecoration(model.getSourceCardinality().equals(
				"N"));
		if (model.isSourceNoInstance()) {
			connection.createSourceZeroCardinalityDecoration();
		} else {
			connection.removeSourceZeroCardinalityDecoration();
		}

		connection.createTargetDecoration(model.getTargetCardinality().equals(
				"N"));
		if (model.isTargetNoInstance()) {
			connection.createTargetZeroCardinalityDecoration();
		} else {
			connection.removeTargetZeroCardinalityDecoration();
		}

		if (model.isCenterMark()) {
			connection.createCenterDecoration();
		} else {
			connection.removeCenterDecoration();
		}

		if (model.getSource() == model.getTarget()) {
			connection.createRecursiveDecoration();
		}
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.editpart.AbstractRelationshipEditPart#refreshVisuals()
	 */
	@Override
	protected void refreshVisuals() {
		super.refreshVisuals();
		updateFigure((RelationshipFigure) getFigure());
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#createEditPolicies()
	 */
	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.CONNECTION_ENDPOINTS_ROLE,
				new ConnectionEndpointEditPolicy());

		installEditPolicy(EditPolicy.CONNECTION_ROLE,
				new ConnectionEditPolicy() {
					protected Command getDeleteCommand(GroupRequest request) {
						ConnectionDeleteCommand cmd = new ConnectionDeleteCommand(
								(AbstractConnectionModel) getModel());
						return cmd;
					}
				});
	}

	/**
	 * ダブルクリック時の処理（リレーションシップ編集)
	 */
	protected void onDoubleClicked() {
		AbstractRelationship model = (AbstractRelationship) getModel();
		AbstractEntityModel source = (AbstractEntityModel) model.getSource();
		AbstractEntityModel target = (AbstractEntityModel) model.getTarget();
		RelationshipEditDialog dialog = new RelationshipEditDialog(getViewer()
				.getControl().getShell(), source.getName(), target.getName(),
				model.getSourceCardinality(), model.getTargetCardinality(),
				model.isSourceNoInstance(), model.isTargetNoInstance());
		if (dialog.open() == Dialog.OK) {
			RelationshipEditCommand command = new RelationshipEditCommand();
			command.setModel(model);
			command.setSourceCardinality(dialog.getSourceCardinality());
			command.setSourceNoInstance(dialog.isSourceNoInstance());
			command.setTargetCardinality(dialog.getTargetCardinality());
			command.setTargetNoInstance(dialog.isTargetNoInstance());
			GraphicalViewer viewer = (GraphicalViewer) getViewer();
			viewer.getEditDomain().getCommandStack().execute(command);
		}
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.editpart.AbstractRelationshipEditPart#propertyChange(java.beans.PropertyChangeEvent)
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals(
				AbstractRelationship.P_SOURCE_CARDINALITY)) {
			refreshVisuals();
		} else if (evt.getPropertyName().equals(
				AbstractRelationship.P_TARGET_CARDINALITY)) {
			refreshVisuals();
		} else if (evt.getPropertyName().equals(
				AbstractRelationship.PROPERTY_CONNECTION)) {
			refreshVisuals();
		} else {
			super.propertyChange(evt);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#performRequest(org.eclipse.gef.Request)
	 */
	@Override
	public void performRequest(Request req) {
		logger.debug(getClass().toString() + req.getType());
		if (req.getType().equals(RequestConstants.REQ_OPEN)) {
			onDoubleClicked();
		} else {
			super.performRequest(req);
		}
	}
}

package jp.sourceforge.tmdmaker.editpart;

import java.beans.PropertyChangeEvent;

import jp.sourceforge.tmdmaker.dialog.RelationshipEditDialog;
import jp.sourceforge.tmdmaker.figure.RelationshipFigure;
import jp.sourceforge.tmdmaker.model.AbstractConnectionModel;
import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.AbstractRelationship;
import jp.sourceforge.tmdmaker.model.Cardinality;
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

/**
 * 
 * @author nakaG
 *
 */
public class RelationshipEditPart extends AbstractRelationshipEditPart {
	/**
	 * 
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.gef.editparts.AbstractConnectionEditPart#createFigure()
	 */
	@Override
	protected IFigure createFigure() {
		RelationshipFigure connection = new RelationshipFigure();
		updateFigure(connection);

		return connection;
	}
	/**
	 * 
	 * @param connection figure
	 */
	protected void updateFigure(RelationshipFigure connection) {
		AbstractRelationship model = (AbstractRelationship) getModel();

		connection.createSourceDecoration(model.getSourceCardinality().equals(
				Cardinality.MANY));
		if (model.isSourceNoInstance()) {
			connection.createSourceZeroCardinalityDecoration();
		} else {
			connection.removeSourceZeroCardinalityDecoration();
		}

		connection.createTargetDecoration(model.getTargetCardinality().equals(
				Cardinality.MANY));
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
				AbstractRelationship.PROPERTY_SOURCE_CARDINALITY)) {
			refreshVisuals();
		} else if (evt.getPropertyName().equals(
				AbstractRelationship.PROPERTY_TARGET_CARDINALITY)) {
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

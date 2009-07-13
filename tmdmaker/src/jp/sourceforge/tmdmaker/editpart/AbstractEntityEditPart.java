package jp.sourceforge.tmdmaker.editpart;

import java.beans.PropertyChangeEvent;
import java.util.List;

import jp.sourceforge.tmdmaker.model.AbstractConnectionModel;
import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.ConnectableElement;
import jp.sourceforge.tmdmaker.model.ModelElement;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 
 * @author nakaG
 *
 */
public abstract class AbstractEntityEditPart extends AbstractTMDEditPart
		implements NodeEditPart {
	/** logging */
	protected static Logger logger = LoggerFactory
			.getLogger(AbstractEntityEditPart.class);

	private ConnectionAnchor anchor;

	public AbstractEntityEditPart() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#activate()
	 */
	@Override
	public void activate() {
		logger.debug(getClass() + "#activate()");
		super.activate();
		((ModelElement) getModel()).addPropertyChangeListener(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#deactivate()
	 */
	@Override
	public void deactivate() {
		logger.debug(getClass() + "#deactivate()");
		super.deactivate();
		((ModelElement) getModel()).removePropertyChangeListener(this);
	}

	protected ConnectionAnchor getConnectionAnchor() {
		if (anchor == null) {
			anchor = new ChopboxAnchor(getFigure());
		}
		return anchor;
	}

	public ConnectionAnchor getSourceConnectionAnchor(
			ConnectionEditPart connection) {
		return getConnectionAnchor();
	}

	public ConnectionAnchor getSourceConnectionAnchor(Request request) {
		return getConnectionAnchor();
	}

	public ConnectionAnchor getTargetConnectionAnchor(
			ConnectionEditPart connection) {
		return getConnectionAnchor();
	}

	public ConnectionAnchor getTargetConnectionAnchor(Request request) {
		return getConnectionAnchor();
	}

	@Override
	protected List<AbstractConnectionModel> getModelSourceConnections() {
		return ((ConnectableElement) getModel()).getModelSourceConnections();
	}

	@Override
	protected List<AbstractConnectionModel> getModelTargetConnections() {
		return ((ConnectableElement) getModel()).getModelTargetConnections();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {

		if (evt.getPropertyName().equals(ModelElement.PROPERTY_NAME)) {
			logger.debug(getClass() + ".PROPERTY_NAME");
			refreshVisuals();
		} else if (evt.getPropertyName().equals(
				ModelElement.PROPERTY_CONSTRAINT)) {
			logger.debug(getClass() + "PROPERTY_CONSTRAINT");
			refreshVisuals();
		} else if (evt.getPropertyName().equals(
				AbstractEntityModel.PROPERTY_ATTRIBUTE)) {
			logger.debug(getClass() + "PROPERTY_ATTRIBUTE");
			refreshVisuals();
		} else if (evt.getPropertyName().equals(
				ConnectableElement.P_SOURCE_CONNECTION)) {
			logger.debug(getClass() + "P_SOURCE_CONNECTION");
			refreshSourceConnections();
		} else if (evt.getPropertyName().equals(
				ConnectableElement.P_TARGET_CONNECTION)) {
			logger.debug(getClass() + "P_TARGET_CONNECTION");
			refreshTargetConnections();
		} else if (evt.getPropertyName().equals(
				AbstractEntityModel.PROPERTY_REUSEKEY)) {
			logger.debug(getClass() + ".PROPERTY_REUSEKEY");
			refreshVisuals();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#refreshVisuals()
	 */
	@Override
	protected void refreshVisuals() {
		logger.debug(getClass().toString() + "#refreshVisuals()");
		super.refreshVisuals();
		Object model = getModel();
		Rectangle bounds = new Rectangle(((ModelElement) model).getConstraint());
		((GraphicalEditPart) getParent()).setLayoutConstraint(this,
				getFigure(), bounds);

		updateFigure(getFigure());
		refreshChildren();
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

	/**
	 * Figureを更新する
	 * 
	 * @param figure
	 *            更新するFigure
	 */
	protected abstract void updateFigure(IFigure figure);

	/**
	 * ダブルクリック時の処理をサブクラスで実装する
	 */
	protected abstract void onDoubleClicked();
}

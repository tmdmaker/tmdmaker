package jp.sourceforge.tmdmaker.editpart;

import java.beans.PropertyChangeEvent;
import java.util.List;

import jp.sourceforge.tmdmaker.model.AbstractConnectionModel;
import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.ConnectableElement;
import jp.sourceforge.tmdmaker.model.ModelElement;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;

public abstract class AbstractEntityEditPart extends AbstractTMDEditPart implements NodeEditPart {
	private ConnectionAnchor anchor;
	public AbstractEntityEditPart() {
		super();
	}
	/* (non-Javadoc)
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#activate()
	 */
	@Override
	public void activate() {
		super.activate();
		((ModelElement) getModel()).addPropertyChangeListener(this);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#deactivate()
	 */
	@Override
	public void deactivate() {
		super.deactivate();
		((ModelElement) getModel()).removePropertyChangeListener(this);
	}
	protected ConnectionAnchor getConnectionAnchor() {
		if (anchor == null) {
			anchor = new ChopboxAnchor(getFigure()); 
		}
		return anchor;
	}
	public ConnectionAnchor getSourceConnectionAnchor(ConnectionEditPart connection) {
		return getConnectionAnchor();
	}

	public ConnectionAnchor getSourceConnectionAnchor(Request request) {
		return getConnectionAnchor();
	}

	public ConnectionAnchor getTargetConnectionAnchor(ConnectionEditPart connection) {
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
			System.out.println("ModelElement.P_NAME");
			refreshVisuals();
		} else if (evt.getPropertyName().equals(ModelElement.PROPERTY_CONSTRAINT)) {
			System.out.println("AbstractEntityModel.P_CONSTRAINT");
			refreshVisuals();
		} else if (evt.getPropertyName().equals(AbstractEntityModel.PROPERTY_ATTRIBUTE)) {
			System.out.println("AbstractEntityModel.P_CONSTRAINT");
			refreshVisuals();
		} else if (evt.getPropertyName().equals(ConnectableElement.P_SOURCE_CONNECTION)) {
			System.out.println("AbstractEntityModel.P_SOURCE_CONNECTION");
			refreshSourceConnections();
		} else if (evt.getPropertyName().equals(ConnectableElement.P_TARGET_CONNECTION)) {
			System.out.println("AbstractEntityModel.P_TARGET_CONNECTION");
			refreshTargetConnections();
		} else if (evt.getPropertyName().equals(AbstractEntityModel.PROPERTY_REUSEKEY))  {
			System.out.println("AbstractEntityModel.PROPERTY_REUSEKEY");
			refreshVisuals();
		}
	}

}


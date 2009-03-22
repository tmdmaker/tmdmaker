package jp.sourceforge.tmdmaker.editpart;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import jp.sourceforge.tmdmaker.model.AbstractConnectionModel;
import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.ConnectableElement;
import jp.sourceforge.tmdmaker.model.ModelElement;

import org.eclipse.draw2d.AbstractConnectionAnchor;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.editparts.AbstractConnectionEditPart;

public abstract class AbstractRelationshipEditPart extends
AbstractConnectionEditPart implements NodeEditPart, PropertyChangeListener{

private ConnectionAnchor anchor;

public AbstractRelationshipEditPart() {
super();
}

private ConnectionAnchor getConnectionAnchor() {
	if (anchor == null) {

//			anchor = new ChopboxAnchor(getFigure());
		anchor = new PolylineConnectionAnchor((PolylineConnection) getFigure());
	}
	return anchor;
}

@Override
public ConnectionAnchor getSourceConnectionAnchor(ConnectionEditPart connection) {
return getConnectionAnchor();
}

@Override
public ConnectionAnchor getSourceConnectionAnchor(Request request) {
return getConnectionAnchor();
}

@Override
public ConnectionAnchor getTargetConnectionAnchor(ConnectionEditPart connection) {
return getConnectionAnchor();
}

@Override
public ConnectionAnchor getTargetConnectionAnchor(Request request) {
return getConnectionAnchor();
}

@Override
public void propertyChange(PropertyChangeEvent evt) {
if (evt.getPropertyName().equals(AbstractEntityModel.PROPERTY_CONSTRAINT)) {
	System.out.println("Connection AbstractEntityModel.P_CONSTRAINT");
	//getFigure().repaint();
	refreshVisuals();
} else if (evt.getPropertyName().equals(ConnectableElement.P_SOURCE_CONNECTION)) {
	System.out.println("Connection AbstractEntityModel.P_SOURCE_CONNECTION");
	refreshSourceConnections();
} else if (evt.getPropertyName().equals(ConnectableElement.P_TARGET_CONNECTION)) {
	System.out.println("Connection AbstractEntityModel.P_TARGET_CONNECTION");
	refreshTargetConnections();
}
}

@Override
public void activate() {
super.activate();
((ModelElement) getModel()).addPropertyChangeListener(this);
}

@Override
public void deactivate() {
super.deactivate();
((ModelElement) getModel()).removePropertyChangeListener(this);
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
public void refresh() {
// TODO Auto-generated method stub
System.out.println("refresh()");
super.refresh();
}

@Override
protected void refreshSourceAnchor() {
// TODO Auto-generated method stub
System.out.println("refreshSourceAnchor()");
super.refreshSourceAnchor();
}

@Override
protected void refreshTargetAnchor() {
// TODO Auto-generated method stub
System.out.println("refreshTargetAnchor()");
super.refreshTargetAnchor();
}

@Override
protected void refreshSourceConnections() {
// TODO Auto-generated method stub
System.out.println("refreshSourceConnections()");
super.refreshSourceConnections();
}

@Override
protected void refreshTargetConnections() {
// TODO Auto-generated method stub
System.out.println("refreshTargetConnections()");
super.refreshTargetConnections();
}

@Override
protected void refreshChildren() {
// TODO Auto-generated method stub
System.out.println("refreshChildren()");
super.refreshChildren();
}

@Override
protected void refreshVisuals() {
// TODO Auto-generated method stub
System.out.println("refreshVisuals()");
super.refreshVisuals();
}
public class PolylineConnectionAnchor extends AbstractConnectionAnchor {

private PolylineConnection owner;

public PolylineConnectionAnchor(PolylineConnection owner) {
	this.owner = owner;
}

/**
 * Returns the midpoint of the owner connection.
 */
public Point getLocation(Point reference) {
	Point midpoint = owner.getPoints().getMidpoint();
	owner.translateToAbsolute(midpoint);
	return midpoint;
}

/*
 * (non-Javadoc)
 * 
 * @see org.eclipse.draw2d.ConnectionAnchor#getOwner()
 */
public IFigure getOwner() {
	return owner;
}

/*
 * (non-Javadoc)
 * 
 * @see org.eclipse.draw2d.ConnectionAnchor#getReferencePoint()
 */
public Point getReferencePoint() {
	if (owner == null) {
		return null;
	} else {
		return getLocation(null);
	}
}

}

}
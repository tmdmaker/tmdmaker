package jp.sourceforge.tmdmaker.editpart;

import java.beans.PropertyChangeListener;

import jp.sourceforge.tmdmaker.model.ModelElement;

import org.eclipse.gef.editparts.AbstractGraphicalEditPart;

/**
 * 
 * @author nakaG
 *
 */
public abstract class AbstractTMDEditPart extends AbstractGraphicalEditPart implements PropertyChangeListener {
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
}

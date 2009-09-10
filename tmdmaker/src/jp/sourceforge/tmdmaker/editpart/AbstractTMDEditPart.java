package jp.sourceforge.tmdmaker.editpart;

import java.beans.PropertyChangeListener;

import jp.sourceforge.tmdmaker.model.ModelElement;

import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author nakaG
 * 
 */
public abstract class AbstractTMDEditPart extends AbstractGraphicalEditPart
		implements PropertyChangeListener {
	/** logging */
	protected static Logger logger = LoggerFactory
			.getLogger(AbstractEntityEditPart.class);

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#activate()
	 */
	@Override
	public void activate() {
		super.activate();
		((ModelElement) getModel()).addPropertyChangeListener(this);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#deactivate()
	 */
	@Override
	public void deactivate() {
		super.deactivate();
		((ModelElement) getModel()).removePropertyChangeListener(this);
	}
}

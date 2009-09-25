package jp.sourceforge.tmdmaker.editpart;

import java.beans.PropertyChangeListener;

import jp.sourceforge.tmdmaker.model.ModelElement;

import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
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
	 * ダブルクリック時の処理をサブクラスで実装する
	 */
	protected abstract void onDoubleClicked();

}

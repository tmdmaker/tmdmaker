package jp.sourceforge.tmdmaker.editpart;

import java.beans.PropertyChangeEvent;

import jp.sourceforge.tmdmaker.model.Attribute;
import jp.sourceforge.tmdmaker.model.ModelElement;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;

public class AttributeEditPart extends AbstractTMDEditPart {

	@Override
	protected IFigure createFigure() {
		
		Attribute model = (Attribute) getModel();
		Label label = new Label();
		label.setText(model.getName());
		return label;
	}

	@Override
	protected void createEditPolicies() {
		// TODO Auto-generated method stub

	}
	/**
	 * 
	 * {@inheritDoc}
	 *
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals(ModelElement.PROPERTY_NAME)) {
			logger.debug("Handle Name Event.");
			handleNameChange(evt);
		} else {
			logger.warn("Not Handle Event Occured.");
		}
	}

	
	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.gef.editparts.AbstractEditPart#refreshVisuals()
	 */
	@Override
	protected void refreshVisuals() {
		Attribute model = (Attribute) getModel();
		Label f = (Label) getFigure();
		f.setText(model.getName());
		System.out.println(model.getName());
		getParent().refresh();
	}
	/**
	 * 名称変更イベント処理
	 * @param evt 発生したイベント情報
	 */
	protected void handleNameChange(PropertyChangeEvent evt) {
		refreshVisuals();
	}

}

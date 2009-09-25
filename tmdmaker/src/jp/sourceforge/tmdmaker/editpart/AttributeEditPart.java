package jp.sourceforge.tmdmaker.editpart;

import java.beans.PropertyChangeEvent;

import jp.sourceforge.tmdmaker.dialog.AttributeListDialog;
import jp.sourceforge.tmdmaker.model.Attribute;
import jp.sourceforge.tmdmaker.model.ModelElement;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.jface.dialogs.Dialog;

public class AttributeEditPart extends AbstractTMDEditPart {
	/**
	 * 
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#createFigure()
	 */
	@Override
	protected IFigure createFigure() {
		
		Attribute model = (Attribute) getModel();
		Label label = new Label();
		label.setText(model.getName());
		return label;
	}
	/**
	 * 
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.gef.editparts.AbstractEditPart#createEditPolicies()
	 */
	@Override
	protected void createEditPolicies() {
		// TODO Auto-generated method stub

	}
	
	/**
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.editpart.AbstractTMDEditPart#onDoubleClicked()
	 */
	@Override
	protected void onDoubleClicked() {
		AttributeListDialog dialog = new AttributeListDialog(getViewer().getControl().getShell());
		if (dialog.open() == Dialog.OK) {
			
		}
		
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
		logger.debug(model.getName());
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

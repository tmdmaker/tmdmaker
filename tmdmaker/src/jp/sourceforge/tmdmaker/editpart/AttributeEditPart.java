package jp.sourceforge.tmdmaker.editpart;

import java.beans.PropertyChangeEvent;

import jp.sourceforge.tmdmaker.dialog.AttributeDialog;
import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.Attribute;
import jp.sourceforge.tmdmaker.model.EditAttribute;
import jp.sourceforge.tmdmaker.model.ModelElement;
import jp.sourceforge.tmdmaker.model.command.AttributeEditCommand;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.jface.dialogs.Dialog;

/**
 * アトリビュートのコントローラ
 * 
 * @author nakaG
 * 
 */
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
		Attribute model = (Attribute) getModel();
		EditAttribute edit = new EditAttribute(model);
		AttributeDialog dialog = new AttributeDialog(getViewer().getControl()
				.getShell(), edit);
		if (dialog.open() == Dialog.OK) {
			EditAttribute edited = dialog.getEditedValue();
			if (edited.isEdited()) {
				System.out.println("edited");
				Attribute editedValueAttribute = new Attribute();
				edited.copyTo(editedValueAttribute);
				Attribute original = edited.getOriginalAttribute();
				AbstractEntityModel entity = (AbstractEntityModel) getParent()
						.getModel();
				AttributeEditCommand editCommand = new AttributeEditCommand(
						original, editedValueAttribute, entity);

				getViewer().getEditDomain().getCommandStack().execute(
						editCommand);
			}
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
	 * 
	 * @param evt
	 *            発生したイベント情報
	 */
	protected void handleNameChange(PropertyChangeEvent evt) {
		refreshVisuals();
	}
}

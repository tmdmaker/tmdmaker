/*
 * Copyright 2009-2011 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jp.sourceforge.tmdmaker.editpart;

import java.beans.PropertyChangeEvent;

import jp.sourceforge.tmdmaker.TMDEditor;
import jp.sourceforge.tmdmaker.dialog.AttributeDialog;
import jp.sourceforge.tmdmaker.dialog.model.EditAttribute;
import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.Attribute;
import jp.sourceforge.tmdmaker.model.IAttribute;
import jp.sourceforge.tmdmaker.model.ModelElement;
import jp.sourceforge.tmdmaker.property.AttributePropertySource;
import jp.sourceforge.tmdmaker.property.IPropertyAvailable;
import jp.sourceforge.tmdmaker.ui.command.AttributeEditCommand;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.ui.views.properties.IPropertySource;

/**
 * アトリビュートのコントローラ
 * 
 * @author nakaG
 * 
 */
public class AttributeEditPart extends AbstractTMDEditPart<Attribute> implements IPropertyAvailable {
	
	/**
	 * コンストラクタ
	 */
	public AttributeEditPart(Attribute attribute)
	{
		super();
		setModel(attribute);
	}
	
	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#createFigure()
	 */
	@Override
	protected IFigure createFigure() {

		Label label = new Label();
		label.setText(createAttributeName(getModel()));
		label.setBorder(new MarginBorder(2, 2, 2, 2));
		label.setLabelAlignment(PositionConstants.LEFT);
		return label;
	}
	private String createAttributeName(Attribute attribute) {
		StringBuilder name = new StringBuilder(attribute.getName());
		if (attribute.isDerivation()) {
			name.append("(D)"); //$NON-NLS-1$
		}
		return name.toString();
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
		EditAttribute edit = new EditAttribute(getModel());
		AttributeDialog dialog = new AttributeDialog(getViewer().getControl()
				.getShell(), edit);
		if (dialog.open() == Dialog.OK) {
			EditAttribute edited = dialog.getEditedValue();
			if (edited.isEdited()) {
				Attribute editedValueAttribute = new Attribute();
				edited.copyTo(editedValueAttribute);
				IAttribute original = edited.getOriginalAttribute();
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
		Label f = (Label) getFigure();
		f.setText(createAttributeName(getModel()));
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
	
	@Override
	public IPropertySource getPropertySource(TMDEditor editor) {
		return new AttributePropertySource(editor, this.getModel());
	}
}

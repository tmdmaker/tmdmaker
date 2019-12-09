/*
 * Copyright 2009-2019 TMD-Maker Project <https://www.tmdmaker.org>
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
package org.tmdmaker.ui.editor.gef3.editparts.node;

import java.beans.PropertyChangeEvent;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.ui.views.properties.IPropertySource;
import org.tmdmaker.core.model.AbstractEntityModel;
import org.tmdmaker.core.model.Attribute;
import org.tmdmaker.core.model.ModelElement;
import org.tmdmaker.ui.editor.gef3.editparts.IAttributeEditPart;
import org.tmdmaker.ui.editor.gef3.editpolicies.AttributeComponentEditPolicy;
import org.tmdmaker.ui.views.properties.IPropertyAvailable;
import org.tmdmaker.ui.views.properties.gef3.IAttributePropertySource;

/**
 * アトリビュートのコントローラ
 * 
 * @author nakaG
 * 
 */
public class AttributeEditPart extends AbstractTMDEditPart<Attribute>
		implements IPropertyAvailable, IAttributeEditPart {

	/**
	 * コンストラクタ
	 */
	public AttributeEditPart(Attribute attribute) {
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
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new AttributeComponentEditPolicy());
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
	public IPropertySource getPropertySource(CommandStack commandStack) {
		return new IAttributePropertySource(commandStack, this.getModel());
	}

	@Override
	public AbstractEntityModel getParentModel() {
		return (AbstractEntityModel) getParent().getModel();
	}
}

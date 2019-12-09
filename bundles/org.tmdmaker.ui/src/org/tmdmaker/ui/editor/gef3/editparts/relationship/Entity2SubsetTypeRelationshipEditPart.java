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
package org.tmdmaker.ui.editor.gef3.editparts.relationship;

import java.beans.PropertyChangeEvent;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editpolicies.ConnectionEndpointEditPolicy;
import org.tmdmaker.core.model.AbstractConnectionModel;
import org.tmdmaker.core.model.Entity2SubsetTypeRelationship;
import org.tmdmaker.core.model.SubsetType;
import org.tmdmaker.ui.editor.draw2d.figure.relationship.Entity2SubsetTypeRelationshipFigure;
import org.tmdmaker.ui.editor.gef3.editpolicies.RelationshipEditPolicy;

/**
 * エンティティ系モデルとサブセット種類とのリレーションシップのコントローラ
 * 
 * @author nakaG
 * 
 */
public class Entity2SubsetTypeRelationshipEditPart extends AbstractRelationshipEditPart {

	/**
	 * コンストラクタ
	 */
	public Entity2SubsetTypeRelationshipEditPart(Entity2SubsetTypeRelationship relationship) {
		super();
		setModel(relationship);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.editparts.AbstractConnectionEditPart#createFigure()
	 */
	@Override
	protected IFigure createFigure() {
		Entity2SubsetTypeRelationshipFigure figure = new Entity2SubsetTypeRelationshipFigure();
		updateFigure(figure);
		return figure;
	}

	/**
	 * 
	 * @param figure
	 *            再描画対象のFigure
	 */
	private void updateFigure(Entity2SubsetTypeRelationshipFigure figure) {
		Entity2SubsetTypeRelationship relationship = (Entity2SubsetTypeRelationship) getModel();
		figure.update(relationship);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#createEditPolicies()
	 */
	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.CONNECTION_ENDPOINTS_ROLE, new ConnectionEndpointEditPolicy());
		installEditPolicy(EditPolicy.CONNECTION_ROLE, new RelationshipEditPolicy());

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.tmdmaker.ui.editor.gef3.editparts.relationship.AbstractRelationshipEditPart#refreshVisuals()
	 */
	@Override
	protected void refreshVisuals() {
		super.refreshVisuals();
		updateFigure((Entity2SubsetTypeRelationshipFigure) getFigure());
		calculateAnchorLocation();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.tmdmaker.ui.editor.gef3.editparts.relationship.AbstractRelationshipEditPart#propertyChange(java.beans.PropertyChangeEvent)
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals(SubsetType.PROPERTY_PARTITION)) {
			refreshVisuals();
		} else if (evt.getPropertyName().equals(AbstractConnectionModel.PROPERTY_CONNECTION)) {
			refreshVisuals();
		} else {
			super.propertyChange(evt);
		}
	}
}

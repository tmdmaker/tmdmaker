/*
 * Copyright 2009-2010 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
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

import jp.sourceforge.tmdmaker.figure.Entity2SubsetTypeRelationshipFigure;
import jp.sourceforge.tmdmaker.model.Attribute;
import jp.sourceforge.tmdmaker.model.Entity2SubsetTypeRelationship;

import org.eclipse.draw2d.IFigure;

/**
 * エンティティ系モデルとサブセット種類とのリレーションシップのコントローラ
 * 
 * @author nakaG
 * 
 */
public class Entity2SubsetTypeRelationshipEditPart extends
		AbstractRelationshipEditPart {

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

		// figure.createPartitionAttributeNameDecoration("test");

		Attribute partitionAttribute = relationship.getPartitionAttribute();
		if (partitionAttribute != null) {
			String name = null;
			if (relationship.isExceptNull()) {
				name = "NULL(" + partitionAttribute.getName() + ")";
			} else {
				name = partitionAttribute.getName();
			}
			figure.createPartitionAttributeNameDecoration(name);
		}
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
	 * @see jp.sourceforge.tmdmaker.editpart.AbstractRelationshipEditPart#refreshVisuals()
	 */
	@Override
	protected void refreshVisuals() {
		Entity2SubsetTypeRelationshipFigure figure = (Entity2SubsetTypeRelationshipFigure) getFigure();
		updateFigure(figure);
		super.refreshVisuals();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.editpart.AbstractRelationshipEditPart#propertyChange(java.beans.PropertyChangeEvent)
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals(
				Entity2SubsetTypeRelationship.PROPERTY_PARTITION)) {
			refreshVisuals();
		} else {
			super.propertyChange(evt);
		}
	}
}

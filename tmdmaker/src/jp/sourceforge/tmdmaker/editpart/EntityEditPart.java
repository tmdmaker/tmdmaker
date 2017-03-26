/*
 * Copyright 2009-2012 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
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

import jp.sourceforge.tmdmaker.TMDEditor;
import jp.sourceforge.tmdmaker.editpolicy.EntityEditComponentPolicy;
import jp.sourceforge.tmdmaker.editpolicy.EntityLayoutEditPolicy;
import jp.sourceforge.tmdmaker.editpolicy.TMDModelGraphicalNodeEditPolicy;
import jp.sourceforge.tmdmaker.figure.EntityFigure;
import jp.sourceforge.tmdmaker.model.Entity;
import jp.sourceforge.tmdmaker.model.EntityType;
import jp.sourceforge.tmdmaker.property.EntityPropertySource;
import jp.sourceforge.tmdmaker.property.IPropertyAvailable;
import jp.sourceforge.tmdmaker.ui.preferences.appearance.ModelAppearance;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPolicy;
import org.eclipse.ui.views.properties.IPropertySource;

/**
 * エンティティのコントローラ
 * 
 * @author nakaG
 * 
 */
public class EntityEditPart extends AbstractEntityModelEditPart<Entity> implements IPropertyAvailable {

	/**
	 * コンストラクタ
	 */
	public EntityEditPart(Entity entity)
	{
		super();
		setModel(entity);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.editpart.AbstractModelEditPart#updateFigure(org.eclipse.draw2d.IFigure)
	 */
	@Override
	protected void updateFigure(IFigure figure) {
		logger.debug(getClass() + "#updateFigure()");
		EntityFigure entityFigure = (EntityFigure) figure;
		Entity entity = getModel();
		entityFigure.setNotImplement(entity.isNotImplement());

		entityFigure.removeAllRelationship();

		entityFigure.setEntityName(entity.getName());
		entityFigure.setEntityType(entity.getEntityType().getLabel());

		entityFigure.setColor(getForegroundColor(), getBackgroundColor());

		entityFigure.setIdentifier(entity.getIdentifier().getName());
		entityFigure.addRelationship(extractRelationship(entity));
	}
	
	@Override
	protected ModelAppearance getAppearance() {
		ModelAppearance appearance = null;
		if (getModel().getEntityType().equals(EntityType.RESOURCE)) {
			appearance = ModelAppearance.RESOURCE;
		} else if (getModel().getEntityType().equals(EntityType.EVENT)) {
			appearance = ModelAppearance.EVENT;
		}
		return appearance;
	}
	
	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#createEditPolicies()
	 */
	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE,
				new EntityEditComponentPolicy());
		installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE,
				new TMDModelGraphicalNodeEditPolicy());
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new EntityLayoutEditPolicy());
	}

	@Override
	public IPropertySource getPropertySource(TMDEditor editor) {
		return new EntityPropertySource(editor, this.getModel());
	}
}

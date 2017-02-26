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

import jp.sourceforge.tmdmaker.editpolicy.EntityLayoutEditPolicy;
import jp.sourceforge.tmdmaker.editpolicy.LaputaEditPolicy;
import jp.sourceforge.tmdmaker.figure.EntityFigure;
import jp.sourceforge.tmdmaker.model.Laputa;
import jp.sourceforge.tmdmaker.ui.preferences.appearance.ModelAppearance;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPolicy;

/**
 * ラピュタのコントローラ
 * 
 * @author nakaG
 * 
 */
public class LaputaEditPart extends AbstractEntityModelEditPart<Laputa> {

	/**
	 * コンストラクタ
	 */
	public LaputaEditPart(Laputa entity)
	{
		super();
		setModel(entity);
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.editpart.AbstractModelEditPart#updateFigure(org.eclipse.draw2d.IFigure)
	 */
	@Override
	protected void updateFigure(IFigure figure) {
		EntityFigure entityFigure = (EntityFigure) figure;
		Laputa entity = getModel();
		// ラピュタは実装しないが×は付けない
		// entityFigure.setNotImplement(entity.isNotImplement());

		entityFigure.removeAllRelationship();
		entityFigure.setEntityName(entity.getName());
		entityFigure.setIdentifier(entity.getIdentifier().getName());
		entityFigure.addRelationship(extractRelationship(entity));
		entityFigure.setColor(getForegroundColor(), getBackgroundColor());
	}

	@Override
	protected ModelAppearance getAppearance() {
		return ModelAppearance.LAPUTA_COLOR;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#createEditPolicies()
	 */
	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new LaputaEditPolicy());
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new EntityLayoutEditPolicy());
	}
}

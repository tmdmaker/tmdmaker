/*
 * Copyright 2009-2015 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
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

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPolicy;

import jp.sourceforge.tmdmaker.dialog.ModelEditDialog;
import jp.sourceforge.tmdmaker.dialog.TableEditDialog;
import jp.sourceforge.tmdmaker.editpolicy.EntityComponentEditPolicy;
import jp.sourceforge.tmdmaker.editpolicy.EntityLayoutEditPolicy;
import jp.sourceforge.tmdmaker.editpolicy.TMDModelGraphicalNodeEditPolicy;
import jp.sourceforge.tmdmaker.figure.EntityFigure;
import jp.sourceforge.tmdmaker.model.other.TurboFile;
import jp.sourceforge.tmdmaker.property.IPropertyAvailable;
import jp.sourceforge.tmdmaker.ui.preferences.appearance.ModelAppearance;

/**
 * ターボファイルのコントローラ
 * @author nakag
 *
 */
public class TurboFileEditPart extends AbstractEntityModelEditPart<TurboFile>
		implements IPropertyAvailable {

	/**
	 * コンストラクタ
	 *
	 * @param entity
	 */
	public TurboFileEditPart(TurboFile entity) {
		super();
		setModel(entity);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.editpart.AbstractEntityModelEditPart#updateFigure(org.eclipse.draw2d.IFigure)
	 */
	@Override
	protected void updateFigure(IFigure figure) {
		logger.debug(getClass() + "#updateFigure()");
		EntityFigure entityFigure = (EntityFigure) figure;
		TurboFile entity = getModel();
		entityFigure.setNotImplement(entity.isNotImplement());

		entityFigure.removeAllRelationship();

		entityFigure.setEntityName(entity.getName());
		entityFigure.setEntityType(entity.getEntityType().getLabel());

		entityFigure.setColor(getForegroundColor(), getBackgroundColor());

		entityFigure.addRelationship(extractRelationship(entity));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.editpart.AbstractEntityModelEditPart#getDialog()
	 */
	@Override
	protected ModelEditDialog<TurboFile> getDialog() {
		return new TableEditDialog<TurboFile>(getControllShell(), "ターボファイル編集", getModel());
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.editpart.AbstractEntityModelEditPart#createEditPolicies()
	 */
	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new EntityComponentEditPolicy());
		installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE, new TMDModelGraphicalNodeEditPolicy());
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new EntityLayoutEditPolicy());
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.editpart.AbstractModelEditPart#getAppearance()
	 */
	@Override
	protected ModelAppearance getAppearance() {
		return ModelAppearance.TURBO_FILE_COLOR;
	}
}

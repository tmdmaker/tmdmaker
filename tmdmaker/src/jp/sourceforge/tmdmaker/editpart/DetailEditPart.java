/*
 * Copyright 2009-2017 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
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

import jp.sourceforge.tmdmaker.dialog.DetailEditDialog;
import jp.sourceforge.tmdmaker.dialog.ModelEditDialog;
import jp.sourceforge.tmdmaker.editpolicy.EntityLayoutEditPolicy;
import jp.sourceforge.tmdmaker.editpolicy.TMDModelGraphicalNodeEditPolicy;
import jp.sourceforge.tmdmaker.figure.EntityFigure;
import jp.sourceforge.tmdmaker.model.Detail;
import jp.sourceforge.tmdmaker.model.IdentifierRef;
import jp.sourceforge.tmdmaker.ui.editor.gef3.commands.TableDeleteCommand;
import jp.sourceforge.tmdmaker.ui.preferences.appearance.ModelAppearance;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

/**
 * ディテールのコントローラ
 * 
 * @author nakaG
 * 
 */
public class DetailEditPart extends AbstractEntityModelEditPart<Detail> {

	/**
	 * コンストラクタ
	 */
	public DetailEditPart(Detail entity) {
		super();
		setModel(entity);
	}

	@Override
	protected ModelEditDialog<Detail> getDialog() {
		return new DetailEditDialog(getControllShell(), getModel());
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.editpart.AbstractModelEditPart#updateFigure(org.eclipse.draw2d.IFigure)
	 */
	@Override
	protected void updateFigure(IFigure figure) {
		EntityFigure entityFigure = (EntityFigure) figure;
		Detail entity = getModel();

		entityFigure.setNotImplement(entity.isNotImplement());
		entityFigure.removeAllRelationship();

		entityFigure.setEntityName(entity.getName());
		IdentifierRef original = entity.getOriginalReusedIdentifier().getUniqueIdentifiers().get(0);
		entityFigure.setIdentifier(original.getName());
		if (entity.isDetailIdentifierEnabled()) {
			entityFigure.setIdentifier(entity.getDetailIdentifier().getName());
		}
		entityFigure.addRelationship(extractRelationship(entity, original));
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#createEditPolicies()
	 */
	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new DetailComponentEditPolicy());
		installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE, new TMDModelGraphicalNodeEditPolicy());
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new EntityLayoutEditPolicy());
	}

	/**
	 * 
	 * @author nakaG
	 * 
	 */
	private static class DetailComponentEditPolicy extends ComponentEditPolicy {

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.gef.editpolicies.ComponentEditPolicy#createDeleteCommand(org.eclipse.gef.requests.GroupRequest)
		 */
		@Override
		protected Command createDeleteCommand(GroupRequest deleteRequest) {
			Detail model = (Detail) getHost().getModel();
			return new TableDeleteCommand(model, model.getModelTargetConnections().get(0));

		}
	}

	@Override
	protected ModelAppearance getAppearance() {
		// TODO Auto-generated method stub
		return null;
	}

}

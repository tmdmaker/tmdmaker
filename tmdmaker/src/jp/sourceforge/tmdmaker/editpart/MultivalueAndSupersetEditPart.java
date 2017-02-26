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

import java.util.List;

import jp.sourceforge.tmdmaker.dialog.ModelEditDialog;
import jp.sourceforge.tmdmaker.dialog.MultivalueAndSupersetEditDialog;
import jp.sourceforge.tmdmaker.editpolicy.AbstractEntityModelEditPolicy;
import jp.sourceforge.tmdmaker.editpolicy.ReconnectableNodeEditPolicy;
import jp.sourceforge.tmdmaker.figure.EntityFigure;
import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.EntityType;
import jp.sourceforge.tmdmaker.model.IAttribute;
import jp.sourceforge.tmdmaker.model.IdentifierRef;
import jp.sourceforge.tmdmaker.model.MultivalueAndSuperset;
import jp.sourceforge.tmdmaker.ui.editor.gef3.commands.ModelEditCommand;
import jp.sourceforge.tmdmaker.ui.editor.gef3.commands.TableDeleteCommand;
import jp.sourceforge.tmdmaker.ui.preferences.appearance.ModelAppearance;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.GroupRequest;
import org.eclipse.jface.dialogs.Dialog;

/**
 * 多値のANDの概念的スーパーセットのコントローラ
 * 
 * @author nakaG
 * 
 */
public class MultivalueAndSupersetEditPart
		extends AbstractEntityModelEditPart<MultivalueAndSuperset> {

	/**
	 * コンストラクタ
	 */
	public MultivalueAndSupersetEditPart(MultivalueAndSuperset entity) {
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
		MultivalueAndSuperset entity = getModel();
		entityFigure.setNotImplement(false);
		List<IAttribute> atts = entity.getAttributes();
		entityFigure.removeAllRelationship();
		entityFigure.removeAllAttributes();

		entityFigure.setEntityName(entity.getName());
		entityFigure.setEntityType(EntityType.MA.getLabel());
		IdentifierRef identifierRef = entity.getReusedIdentifiers().entrySet().iterator().next()
				.getValue().getUniqueIdentifiers().get(0);
		entityFigure.setIdentifier(identifierRef.getName());
		for (IAttribute a : atts) {
			entityFigure.addAttribute(a.getName());
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
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new SupersetComponentEditPolicy());
		installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE, new ReconnectableNodeEditPolicy());
	}

	/**
	 * 
	 * @author nakaG
	 * 
	 */
	private static class SupersetComponentEditPolicy extends AbstractEntityModelEditPolicy<MultivalueAndSuperset> {
		@Override
		protected ModelEditDialog<MultivalueAndSuperset> getDialog() {
			return new MultivalueAndSupersetEditDialog(getControllShell(), getModel());
		}
		
		/**
		 * 
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.gef.editparts.AbstractEditPart#getCommand(org.eclipse.gef.Request)
		 */
		@Override
		public Command getCommand(Request request) {
			if (REQ_OPEN.equals(request.getType())){
				AbstractEntityModel entity = getModel();
				ModelEditDialog<MultivalueAndSuperset> dialog = getDialog();
				
				if (dialog.open() == Dialog.OK) {
					return new ModelEditCommand(entity, dialog.getEditedValue());
				}
			}
			return null;
		}
		
		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.gef.editpolicies.ComponentEditPolicy#createDeleteCommand(org.eclipse.gef.requests.GroupRequest)
		 */
		@Override
		protected Command createDeleteCommand(GroupRequest deleteRequest) {
			return new TableDeleteCommand(getModel(),
					getModel().getDetail().getModelTargetConnections().get(0));
		}
	}

	@Override
	protected ModelAppearance getAppearance() {
		return null;
	}

}

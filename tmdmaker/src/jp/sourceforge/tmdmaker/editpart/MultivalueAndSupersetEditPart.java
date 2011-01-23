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

import java.util.List;

import jp.sourceforge.tmdmaker.dialog.SupersetEditDialog;
import jp.sourceforge.tmdmaker.figure.EntityFigure;
import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.EntityType;
import jp.sourceforge.tmdmaker.model.IAttribute;
import jp.sourceforge.tmdmaker.model.IdentifierRef;
import jp.sourceforge.tmdmaker.model.MultivalueAndSuperset;
import jp.sourceforge.tmdmaker.model.command.ModelEditCommand;
import jp.sourceforge.tmdmaker.model.command.TableDeleteCommand;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;
import org.eclipse.jface.dialogs.Dialog;

/**
 * 多値のANDの概念的スーパーセットのコントローラ
 * 
 * @author nakaG
 * 
 */
public class MultivalueAndSupersetEditPart extends AbstractEntityEditPart {
	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.editpart.AbstractEntityEditPart#onDoubleClicked()
	 */
	@Override
	protected void onDoubleClicked() {
		AbstractEntityModel entity = (AbstractEntityModel) getModel();
		SupersetEditDialog dialog = new SupersetEditDialog(getViewer()
				.getControl().getShell(), entity);
		if (dialog.open() == Dialog.OK) {
			getViewer()
					.getEditDomain()
					.getCommandStack()
					.execute(
							new ModelEditCommand(entity, dialog
									.getEditedValue()));
		}
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.editpart.AbstractEntityEditPart#updateFigure(org.eclipse.draw2d.IFigure)
	 */
	@Override
	protected void updateFigure(IFigure figure) {
		logger.debug(getClass() + "#updateFigure()");
		EntityFigure entityFigure = (EntityFigure) figure;
		MultivalueAndSuperset entity = (MultivalueAndSuperset) getModel();
		entityFigure.setNotImplement(entity.isNotImplement());

		List<IAttribute> atts = entity.getAttributes();
		entityFigure.removeAllRelationship();
		entityFigure.removeAllAttributes();

		entityFigure.setEntityName(entity.getName());
		entityFigure.setEntityType(EntityType.MA.getLabel());
		// figure.setIdentifier(entity.getIdentifier().getName());
		IdentifierRef identifierRef = entity.getReusedIdentifieres().entrySet()
				.iterator().next().getValue().getIdentifires().get(0);
		entityFigure.setIdentifier(identifierRef.getName());
		// for (Map.Entry<AbstractEntityModel, ReusedIdentifier> rk : entity
		// .getReusedIdentifieres().entrySet()) {
		// for (Identifier i : rk.getValue().getIdentifires()) {
		// entityFigure.addRelationship(i.getName());
		// }
		// }
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
		installEditPolicy(EditPolicy.COMPONENT_ROLE,
				new SupersetComponentEditPolicy());

	}

	/**
	 * 
	 * @author nakaG
	 * 
	 */
	private static class SupersetComponentEditPolicy extends
			ComponentEditPolicy {

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.gef.editpolicies.ComponentEditPolicy#createDeleteCommand(org.eclipse.gef.requests.GroupRequest)
		 */
		@Override
		protected Command createDeleteCommand(GroupRequest deleteRequest) {
			MultivalueAndSuperset model = (MultivalueAndSuperset) getHost()
					.getModel();
			return new TableDeleteCommand(model, model.getDetail()
					.getModelTargetConnections().get(0));
		}

	}
}

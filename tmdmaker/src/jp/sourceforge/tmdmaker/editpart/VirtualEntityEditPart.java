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

import java.util.List;
import java.util.Map;

import jp.sourceforge.tmdmaker.dialog.TableEditDialog;
import jp.sourceforge.tmdmaker.dialog.model.EditAttribute;
import jp.sourceforge.tmdmaker.editpolicy.EntityLayoutEditPolicy;
import jp.sourceforge.tmdmaker.editpolicy.ReconnectableNodeEditPolicy;
import jp.sourceforge.tmdmaker.figure.EntityFigure;
import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.EntityType;
import jp.sourceforge.tmdmaker.model.Identifier;
import jp.sourceforge.tmdmaker.model.ReusedIdentifier;
import jp.sourceforge.tmdmaker.model.VirtualEntity;
import jp.sourceforge.tmdmaker.model.command.ImplementDerivationModelsDeleteCommand;
import jp.sourceforge.tmdmaker.model.command.ModelEditCommand;
import jp.sourceforge.tmdmaker.model.command.TableDeleteCommand;
import jp.sourceforge.tmdmaker.model.rule.ImplementRule;
import jp.sourceforge.tmdmaker.ui.setting.AppearanceSetting;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.graphics.Color;

/**
 * みなしエンティティのコントローラ
 * 
 * @author nakaG
 * 
 */
public class VirtualEntityEditPart extends AbstractEntityEditPart {
	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.editpart.AbstractEntityEditPart#onDoubleClicked()
	 */
	@Override
	protected void onDoubleClicked() {
		AbstractEntityModel entity = (AbstractEntityModel) getModel();
		TableEditDialog dialog = new TableEditDialog(getViewer().getControl()
				.getShell(), "みなしエンティティ編集", entity);
		if (dialog.open() == Dialog.OK) {
			CompoundCommand ccommand = new CompoundCommand();

			List<EditAttribute> editAttributeList = dialog
					.getEditAttributeList();
			addAttributeEditCommands(ccommand, entity, editAttributeList);

			AbstractEntityModel edited = dialog.getEditedValue();
			ModelEditCommand command = new ModelEditCommand(entity, edited);

			if (entity.isNotImplement() && !edited.isNotImplement()) {
				AbstractEntityModel original = ImplementRule
						.findOriginalImplementModel(entity);
				ccommand.add(new ImplementDerivationModelsDeleteCommand(entity,
						original));
			}

			ccommand.add(command);
			getViewer().getEditDomain().getCommandStack().execute(ccommand);
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
		EntityFigure entityFigure = (EntityFigure) figure;
		AbstractEntityModel entity = (AbstractEntityModel) getModel();
		entityFigure.setNotImplement(entity.isNotImplement());

		// List<Attribute> atts = entity.getAttributes();
		entityFigure.removeAllRelationship();
		// entityFigure.removeAllAttributes();

		entityFigure.setEntityName(entity.getName());
		entityFigure.setEntityType(EntityType.VE.getLabel());
		// figure.setIdentifier(entity.getIdentifier().getName());
		for (Map.Entry<AbstractEntityModel, ReusedIdentifier> rk : entity
				.getReusedIdentifieres().entrySet()) {
			for (Identifier i : rk.getValue().getIdentifires()) {
				entityFigure.addRelationship(i.getName());
			}
		}
		if (AppearanceSetting.getInstance().isColorEnabled()) {
			entityFigure.setBackgroundColor(new Color(null, AppearanceSetting
					.getInstance().getVirtualEntityRGB()));
		} else {
			entityFigure.setBackgroundColor(ColorConstants.white);
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
				new VirtualEntityComponentEditPolicy());
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new EntityLayoutEditPolicy());
		installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE,
				new ReconnectableNodeEditPolicy());
	}

	/**
	 * 
	 * @author nakaG
	 * 
	 */
	private static class VirtualEntityComponentEditPolicy extends
			ComponentEditPolicy {

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.gef.editpolicies.ComponentEditPolicy#createDeleteCommand(org.eclipse.gef.requests.GroupRequest)
		 */
		@Override
		protected Command createDeleteCommand(GroupRequest deleteRequest) {
			VirtualEntity model = (VirtualEntity) getHost().getModel();
			CompoundCommand ccommand = new CompoundCommand();
			if (model.isNotImplement()) {
				AbstractEntityModel original = ImplementRule
						.findOriginalImplementModel(model);
				ccommand.add(new ImplementDerivationModelsDeleteCommand(model,
						original));
			}

			ccommand.add(new TableDeleteCommand(model, model
					.getModelTargetConnections().get(0)));

			return ccommand;
		}

	}
}

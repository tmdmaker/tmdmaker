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

import java.util.ArrayList;
import java.util.List;

import jp.sourceforge.tmdmaker.dialog.ModelEditDialog;
import jp.sourceforge.tmdmaker.dialog.VirtualEntityEditDialog;
import jp.sourceforge.tmdmaker.dialog.model.EditAttribute;
import jp.sourceforge.tmdmaker.editpolicy.EntityLayoutEditPolicy;
import jp.sourceforge.tmdmaker.editpolicy.ReconnectableNodeEditPolicy;
import jp.sourceforge.tmdmaker.figure.EntityFigure;
import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.EntityType;
import jp.sourceforge.tmdmaker.model.Identifier;
import jp.sourceforge.tmdmaker.model.VirtualEntity;
import jp.sourceforge.tmdmaker.model.VirtualEntityType;
import jp.sourceforge.tmdmaker.model.rule.ImplementRule;
import jp.sourceforge.tmdmaker.ui.command.ImplementDerivationModelsDeleteCommand;
import jp.sourceforge.tmdmaker.ui.command.TableDeleteCommand;
import jp.sourceforge.tmdmaker.ui.preferences.appearance.ModelAppearance;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

/**
 * みなしエンティティのコントローラ
 * 
 * @author nakaG
 * 
 */
public class VirtualEntityEditPart extends AbstractEntityModelEditPart<VirtualEntity> {
	
	/**
	 * コンストラクタ
	 */
	public VirtualEntityEditPart(VirtualEntity entity)
	{
		super();
		setModel(entity);
	}
	
	@Override
	protected CompoundCommand createEditCommand(List<EditAttribute> editAttributeList, AbstractEntityModel editedValue)
	{
		CompoundCommand ccommand = super.createEditCommand(editAttributeList, editedValue);
		Command deleteCommand    = getDeleteCommand(editedValue);
		if (deleteCommand != null)
		{
			ccommand.add(deleteCommand);
		}
		return ccommand;
	}
	
	@Override
	protected ModelEditDialog<VirtualEntity> getDialog()
	{
		return new VirtualEntityEditDialog(getControllShell(), getModel());
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
		VirtualEntity entity = getModel();
		entityFigure.setNotImplement(entity.isNotImplement());

		entityFigure.removeAllRelationship();
		entityFigure.setEntityName(entity.getName());
		entityFigure.setEntityType(EntityType.VE.getLabel());
		entityFigure.addRelationship(extractRelationship(entity));
		entityFigure.setColor(getForegroundColor(), getBackgroundColor());
	}
	
	/**
	 * @param table
	 */
	protected List<String> extractRelationship(VirtualEntity table) {
		List<String> relationship = new ArrayList<String>();

		for (Identifier i : table.getOriginalReusedIdentifier().getUniqueIdentifiers()) {
			relationship.add(i.getName());
		}
		return relationship;
	}

	@Override
	protected ModelAppearance getAppearance() {
		ModelAppearance appearance = null;
		if (getModel().getVirtualEntityType().equals(VirtualEntityType.RESOURCE)) {
			appearance = ModelAppearance.RESOURCE_VIRTUAL_ENTITY;
		} else if (getModel().getVirtualEntityType()
				.equals(VirtualEntityType.EVENT)) {
			appearance = ModelAppearance.EVENT_SUBSET;
		} else {
			appearance = ModelAppearance.VIRTUAL_ENTITY;
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

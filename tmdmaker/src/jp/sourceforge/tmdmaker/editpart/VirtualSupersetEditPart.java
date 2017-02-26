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

import jp.sourceforge.tmdmaker.dialog.ModelEditDialog;
import jp.sourceforge.tmdmaker.dialog.SupersetEditDialog;
import jp.sourceforge.tmdmaker.editpolicy.AbstractEntityModelEditPolicy;
import jp.sourceforge.tmdmaker.editpolicy.ReconnectableNodeEditPolicy;
import jp.sourceforge.tmdmaker.figure.EntityFigure;
import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.EntityType;
import jp.sourceforge.tmdmaker.model.VirtualSuperset;
import jp.sourceforge.tmdmaker.model.VirtualSupersetType;
import jp.sourceforge.tmdmaker.ui.editor.gef3.commands.ModelDeleteCommand;
import jp.sourceforge.tmdmaker.ui.editor.gef3.commands.ModelEditCommand;
import jp.sourceforge.tmdmaker.ui.editor.gef3.commands.VirtualSupersetTypeDeleteCommand;
import jp.sourceforge.tmdmaker.ui.preferences.appearance.ModelAppearance;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.requests.GroupRequest;
import org.eclipse.jface.dialogs.Dialog;

/**
 * みなしスーパーセットのコントローラ
 *
 * @author nakaG
 *
 */
public class VirtualSupersetEditPart extends AbstractEntityModelEditPart<VirtualSuperset> {

	/**
	 * コンストラクタ
	 */
	public VirtualSupersetEditPart(VirtualSuperset entity) {
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
		EntityFigure entityFigure = (EntityFigure) figure;
		AbstractEntityModel entity = getModel();
		entityFigure.setNotImplement(entity.isNotImplement());

		// みなしスーパーセットはReusedを表示させない
		// entityFigure.removeAllRelationship();

		entityFigure.setEntityName(entity.getName());
		entityFigure.setEntityType(EntityType.VE.getLabel());
		entityFigure.setColor(getForegroundColor(), getBackgroundColor());
	}

	@Override
	protected ModelAppearance getAppearance() {
		return ModelAppearance.SUPERSET_COLOR;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.editpart.AbstractTMDEditPart#onDoubleClicked()
	 */
	@Override
	protected void onDoubleClicked() {
		AbstractEntityModel entity = getModel();
		SupersetEditDialog dialog = new SupersetEditDialog(getViewer().getControl().getShell(),
				entity);
		if (dialog.open() == Dialog.OK) {
			getViewer().getEditDomain().getCommandStack()
					.execute(new ModelEditCommand(entity, dialog.getEditedValue()));
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
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new VirtualSupersetComponentEditPolicy());
		installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE, new ReconnectableNodeEditPolicy());
	}

	/**
	 * みなしスーパーセットのComponentEditPolicy
	 * 
	 * @author nakaG
	 * 
	 */
	private static class VirtualSupersetComponentEditPolicy extends AbstractEntityModelEditPolicy<VirtualSuperset> {
		
		@Override
		protected ModelEditDialog<VirtualSuperset> getDialog() {
			return null;
		}

		/**
		 * 
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.gef.editpolicies.ComponentEditPolicy#createDeleteCommand(org.eclipse.gef.requests.GroupRequest)
		 */
		@Override
		protected Command createDeleteCommand(GroupRequest deleteRequest) {
			VirtualSupersetType aggregator = getModel().getVirtualSupersetType();

			CompoundCommand ccommand = new CompoundCommand();
			ccommand.add(new VirtualSupersetTypeDeleteCommand(getDiagram(), aggregator));
			ccommand.add(new ModelDeleteCommand(getDiagram(), getModel()));
			return ccommand.unwrap();
		}
	}
}

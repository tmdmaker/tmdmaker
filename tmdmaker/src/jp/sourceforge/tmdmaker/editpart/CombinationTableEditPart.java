/*
 * Copyright 2009-2017 TMD-Maker Project <http://tmdmaker.osdn.jp/>
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

import jp.sourceforge.tmdmaker.Messages;
import jp.sourceforge.tmdmaker.dialog.CombinationTableEditDialog;
import jp.sourceforge.tmdmaker.dialog.ModelEditDialog;
import jp.sourceforge.tmdmaker.editpolicy.EntityLayoutEditPolicy;
import jp.sourceforge.tmdmaker.editpolicy.TMDModelGraphicalNodeEditPolicy;
import jp.sourceforge.tmdmaker.figure.EntityFigure;
import jp.sourceforge.tmdmaker.model.AbstractConnectionModel;
import jp.sourceforge.tmdmaker.model.CombinationTable;
import jp.sourceforge.tmdmaker.ui.editor.gef3.commands.TableDeleteCommand;
import jp.sourceforge.tmdmaker.ui.preferences.appearance.ModelAppearance;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;
import org.eclipse.jface.dialogs.Dialog;

/**
 * 対照表のコントローラ
 * 
 * @author nakaG
 * 
 */
public class CombinationTableEditPart extends AbstractEntityModelEditPart<CombinationTable> {

	/**
	 * コンストラクタ
	 */
	public CombinationTableEditPart(CombinationTable table) {
		super();
		setModel(table);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.editpart.AbstractModelEditPart#updateFigure(org.eclipse.draw2d.IFigure)
	 */
	@Override
	protected void updateFigure(IFigure figure) {
		logger.debug(getClass() + "#updateFigure()"); //$NON-NLS-1$
		EntityFigure entityFigure = (EntityFigure) figure;
		CombinationTable table = getModel();

		entityFigure.setEntityType(table.getCombinationTableType().getLabel());

		entityFigure.setNotImplement(table.isNotImplement());
		entityFigure.removeAllRelationship();
		entityFigure.setEntityName(table.getName());

		entityFigure.addRelationship(extractRelationship(table));
		entityFigure.setColor(getForegroundColor(), getBackgroundColor());
	}

	@Override
	protected ModelAppearance getAppearance() {
		return ModelAppearance.COMBINATION_TABLE;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#createEditPolicies()
	 */
	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new CombinationTableComponentEditPolicy());
		installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE, new TMDModelGraphicalNodeEditPolicy());
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new EntityLayoutEditPolicy());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.editpart.AbstractModelEditPart#onDoubleClicked()
	 */
	@Override
	protected void onDoubleClicked() {
		logger.debug(getClass() + "#onDoubleClicked()"); //$NON-NLS-1$

		ModelEditDialog<CombinationTable> dialog = getDialog();
		if (dialog.open() != Dialog.OK)
			return;

		CompoundCommand ccommand = createEditCommand(dialog.getEditAttributeList(),
				dialog.getEditedValue());
		executeEditCommand(ccommand.unwrap());
	}

	@Override
	protected ModelEditDialog<CombinationTable> getDialog() {
		return new CombinationTableEditDialog(getControllShell(), Messages.EditCombinationTable,
				getModel());
	}

	/**
	 * 
	 * @author nakaG
	 * 
	 */
	private static class CombinationTableComponentEditPolicy extends ComponentEditPolicy {
		/**
		 * 
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.gef.editpolicies.ComponentEditPolicy#createDeleteCommand(org.eclipse.gef.requests.GroupRequest)
		 */
		@Override
		protected Command createDeleteCommand(GroupRequest deleteRequest) {
			CombinationTable model = (CombinationTable) getHost().getModel();
			AbstractConnectionModel creationRelationship = (AbstractConnectionModel) model
					.findCreationRelationship().getSource();
			return new TableDeleteCommand(model, creationRelationship);
		}
	}
}

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

import jp.sourceforge.tmdmaker.dialog.ModelEditDialog;
import jp.sourceforge.tmdmaker.dialog.TableEditDialog;
import jp.sourceforge.tmdmaker.editpolicy.EntityLayoutEditPolicy;
import jp.sourceforge.tmdmaker.editpolicy.TMDModelGraphicalNodeEditPolicy;
import jp.sourceforge.tmdmaker.figure.EntityFigure;
import jp.sourceforge.tmdmaker.model.RecursiveTable;
import jp.sourceforge.tmdmaker.ui.command.TableDeleteCommand;
import jp.sourceforge.tmdmaker.ui.preferences.appearance.ModelAppearance;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

/**
 * 再帰表のコントローラ
 * 
 * @author nakaG
 * 
 */
public class RecursiveTableEditPart extends AbstractEntityModelEditPart<RecursiveTable> {

	/**
	 * コンストラクタ
	 */
	public RecursiveTableEditPart(RecursiveTable table)
	{
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
		EntityFigure entityFigure = (EntityFigure) figure;
		RecursiveTable table = getModel();
		
		entityFigure.setNotImplement(table.isNotImplement());
		entityFigure.removeAllRelationship();
		entityFigure.setEntityName(table.getName());
		entityFigure.addRelationship(extractRelationship(table));
		entityFigure.setColor(getForegroundColor(), getBackgroundColor());
	}

	@Override
	protected ModelAppearance getAppearance() {
		return ModelAppearance.RECURSIVE_TABLE;
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
				new RecursiveTableComponentEditPolicy());
		installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE,
				new TMDModelGraphicalNodeEditPolicy());
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new EntityLayoutEditPolicy());
	}

	@Override
	protected ModelEditDialog<RecursiveTable> getDialog()
	{
		return new TableEditDialog<RecursiveTable>(getControllShell(), "再帰表編集", getModel());
	}

	/**
	 * 
	 * @author nakaG
	 * 
	 */
	private static class RecursiveTableComponentEditPolicy extends
			ComponentEditPolicy {

		/**
		 * 
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.gef.editpolicies.ComponentEditPolicy#createDeleteCommand(org.eclipse.gef.requests.GroupRequest)
		 */
		@Override
		protected Command createDeleteCommand(GroupRequest deleteRequest) {
			RecursiveTable model = (RecursiveTable) getHost().getModel();
			return new TableDeleteCommand(model, model
					.getModelTargetConnections().get(0));
		}

	}
}

/*
 * Copyright 2009-2010 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
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
import java.util.Map;

import jp.sourceforge.tmdmaker.dialog.CombinationTableEditDialog;
import jp.sourceforge.tmdmaker.dialog.model.EditAttribute;
import jp.sourceforge.tmdmaker.editpolicy.EntityLayoutEditPolicy;
import jp.sourceforge.tmdmaker.editpolicy.TMDModelGraphicalNodeEditPolicy;
import jp.sourceforge.tmdmaker.figure.EntityFigure;
import jp.sourceforge.tmdmaker.model.AbstractConnectionModel;
import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.CombinationTable;
import jp.sourceforge.tmdmaker.model.Identifier;
import jp.sourceforge.tmdmaker.model.RecursiveTable;
import jp.sourceforge.tmdmaker.model.ReusedIdentifier;
import jp.sourceforge.tmdmaker.model.command.ModelEditCommand;
import jp.sourceforge.tmdmaker.model.command.TableDeleteCommand;

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
public class CombinationTableEditPart extends AbstractEntityEditPart {

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#createFigure()
	 */
	@Override
	protected IFigure createFigure() {
		EntityFigure figure = new EntityFigure();
		updateFigure(figure);

		return figure;
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
		CombinationTable table = (CombinationTable) getModel();

		entityFigure.setEntityType(table.getCombinationTableType().getLabel());
		entityFigure.setNotImplement(table.isNotImplement());
		// List<Attribute> atts = table.getAttributes();
		entityFigure.removeAllRelationship();
		// entityFigure.removeAllAttributes();

		entityFigure.setEntityName(table.getName());
		List<String> reusedIdentifierNames = new ArrayList<String>();
		for (Map.Entry<AbstractEntityModel, ReusedIdentifier> rk : table
				.getReusedIdentifieres().entrySet()) {
			for (Identifier i : rk.getValue().getIdentifires()) {
				if (!reusedIdentifierNames.contains(i.getName())
						|| rk.getKey() instanceof RecursiveTable) {
					reusedIdentifierNames.add(i.getName());
				}
			}
		}
		for (String name : reusedIdentifierNames) {
			entityFigure.addRelationship(name);
		}
		// for (Attribute a : atts) {
		// entityFigure.addAttribute(a.getName());
		// }
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
				new CombinationTableComponentEditPolicy());
		installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE,
				new TMDModelGraphicalNodeEditPolicy());
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new EntityLayoutEditPolicy());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.editpart.AbstractEntityEditPart#onDoubleClicked()
	 */
	@Override
	protected void onDoubleClicked() {
		logger.debug(getClass() + "#onDoubleClicked()");
		CombinationTable table = (CombinationTable) getModel();
		// TableEditDialog dialog = new TableEditDialog(getViewer().getControl()
		// .getShell(), table.getName(), table.getReuseKeys(), table
		// .getAttributes());
		CombinationTableEditDialog dialog = new CombinationTableEditDialog(
				getViewer().getControl().getShell(), "対照表編集", table);
		if (dialog.open() == Dialog.OK) {
			CompoundCommand ccommand = new CompoundCommand();

			List<EditAttribute> editAttributeList = dialog
					.getEditAttributeList();
			addAttributeEditCommands(ccommand, table, editAttributeList);

			// CombinationTableEditCommand command = new
			// CombinationTableEditCommand(
			// table, (CombinationTable) dialog.getEditedValue());
			ModelEditCommand command = new ModelEditCommand(table, dialog
					.getEditedValue());
			ccommand.add(command);
			getViewer().getEditDomain().getCommandStack().execute(
					ccommand.unwrap());
			// TableEditCommand<CombinationTable> command = new
			// TableEditCommand<CombinationTable>(
			// table, dialog.getEntityName(), dialog.getReuseKeys(),
			// dialog.getAttributes());
			// getViewer().getEditDomain().getCommandStack().execute(command);
		}

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#getContentPane()
	 */
	@Override
	public IFigure getContentPane() {
		return ((EntityFigure) getFigure()).getAttributeCompartmentFigure();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#getModelChildren()
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected List getModelChildren() {
		return ((AbstractEntityModel) getModel()).getAttributes();
	}

	/**
	 * 
	 * @author nakaG
	 * 
	 */
	private static class CombinationTableComponentEditPolicy extends
			ComponentEditPolicy {
		/**
		 * 
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.gef.editpolicies.ComponentEditPolicy#createDeleteCommand(org.eclipse.gef.requests.GroupRequest)
		 */
		@Override
		protected Command createDeleteCommand(GroupRequest deleteRequest) {
			// CombinationTableDeleteCommand command = new
			// CombinationTableDeleteCommand(
			// (CombinationTable) getHost().getModel());
			// return command;
			CombinationTable model = (CombinationTable) getHost().getModel();
			AbstractConnectionModel creationRelationship = (AbstractConnectionModel) model
					.findCreationRelationship().getSource();
			return new TableDeleteCommand(model, creationRelationship);
		}
	}

	// /**
	// *
	// * @author nakaG
	// *
	// */
	// private static class CombinationTableEditCommand extends Command {
	// private String newName;
	// // private List<Identifier> reusedIdentifieres;
	// private List<Attribute> newAttributes;
	// private boolean newNotImplement;
	// private String newImplementName;
	// private CombinationTableType newType;
	// protected CombinationTable model;
	// protected CombinationTable newValue;
	//
	// private String oldName;
	// // private List<Identifier> oldReuseKeys = new ArrayList<Identifier>();
	// private List<Attribute> oldAttributes;
	// private boolean oldNotImplement;
	// private CombinationTableType oldType;
	// private String oldImplementName;
	//
	// public CombinationTableEditCommand(CombinationTable toBeEdit,
	// CombinationTable newValue) {
	// this.model = toBeEdit;
	// this.newValue = newValue;
	// this.oldName = toBeEdit.getName();
	// this.oldAttributes = toBeEdit.getAttributes();
	// this.oldNotImplement = toBeEdit.isNotImplement();
	// this.oldImplementName = toBeEdit.getImplementName();
	// this.oldType = toBeEdit.getCombinationTableType();
	// this.newName = this.newValue.getName();
	// this.newAttributes = this.newValue.getAttributes();
	// this.newNotImplement = this.newValue.isNotImplement();
	// this.newImplementName = this.newValue.getImplementName();
	// this.newType = this.newValue.getCombinationTableType();
	// }
	//
	// /**
	// * {@inheritDoc}
	// *
	// * @see org.eclipse.gef.commands.Command#execute()
	// */
	// @Override
	// public void execute() {
	// model.setAttributes(newAttributes);
	// model.setNotImplement(newNotImplement);
	// model.setImplementName(newImplementName);
	// model.setCombinationTableType(newType);
	// model.setName(newName);
	// }
	//
	// /**
	// * {@inheritDoc}
	// *
	// * @see org.eclipse.gef.commands.Command#undo()
	// */
	// @Override
	// public void undo() {
	// model.setAttributes(oldAttributes);
	// model.setNotImplement(oldNotImplement);
	// model.setImplementName(oldImplementName);
	// model.setCombinationTableType(oldType);
	// model.setName(oldName);
	// }
	// }

}

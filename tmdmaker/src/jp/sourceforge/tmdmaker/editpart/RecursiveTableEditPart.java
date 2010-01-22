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

import java.util.List;
import java.util.Map;

import jp.sourceforge.tmdmaker.dialog.TableEditDialog;
import jp.sourceforge.tmdmaker.editpolicy.TMDModelGraphicalNodeEditPolicy;
import jp.sourceforge.tmdmaker.editpolicy.EntityLayoutEditPolicy;
import jp.sourceforge.tmdmaker.figure.EntityFigure;
import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.EditAttribute;
import jp.sourceforge.tmdmaker.model.Identifier;
import jp.sourceforge.tmdmaker.model.RecursiveTable;
import jp.sourceforge.tmdmaker.model.ReusedIdentifier;
import jp.sourceforge.tmdmaker.model.command.TableDeleteCommand;
import jp.sourceforge.tmdmaker.model.command.TableEditCommand;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;
import org.eclipse.jface.dialogs.Dialog;

/**
 * 再帰表のコントローラ
 * 
 * @author nakaG
 * 
 */
public class RecursiveTableEditPart extends AbstractEntityEditPart {

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
		EntityFigure entityFigure = (EntityFigure) figure;
		RecursiveTable table = (RecursiveTable) getModel();
		entityFigure.setNotImplement(table.isNotImplement());
		// List<Identifier> ids = table.getReuseKeys();
		// List<Attribute> atts = table.getAttributes();
		entityFigure.removeAllRelationship();
		// entityFigure.removeAllAttributes();

		entityFigure.setEntityName(table.getName());
		for (Map.Entry<AbstractEntityModel, ReusedIdentifier> rk : table
				.getReusedIdentifieres().entrySet()) {
			for (Identifier i : rk.getValue().getIdentifires()) {
				entityFigure.addRelationship(i.getName());
			}
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
				new RecursiveTableComponentEditPolicy());
		installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE,
				new TMDModelGraphicalNodeEditPolicy());
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new EntityLayoutEditPolicy());
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
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.editpart.AbstractEntityEditPart#onDoubleClicked()
	 */
	@Override
	protected void onDoubleClicked() {
		logger.debug(getClass() + "#onDoubleClicked()");
		RecursiveTable table = (RecursiveTable) getModel();
		// TableEditDialog dialog = new TableEditDialog(getViewer().getControl()
		// .getShell(), table.getName(), table.getReuseKeys(), table
		// .getAttributes());
		TableEditDialog dialog = new TableEditDialog(getViewer().getControl()
				.getShell(), "再帰表編集", table);
		if (dialog.open() == Dialog.OK) {
			CompoundCommand ccommand = new CompoundCommand();

			List<EditAttribute> editAttributeList = dialog
					.getEditAttributeList();
			addAttributeEditCommands(ccommand, table, editAttributeList);

			TableEditCommand<RecursiveTable> command = new TableEditCommand<RecursiveTable>(
					table, (RecursiveTable) dialog.getEditedValue());
			ccommand.add(command);
			getViewer().getEditDomain().getCommandStack().execute(ccommand);

			// TableEditCommand<RecursiveTable> command = new
			// TableEditCommand<RecursiveTable>(
			// table, dialog.getEntityName(), dialog.getReuseKeys(),
			// dialog.getAttributes());
			// getViewer().getEditDomain().getCommandStack().execute(command);
		}

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
			// RecursiveTableDeleteCommand command = new
			// RecursiveTableDeleteCommand(
			// (Diagram) getHost().getParent().getModel(),
			// (RecursiveTable) getHost().getModel());
			// return command;
			RecursiveTable model = (RecursiveTable) getHost().getModel();
			return new TableDeleteCommand(model, model
					.getModelTargetConnections().get(0));
		}

	}

	// /**
	// *
	// * @author nakaG
	// *
	// */
	// private static class RecursiveTableDeleteCommand extends
	// ConnectableElementDeleteCommand {
	// /** 再帰表 */
	// private RecursiveTable model;
	//
	// /**
	// * コンストラクタ
	// *
	// * @param diagram
	// * 親
	// * @param model
	// * 再帰表
	// */
	// public RecursiveTableDeleteCommand(Diagram diagram, RecursiveTable model)
	// {
	// super();
	// this.diagram = diagram;
	// this.model = model;
	// this.sourceConnections.addAll(model.getModelSourceConnections());
	// this.targetConnections.addAll(model.getModelTargetConnections());
	// }
	//
	// /**
	// *
	// * {@inheritDoc}
	// *
	// * @see org.eclipse.gef.commands.Command#execute()
	// */
	// @Override
	// public void execute() {
	//
	// diagram.removeChild(model);
	//
	// detachConnections(sourceConnections);
	// detachConnections(targetConnections);
	// }
	//
	// /**
	// *
	// * {@inheritDoc}
	// *
	// * @see org.eclipse.gef.commands.Command#undo()
	// */
	// @Override
	// public void undo() {
	// diagram.addChild(model);
	// attathConnections(sourceConnections);
	// attathConnections(targetConnections);
	// }
	// }
}

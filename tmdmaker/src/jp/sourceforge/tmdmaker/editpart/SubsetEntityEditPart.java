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
import jp.sourceforge.tmdmaker.editpolicy.EntityLayoutEditPolicy;
import jp.sourceforge.tmdmaker.editpolicy.TMDModelGraphicalNodeEditPolicy;
import jp.sourceforge.tmdmaker.figure.EntityFigure;
import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.Diagram;
import jp.sourceforge.tmdmaker.model.EditAttribute;
import jp.sourceforge.tmdmaker.model.Identifier;
import jp.sourceforge.tmdmaker.model.ReusedIdentifier;
import jp.sourceforge.tmdmaker.model.SubsetEntity;
import jp.sourceforge.tmdmaker.model.SubsetType;
import jp.sourceforge.tmdmaker.model.SubsetType2SubsetRelationship;
import jp.sourceforge.tmdmaker.model.command.SubsetTypeDeleteCommand;
import jp.sourceforge.tmdmaker.model.command.TableEditCommand;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;
import org.eclipse.jface.dialogs.Dialog;

/**
 * サブセットのコントローラ
 * 
 * @author nakaG
 * 
 */
public class SubsetEntityEditPart extends AbstractEntityEditPart {
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
		SubsetEntity entity = (SubsetEntity) getModel();
		entityFigure.setNotImplement(entity.isNotImplement());

		// List<Identifier> ids = entity.getReuseKeys().;

		// List<Attribute> atts = entity.getAttributes();
		entityFigure.removeAllRelationship();
		// entityFigure.removeAllAttributes();

		entityFigure.setEntityName(entity.getName());

		if (entity.isSupersetAnEntity()) {
			entityFigure.setIdentifier(entity.getOriginalReusedIdentifier()
					.getIdentifires().get(0).getName());
		} else {
			for (Identifier i : entity.getOriginalReusedIdentifier()
					.getIdentifires()) {
				entityFigure.addRelationship(i.getName());
			}
		}

		for (Map.Entry<AbstractEntityModel, ReusedIdentifier> rk : entity
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
				new SubsetEntityComponentEditPolicy());
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
		SubsetEntity table = (SubsetEntity) getModel();
		// TableEditDialog dialog = new TableEditDialog(getViewer().getControl()
		// .getShell(), table.getName(), table.getReuseKeys(), table
		// .getAttributes());
		TableEditDialog dialog = new TableEditDialog(getViewer().getControl()
				.getShell(), "サブセット表編集", table);
		if (dialog.open() == Dialog.OK) {
			CompoundCommand ccommand = new CompoundCommand();

			List<EditAttribute> editAttributeList = dialog
					.getEditAttributeList();
			addAttributeEditCommands(ccommand, table, editAttributeList);

			TableEditCommand<SubsetEntity> command = new TableEditCommand<SubsetEntity>(
					table, (SubsetEntity) dialog.getEditedValue());
			ccommand.add(command);
			getViewer().getEditDomain().getCommandStack().execute(ccommand);
			// TableEditCommand<SubsetEntity> command = new
			// TableEditCommand<SubsetEntity>(
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
	private static class SubsetEntityComponentEditPolicy extends
			ComponentEditPolicy {

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.gef.editpolicies.ComponentEditPolicy#createDeleteCommand(org.eclipse.gef.requests.GroupRequest)
		 */
		@Override
		protected Command createDeleteCommand(GroupRequest deleteRequest) {
			CompoundCommand ccommand = new CompoundCommand();
			Diagram diagram = (Diagram) getHost().getParent().getModel();
			SubsetEntity model = (SubsetEntity) getHost().getModel();
			SubsetEntityDeleteCommand command1 = new SubsetEntityDeleteCommand(
					diagram, model);
			ccommand.add(command1);
			// RelatedRelationship relationship = findRelatedRelationship(model
			// .getModelTargetConnections());
			SubsetType2SubsetRelationship relationship = (SubsetType2SubsetRelationship) model
					.findRelationshipFromTargetConnections(
							SubsetType2SubsetRelationship.class).get(0);
			SubsetTypeDeleteCommand command2 = new SubsetTypeDeleteCommand(
					diagram, (SubsetType) relationship.getSource());
			ccommand.add(command2);
			return ccommand;
		}

	}

	/**
	 * 
	 * @author nakaG
	 * 
	 */
	private static class SubsetEntityDeleteCommand extends Command {
		/** 親 */
		private Diagram diagram;
		/** 削除対象 */
		private SubsetEntity model;
		/** サブセットタイプとのコネクション */
		private SubsetType2SubsetRelationship subsetType2SubsetEntityRelationship;

		// private SubsetType subsetType;

		/**
		 * コンストラクタ
		 * 
		 * @param diagram
		 *            親
		 * @param model
		 *            削除対象
		 */
		public SubsetEntityDeleteCommand(Diagram diagram, SubsetEntity model) {
			super();
			this.diagram = diagram;
			this.model = model;
			this.subsetType2SubsetEntityRelationship = (SubsetType2SubsetRelationship) this.model
					.findRelationshipFromTargetConnections(
							SubsetType2SubsetRelationship.class).get(0);
			// findRelatedRelationship(this.model.getModelTargetConnections());
			// this.subsetType = (SubsetType)
			// subsetType2SubsetEntityRelationship
			// .getSource();
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.gef.commands.Command#canExecute()
		 */
		@Override
		public boolean canExecute() {
			return model.isDeletable();
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.gef.commands.Command#execute()
		 */
		@Override
		public void execute() {
			this.subsetType2SubsetEntityRelationship.disconnect();
			// this.model.setDiagram(null);
			this.diagram.removeChild(this.model);

		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.gef.commands.Command#undo()
		 */
		@Override
		public void undo() {
			this.diagram.addChild(this.model);
			// this.model.setDiagram(this.diagram);
			this.subsetType2SubsetEntityRelationship.connect();
		}

	}
}

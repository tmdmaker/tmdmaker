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

import java.util.Map;

import jp.sourceforge.tmdmaker.dialog.SupersetEditDialog;
import jp.sourceforge.tmdmaker.figure.EntityFigure;
import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.Diagram;
import jp.sourceforge.tmdmaker.model.EntityType;
import jp.sourceforge.tmdmaker.model.Identifier;
import jp.sourceforge.tmdmaker.model.ReusedIdentifier;
import jp.sourceforge.tmdmaker.model.VirtualSuperset;
import jp.sourceforge.tmdmaker.model.VirtualSupersetAggregator;
import jp.sourceforge.tmdmaker.model.command.ConnectableElementDeleteCommand;
import jp.sourceforge.tmdmaker.model.command.ModelEditCommand;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;
import org.eclipse.jface.dialogs.Dialog;

/**
 * みなしスーパーセットのコントローラ
 * 
 * @author nakaG
 * 
 */
public class VirtualSupersetEditPart extends AbstractEntityEditPart {
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

		entityFigure.removeAllRelationship();

		entityFigure.setEntityName(entity.getName());
		entityFigure.setEntityType(EntityType.VE.getLabel());
		for (Map.Entry<AbstractEntityModel, ReusedIdentifier> rk : entity
				.getReusedIdentifieres().entrySet()) {
			for (Identifier i : rk.getValue().getIdentifires()) {
				entityFigure.addRelationship(i.getName());
			}
		}
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.editpart.AbstractTMDEditPart#onDoubleClicked()
	 */
	@Override
	protected void onDoubleClicked() {
		AbstractEntityModel entity = (AbstractEntityModel) getModel();
		SupersetEditDialog dialog = new SupersetEditDialog(
				getViewer().getControl().getShell(), entity);
		if (dialog.open() == Dialog.OK) {
			getViewer().getEditDomain().getCommandStack().execute(
					new ModelEditCommand(entity, dialog.getEditedValue()));
		}
	}

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
	 * @see org.eclipse.gef.editparts.AbstractEditPart#createEditPolicies()
	 */
	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE,
				new VirtualSupersetComponentEditPolicy());

	}

	/**
	 * みなしスーパーセットのComponentEditPolicy
	 * 
	 * @author hiro
	 * 
	 */
	private static class VirtualSupersetComponentEditPolicy extends
			ComponentEditPolicy {
		/**
		 * 
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.gef.editpolicies.ComponentEditPolicy#createDeleteCommand(org.eclipse.gef.requests.GroupRequest)
		 */
		@Override
		protected Command createDeleteCommand(GroupRequest deleteRequest) {
			VirtualSuperset model = (VirtualSuperset) getHost().getModel();
			Diagram diagram = model.getDiagram();
			VirtualSupersetAggregator aggregator = model
					.getVirtualSupersetAggregator();

			CompoundCommand ccommand = new CompoundCommand();
			ccommand.add(new VirtualSupersetAggregatorDeleteCommand(diagram,
					aggregator));
			ccommand.add(new VirtualSupersetDeleteCommand(diagram, model));
			return ccommand.unwrap();
		}

	}

	/**
	 * みなしスーパーセット削除Command
	 * 
	 * @author hiro
	 * 
	 */
	private static class VirtualSupersetDeleteCommand extends Command {
		private Diagram diagram;
		private VirtualSuperset model;

		/**
		 * コンストラクタ
		 * 
		 * @param diagram
		 *            ダイアグラム
		 * @param model
		 *            みなしスーパーセット
		 */
		public VirtualSupersetDeleteCommand(Diagram diagram,
				VirtualSuperset model) {
			this.diagram = diagram;
			this.model = model;
		}

		/**
		 * 
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.gef.commands.Command#execute()
		 */
		@Override
		public void execute() {
			diagram.removeChild(model);
		}

		/**
		 * 
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.gef.commands.Command#undo()
		 */
		@Override
		public void undo() {
			diagram.addChild(model);
		}

	}

	/**
	 * みなしスーパーセットとの接点モデルの削除Command
	 * 
	 * @author hiro
	 * 
	 */
	private static class VirtualSupersetAggregatorDeleteCommand extends
			ConnectableElementDeleteCommand {
		private VirtualSupersetAggregator model;

		/**
		 * コンストラクタ
		 * 
		 * @param diagram
		 *            ダイアグラム
		 * @param model
		 *            みなしスーパーセットとの接点
		 */
		public VirtualSupersetAggregatorDeleteCommand(Diagram diagram,
				VirtualSupersetAggregator model) {
			this.diagram = diagram;
			this.model = model;
			sourceConnections.addAll(model.getModelSourceConnections());
			targetConnections.addAll(model.getModelTargetConnections());
		}

		/**
		 * 
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.gef.commands.Command#execute()
		 */
		@Override
		public void execute() {
			// 接点と接続しているコネクションは全て削除
			detachConnections(sourceConnections);
			detachConnections(targetConnections);
			diagram.removeChild(model);
		}

		/**
		 * 
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.gef.commands.Command#undo()
		 */
		@Override
		public void undo() {
			diagram.addChild(model);
			attathConnections(sourceConnections);
			attathConnections(targetConnections);
		}

	}
}

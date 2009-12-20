/*
 * Copyright 2009 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
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
package jp.sourceforge.tmdmaker.action;

import java.util.List;

import jp.sourceforge.tmdmaker.dialog.VirtualSupersetEditDialog;
import jp.sourceforge.tmdmaker.editpart.AbstractTMDEditPart;
import jp.sourceforge.tmdmaker.editpart.DiagramEditPart;
import jp.sourceforge.tmdmaker.editpart.VirtualSupersetEditPart;
import jp.sourceforge.tmdmaker.model.AbstractConnectionModel;
import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.ConnectableElement;
import jp.sourceforge.tmdmaker.model.Diagram;
import jp.sourceforge.tmdmaker.model.RelatedRelationship;
import jp.sourceforge.tmdmaker.model.VirtualSuperset;
import jp.sourceforge.tmdmaker.model.VirtualSupersetAggregator;
import jp.sourceforge.tmdmaker.model.command.ConnectionCreateCommand;
import jp.sourceforge.tmdmaker.model.command.ConnectionDeleteCommand;
import jp.sourceforge.tmdmaker.model.command.TableEditCommand;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbenchPart;

/**
 * みなしスーパーセット作成アクション
 * 
 * @author nakaG
 * 
 */
public class VirtualSupersetCreateAction extends SelectionAction {
	/** みなしスーパーセット作成アクションを表す定数 */
	public static final String ID = "_VS";

	/**
	 * コンストラクタ
	 * 
	 * @param part
	 *            エディター
	 */
	public VirtualSupersetCreateAction(IWorkbenchPart part) {
		super(part);
		setText("みなしSuperset編集");
		setId(ID);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
		// アクション呼び出し時のマウスカーソル位置を取得。位置の微調整必要かも
		Point pos = getControlCursorLocation();

		Diagram diagram = null;
		VirtualSuperset original = null;
		VirtualSupersetAggregator aggregator = null;

		Object m = getPart().getModel();
		if (m instanceof Diagram) {
			diagram = (Diagram) m;
		} else if (m instanceof VirtualSuperset) {
			original = (VirtualSuperset) m;
			aggregator = original.getVirtualSupersetAggregator();
			diagram = original.getDiagram();
		}
		VirtualSupersetEditDialog dialog = new VirtualSupersetEditDialog(
				getPart().getViewer().getControl().getShell(), diagram,
				original);
		if (dialog.open() == Dialog.OK) {
			CompoundCommand ccommand = new CompoundCommand();
			VirtualSuperset edited = dialog.getEditedValue();
			// みなしスーパーセット
			if (original == null) {
				List<AbstractEntityModel> selection = dialog.getSelection();
				if (selection.size() == 0) {
					return;
				}
				Rectangle constraint = new Rectangle(pos.x, pos.y, -1, -1);
				edited.setConstraint(constraint);
				ccommand.add(new VirtualSupersetCreateCommand(diagram, edited));

				// みなしスーパーセットと既存エンティティとの接点
				aggregator = new VirtualSupersetAggregator();
				aggregator.setConstraint(constraint.getTranslated(0, 50));
				ccommand.add(new VirtualSupersetAggregatorCreateCommand(
						diagram, aggregator));

				ccommand.add(new ConnectionCreateCommand(
						new RelatedRelationship(edited, aggregator), edited,
						aggregator));

				// 接点とみなしサブセットの接続
				for (AbstractEntityModel model : selection) {
					ccommand.add(new ConnectionCreateCommand(
							new RelatedRelationship(aggregator, model),
							aggregator, model));
				}
			} else {
				// みなしスーパーセット編集
				ccommand.add(new TableEditCommand<VirtualSuperset>(original,
						edited));

				List<AbstractEntityModel> notSelection = dialog
						.getNotSelection();
				List<AbstractEntityModel> selection = dialog.getSelection();
				List<AbstractEntityModel> selectedList = original
						.getVirtualSubsetList();
				// 接点編集
				for (AbstractConnectionModel con : original
						.getVirtualSubsetRelationshipList()) {
					ConnectableElement target = con.getTarget();
					// 解除されたみなしサブセットとの接続を切る
					if (notSelection.contains(target)) {
						ccommand.add(new ConnectionDeleteCommand(con));
					}
				}
				if (selection.size() == 0) {
					// TODO みなしエンティティと接点とのコネクション削除
					ccommand.add(new ConnectionDeleteCommand(aggregator
							.getModelTargetConnections().get(0)));
					// TODO 接点削除
					ccommand.add(new VirtualSupersetAggregatorDeleteCommand(
							diagram, aggregator));
					// TODO みなしエンティティ削除
					ccommand.add(new VirtualSupersetDeleteCommand(diagram,
							original));
				} else {
					// 未接続のみなしサブセットとの接続
					for (AbstractEntityModel s : selection) {
						if (!selectedList.contains(s)) {
							ccommand.add(new ConnectionCreateCommand(
									new RelatedRelationship(aggregator, s),
									aggregator, s));
						}
					}
				}
			}
			execute(ccommand);
		}
	}

	private Point getControlCursorLocation() {
		Control control = getPart().getViewer().getControl();
		Point pos = control.getDisplay().getCursorLocation();
		System.out.println(pos);
		pos = control.toControl(pos);
		System.out.println(pos);

		return pos;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.ui.actions.WorkbenchPartAction#calculateEnabled()
	 */
	@Override
	protected boolean calculateEnabled() {
		if (getSelectedObjects().size() == 1) {
			Object selection = getSelectedObjects().get(0);
			return selection instanceof DiagramEditPart
					|| selection instanceof VirtualSupersetEditPart;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * @return コントローラ(EditPart)
	 */
	protected AbstractTMDEditPart getPart() {
		return (AbstractTMDEditPart) getSelectedObjects().get(0);
	}

	/**
	 * 
	 * @author nakaG
	 * 
	 */
	private static class VirtualSupersetCreateCommand extends Command {
		private Diagram diagram;
		private VirtualSuperset model;

		public VirtualSupersetCreateCommand(Diagram diagram,
				VirtualSuperset model) {
			this.diagram = diagram;
			this.model = model;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.gef.commands.Command#execute()
		 */
		@Override
		public void execute() {
			diagram.addChild(model);
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.gef.commands.Command#undo()
		 */
		@Override
		public void undo() {
			diagram.removeChild(model);
		}

	}

	private static class VirtualSupersetDeleteCommand extends Command {
		private Diagram diagram;
		private VirtualSuperset model;

		public VirtualSupersetDeleteCommand(Diagram diagram,
				VirtualSuperset model) {
			this.diagram = diagram;
			this.model = model;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.gef.commands.Command#execute()
		 */
		@Override
		public void execute() {
			diagram.removeChild(model);
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.gef.commands.Command#undo()
		 */
		@Override
		public void undo() {
			diagram.addChild(model);
		}
	}

	private static class VirtualSupersetAggregatorCreateCommand extends Command {
		private Diagram diagram;
		private VirtualSupersetAggregator aggregator;

		public VirtualSupersetAggregatorCreateCommand(Diagram diagram,
				VirtualSupersetAggregator aggregator) {
			super();
			this.diagram = diagram;
			this.aggregator = aggregator;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.gef.commands.Command#execute()
		 */
		@Override
		public void execute() {
			diagram.addChild(aggregator);
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.gef.commands.Command#undo()
		 */
		@Override
		public void undo() {
			diagram.removeChild(aggregator);
		}

	}

	private static class VirtualSupersetAggregatorDeleteCommand extends Command {
		private Diagram diagram;
		private VirtualSupersetAggregator model;

		public VirtualSupersetAggregatorDeleteCommand(Diagram diagram,
				VirtualSupersetAggregator model) {
			this.diagram = diagram;
			this.model = model;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.gef.commands.Command#execute()
		 */
		@Override
		public void execute() {
			diagram.removeChild(model);
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.gef.commands.Command#undo()
		 */
		@Override
		public void undo() {
			diagram.addChild(model);
		}
	}

}

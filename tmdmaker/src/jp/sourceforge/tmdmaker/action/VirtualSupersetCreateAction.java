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
package jp.sourceforge.tmdmaker.action;

import java.util.List;

import jp.sourceforge.tmdmaker.dialog.VirtualSupersetCreateDialog;
import jp.sourceforge.tmdmaker.editpart.AbstractEntityModelEditPart;
import jp.sourceforge.tmdmaker.editpart.AbstractModelEditPart;
import jp.sourceforge.tmdmaker.editpart.DiagramEditPart;
import jp.sourceforge.tmdmaker.model.AbstractConnectionModel;
import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.ConnectableElement;
import jp.sourceforge.tmdmaker.model.Constraint;
import jp.sourceforge.tmdmaker.model.Diagram;
import jp.sourceforge.tmdmaker.model.Entity2VirtualSupersetTypeRelationship;
import jp.sourceforge.tmdmaker.model.RelatedRelationship;
import jp.sourceforge.tmdmaker.model.VirtualSuperset;
import jp.sourceforge.tmdmaker.model.VirtualSupersetType;
import jp.sourceforge.tmdmaker.ui.command.ConnectionCreateCommand;
import jp.sourceforge.tmdmaker.ui.command.ConnectionDeleteCommand;
import jp.sourceforge.tmdmaker.ui.command.ModelDeleteCommand;
import jp.sourceforge.tmdmaker.ui.command.ModelEditCommand;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.editparts.AbstractEditPart;
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
public class VirtualSupersetCreateAction extends AbstractMultipleSelectionAction {
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
		List<AbstractEntityModel> selectedModels = getSelectedModelList();

		Diagram diagram = null;
		VirtualSuperset original = null;
		VirtualSupersetType aggregator = null;
		if (selectedModels.size() == 0) {
			diagram = getDiagram();
		} else {
			diagram = selectedModels.get(0).getDiagram();
			original = getVirtualSuperset();
			if (original != null) {
				selectedModels.remove(original);
				aggregator = original.getVirtualSupersetType();
			}
		}

		VirtualSupersetCreateDialog dialog = new VirtualSupersetCreateDialog(getControl()
				.getShell(), diagram, original, selectedModels);
		if (dialog.open() == Dialog.OK) {
			CompoundCommand ccommand = new CompoundCommand();
			VirtualSuperset edited = dialog.getEditedValue();
			// みなしスーパーセット
			if (original == null) {
				List<AbstractEntityModel> selection = dialog.getSelection();
				if (selection.size() == 0) {
					return;
				}
				Constraint constraint = new Constraint(pos.x, pos.y, -1, -1);
				edited.setConstraint(constraint);
				aggregator = dialog.getEditedAggregator();
				edited.setReusedIdentifieres(aggregator.getReusedIdentifieres());
				ccommand.add(new VirtualSupersetCreateCommand(diagram, edited));

				// みなしスーパーセットと既存エンティティとの接点
				aggregator.setConstraint(constraint.getTranslated(0, 50));
				ccommand.add(new VirtualSupersetTypeCreateCommand(diagram, aggregator));

				ccommand.add(new ConnectionCreateCommand(
						new RelatedRelationship(aggregator, edited), aggregator, edited));

				// 接点とみなしサブセットの接続
				for (AbstractEntityModel model : selection) {
					ccommand.add(new ConnectionCreateCommand(
							new Entity2VirtualSupersetTypeRelationship(model, aggregator), model,
							aggregator));
				}
			} else {
				// みなしスーパーセット編集
				// ccommand.add(new TableEditCommand<VirtualSuperset>(original,
				// edited));
				ccommand.add(new ModelEditCommand(original, edited));

				// 接点との接続
				List<AbstractEntityModel> notSelection = dialog.getNotSelection();
				List<AbstractEntityModel> selection = dialog.getSelection();
				List<AbstractEntityModel> selectedList = original.getVirtualSubsetList();

				for (AbstractConnectionModel con : original.getVirtualSubsetRelationshipList()) {
					ConnectableElement source = con.getSource();
					// 解除されたみなしサブセットとの接続を切る
					if (notSelection.contains(source)) {
						ccommand.add(new ConnectionDeleteCommand(con));
					}
				}
				if (selection.size() == 0) {
					// // みなしスーパーセットと接点とのコネクション削除
					// ccommand.add(new ConnectionDeleteCommand(aggregator
					// .getModelSourceConnections().get(0)));
					// 接点削除
					ccommand.add(new VirtualSupersetTypeDeleteCommand(diagram, aggregator));
					// みなしスーパーセット削除
					ccommand.add(new ModelDeleteCommand(diagram, original));
				} else {
					// 接点編集
					ccommand.add(new VirtualSupersetTypeChangeCommand(original
							.getVirtualSupersetType(), dialog.getEditedAggregator()
							.isApplyAttribute()));

					// 未接続のみなしサブセットとの接続
					for (AbstractEntityModel s : selection) {
						if (!selectedList.contains(s)) {
							ccommand.add(new ConnectionCreateCommand(
									new Entity2VirtualSupersetTypeRelationship(s, aggregator), s,
									aggregator));
						}
					}
				}
			}
			execute(ccommand);
		}
	}

	private Control getControl() {
		return ((AbstractEditPart) getSelectedObjects().get(0)).getViewer().getControl();
	}

	private Point getControlCursorLocation() {
		Control control = getControl();
		Point pos = control.getDisplay().getCursorLocation();
		System.out.println(pos);
		pos = control.toControl(pos);
		System.out.println(pos);
		pos.x = pos.x - 200;
		pos.y = pos.y - 100;
		return pos;
	}

	private Diagram getDiagram() {
		for (Object o : getSelectedObjects()) {
			if (o instanceof DiagramEditPart) {
				return (Diagram) ((DiagramEditPart) o).getModel();
			} else if (o instanceof AbstractModelEditPart) {
				return (Diagram) (((AbstractModelEditPart<?>) o).getParent()).getModel();
			}
		}
		return null;
	}

	private VirtualSuperset getVirtualSuperset() {
		// 選択したモデルからスーパーセット検索
		List<AbstractEntityModel> list = getSelectedModelList();
		for (AbstractEntityModel o : list) {
			if (o instanceof VirtualSuperset) {
				return (VirtualSuperset) o;
			}
		}
		// 選択したモデルが接続しているスーパーセットを検索
		for (AbstractEntityModel o : list) {
			VirtualSupersetType type = o.findVirtualSupersetType();
			if (type != null) {
				return (VirtualSuperset) type.getModelSourceConnections().get(0).getTarget();
			}
		}
		return null;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.ui.actions.WorkbenchPartAction#calculateEnabled()
	 */
	@Override
	protected boolean calculateEnabled() {
		if (getSelectedObjects().size() >= 1 && hasEntityModel()) {
			return true;
		} else if (isDiagramSelected()) {
			return true;
		}
		return false;
	}

	private boolean hasEntityModel() {
		for (Object o : getSelectedObjects()) {
			if (o instanceof AbstractEntityModelEditPart) {
					return true;
			}
		}
		return false;
	}

	private boolean isDiagramSelected() {
		return getSelectedObjects().size() == 1
				&& getSelectedObjects().get(0) instanceof DiagramEditPart;
	}

	/**
	 * 
	 * @author nakaG
	 * 
	 */
	private static class VirtualSupersetCreateCommand extends Command {
		private Diagram diagram;
		private VirtualSuperset model;

		public VirtualSupersetCreateCommand(Diagram diagram, VirtualSuperset model) {
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

	private static class VirtualSupersetTypeCreateCommand extends Command {
		private Diagram diagram;
		private VirtualSupersetType aggregator;

		public VirtualSupersetTypeCreateCommand(Diagram diagram, VirtualSupersetType aggregator) {
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

	private static class VirtualSupersetTypeDeleteCommand extends Command {
		private Diagram diagram;
		private VirtualSupersetType model;

		public VirtualSupersetTypeDeleteCommand(Diagram diagram, VirtualSupersetType model) {
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

	/**
	 * 
	 * @author nakaG
	 * 
	 */
	private static class VirtualSupersetTypeChangeCommand extends Command {
		private VirtualSupersetType model;
		private boolean oldApplyAttribute;
		private boolean newApplyAttribute;

		/**
		 * 
		 * @param model
		 * @param newApplyAttribute
		 */
		public VirtualSupersetTypeChangeCommand(VirtualSupersetType model, boolean newApplyAttribute) {
			this.model = model;
			this.newApplyAttribute = newApplyAttribute;
			this.oldApplyAttribute = model.isApplyAttribute();
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.gef.commands.Command#execute()
		 */
		@Override
		public void execute() {
			this.model.setApplyAttribute(newApplyAttribute);
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.gef.commands.Command#undo()
		 */
		@Override
		public void undo() {
			this.model.setApplyAttribute(oldApplyAttribute);
		}
	}
}

/*
 * Copyright 2009-2016 TMD-Maker Project <http://tmdmaker.osdn.jp/>
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

import jp.sourceforge.tmdmaker.Messages;
import jp.sourceforge.tmdmaker.dialog.VirtualSupersetCreateDialog;
import jp.sourceforge.tmdmaker.editpart.AbstractEntityModelEditPart;
import jp.sourceforge.tmdmaker.editpart.AbstractModelEditPart;
import jp.sourceforge.tmdmaker.editpart.DiagramEditPart;
import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.Constraint;
import jp.sourceforge.tmdmaker.model.Diagram;
import jp.sourceforge.tmdmaker.model.VirtualSuperset;
import jp.sourceforge.tmdmaker.model.VirtualSupersetType;
import jp.sourceforge.tmdmaker.ui.command.ModelEditCommand;
import jp.sourceforge.tmdmaker.ui.command.VirtualSubsetAddCommand;
import jp.sourceforge.tmdmaker.ui.command.VirtualSubsetDisconnectCommand;
import jp.sourceforge.tmdmaker.ui.command.VirtualSupersetTypeChangeCommand;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.editparts.AbstractEditPart;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbenchPart;

/**
 * みなしスーパーセット作成アクション.
 *
 * @author nakaG
 * 
 */
public class VirtualSupersetCreateAction extends AbstractMultipleSelectionAction {
	/** みなしスーパーセット作成アクションを表す定数. */
	public static final String ID = "_VS"; //$NON-NLS-1$

	/**
	 * コンストラクタ.
	 *
	 * @param part
	 *            エディター.
	 */
	public VirtualSupersetCreateAction(IWorkbenchPart part) {
		super(part);
		setText(Messages.EditVirtualSuperset);
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

		VirtualSupersetCreateDialog dialog = new VirtualSupersetCreateDialog(
				getControl().getShell(), diagram, original, selectedModels);
		if (dialog.open() == Dialog.OK) {
			CompoundCommand ccommand = new CompoundCommand();
			VirtualSuperset edited = dialog.getEditedValue();
			aggregator = dialog.getEditedAggregator();
			// みなしスーパーセット作成
			if (original == null) {
				List<AbstractEntityModel> selection = dialog.getSelection();
				if (selection.size() == 0) {
					return;
				}

				ccommand.add(new VirtualSupersetCreateCommand(diagram, edited.getName(),
						aggregator.isApplyAttribute(), selection, pos.x, pos.y));

			} else {
				// みなしスーパーセット編集
				ccommand.add(new ModelEditCommand(original, edited));

				// 接点編集
				ccommand.add(new VirtualSupersetTypeChangeCommand(original.getVirtualSupersetType(),
						dialog.getEditedAggregator().isApplyAttribute()));

				// 接点との接続
				List<AbstractEntityModel> notSelection = dialog.getNotSelection();
				List<AbstractEntityModel> selection = dialog.getSelection();
				List<AbstractEntityModel> selectedList = original.getVirtualSubsetList();

				// 未接続のみなしサブセットとの接続
				for (AbstractEntityModel s : selection) {
					if (!selectedList.contains(s)) {
						ccommand.add(new VirtualSubsetAddCommand(original, s));
					}
				}
				// 接続していたが未選択に変更したサブセットとの接続を解除
				for (AbstractEntityModel m : original.getVirtualSubsetList()) {
					if (notSelection.contains(m)) {
						ccommand.add(new VirtualSubsetDisconnectCommand(original, m));
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
		pos = control.toControl(pos);
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
				return type.getSuperset();
			}
		}
		return null;
	}

	/**
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

	private static class VirtualSupersetCreateCommand extends Command {
		private Diagram diagram;
		private String virtualSupersetName;
		private boolean applyAttribute;
		private List<AbstractEntityModel> subsets;
		private VirtualSuperset model;
		private int x;
		private int y;
		private Constraint typeConstraint;

		public VirtualSupersetCreateCommand(Diagram diagram, String virtualSupersetName,
				boolean applyAttribute, List<AbstractEntityModel> subsets, int x, int y) {
			this.diagram = diagram;
			this.virtualSupersetName = virtualSupersetName;
			this.applyAttribute = applyAttribute;
			this.subsets = subsets;
			this.x = x;
			this.y = y;
			Constraint supersetConstraint = new Constraint(x, y, -1, -1);
			typeConstraint = supersetConstraint.getTranslated(0, 50);
		}

		/**
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.gef.commands.Command#execute()
		 */
		@Override
		public void execute() {
			model = diagram.createVirtualSuperset(virtualSupersetName, subsets);
			model.move(x, y);
			VirtualSupersetType type = model.getVirtualSupersetType();
			type.setConstraint(typeConstraint);
			type.setApplyAttribute(applyAttribute);
		}

		/**
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.gef.commands.Command#undo()
		 */
		@Override
		public void undo() {
			for (AbstractEntityModel m : subsets) {
				model.disconnectSubset(m);
			}
			diagram.removeChild(model);
		}
	}
}

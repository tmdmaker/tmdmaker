/*
 * Copyright 2009-2019 TMD-Maker Project <https://tmdmaker.osdn.jp/>
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
package jp.sourceforge.tmdmaker.ui.actions;

import java.util.List;

import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.editparts.AbstractEditPart;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbenchPart;

import jp.sourceforge.tmdmaker.Messages;
import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.Diagram;
import jp.sourceforge.tmdmaker.model.VirtualSuperset;
import jp.sourceforge.tmdmaker.model.VirtualSupersetType;
import jp.sourceforge.tmdmaker.ui.dialogs.VirtualSupersetCreateDialog;
import jp.sourceforge.tmdmaker.ui.editor.gef3.commands.VirtualSupersetCreateCommand;
import jp.sourceforge.tmdmaker.ui.editor.gef3.commands.VirtualSupersetEditCommand;
import jp.sourceforge.tmdmaker.ui.editor.gef3.commands.VirtualSupersetTypeChangeCommand;
import jp.sourceforge.tmdmaker.ui.editor.gef3.editparts.DiagramEditPart;
import jp.sourceforge.tmdmaker.ui.editor.gef3.editparts.node.AbstractEntityModelEditPart;
import jp.sourceforge.tmdmaker.ui.editor.gef3.editparts.node.AbstractModelEditPart;

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

		Diagram diagram = getDiagram();
		VirtualSuperset original = null;
		VirtualSupersetType aggregator = null;
		if (!selectedModels.isEmpty()) {
			original = getVirtualSuperset();

			if (original != null) {
				selectedModels.remove(original);
			}
		}

		VirtualSupersetCreateDialog dialog = new VirtualSupersetCreateDialog(
				getControl().getShell(), diagram, original, selectedModels);
		if (dialog.open() == Dialog.OK) {
			CompoundCommand ccommand = null;
			VirtualSuperset edited = dialog.getEditedValue();
			aggregator = dialog.getEditedAggregator();
			List<AbstractEntityModel> selection = dialog.getSelection();

			// みなしスーパーセット作成
			if (original == null) {
				if (selection.isEmpty()) {
					return;
				}
				ccommand = new CompoundCommand();
				ccommand.add(new VirtualSupersetCreateCommand(edited, selection, pos.x, pos.y));
				ccommand.add(new VirtualSupersetTypeChangeCommand(edited,
						aggregator.isApplyAttribute()));
			} else {
				ccommand = new VirtualSupersetEditCommand(original, edited, selection,
						aggregator.isApplyAttribute());
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
				return ((DiagramEditPart) o).getModel();
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
		if (!getSelectedObjects().isEmpty() && hasEntityModel()) {
			return true;
		}
		return isDiagramSelected();
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

}

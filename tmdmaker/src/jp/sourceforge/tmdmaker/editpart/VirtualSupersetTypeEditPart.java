/*
 * Copyright 2009-2015 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
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

import java.beans.PropertyChangeEvent;
import java.util.List;

import jp.sourceforge.tmdmaker.dialog.VirtualSupersetCreateDialog;
import jp.sourceforge.tmdmaker.figure.SubsetTypeFigure;
import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.Diagram;
import jp.sourceforge.tmdmaker.model.VirtualSuperset;
import jp.sourceforge.tmdmaker.model.VirtualSupersetType;
import jp.sourceforge.tmdmaker.ui.command.ModelEditCommand;
import jp.sourceforge.tmdmaker.ui.command.VirtualSubsetAddCommand;
import jp.sourceforge.tmdmaker.ui.command.VirtualSubsetDisconnectCommand;
import jp.sourceforge.tmdmaker.ui.command.VirtualSupersetTypeChangeCommand;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.jface.dialogs.Dialog;

/**
 * みなしスーパーセット種類（同一(=)/相違マーク(×)）のコントローラ.
 * 
 * @author nakaG
 * 
 */
public class VirtualSupersetTypeEditPart extends AbstractSubsetTypeEditPart<VirtualSupersetType> {

	/**
	 * コンストラクタ.
	 * 
	 * @param type
	 *            みなしサブセット種類.
	 */
	public VirtualSupersetTypeEditPart(VirtualSupersetType type) {
		super();
		setModel(type);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.editpart.AbstractModelEditPart#updateFigure(org.eclipse.draw2d.IFigure)
	 */
	@Override
	protected void updateFigure(IFigure figure) {
		SubsetTypeFigure sf = (SubsetTypeFigure) figure;
		VirtualSupersetType model = getModel();
		sf.setVertical(model.isVertical());
		sf.setSameType(model.isApplyAttribute());
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.editpart.AbstractTMDEditPart#onDoubleClicked()
	 */
	@Override
	protected void onDoubleClicked() {
		VirtualSupersetType type = getModel();
		VirtualSuperset superset = type.getSuperset();
		Diagram diagram = superset.getDiagram();
		List<AbstractEntityModel> list = type.getSubsetList();
		VirtualSupersetCreateDialog dialog = new VirtualSupersetCreateDialog(getViewer()
				.getControl().getShell(), diagram, superset, list);
		if (dialog.open() == Dialog.OK) {
			CompoundCommand ccommand = new CompoundCommand();
			VirtualSuperset edited = dialog.getEditedValue();
			// みなしスーパーセット編集
			ccommand.add(new ModelEditCommand(superset, edited));

			// 接点編集
			ccommand.add(new VirtualSupersetTypeChangeCommand(superset.getVirtualSupersetType(),
					dialog.getEditedAggregator().isApplyAttribute()));

			// 接点との接続
			List<AbstractEntityModel> notSelection = dialog.getNotSelection();
			List<AbstractEntityModel> selection = dialog.getSelection();
			List<AbstractEntityModel> selectedList = superset.getVirtualSubsetList();

			// 未接続のみなしサブセットとの接続
			for (AbstractEntityModel s : selection) {
				if (!selectedList.contains(s)) {
					ccommand.add(new VirtualSubsetAddCommand(superset, s));
				}
			}
			// 接続していたが未選択に変更したサブセットとの接続を解除
			for (AbstractEntityModel m : superset.getVirtualSubsetList()) {
				if (notSelection.contains(m)) {
					ccommand.add(new VirtualSubsetDisconnectCommand(superset, m));
				}
			}
			getViewer().getEditDomain().getCommandStack().execute(ccommand);
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.editpart.AbstractModelEditPart#propertyChange(java.beans.PropertyChangeEvent)
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals(VirtualSupersetType.PROPERTY_SUPERSET_TYPE)) {
			refreshVisuals();
		} else {
			super.propertyChange(evt);
		}
	}
}

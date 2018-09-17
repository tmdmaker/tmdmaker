/*
 * Copyright 2009-2018 TMD-Maker Project <https://tmdmaker.osdn.jp/>
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
package jp.sourceforge.tmdmaker.ui.editor.gef3.editparts.node;

import java.beans.PropertyChangeEvent;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.jface.dialogs.Dialog;

import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.Diagram;
import jp.sourceforge.tmdmaker.model.VirtualSuperset;
import jp.sourceforge.tmdmaker.model.VirtualSupersetType;
import jp.sourceforge.tmdmaker.ui.dialogs.VirtualSupersetCreateDialog;
import jp.sourceforge.tmdmaker.ui.editor.draw2d.figure.node.SubsetTypeFigure;
import jp.sourceforge.tmdmaker.ui.editor.gef3.commands.VirtualSupersetEditCommand;

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
	 * @see jp.sourceforge.tmdmaker.ui.editor.gef3.editparts.node.AbstractModelEditPart#updateFigure(org.eclipse.draw2d.IFigure)
	 */
	@Override
	protected void updateFigure(IFigure figure) {
		SubsetTypeFigure sf = (SubsetTypeFigure) figure;
		VirtualSupersetType model = getModel();
		sf.setVertical(model.isVertical());
		sf.setSameType(model.isApplyAttribute());
	}

	/**
	 * 
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.gef.editparts.AbstractEditPart#getCommand(org.eclipse.gef.Request)
	 */
	@Override
	public Command getCommand(Request request) {
		if (!REQ_OPEN.equals(request.getType()))
			return super.getCommand(request);

		VirtualSupersetType type = getModel();
		VirtualSuperset superset = type.getSuperset();
		Diagram diagram = superset.getDiagram();
		List<AbstractEntityModel> list = type.getSubsetList();
		VirtualSupersetCreateDialog dialog = new VirtualSupersetCreateDialog(
				getViewer().getControl().getShell(), diagram, superset, list);
		if (dialog.open() != Dialog.OK)
			return null;

		return new VirtualSupersetEditCommand(superset, dialog.getEditedValue(),
				dialog.getSelection(), dialog.getEditedAggregator().isApplyAttribute());
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.ui.editor.gef3.editparts.node.AbstractModelEditPart#propertyChange(java.beans.PropertyChangeEvent)
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

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
package org.tmdmaker.ui.editor.gef3.editparts.node;

import java.beans.PropertyChangeEvent;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.jface.dialogs.Dialog;
import org.tmdmaker.core.model.AbstractEntityModel;
import org.tmdmaker.core.model.SubsetType;
import org.tmdmaker.ui.dialogs.SubsetCreateDialog;
import org.tmdmaker.ui.editor.draw2d.figure.node.SubsetTypeFigure;

/**
 * サブセット種類のコントローラ.
 * 
 * @author nakaG
 * 
 */
public class SubsetTypeEditPart extends AbstractSubsetTypeEditPart<SubsetType> {

	/**
	 * コンストラクタ.
	 */
	public SubsetTypeEditPart(SubsetType type) {
		super();
		setModel(type);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.tmdmaker.ui.editor.gef3.editparts.node.AbstractSubsetTypeEditPart#propertyChange(java.beans.PropertyChangeEvent)
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals(SubsetType.PROPERTY_TYPE)) {
			refreshVisuals();
		} else if (evt.getPropertyName().equals(SubsetType.PROPERTY_PARTITION)) {
			refreshVisuals();
		} else {
			super.propertyChange(evt);
		}
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.tmdmaker.ui.editor.gef3.editparts.node.AbstractModelEditPart#updateFigure(org.eclipse.draw2d.IFigure)
	 */
	@Override
	protected void updateFigure(IFigure figure) {
		SubsetTypeFigure sf = (SubsetTypeFigure) figure;
		SubsetType model = getModel();
		sf.setVertical(model.isVertical());
		sf.setSameType(model.isSameType());
	}

	/**
	 * 
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.gef.editparts.AbstractEditPart#getCommand(org.eclipse.gef.Request)
	 */
	@Override
	public Command getCommand(Request request) {
		if (REQ_OPEN.equals(request.getType())) {
			logger.debug("{}#getCommand(req)", getClass());
			SubsetType subsetType = getModel();
			AbstractEntityModel model = subsetType.getSuperset();

			SubsetCreateDialog dialog = new SubsetCreateDialog(getViewer().getControl().getShell(),
					model);
			if (dialog.open() == Dialog.OK) {
				return dialog.getCcommand();
			}
		}
		return super.getCommand(request);
	}
}

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
package jp.sourceforge.tmdmaker.editpart;

import java.beans.PropertyChangeEvent;
import java.util.Collections;
import java.util.List;

import jp.sourceforge.tmdmaker.dialog.SubsetCreateDialog;
import jp.sourceforge.tmdmaker.figure.SubsetTypeFigure;
import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.SubsetType;

import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.jface.dialogs.Dialog;

/**
 * サブセット種類のコントローラ
 * 
 * @author nakaG
 * 
 */
public class SubsetTypeEditPart extends AbstractEntityEditPart {

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#createFigure()
	 */
	@Override
	protected IFigure createFigure() {
		Figure figure = new SubsetTypeFigure();
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
		SubsetTypeFigure sf = (SubsetTypeFigure) figure;
		SubsetType model = (SubsetType) getModel();
		sf.setVertical(model.isVertical());
		sf.setSameType(model.isSameType());
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#createEditPolicies()
	 */
	@Override
	protected void createEditPolicies() {
		// TODO Auto-generated method stub

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.editpart.AbstractEntityEditPart#getConnectionAnchor()
	 */
	@Override
	protected ConnectionAnchor getConnectionAnchor() {
		return new CenterAnchor(getFigure());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.editpart.AbstractEntityEditPart#propertyChange(java.beans.PropertyChangeEvent)
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals(SubsetType.PROPERTY_TYPE)) {
			refreshVisuals();
		} else if (evt.getPropertyName().equals(SubsetType.PROPERTY_PARTITION)) {
			refreshVisuals();
		} else if (evt.getPropertyName().equals(SubsetType.PROPERTY_DIRECTION)) {
			refreshVisuals();
		} else {
			super.propertyChange(evt);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.editpart.AbstractEntityEditPart#onDoubleClicked()
	 */
	@Override
	protected void onDoubleClicked() {
		logger.debug(getClass() + "#onDoubleClicked()");
		SubsetType subsetType = (SubsetType) getModel();
		AbstractEntityModel model = subsetType.getSuperset();

		SubsetCreateDialog dialog = new SubsetCreateDialog(getViewer().getControl().getShell(),
				subsetType, model);
		if (dialog.open() == Dialog.OK) {
			CompoundCommand ccommand = dialog.getCcommand();
			getViewer().getEditDomain().getCommandStack().execute(ccommand);
		}

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.editpart.AbstractEntityEditPart#getSourceConnectionAnchor(org.eclipse.gef.ConnectionEditPart)
	 */
	@Override
	public ConnectionAnchor getSourceConnectionAnchor(ConnectionEditPart connection) {
		return new CenterAnchor(getFigure());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.editpart.AbstractEntityEditPart#getSourceConnectionAnchor(org.eclipse.gef.Request)
	 */
	@Override
	public ConnectionAnchor getSourceConnectionAnchor(Request request) {
		return new CenterAnchor(getFigure());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.editpart.AbstractEntityEditPart#getTargetConnectionAnchor(org.eclipse.gef.ConnectionEditPart)
	 */
	@Override
	public ConnectionAnchor getTargetConnectionAnchor(ConnectionEditPart connection) {
		return new CenterAnchor(getFigure());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.editpart.AbstractEntityEditPart#getTargetConnectionAnchor(org.eclipse.gef.Request)
	 */
	@Override
	public ConnectionAnchor getTargetConnectionAnchor(Request request) {
		return new CenterAnchor(getFigure());
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.editpart.AbstractEntityEditPart#getModelChildren()
	 */
	@SuppressWarnings("rawtypes")
	@Override
	protected List getModelChildren() {
		return Collections.EMPTY_LIST;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.editpart.AbstractEntityEditPart#getContentPane()
	 */
	@Override
	public IFigure getContentPane() {
		return getFigure();
	}

}

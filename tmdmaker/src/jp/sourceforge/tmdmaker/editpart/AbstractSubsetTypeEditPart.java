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
import java.util.Collections;
import java.util.List;

import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.Request;

import jp.sourceforge.tmdmaker.figure.SubsetTypeFigure;
import jp.sourceforge.tmdmaker.model.AbstractSubsetType;
import jp.sourceforge.tmdmaker.model.ConnectableElement;

/**
 * スーパーセットとサブセットとの接点のEditPartの基底クラス.
 *
 * @author nakag
 *
 */
public abstract class AbstractSubsetTypeEditPart<T extends ConnectableElement>
		extends AbstractModelEditPart<T> {

	/**
	 * コンストラクタ.
	 */
	public AbstractSubsetTypeEditPart() {
		super();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.editpart.AbstractModelEditPart#createFigure()
	 */
	@Override
	protected IFigure createFigure() {
		Figure figure = new SubsetTypeFigure();
		updateFigure(figure);
		return figure;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.editpart.AbstractModelEditPart#getConnectionAnchor()
	 */
	@Override
	protected ConnectionAnchor getConnectionAnchor() {
		return new CenterAnchor(getFigure());
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.editpart.AbstractModelEditPart#propertyChange(java.beans.PropertyChangeEvent)
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals(AbstractSubsetType.PROPERTY_DIRECTION)) {
			refreshVisuals();
		} else {
			super.propertyChange(evt);
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.editpart.AbstractModelEditPart#getSourceConnectionAnchor(org.eclipse.gef.ConnectionEditPart)
	 */
	@Override
	public ConnectionAnchor getSourceConnectionAnchor(ConnectionEditPart connection) {
		return new CenterAnchor(getFigure());
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.editpart.AbstractModelEditPart#getSourceConnectionAnchor(org.eclipse.gef.Request)
	 */
	@Override
	public ConnectionAnchor getSourceConnectionAnchor(Request request) {
		return new CenterAnchor(getFigure());
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.editpart.AbstractModelEditPart#getTargetConnectionAnchor(org.eclipse.gef.ConnectionEditPart)
	 */
	@Override
	public ConnectionAnchor getTargetConnectionAnchor(ConnectionEditPart connection) {
		return new CenterAnchor(getFigure());
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.editpart.AbstractModelEditPart#getTargetConnectionAnchor(org.eclipse.gef.Request)
	 */
	@Override
	public ConnectionAnchor getTargetConnectionAnchor(Request request) {
		return new CenterAnchor(getFigure());
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.editpart.AbstractModelEditPart#getModelChildren()
	 */
	@SuppressWarnings("rawtypes")
	@Override
	protected List getModelChildren() {
		return Collections.EMPTY_LIST;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.editpart.AbstractModelEditPart#getContentPane()
	 */
	@Override
	public IFigure getContentPane() {
		return getFigure();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.gef.editparts.AbstractEditPart#createEditPolicies()
	 */
	@Override
	protected void createEditPolicies() {
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.editpart.AbstractModelEditPart#canAutoSize()
	 */
	@Override
	public boolean canAutoSize() {
		return false;
	}
}

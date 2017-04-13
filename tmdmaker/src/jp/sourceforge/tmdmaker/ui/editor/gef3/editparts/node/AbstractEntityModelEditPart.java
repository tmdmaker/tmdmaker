/*
 * Copyright 2009-2017 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
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

import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.ui.views.properties.IPropertySource;

import jp.sourceforge.tmdmaker.TMDEditor;
import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.property.AbstractEntityModelPropertySource;
import jp.sourceforge.tmdmaker.property.IPropertyAvailable;
import jp.sourceforge.tmdmaker.ui.editor.draw2d.figure.node.AbstractModelFigure;

/**
 * Entity系のeditpartの基底クラス
 *
 * @author tohosaku
 *
 */
public abstract class AbstractEntityModelEditPart<T extends AbstractEntityModel>
		extends AbstractModelEditPart<T> implements IPropertyAvailable {
	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#createFigure()
	 */
	@Override
	protected IFigure createFigure() {
		AbstractModelFigure<T> figure = createTMDFigure();
		updateFigure(figure);

		return figure;
	}

	abstract protected AbstractModelFigure<T> createTMDFigure();

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.ui.editor.gef3.editparts.node.AbstractModelEditPart#updateFigure(org.eclipse.draw2d.IFigure)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected void updateFigure(IFigure figure) {
		AbstractModelFigure<T> entityFigure = (AbstractModelFigure<T>) figure;
		entityFigure.update(getModel());
	}

	@Override
	abstract protected void createEditPolicies();

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.gef.editparts.AbstractEditPart#getModelChildren()
	 */
	@SuppressWarnings({ "rawtypes" })
	@Override
	protected List getModelChildren() {
		return getModel().getAttributes();
	}

	public IPropertySource getPropertySource(TMDEditor editor) {
		return new AbstractEntityModelPropertySource(editor, this.getModel());
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.ui.editor.gef3.editparts.node.AbstractModelEditPart#canAutoSize()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean canAutoSize() {
		return ((AbstractModelFigure<T>) getFigure()).canAutoSize();
	}
}

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
package org.tmdmaker.ui.editor.gef3.editparts.node;

import org.eclipse.gef.EditPolicy;
import org.tmdmaker.core.model.Laputa;
import org.tmdmaker.ui.editor.draw2d.figure.node.AbstractModelFigure;
import org.tmdmaker.ui.editor.draw2d.figure.node.LaputaFigure;
import org.tmdmaker.ui.editor.gef3.editpolicies.EntityLayoutEditPolicy;
import org.tmdmaker.ui.editor.gef3.editpolicies.LaputaComponentEditPolicy;

/**
 * ラピュタのコントローラ
 * 
 * @author nakaG
 * 
 */
public class LaputaEditPart extends AbstractEntityModelEditPart<Laputa> {

	/**
	 * コンストラクタ
	 */
	public LaputaEditPart(Laputa entity) {
		super();
		setModel(entity);
	}

	/**
	 * 
	 * {@inheritDoc}
	 *
	 * @see org.tmdmaker.ui.editor.gef3.editparts.node.AbstractEntityModelEditPart#createTMDFigure()
	 */
	@Override
	protected AbstractModelFigure<Laputa> createTMDFigure() {
		return new LaputaFigure();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#createEditPolicies()
	 */
	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new LaputaComponentEditPolicy());
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new EntityLayoutEditPolicy());
	}

	/**
	 * 
	 * {@inheritDoc}
	 *
	 * @see org.tmdmaker.ui.editor.gef3.editparts.node.AbstractModelEditPart#canCallSelectionAction()
	 */
	@Override
	public boolean canCallSelectionAction() {
		return false;
	}
}

/*
 * Copyright 2009-2017 TMD-Maker Project <http://tmdmaker.osdn.jp/>
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
import org.tmdmaker.core.model.MultivalueOrEntity;
import org.tmdmaker.ui.editor.draw2d.figure.node.AbstractModelFigure;
import org.tmdmaker.ui.editor.draw2d.figure.node.MultivalueOrEntityFigure;
import org.tmdmaker.ui.editor.gef3.editpolicies.EntityLayoutEditPolicy;
import org.tmdmaker.ui.editor.gef3.editpolicies.MultivalueOrEntityComponentEditPolicy;
import org.tmdmaker.ui.editor.gef3.editpolicies.TMDModelGraphicalNodeEditPolicy;

/**
 * 多値のORのコントローラ
 * 
 * @author nakaG
 * 
 */
public class MultivalueOrEditPart extends AbstractEntityModelEditPart<MultivalueOrEntity> {

	/**
	 * コンストラクタ
	 */
	public MultivalueOrEditPart(MultivalueOrEntity entity) {
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
	protected AbstractModelFigure<MultivalueOrEntity> createTMDFigure() {
		return new MultivalueOrEntityFigure();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#createEditPolicies()
	 */
	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new MultivalueOrEntityComponentEditPolicy());
		installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE, new TMDModelGraphicalNodeEditPolicy());
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new EntityLayoutEditPolicy());
	}

}

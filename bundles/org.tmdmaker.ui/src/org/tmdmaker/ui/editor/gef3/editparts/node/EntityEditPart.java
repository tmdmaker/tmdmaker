/*
 * Copyright 2009-2019 TMD-Maker Project <https://www.tmdmaker.org>
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
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.ui.views.properties.IPropertySource;
import org.tmdmaker.core.model.Entity;
import org.tmdmaker.ui.editor.draw2d.figure.node.AbstractModelFigure;
import org.tmdmaker.ui.editor.draw2d.figure.node.EntityFigure;
import org.tmdmaker.ui.editor.gef3.editpolicies.EntityEditComponentPolicy;
import org.tmdmaker.ui.editor.gef3.editpolicies.EntityLayoutEditPolicy;
import org.tmdmaker.ui.editor.gef3.editpolicies.TMDModelGraphicalNodeEditPolicy;
import org.tmdmaker.ui.views.properties.IPropertyAvailable;
import org.tmdmaker.ui.views.properties.gef3.EntityPropertySource;

/**
 * エンティティのコントローラ
 * 
 * @author nakaG
 * 
 */
public class EntityEditPart extends AbstractEntityModelEditPart<Entity>
		implements IPropertyAvailable {

	/**
	 * コンストラクタ
	 */
	public EntityEditPart(Entity entity) {
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
	protected AbstractModelFigure<Entity> createTMDFigure() {
		return new EntityFigure();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#createEditPolicies()
	 */
	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new EntityEditComponentPolicy());
		installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE, new TMDModelGraphicalNodeEditPolicy());
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new EntityLayoutEditPolicy());
	}

	@Override
	public IPropertySource getPropertySource(CommandStack commandStack) {
		return new EntityPropertySource(commandStack, this.getModel());
	}
}

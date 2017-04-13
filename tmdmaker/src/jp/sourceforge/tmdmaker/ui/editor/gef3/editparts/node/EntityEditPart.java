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
package jp.sourceforge.tmdmaker.ui.editor.gef3.editparts.node;

import org.eclipse.gef.EditPolicy;
import org.eclipse.ui.views.properties.IPropertySource;

import jp.sourceforge.tmdmaker.TMDEditor;
import jp.sourceforge.tmdmaker.model.Entity;
import jp.sourceforge.tmdmaker.ui.editor.draw2d.figure.node.AbstractModelFigure;
import jp.sourceforge.tmdmaker.ui.editor.draw2d.figure.node.EntityFigure;
import jp.sourceforge.tmdmaker.ui.editor.gef3.editpolicies.EntityEditComponentPolicy;
import jp.sourceforge.tmdmaker.ui.editor.gef3.editpolicies.EntityLayoutEditPolicy;
import jp.sourceforge.tmdmaker.ui.editor.gef3.editpolicies.TMDModelGraphicalNodeEditPolicy;
import jp.sourceforge.tmdmaker.ui.views.properties.EntityPropertySource;
import jp.sourceforge.tmdmaker.ui.views.properties.IPropertyAvailable;

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
	 * @see jp.sourceforge.tmdmaker.ui.editor.gef3.editparts.node.AbstractEntityModelEditPart#createTMDFigure()
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
	public IPropertySource getPropertySource(TMDEditor editor) {
		return new EntityPropertySource(editor, this.getModel());
	}
}

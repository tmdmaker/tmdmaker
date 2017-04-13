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

import org.eclipse.gef.EditPolicy;

import jp.sourceforge.tmdmaker.editpolicy.ReconnectableNodeEditPolicy;
import jp.sourceforge.tmdmaker.editpolicy.VirtualSupersetComponentEditPolicy;
import jp.sourceforge.tmdmaker.model.VirtualSuperset;
import jp.sourceforge.tmdmaker.ui.editor.draw2d.figure.node.AbstractModelFigure;
import jp.sourceforge.tmdmaker.ui.editor.draw2d.figure.node.VirtualSupersetFigure;

/**
 * みなしスーパーセットのコントローラ
 *
 * @author nakaG
 *
 */
public class VirtualSupersetEditPart extends AbstractEntityModelEditPart<VirtualSuperset> {

	/**
	 * コンストラクタ
	 */
	public VirtualSupersetEditPart(VirtualSuperset entity) {
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
	protected AbstractModelFigure<VirtualSuperset> createTMDFigure() {
		return new VirtualSupersetFigure();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#createEditPolicies()
	 */
	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new VirtualSupersetComponentEditPolicy());
		installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE, new ReconnectableNodeEditPolicy());
	}
}

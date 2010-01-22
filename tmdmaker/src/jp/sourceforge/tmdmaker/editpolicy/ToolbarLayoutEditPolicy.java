/*
 * Copyright 2009-2010 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
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
package jp.sourceforge.tmdmaker.editpolicy;

import org.eclipse.draw2d.FlowLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.LayoutManager;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.editpolicies.FlowLayoutEditPolicy;

/**
 * ToolbarLayout用のEditPolicy
 * 
 * @author nakaG
 * 
 */
public abstract class ToolbarLayoutEditPolicy extends FlowLayoutEditPolicy {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.editpolicies.FlowLayoutEditPolicy#isHorizontal()
	 */
	@Override
	protected boolean isHorizontal() {
		IFigure figure = ((GraphicalEditPart) getHost()).getContentPane();
		LayoutManager layout = figure.getLayoutManager();
		if (layout instanceof FlowLayout)
			return ((FlowLayout) figure.getLayoutManager()).isHorizontal();
		if (layout instanceof ToolbarLayout)
			return ((ToolbarLayout) figure.getLayoutManager()).isHorizontal();
		return false;
	}

}

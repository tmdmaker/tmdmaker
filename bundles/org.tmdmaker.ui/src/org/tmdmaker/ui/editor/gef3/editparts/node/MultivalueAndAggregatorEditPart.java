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

import org.eclipse.draw2d.IFigure;
import org.tmdmaker.core.model.MultivalueAndAggregator;
import org.tmdmaker.ui.editor.draw2d.figure.node.SubsetTypeFigure;

/**
 * 多値のANDの相違マーク(×)のコントローラ.
 *
 * @author nakaG
 *
 */
public class MultivalueAndAggregatorEditPart extends
		AbstractSubsetTypeEditPart<MultivalueAndAggregator> {

	/**
	 * コンストラクタ.
	 */
	public MultivalueAndAggregatorEditPart(MultivalueAndAggregator aggregator) {
		super();
		setModel(aggregator);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.tmdmaker.ui.editor.gef3.editparts.node.AbstractModelEditPart#updateFigure(org.eclipse.draw2d.IFigure)
	 */
	@Override
	protected void updateFigure(IFigure figure) {
		SubsetTypeFigure sf = (SubsetTypeFigure) figure;
		MultivalueAndAggregator model = getModel();
		sf.setVertical(model.isVertical());
		sf.setSameType(false);
	}
}

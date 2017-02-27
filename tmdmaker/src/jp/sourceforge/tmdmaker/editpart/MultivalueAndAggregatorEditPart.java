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

import jp.sourceforge.tmdmaker.figure.SubsetTypeFigure;
import jp.sourceforge.tmdmaker.model.MultivalueAndAggregator;

import org.eclipse.draw2d.IFigure;

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
	 * @see jp.sourceforge.tmdmaker.editpart.AbstractModelEditPart#updateFigure(org.eclipse.draw2d.IFigure)
	 */
	@Override
	protected void updateFigure(IFigure figure) {
		SubsetTypeFigure sf = (SubsetTypeFigure) figure;
		MultivalueAndAggregator model = getModel();
		sf.setVertical(model.isVertical());
		sf.setSameType(false);
	}
}

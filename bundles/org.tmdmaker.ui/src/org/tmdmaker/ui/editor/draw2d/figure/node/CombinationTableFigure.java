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
package org.tmdmaker.ui.editor.draw2d.figure.node;

import org.tmdmaker.core.model.CombinationTable;
import org.tmdmaker.ui.preferences.appearance.ModelAppearance;

/**
 * CombinationTableのFigure.
 * 
 * @author nakag
 *
 */
public class CombinationTableFigure extends AbstractModelFigure<CombinationTable> {

	/**
	 * 
	 * {@inheritDoc}
	 *
	 * @see org.tmdmaker.ui.editor.draw2d.figure.node.AbstractModelFigure#getTypeLabel(org.tmdmaker.core.model.AbstractEntityModel)
	 */
	@Override
	protected String getTypeLabel(CombinationTable model) {
		return model.getCombinationTableType().getLabel();
	}

	/**
	 * 
	 * {@inheritDoc}
	 *
	 * @see org.tmdmaker.ui.editor.draw2d.figure.node.AbstractModelFigure#getAppearance(org.tmdmaker.core.model.AbstractEntityModel)
	 */
	@Override
	protected ModelAppearance getAppearance(CombinationTable model) {
		return ModelAppearance.COMBINATION_TABLE;
	}

}

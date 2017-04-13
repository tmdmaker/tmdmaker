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
package jp.sourceforge.tmdmaker.ui.editor.draw2d.figure.node;

import jp.sourceforge.tmdmaker.model.MappingList;
import jp.sourceforge.tmdmaker.ui.preferences.appearance.ModelAppearance;

/**
 * MappingListのFigure.
 * 
 * @author nakag
 *
 */
public class MappingListFigure extends AbstractModelFigure<MappingList> {

	/**
	 * 
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.ui.editor.draw2d.figure.node.AbstractModelFigure#getAppearance(jp.sourceforge.tmdmaker.model.AbstractEntityModel)
	 */
	@Override
	protected ModelAppearance getAppearance(MappingList model) {
		return ModelAppearance.MAPPING_LIST;
	}

	/**
	 * 
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.ui.editor.draw2d.figure.node.AbstractModelFigure#getTypeLabel(jp.sourceforge.tmdmaker.model.AbstractEntityModel)
	 */
	@Override
	protected String getTypeLabel(MappingList model) {
		return null;
	}
}

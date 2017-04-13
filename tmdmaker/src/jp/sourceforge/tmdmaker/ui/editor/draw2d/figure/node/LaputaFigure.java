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

import java.util.Arrays;
import java.util.List;

import jp.sourceforge.tmdmaker.model.Laputa;
import jp.sourceforge.tmdmaker.ui.preferences.appearance.ModelAppearance;

/**
 * LaputaのFigure.
 * 
 * @author nakag
 *
 */
public class LaputaFigure extends AbstractModelFigure<Laputa> {

	/**
	 * 
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.ui.editor.draw2d.figure.node.AbstractModelFigure#setupIdentifierList(jp.sourceforge.tmdmaker.model.AbstractEntityModel)
	 */
	@Override
	protected List<String> setupIdentifierList(Laputa model) {
		return Arrays.asList(model.getIdentifier().getName());
	}

	/**
	 * 
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.ui.editor.draw2d.figure.node.AbstractModelFigure#getAppearance(jp.sourceforge.tmdmaker.model.AbstractEntityModel)
	 */
	@Override
	protected ModelAppearance getAppearance(Laputa model) {
		return ModelAppearance.LAPUTA_COLOR;
	}

	/**
	 * 
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.ui.editor.draw2d.figure.node.AbstractModelFigure#getTypeLabel(jp.sourceforge.tmdmaker.model.AbstractEntityModel)
	 */
	@Override
	protected String getTypeLabel(Laputa model) {
		return null;
	}

	/**
	 * 
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.ui.editor.draw2d.figure.node.AbstractModelFigure#isNotImplement(jp.sourceforge.tmdmaker.model.AbstractEntityModel)
	 */
	@Override
	protected boolean isNotImplement(Laputa model) {
		// ラピュタは実装しないが×は付けない
		return false;
	}
}
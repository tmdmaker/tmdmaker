/*
 * Copyright 2009-2018 TMD-Maker Project <https://tmdmaker.osdn.jp/>
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
package jp.sourceforge.tmdmaker.ui.editor.draw2d.adjuster;

import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.multivalue.MultivalueAnd;
import jp.sourceforge.tmdmaker.ui.editor.draw2d.ConstraintAdjuster;
import jp.sourceforge.tmdmaker.ui.editor.draw2d.ConstraintConverter;

/**
 * 多値のANDの位置調整.
 * 
 * @author nakag
 *
 */
public class MultivalueAndAdjuster implements ConstraintAdjuster {
	private AbstractEntityModel header;

	public MultivalueAndAdjuster(AbstractEntityModel header) {
		this.header = header;
	}

	/**
	 * 
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.ui.editor.draw2d.ConstraintAdjuster#adjust()
	 */
	@Override
	public void adjust() {
		MultivalueAnd and = this.header.multivalueAnd();
		ConstraintConverter.setTranslatedConstraint(and.detail(), this.header, 100, 0);
		ConstraintConverter.setTranslatedConstraint(and.superset(), this.header, 64, -80);
		ConstraintConverter.setTranslatedConstraint(and.aggregator(), this.header, 75, -30);
	}

}

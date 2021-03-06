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
package org.tmdmaker.ui.editor.draw2d.adjuster;

import org.tmdmaker.core.model.AbstractEntityModel;
import org.tmdmaker.core.model.multivalue.MultivalueAnd;
import org.tmdmaker.ui.editor.draw2d.ConstraintAdjuster;
import org.tmdmaker.ui.editor.draw2d.ConstraintConverter;

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
	 * @see org.tmdmaker.ui.editor.draw2d.ConstraintAdjuster#adjust()
	 */
	@Override
	public void adjust() {
		MultivalueAnd and = this.header.multivalueAnd();
		ConstraintConverter.setTranslatedConstraint(and.detail(), this.header, 100, 0);
		ConstraintConverter.setTranslatedConstraint(and.superset(), this.header, 64, -80);
		ConstraintConverter.setTranslatedConstraint(and.aggregator(), this.header, 75, -30);
	}

}

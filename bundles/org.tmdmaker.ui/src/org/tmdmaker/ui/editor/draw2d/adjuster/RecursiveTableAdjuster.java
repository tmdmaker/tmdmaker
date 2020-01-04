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
import org.tmdmaker.core.model.RecursiveRelationship;
import org.tmdmaker.core.model.RecursiveTable;
import org.tmdmaker.ui.editor.draw2d.ConstraintAdjuster;
import org.tmdmaker.ui.editor.draw2d.ConstraintConverter;

/**
 * 再帰表作成時の位置調整.
 * 
 * @author nakag
 *
 */
public class RecursiveTableAdjuster implements ConstraintAdjuster {

	private RecursiveRelationship relationship;

	public RecursiveTableAdjuster(RecursiveRelationship relationship) {
		this.relationship = relationship;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.tmdmaker.ui.editor.draw2d.ConstraintAdjuster#adjust()
	 */
	@Override
	public void adjust() {
		AbstractEntityModel source = relationship.getSource();
		RecursiveTable table = relationship.getTable();
		if (ConstraintConverter.isInitialPosition(table)) {
			ConstraintConverter.setTranslatedConstraint(table, source, 100, 0);
		}
	}

}

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
package jp.sourceforge.tmdmaker.ui.editor.draw2d.adjuster;

import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.RecursiveRelationship;
import jp.sourceforge.tmdmaker.model.RecursiveTable;
import jp.sourceforge.tmdmaker.ui.editor.draw2d.ConstraintAdjuster;
import jp.sourceforge.tmdmaker.ui.editor.draw2d.ConstraintConverter;

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
	 * @see jp.sourceforge.tmdmaker.ui.editor.draw2d.ConstraintAdjuster#adjust(jp.sourceforge.tmdmaker.model.ConnectableElement)
	 */
	@Override
	public void adjust() {
		AbstractEntityModel source = relationship.getSource();
		RecursiveTable table = relationship.getTable();
		ConstraintConverter.setTranslatedConstraint(table, source, 100, 0);
	}

}

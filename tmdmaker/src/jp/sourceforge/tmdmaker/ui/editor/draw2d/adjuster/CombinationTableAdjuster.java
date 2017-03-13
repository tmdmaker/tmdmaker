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
import jp.sourceforge.tmdmaker.model.CombinationTable;
import jp.sourceforge.tmdmaker.model.Resource2ResourceRelationship;
import jp.sourceforge.tmdmaker.ui.editor.draw2d.ConstraintAdjuster;
import jp.sourceforge.tmdmaker.ui.editor.draw2d.ConstraintConverter;

/**
 * 対照表作成時の位置調整.
 * 
 * @author nakag
 *
 */
public class CombinationTableAdjuster implements ConstraintAdjuster {

	private Resource2ResourceRelationship relationship;

	public CombinationTableAdjuster(Resource2ResourceRelationship relationship) {
		this.relationship = relationship;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.ui.editor.draw2d.ConstraintAdjuster#adjust()
	 */
	@Override
	public void adjust() {
		AbstractEntityModel source = relationship.getSource();
		CombinationTable table = relationship.getTable();
		if (ConstraintConverter.isInitialPosition(table)) {
			ConstraintConverter.setTranslatedConstraint(table, source, 100, 100);
		}
	}
}

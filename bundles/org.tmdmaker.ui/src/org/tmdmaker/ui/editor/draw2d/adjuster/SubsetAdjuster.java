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

import java.util.List;

import org.tmdmaker.core.model.AbstractEntityModel;
import org.tmdmaker.core.model.SubsetEntity;
import org.tmdmaker.core.model.SubsetType;
import org.tmdmaker.ui.editor.draw2d.ConstraintAdjuster;
import org.tmdmaker.ui.editor.draw2d.ConstraintConverter;

/**
 * サブセット作成時の位置調整.
 * 
 * @author nakag
 *
 */
public class SubsetAdjuster implements ConstraintAdjuster {
	private AbstractEntityModel superset;
	private List<SubsetEntity> addSubsetEntityList;

	public SubsetAdjuster(AbstractEntityModel superset, List<SubsetEntity> addSubsetEntityList) {
		this.superset = superset;
		this.addSubsetEntityList = addSubsetEntityList;
	}

	/**
	 * 
	 * {@inheritDoc}
	 *
	 * @see org.tmdmaker.ui.editor.draw2d.ConstraintAdjuster#adjust()
	 */
	@Override
	public void adjust() {
		int subsetwidth = calcurateSubsetWidth();
		int totalWidthHalf = this.addSubsetEntityList.size() * subsetwidth / 2;
		SubsetType subsetType = this.superset.subsets().subsetType();
		int subsetX = totalWidthHalf * -1 + subsetType.getConstraint().getX();
		final int SUBSET_Y = 50 + subsetType.getConstraint().getY();

		for (SubsetEntity s : this.addSubsetEntityList) {
			ConstraintConverter.setConstraint(s, subsetX, SUBSET_Y);
			subsetX += subsetwidth;
		}
	}

	private int calcurateSubsetWidth() {
		final int SPACE = 14;
		// １文字あたりのdraw2dモデルの幅
		final int CHAR_SIZE = 12;
		int subsetNameLength = calcurateSubsetNameSize();
		int reusedNameLength = this.superset.calcurateMaxIdentifierRefSize();
		int charLength = Math.max(subsetNameLength, reusedNameLength) * CHAR_SIZE;
		return charLength + SPACE;
	}

	private int calcurateSubsetNameSize() {
		int nameLength = 0;
		for (SubsetEntity e : this.addSubsetEntityList) {
			nameLength = Math.max(nameLength, e.getName().length());
		}
		return nameLength;
	}
}

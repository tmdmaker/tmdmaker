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

import org.eclipse.draw2d.geometry.Rectangle;

import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.Entity;
import jp.sourceforge.tmdmaker.model.SubsetType;
import jp.sourceforge.tmdmaker.ui.editor.draw2d.ConstraintAdjuster;
import jp.sourceforge.tmdmaker.ui.editor.draw2d.ConstraintConverter;

/**
 * サブセットタイプの位置調整.
 * 
 * @author nakag
 *
 */
public class SubsetTypeAdjuster implements ConstraintAdjuster {
	/** １文字あたりのdraw2dモデルの幅 */
	public static final int CHAR_SIZE = 12;

	private AbstractEntityModel superset;
	private SubsetType subsetType;

	public SubsetTypeAdjuster(AbstractEntityModel superset, SubsetType subsetType) {
		super();
		this.superset = superset;
		this.subsetType = subsetType;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.ui.editor.draw2d.ConstraintAdjuster#adjust()
	 */
	@Override
	public void adjust() {
		ConstraintConverter.setConstraint(subsetType, calculateSubsetTypePosition());
	}

	private Rectangle calculateSubsetTypePosition() {
		final int MODE_TO_TYPE_DISTANCE = 70;
		int rx = superset.calcurateMaxIdentifierRefSize();
		int ax = superset.calcurateMaxAttributeNameSize();
		int x = ((rx + ax) * CHAR_SIZE) / 2;

		// y軸の位置
		int identifierCount = superset.getReusedIdentifiers().size();
		if (superset instanceof Entity) {
			identifierCount++;
		}

		int attributeCount = superset.getAttributes().size();
		int acount = Math.max(identifierCount, attributeCount);
		int y = MODE_TO_TYPE_DISTANCE + CHAR_SIZE * acount;
		Rectangle rectangle = ConstraintConverter.getTranslatedRectangle(superset, x, y);

		return rectangle;
	}
}

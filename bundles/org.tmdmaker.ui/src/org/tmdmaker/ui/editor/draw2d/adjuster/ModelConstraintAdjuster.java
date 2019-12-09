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
import org.tmdmaker.ui.editor.draw2d.ConstraintAdjuster;
import org.tmdmaker.ui.editor.draw2d.ConstraintConverter;

/**
 * 
 * モデル作成時の位置調整.
 * 
 * @author nakag
 *
 */
public class ModelConstraintAdjuster implements ConstraintAdjuster {
	private AbstractEntityModel base;
	private AbstractEntityModel model;
	private int x;
	private int y;

	public ModelConstraintAdjuster(AbstractEntityModel model, AbstractEntityModel base, int x,
			int y) {
		this.model = model;
		this.base = base;
		this.x = x;
		this.y = y;
	}

	@Override
	public void adjust() {
		ConstraintConverter.setTranslatedConstraint(this.model, this.base, this.x, this.y);
	}

}

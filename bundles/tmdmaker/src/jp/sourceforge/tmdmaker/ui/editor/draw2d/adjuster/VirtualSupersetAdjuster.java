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

import jp.sourceforge.tmdmaker.model.VirtualSuperset;
import jp.sourceforge.tmdmaker.model.VirtualSupersetType;
import jp.sourceforge.tmdmaker.ui.editor.draw2d.ConstraintAdjuster;
import jp.sourceforge.tmdmaker.ui.editor.draw2d.ConstraintConverter;

/**
 * みなしスーパーセットの位置調整.
 * 
 * @author nakag
 *
 */
public class VirtualSupersetAdjuster implements ConstraintAdjuster {

	private VirtualSuperset superset;
	private VirtualSupersetType type;
	private int x;
	private int y;

	public VirtualSupersetAdjuster(VirtualSuperset superset, int x, int y) {
		super();
		this.superset = superset;
		this.type = superset.getVirtualSupersetType();
		this.x = x;
		this.y = y;
	}

	/**
	 * 
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.ui.editor.draw2d.ConstraintAdjuster#adjust()
	 */
	@Override
	public void adjust() {
		ConstraintConverter.setConstraint(superset, x, y);
		ConstraintConverter.setConstraint(type, x, y + 50);
	}
}
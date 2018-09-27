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
package jp.sourceforge.tmdmaker.ui.editor.gef3.commands;

import java.util.List;

import org.eclipse.gef.commands.Command;

import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.VirtualSuperset;
import jp.sourceforge.tmdmaker.model.virtual.VirtualSubsetBuilder;
import jp.sourceforge.tmdmaker.ui.editor.draw2d.adjuster.VirtualSupersetAdjuster;

/**
 * みなしスーパーセット作成Command.
 * 
 * @author nakag
 *
 */
public class VirtualSupersetCreateCommand extends Command {
	private VirtualSuperset superset;
	private VirtualSubsetBuilder builder;
	private int x;
	private int y;

	/**
	 * みなしスーパーセット、サブセットの新規作成用
	 * 
	 * @param superset
	 * @param subsets
	 * @param x
	 * @param y
	 */
	public VirtualSupersetCreateCommand(VirtualSuperset superset, List<AbstractEntityModel> subsets,
			int x, int y) {
		this.superset = superset;
		this.builder = this.superset.virtualSubsets().builder();
		this.builder.subsetList(subsets);
		this.x = x;
		this.y = y;
	}

	/**
	 * みなしスーパーセット、サブセットの編集用
	 * 
	 * @param superset
	 * @param subsets
	 */
	public VirtualSupersetCreateCommand(VirtualSuperset superset,
			List<AbstractEntityModel> subsets) {
		this(superset, subsets, 0, 0);
	}

	/**
	 * 
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		this.builder.build();
		if (needPositionAdjust()) {
			new VirtualSupersetAdjuster(superset, x, y).adjust();
		}
	}

	private boolean needPositionAdjust() {
		return !(this.x == 0 && this.y == 0);
	}

	/**
	 * 
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		this.builder.rollback();
	}

	/**
	 * 
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.gef.commands.Command#redo()
	 */
	@Override
	public void redo() {
		this.builder.build();
	}

}

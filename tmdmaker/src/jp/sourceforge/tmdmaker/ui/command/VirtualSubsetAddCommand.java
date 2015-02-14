/*
 * Copyright 2009-2015 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
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
package jp.sourceforge.tmdmaker.ui.command;

import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.VirtualSuperset;

import org.eclipse.gef.commands.Command;

/**
 * みなしサブセット接続Command.
 *
 * @author nakag
 */
public class VirtualSubsetAddCommand extends Command {
	private VirtualSuperset superset;
	private AbstractEntityModel model;

	/**
	 * コンストラクタ.
	 *
	 * @param superset
	 *            みなしスーパーセット.
	 * @param model
	 *            みなしサブセット.
	 */
	public VirtualSubsetAddCommand(VirtualSuperset superset, AbstractEntityModel model) {
		this.superset = superset;
		this.model = model;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		superset.connectSubset(model);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		superset.disconnectSubset(model);
	}
}
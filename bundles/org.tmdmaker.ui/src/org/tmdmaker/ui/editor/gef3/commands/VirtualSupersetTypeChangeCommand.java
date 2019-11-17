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
package org.tmdmaker.ui.editor.gef3.commands;

import org.eclipse.gef.commands.Command;
import org.tmdmaker.core.model.VirtualSuperset;

/**
 * みなしスーパーセット種類変更Command.
 *
 * @author nakag
 */
public class VirtualSupersetTypeChangeCommand extends Command {
	private VirtualSuperset model;
	private boolean oldApplyAttribute;
	private boolean newApplyAttribute;

	/**
	 * コンストラクタ.
	 *
	 * @param model
	 *            みなしスーパーセット種類.
	 * @param newApplyAttribute
	 *            アトリビュートに適用.
	 */
	public VirtualSupersetTypeChangeCommand(VirtualSuperset model, boolean newApplyAttribute) {
		this.model = model;
		this.newApplyAttribute = newApplyAttribute;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		this.oldApplyAttribute = this.model.getVirtualSupersetType().isApplyAttribute();
		this.model.getVirtualSupersetType().setApplyAttribute(newApplyAttribute);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		this.model.getVirtualSupersetType().setApplyAttribute(oldApplyAttribute);
	}

	@Override
	public void redo() {
		this.model.getVirtualSupersetType().setApplyAttribute(newApplyAttribute);
	}
}

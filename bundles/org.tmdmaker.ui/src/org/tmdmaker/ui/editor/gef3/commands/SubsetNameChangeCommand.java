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
package org.tmdmaker.ui.editor.gef3.commands;

import org.eclipse.gef.commands.Command;
import org.tmdmaker.core.model.SubsetEntity;

/**
 * サブセット名変更Command
 * 
 * @author nakaG
 * 
 */
public class SubsetNameChangeCommand extends Command {
	private SubsetEntity model;
	private String oldName;
	private String newName;

	/**
	 * コンストラクタ
	 * 
	 * @param model
	 * @param name
	 */
	public SubsetNameChangeCommand(SubsetEntity model, String name) {
		this.model = model;
		this.oldName = model.getName();
		this.newName = name;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		this.model.setName(newName);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		this.model.setName(oldName);
	}

}

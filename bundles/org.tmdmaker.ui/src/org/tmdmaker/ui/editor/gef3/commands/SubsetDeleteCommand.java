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
import org.tmdmaker.core.model.AbstractEntityModel;
import org.tmdmaker.core.model.SubsetEntity;
import org.tmdmaker.core.model.subset.SubsetBuilder;

/**
 * サブセット削除Command
 * 
 * @author nakaG
 * 
 */
public class SubsetDeleteCommand extends Command {
	private AbstractEntityModel parent;
	private SubsetBuilder builder;
	private SubsetEntity subsetEntity;

	/**
	 * コンストラクタ
	 * 
	 * @param subsetEntity
	 */
	public SubsetDeleteCommand(SubsetEntity subsetEntity) {
		this.parent = subsetEntity.getSuperset();
		this.builder = this.parent.subsets().builder();
		this.subsetEntity = subsetEntity;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		builder.remove(subsetEntity).build();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		builder.rollbackRemove();
	}

}

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

import java.util.List;

import org.eclipse.gef.commands.Command;
import org.tmdmaker.core.model.AbstractEntityModel;
import org.tmdmaker.core.model.SubsetEntity;
import org.tmdmaker.core.model.SubsetType;
import org.tmdmaker.core.model.parts.ModelName;
import org.tmdmaker.core.model.subset.SubsetBuilder;
import org.tmdmaker.ui.editor.draw2d.adjuster.SubsetAdjuster;
import org.tmdmaker.ui.editor.draw2d.adjuster.SubsetTypeAdjuster;

/**
 * サブセット追加Command
 * 
 * @author nakag
 *
 */
public class SubsetAddCommand extends Command {
	private AbstractEntityModel parent;
	private List<ModelName> addSubsetNameList;
	private List<SubsetEntity> addedList;
	private SubsetBuilder builder;

	/**
	 * コンストラクタ.
	 * 
	 * @param parent
	 *            サブセット元
	 * @param addSubsetNameList
	 *            追加するサブセットの名称
	 */
	public SubsetAddCommand(AbstractEntityModel parent, List<ModelName> addSubsetNameList) {
		this.parent = parent;
		this.addSubsetNameList = addSubsetNameList;
	}

	/**
	 * 
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		this.builder = this.parent.subsets().builder();
		for (ModelName name : this.addSubsetNameList) {
			this.builder.add(name);
		}
		this.builder.build();
		SubsetType subsetType = this.parent.subsets().subsetType();
		if (subsetType.isInitialPosition()) {
			new SubsetTypeAdjuster(this.parent, subsetType).adjust();
		}
		this.addedList = this.builder.getAddedSubsetList();
		new SubsetAdjuster(this.parent, this.addedList).adjust();
	}

	/**
	 * 
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		this.builder.rollbackAdd();
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

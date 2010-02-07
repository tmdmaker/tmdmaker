/*
 * Copyright 2009-2010 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
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
package jp.sourceforge.tmdmaker.model.command;

import jp.sourceforge.tmdmaker.model.AbstractEntityModel;

import org.eclipse.gef.commands.Command;

/**
 * エンティティ系モデル編集Command
 * 
 * @author nakaG
 * 
 */
public class ModelEditCommand extends Command {
	/** 更新前値 */
	private AbstractEntityModel oldValue;
	/** 更新値 */
	private AbstractEntityModel newValue;
	/** 編集対象 */
	private AbstractEntityModel editModel;

	/**
	 * コンストラクタ
	 * 
	 * @param editModel
	 *            編集対象
	 * @param newValue
	 *            更新値
	 */
	public ModelEditCommand(AbstractEntityModel editModel,
			AbstractEntityModel newValue) {
		this.editModel = editModel;
		this.oldValue = editModel.getCopy();
		this.newValue = newValue;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		newValue.copyTo(editModel);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		oldValue.copyTo(editModel);
	}

}

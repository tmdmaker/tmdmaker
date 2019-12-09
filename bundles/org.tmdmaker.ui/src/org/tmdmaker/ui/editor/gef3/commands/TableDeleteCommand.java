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
import org.tmdmaker.core.model.AbstractConnectionModel;
import org.tmdmaker.core.model.AbstractEntityModel;

/**
 * 表系モデル削除Command
 * 
 * @author nakaG
 * 
 */
public class TableDeleteCommand extends Command {
	/** 削除対象の表モデル */
	private AbstractEntityModel model;
	/** 削除対象の表モデルを作成する契機となったリレーションシップ */
	private AbstractConnectionModel creationRelationship;

	/**
	 * 
	 * @param model
	 * @param creationRelationship
	 */
	public TableDeleteCommand(AbstractEntityModel model,
			AbstractConnectionModel creationRelationship) {
		this.model = model;
		this.creationRelationship = creationRelationship;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.commands.Command#canExecute()
	 */
	@Override
	public boolean canExecute() {
		return model.isDeletable();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		creationRelationship.disconnect();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		creationRelationship.connect();
	}

}

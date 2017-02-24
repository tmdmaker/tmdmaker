/*
 * Copyright 2009-2017 TMD-Maker Project <http://tmdmaker.osdn.jp/>
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

import org.eclipse.gef.commands.Command;

import jp.sourceforge.tmdmaker.model.AbstractRelationship;

/**
 * GEFのコネクションツールを利用せずに作成済みのRelationshipを接続するCommand.
 * 
 * @author nakag
 *
 */
public class RelationshipConnectionCommand extends Command {
	private AbstractRelationship relationship;

	public RelationshipConnectionCommand(AbstractRelationship relationship) {
		this.relationship = relationship;
	}

	/**
	 * 
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		this.relationship.connect();
	}

	/**
	 * 
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		this.relationship.disconnect();
	}
}

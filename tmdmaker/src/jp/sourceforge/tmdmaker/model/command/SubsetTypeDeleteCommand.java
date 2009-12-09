/*
 * Copyright 2009 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
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

import jp.sourceforge.tmdmaker.model.Diagram;
import jp.sourceforge.tmdmaker.model.Entity2SubsetTypeRelationship;
import jp.sourceforge.tmdmaker.model.SubsetType;

import org.eclipse.gef.commands.Command;

/**
 * サブセット種類削除Command
 * 
 * @author nakaG
 * 
 */
public class SubsetTypeDeleteCommand extends Command {
	private Diagram diagram;
	private SubsetType model;
	private Entity2SubsetTypeRelationship relationship;

	public SubsetTypeDeleteCommand(Diagram diagram, SubsetType model) {
		this.diagram = diagram;
		this.model = model;
		this.relationship = (Entity2SubsetTypeRelationship) model
				.getModelTargetConnections().get(0);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		if (model.getModelSourceConnections().size() == 0) {
			relationship.disconnect();
			diagram.removeChild(model);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		if (model.getModelSourceConnections().size() == 0) {
			diagram.addChild(model);
			relationship.connect();
		}
	}

}

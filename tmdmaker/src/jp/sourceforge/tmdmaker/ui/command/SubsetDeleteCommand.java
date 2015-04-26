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

import jp.sourceforge.tmdmaker.model.Diagram;
import jp.sourceforge.tmdmaker.model.RelatedRelationship;
import jp.sourceforge.tmdmaker.model.SubsetEntity;

import org.eclipse.gef.commands.Command;

/**
 * サブセット削除Command
 * 
 * @author nakaG
 * 
 */
public class SubsetDeleteCommand extends Command {
	private SubsetEntity model;
	private Diagram diagram;
	private RelatedRelationship relationship;

	/**
	 * コンストラクタ
	 * 
	 * @param model
	 */
	public SubsetDeleteCommand(SubsetEntity model) {
		this.model = model;
		this.diagram = model.getDiagram();
		this.relationship = (RelatedRelationship) model.getModelTargetConnections().get(0);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		this.relationship.disconnect();
		this.diagram.removeChild(model);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		this.diagram.addChild(model);
		this.relationship.connect();
	}

}

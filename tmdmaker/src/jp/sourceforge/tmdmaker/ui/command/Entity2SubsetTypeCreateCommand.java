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
package jp.sourceforge.tmdmaker.ui.command;

import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.Diagram;
import jp.sourceforge.tmdmaker.model.Entity2SubsetTypeRelationship;
import jp.sourceforge.tmdmaker.model.IAttribute;
import jp.sourceforge.tmdmaker.model.SubsetType;

import org.eclipse.gef.commands.Command;

/**
 * エンティティ系モデルとサブセット種類を接続するCommand
 * 
 * @author nakaG
 * 
 */
public class Entity2SubsetTypeCreateCommand extends Command {
	private Diagram diagram;
	private AbstractEntityModel model;
	private SubsetType subsetType;
	private Entity2SubsetTypeRelationship model2subsetRelationship;

	public Entity2SubsetTypeCreateCommand(AbstractEntityModel model, SubsetType subsetType,
			IAttribute partitionAttribute) {
		this.diagram = model.getDiagram();
		this.model = model;
		this.subsetType = subsetType;
		this.model2subsetRelationship = new Entity2SubsetTypeRelationship(this.model,
				this.subsetType);
		this.subsetType.setPartitionAttribute(partitionAttribute);
		// this.model2subsetRelationship
		// .setPartitionAttribute(partitionAttribute);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		diagram.addChild(subsetType);
		model2subsetRelationship.connect();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		model2subsetRelationship.disconnect();
		diagram.removeChild(subsetType);

	}
}

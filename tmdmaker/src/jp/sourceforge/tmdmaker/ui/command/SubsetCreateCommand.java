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
import jp.sourceforge.tmdmaker.model.Constraint;
import jp.sourceforge.tmdmaker.model.Diagram;
import jp.sourceforge.tmdmaker.model.Entity2SubsetTypeRelationship;
import jp.sourceforge.tmdmaker.model.SubsetEntity;
import jp.sourceforge.tmdmaker.model.SubsetType;
import jp.sourceforge.tmdmaker.model.SubsetType2SubsetRelationship;

import org.eclipse.gef.commands.Command;

/**
 * サブセット作成Command
 * 
 * @author nakaG
 * 
 */
public class SubsetCreateCommand extends Command {
	private Diagram diagram;
	private AbstractEntityModel model;
	private SubsetType subsetType;
	private Entity2SubsetTypeRelationship model2subsetTypeRelationship;
	private SubsetEntity subsetEntity;
	private SubsetType2SubsetRelationship subsetType2subsetEntityRelationship;

	/**
	 * コンストラクタ
	 * 
	 * @param model
	 * @param subsetType
	 * @param subsetEntity
	 */
	public SubsetCreateCommand(AbstractEntityModel model, SubsetType subsetType,
			SubsetEntity subsetEntity) {
		super();
		this.model = model;
		this.subsetType = subsetType;
		this.diagram = model.getDiagram();
		this.subsetEntity = subsetEntity;
		subsetType2subsetEntityRelationship = new SubsetType2SubsetRelationship(this.subsetType,
				this.subsetEntity);
		Constraint constraint = subsetType.getConstraint().getTranslated(0, 0);
		subsetEntity.setConstraint(constraint);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		if (subsetType.getModelTargetConnections().size() == 0) {
			this.model2subsetTypeRelationship = new Entity2SubsetTypeRelationship(this.model,
					this.subsetType);
		} else {
			this.model2subsetTypeRelationship = (Entity2SubsetTypeRelationship) subsetType
					.getModelTargetConnections().get(0);
		}
		model2subsetTypeRelationship.connect();
		subsetType2subsetEntityRelationship.connect();
		diagram.addChild(subsetEntity);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		subsetType2subsetEntityRelationship.disconnect();
		diagram.removeChild(subsetEntity);
		if (subsetType.getModelTargetConnections().size() == 0) {
			model2subsetTypeRelationship.disconnect();
		}
	}

}

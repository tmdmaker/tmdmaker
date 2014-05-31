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

import jp.sourceforge.tmdmaker.model.Entity2SubsetTypeRelationship;
import jp.sourceforge.tmdmaker.model.IAttribute;
import jp.sourceforge.tmdmaker.model.SubsetType;
import jp.sourceforge.tmdmaker.model.SubsetType.SubsetTypeValue;

import org.eclipse.gef.commands.Command;

/**
 * @author nakaG
 *
 */
public class SubsetTypeChangeCommand extends Command {
	private SubsetType subsetType;
	private SubsetType.SubsetTypeValue newSubsetTypeValue;
	private SubsetType.SubsetTypeValue oldSubsetTypeValue;
	private IAttribute oldPartitionAttribute;
	private IAttribute newPartitionAttribute;
	private boolean oldExceptNull;
	private boolean newExceptNull;
	private Entity2SubsetTypeRelationship relationship;

	/**
	 * コンストラクタ
	 * 
	 * @param subsetType
	 * @param newSubsetTypeValue
	 * @param selectedPartitionAttribute
	 * @param newExceptNull
	 */
	public SubsetTypeChangeCommand(SubsetType subsetType, SubsetTypeValue newSubsetTypeValue,
			IAttribute selectedPartitionAttribute, boolean newExceptNull) {
		this.subsetType = subsetType;
		this.oldSubsetTypeValue = subsetType.getSubsetType();
		this.newSubsetTypeValue = newSubsetTypeValue;
		this.newPartitionAttribute = selectedPartitionAttribute;
		this.newExceptNull = newExceptNull;
		this.oldExceptNull = subsetType.isExceptNull();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		this.subsetType.setSubsetType(newSubsetTypeValue);
		if (this.subsetType.getModelTargetConnections() != null
				&& this.subsetType.getModelTargetConnections().size() > 0) {
			this.relationship = (Entity2SubsetTypeRelationship) this.subsetType
					.getModelTargetConnections().get(0);
			this.oldPartitionAttribute = this.relationship.getPartitionAttribute();
			this.subsetType.setExceptNull(newExceptNull);
			this.subsetType.setPartitionAttribute(newPartitionAttribute);

			// this.relationship.setPartitionAttribute(newPartitionAttribute);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		this.subsetType.setSubsetType(oldSubsetTypeValue);
		if (this.relationship != null) {
			this.subsetType.setExceptNull(oldExceptNull);
			this.subsetType.setPartitionAttribute(oldPartitionAttribute);
			// this.relationship.setPartitionAttribute(oldPartitionAttribute);
		}
	}

}

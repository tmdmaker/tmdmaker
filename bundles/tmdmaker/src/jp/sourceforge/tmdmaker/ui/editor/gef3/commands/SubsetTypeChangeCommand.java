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
package jp.sourceforge.tmdmaker.ui.editor.gef3.commands;

import org.eclipse.gef.commands.Command;

import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.IAttribute;
import jp.sourceforge.tmdmaker.model.SubsetType;
import jp.sourceforge.tmdmaker.model.SubsetType.SubsetTypeValue;

/**
 * @author nakaG
 *
 */
public class SubsetTypeChangeCommand extends Command {
	private AbstractEntityModel parent;
	private SubsetType.SubsetTypeValue newSubsetTypeValue;
	private SubsetType.SubsetTypeValue oldSubsetTypeValue = SubsetType.SubsetTypeValue.SAME;
	private IAttribute oldPartitionAttribute;
	private IAttribute newPartitionAttribute;
	private boolean oldExceptNull = false;
	private boolean newExceptNull;

	/**
	 * コンストラクタ
	 * 
	 * @param subsetType
	 * @param newSubsetTypeValue
	 * @param selectedPartitionAttribute
	 * @param newExceptNull
	 */
	public SubsetTypeChangeCommand(AbstractEntityModel parent, SubsetTypeValue newSubsetTypeValue,
			IAttribute selectedPartitionAttribute, boolean newExceptNull) {
		this.parent = parent;
		this.newSubsetTypeValue = newSubsetTypeValue;
		this.newPartitionAttribute = selectedPartitionAttribute;
		this.newExceptNull = newExceptNull;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		SubsetType type = this.parent.subsets().subsetType();

		this.oldSubsetTypeValue = type.getSubsetType();
		this.oldExceptNull = type.isExceptNull();
		this.oldPartitionAttribute = type.getPartitionAttribute();

		type.setSubsetType(this.newSubsetTypeValue);
		type.setPartitionAttribute(this.newPartitionAttribute);
		type.setExceptNull(this.newExceptNull);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		SubsetType type = this.parent.subsets().subsetType();
		type.setSubsetType(this.oldSubsetTypeValue);
		type.setPartitionAttribute(this.oldPartitionAttribute);
		type.setExceptNull(this.oldExceptNull);
	}

	@Override
	public void redo() {
		SubsetType type = this.parent.subsets().subsetType();

		type.setSubsetType(this.newSubsetTypeValue);
		type.setPartitionAttribute(this.newPartitionAttribute);
		type.setExceptNull(this.newExceptNull);
	}

}

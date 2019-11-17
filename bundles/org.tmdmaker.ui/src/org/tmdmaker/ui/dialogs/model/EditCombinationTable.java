/*
 * Copyright 2009-2011 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
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
package org.tmdmaker.ui.dialogs.model;

import org.tmdmaker.core.model.AbstractEntityModel;
import org.tmdmaker.core.model.CombinationTable;
import org.tmdmaker.core.model.CombinationTableType;

/**
 * 対照表の編集用
 * 
 * @author nakaG
 * 
 */
public class EditCombinationTable extends EditTable {
	/** 対照表種類 */
	private CombinationTableType combinationTableType;

	/**
	 * コンストラクタ
	 * 
	 * @param entity
	 *            編集対象の対照表
	 */
	public EditCombinationTable(CombinationTable entity) {
		super(entity);
		combinationTableType = entity.getCombinationTableType();
	}

	/**
	 * @return the combinationTableType
	 */
	public CombinationTableType getCombinationTableType() {
		return combinationTableType;
	}

	/**
	 * @param combinationTableType
	 *            the combinationTableType to set
	 */
	public void setCombinationTableType(
			CombinationTableType combinationTableType) {
		this.combinationTableType = combinationTableType;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.tmdmaker.ui.dialogs.model.EditTable#copySpecialTo(org.tmdmaker.core.model.AbstractEntityModel)
	 */
	@Override
	protected void copySpecialTo(AbstractEntityModel to) {
		CombinationTable edited = (CombinationTable) to;
		edited.setCombinationTableType(getCombinationTableType());
	}

}

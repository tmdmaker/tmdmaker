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
package jp.sourceforge.tmdmaker.dialog.model;

import jp.sourceforge.tmdmaker.model.Detail;

/**
 * ディテールの編集用
 * 
 * @author nakaG
 * 
 */
public class EditDetail extends EditTable {
	public static final String PROPERTY_UP_IDENTIFIER = "_edit_property_up_identifier";
	public static final String PROPERTY_IDENTIFIER = "_edit_property_identifier";
	/** 編集対象の個体指定子 */
	private EditAttribute editIdentifier;

	/**
	 * コンストラクタ
	 * 
	 * @param entity
	 *            編集対象のディテール
	 */
	public EditDetail(Detail entity) {
		super(entity);
		this.editIdentifier = new EditAttribute(entity.getDetailIdentifier());
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.dialog.model.EditTable#uptoIdentifier(int)
	 */
	@Override
	public void uptoIdentifier(int index) {
		EditAttribute toIdentifier = attributes.get(index);

		EditAttribute tempIdentifier = new EditAttribute();
		editIdentifier.copyTo(tempIdentifier);
		toIdentifier.copyTo(editIdentifier);
		tempIdentifier.copyTo(toIdentifier);
		firePropertyChange(PROPERTY_UP_IDENTIFIER, tempIdentifier, toIdentifier);
	}

	/**
	 * 個体指定子名称を設定する
	 * 
	 * @param name
	 *            個体指定子名称
	 */
	public void setIdentifierName(String name) {
		editIdentifier.setName(name);
		firePropertyChange(PROPERTY_IDENTIFIER, null, editIdentifier);
	}

	/**
	 * 編集用の個体指定子を更新する
	 * 
	 * @param identifier
	 *            更新後の個体指定子の値
	 */
	public void updateEditIdentifier(EditAttribute identifier) {
		identifier.copyTo(editIdentifier);
		firePropertyChange(PROPERTY_IDENTIFIER, null, editIdentifier);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.dialog.model.EditTable#canUpToIdentifier()
	 */
	@Override
	public boolean canUpToIdentifier() {
		return true;
	}
	/**
	 * @return the editIdentifier;
	 */
	public EditAttribute getEditIdentifier() {
		return editIdentifier;
	}
}

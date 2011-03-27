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

import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.Detail;
import jp.sourceforge.tmdmaker.model.Identifier;

/**
 * @author nakaG
 * 
 */
public class EditDetail extends EditEntity {

	/**
	 * コンストラクタ
	 * 
	 * @param entity
	 *            編集対象のエンティティ
	 */
	public EditDetail(Detail entity) {
		super(entity);
		this.editIdentifier = new EditAttribute(entity.getDetailIdentifier());
	}

	/**
	 */
	@Override
	protected void copySpecialTo(AbstractEntityModel to) {
		Detail edited = (Detail) to;
		Identifier newIdentifier = new Identifier();
		getEditIdentifier().copyTo(newIdentifier);
		edited.setDetailIdentifier(newIdentifier);
		edited.setEntityType(getType());
	}

}

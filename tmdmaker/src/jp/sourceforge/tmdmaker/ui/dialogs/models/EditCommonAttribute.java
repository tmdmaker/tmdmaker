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
package jp.sourceforge.tmdmaker.ui.dialogs.models;

import java.util.List;

import jp.sourceforge.tmdmaker.model.IAttribute;

/**
 * 共通属性編集用のモデル
 * 
 * @author nakaG
 * 
 */
public class EditCommonAttribute extends EditTable {
	/**
	 * コンストラクタ
	 * 
	 * @param originLAttributes
	 *            共通属性のリスト
	 */
	public EditCommonAttribute(List<IAttribute> originLAttributes) {
		if (originLAttributes != null) {
			for (IAttribute a : originLAttributes) {
				attributes.add(new EditAttribute(a));
			}
		}
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.ui.dialogs.models.EditTable#isValid()
	 */
	@Override
	public boolean isValid() {
		return true;
	}

}
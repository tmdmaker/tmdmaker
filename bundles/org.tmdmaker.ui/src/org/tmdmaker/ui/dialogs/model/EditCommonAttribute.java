/*
 * Copyright 2009-2019 TMD-Maker Project <https://www.tmdmaker.org>
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

import java.util.List;

import org.tmdmaker.core.model.IAttribute;

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
	 * @see org.tmdmaker.ui.dialogs.model.EditTable#isValid()
	 */
	@Override
	public boolean isValid() {
		return true;
	}

}

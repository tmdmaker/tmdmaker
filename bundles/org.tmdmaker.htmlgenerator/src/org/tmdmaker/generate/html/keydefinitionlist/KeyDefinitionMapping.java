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
package org.tmdmaker.generate.html.keydefinitionlist;

import org.tmdmaker.core.model.IAttribute;
import org.tmdmaker.core.model.KeyModel;

/**
 * キー定義のデータ
 * 
 * @author nakaG
 * 
 */
public class KeyDefinitionMapping {
	private IAttribute attribute;
	private KeyModel keyModel;
	/** キーにおけるアトリビュートの順序 */
	private String keyOrder;

	/**
	 * コンストラクタ
	 * 
	 * @param attribute 組み合わせ対象のアトリビュート
	 * @param keyModel 組み合わせ対象のキー
	 */
	public KeyDefinitionMapping(IAttribute attribute, KeyModel keyModel) {
		this.attribute = attribute;
		this.keyModel = keyModel;

		setup();
	}

	private void setup() {
		if (this.keyModel.getAttributes().contains(this.attribute)) {
			keyOrder = String.valueOf(this.keyModel.getAttributes().indexOf(
					this.attribute) + 1);
		} else {
			keyOrder = "-";
		}
	}

	/**
	 * @return the keyOrder
	 */
	public String getKeyOrder() {
		return keyOrder;
	}
}

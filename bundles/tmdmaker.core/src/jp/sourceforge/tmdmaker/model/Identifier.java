/*
 * Copyright 2009-2017 TMD-Maker Project <http://tmdmaker.osdn.jp/>
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
package jp.sourceforge.tmdmaker.model;

import jp.sourceforge.tmdmaker.model.parts.ModelName;
import jp.sourceforge.tmdmaker.model.rule.EntityRecognitionRule;

/**
 * 個体指定子モデル
 * 
 * @author nakaG
 * 
 */
@SuppressWarnings("serial")
public class Identifier extends Attribute {
	/**
	 * コンストラクタ
	 * 
	 * @param name
	 *            個体指定子の名称
	 */
	public Identifier(String name) {
		super(name);
	}

	/**
	 * コンストラクタ
	 */
	public Identifier() {
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.Attribute#getCopy()
	 */
	@Override
	public IAttribute getCopy() {
		Identifier copy = new Identifier();
		copyTo(copy);
		return copy;
	}

	public ModelName createEntityName() {
		return new ModelName(EntityRecognitionRule.getInstance().generateEntityNameFromIdentifier(getName()));
	}
}

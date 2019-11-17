/*
 * Copyright 2009-2010 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
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
package org.tmdmaker.core.model.generate.attributelist;

import org.tmdmaker.core.model.AbstractEntityModel;
import org.tmdmaker.core.model.IAttribute;

/**
 * エンティティ系モデルとアトリビュートの１：１のペアモデル
 * 
 * @author nakaG
 * 
 */
public class EntityAttributePair {
	private IAttribute attribute;
	private AbstractEntityModel model;

	/**
	 * コンストラクタ
	 * 
	 * @param model
	 * @param attribute
	 */
	public EntityAttributePair(AbstractEntityModel model, IAttribute attribute) {
		this.model = model;
		this.attribute = attribute;
	}

	public String createAttributeFileKey() {
		return attribute.getName() + "_" + model.getName();
	}

	/**
	 * @return the attribute
	 */
	public IAttribute getAttribute() {
		return attribute;
	}

	/**
	 * @return the model
	 */
	public AbstractEntityModel getModel() {
		return model;
	}
}

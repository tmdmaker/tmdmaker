package jp.sourceforge.tmdmaker.sphinx.attributelist

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
import jp.sourceforge.tmdmaker.model.AbstractEntityModel
import jp.sourceforge.tmdmaker.model.IAttribute
import org.eclipse.xtend.lib.annotations.Accessors

/** 
 * エンティティ系モデルとアトリビュートの１：１のペアモデル
 * @author tohosaku
 */
class EntityAttributePair {
	@Accessors(#[PUBLIC_GETTER, PUBLIC_SETTER]) IAttribute attribute
	@Accessors(#[PUBLIC_GETTER, PUBLIC_SETTER]) AbstractEntityModel model

	/** 
	 * コンストラクタ
	 * @param model
	 * @param attribute
	 */
	new(AbstractEntityModel model, IAttribute attribute) {
		this.model     = model
		this.attribute = attribute
	}

	def String createAttributeFileKey() {
		'''«attribute.implementName»_«model.implementName»'''
	}
}

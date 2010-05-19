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
package jp.sourceforge.tmdmaker.model;

/**
 * アトリビュートの実装方法の編集用クラス
 * 
 * @author nakaG
 * 
 */
public class EditImplementAttribute extends EditAttribute {
	/** 編集対象のアトリビュートを保持しているモデル */
	private AbstractEntityModel containerModel;

	/**
	 * コンストラクタ
	 * 
	 * @param containerModel
	 */
	public EditImplementAttribute(AbstractEntityModel containerModel,
			Attribute original) {
		super(original);
		this.containerModel = containerModel;
	}

	/**
	 * @return the containerModel
	 */
	public AbstractEntityModel getContainerModel() {
		return containerModel;
	}
}

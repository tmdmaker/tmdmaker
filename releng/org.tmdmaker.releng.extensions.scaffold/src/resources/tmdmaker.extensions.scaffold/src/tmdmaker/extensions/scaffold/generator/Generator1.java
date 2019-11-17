/*
 * Copyright 2009-2018 TMD-Maker Project <https://tmdmaker.osdn.jp/>
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
package tmdmaker.extensions.scaffold.generator;

import java.util.List;

import org.tmdmaker.model.generate.Generator;

import org.tmdmaker.core.model.AbstractEntityModel;

public class Generator1 implements Generator {

	public Generator1() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 生成処理実行
	 * 
	 * @param rootDir
	 *            生成先ディレクトリ
	 * @param models
	 *            対象モデル
	 */
	@Override
	public void execute(String arg0, List<AbstractEntityModel> arg1) {
		// TODO Auto-generated method stub
	}

	/**
	 * Generator名を返す。コンテキストメニューに表示する名称となる。
	 * 
	 * @return Generator名
	 */
	@Override
	public String getGeneratorName() {
		// TODO Auto-generated method stub
		return "Generator雛形";
	}

	/**
	 * Generatorのグループ名を返す。グループ名,Generator名の順でコンテキストメニューに表示される。
	 * 
	 * @return グループ名
	 */
	@Override
	public String getGroupName() {
		// TODO Auto-generated method stub
		return "雛形グループ";
	}

	/**
	 * 実装モデルだけを出力対象とするか？
	 * 
	 * モデル選択ダイアログで対象とするモデルを絞り込む際に利用している。
	 * 
	 * @return 実装モデルだけを対照とする場合にtrueを返す。
	 */
	@Override
	public boolean isImplementModelOnly() {
		// TODO Auto-generated method stub
		return false;
	}

}

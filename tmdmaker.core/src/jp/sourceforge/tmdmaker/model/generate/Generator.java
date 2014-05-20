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
package jp.sourceforge.tmdmaker.model.generate;

import java.util.List;

import jp.sourceforge.tmdmaker.model.AbstractEntityModel;

/**
 * ダイアグラムを元に何かを生成するためのインターフェース
 * 
 * @author nakaG
 * 
 */
public interface Generator {
	/**
	 * Generatorのグループ名を返す。グループ名,Generator名の順でコンテキストメニューに表示される。
	 * 
	 * @return グループ名
	 */
	String getGroupName();

	/**
	 * Generator名を返す。コンテキストメニューに表示する名称となる。
	 * 
	 * @return Generator名
	 */
	String getGeneratorName();

	/**
	 * 実装モデルだけを出力対象とするか？
	 * 
	 * @return 実装モデルだけを対照とする場合にtrueを返す。
	 */
	boolean isImplementModelOnly();

	/**
	 * 生成処理実行
	 * 
	 * @param rootDir
	 *            生成先ディレクトリ
	 * @param models
	 *            対象モデル
	 */
	void execute(String rootDir, List<AbstractEntityModel> models);
}

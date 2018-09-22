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
package jp.sourceforge.tmdmaker.model.importer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import jp.sourceforge.tmdmaker.model.AbstractEntityModel;

/**
 * ファイルからエンティティをインポートする機能のインターフェース
 * 
 * @author nakaG
 *
 */
public interface FileImporter {
	/**
	 * エンティティをインポートする
	 * 
	 * @param filePath
	 *            インポートするファイルのパス
	 * @return インポートしたエンティティのモデル
	 * @throws FileNotFoundException
	 *             インポートするファイルが見つからない場合
	 * @throws IOException
	 *             その他ファイル読み込み時にエラーが発生した場合
	 */
	List<AbstractEntityModel> importEntities(String filePath) throws FileNotFoundException,
			IOException;

	/**
	 * Importer名を返す。コンテキストメニューに表示する名称となる。
	 * 
	 * @return Importer名
	 */
	String getImporterName();
	
	String[] getAvailableExtensions();
}
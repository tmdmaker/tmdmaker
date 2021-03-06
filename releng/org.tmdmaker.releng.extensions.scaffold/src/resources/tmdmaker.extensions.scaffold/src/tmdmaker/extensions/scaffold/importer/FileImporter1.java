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
package tmdmaker.extensions.scaffold.importer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.tmdmaker.model.importer.FileImporter;

import org.tmdmaker.core.model.AbstractEntityModel;

public class FileImporter1 implements FileImporter {

	public FileImporter1() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * Importer名を返す。コンテキストメニューに表示する名称となる。
	 * 
	 * @return Importer名
	 */
	@Override
	public String getImporterName() {
		// TODO Auto-generated method stub
		return "Importer雛形";
	}
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
	@Override
	public List<AbstractEntityModel> importEntities(String filePath) throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * このImporterで利用可能なファイルの拡張子を返す.
	 * 
	 * @return このImporterで利用可能なファイルの拡張子
	 */
	@Override
	public String[] getAvailableExtensions() {
		return new String[] {"txt"};
	}
}

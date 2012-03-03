/*
 * Copyright 2009-2012 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
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
package jp.sourceforge.tmdmaker.importer.impl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jp.sourceforge.tmdmaker.importer.FileImporter;
import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.Attribute;
import jp.sourceforge.tmdmaker.model.rule.EntityRecognitionRule;
import au.com.bytecode.opencsv.CSVReader;

/**
 * アトリビュートをCSVファイルからインポートするクラス
 * 
 * @author nakaG
 * 
 */
public class AttributeFileImporter implements FileImporter {

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.importer.FileImporter#importEntities(java.lang.String)
	 */
	@Override
	public List<AbstractEntityModel> importEntities(String filePath)
			throws FileNotFoundException, IOException {

		CSVReader reader = new CSVReader(new BufferedReader(new FileReader(
				filePath)));
		String[] nextLine;
		AbstractEntityModel l = EntityRecognitionRule.createLaputa();
		
		while ((nextLine = reader.readNext()) != null) {
			String attributeName = nextLine[0];
			l.addAttribute(new Attribute(attributeName));
		}
		List<AbstractEntityModel> list = new ArrayList<AbstractEntityModel>();
		list.add(l);
		return list;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.importer.FileImporter#getImporterName()
	 */
	@Override
	public String getImporterName() {
		return "ファイルからアトリビュートをインポート";
	}

}

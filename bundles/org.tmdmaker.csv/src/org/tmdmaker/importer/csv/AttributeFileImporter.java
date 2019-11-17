/*
 * Copyright 2009-2019 TMD-Maker Project <https://tmdmaker.osdn.jp/>
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
package org.tmdmaker.importer.csv;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.tmdmaker.core.model.AbstractEntityModel;
import org.tmdmaker.core.model.Attribute;
import org.tmdmaker.core.model.Laputa;
import org.tmdmaker.csv.Messages;
import org.tmdmaker.model.importer.FileImporter;

import au.com.bytecode.opencsv.CSVReader;

/**
 * アトリビュートをCSVファイルからインポートするクラス
 * 
 * @author nakaG
 * 
 */
public class AttributeFileImporter implements FileImporter {
	private static final String[] EXTENSION = new String[] {"csv"}; //$NON-NLS-1$

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.tmdmaker.model.importer.FileImporter#importEntities(java.lang.String)
	 */
	@Override
	public List<AbstractEntityModel> importEntities(String filePath)
			throws FileNotFoundException, IOException {

		CSVReader reader = new CSVReader(new BufferedReader(new FileReader(
				filePath)));
		String[] nextLine;
		AbstractEntityModel l = Laputa.of();

		while ((nextLine = reader.readNext()) != null) {
			String attributeName = nextLine[0];
			l.addAttribute(new Attribute(attributeName));
		}
		reader.close();
		
		List<AbstractEntityModel> list = new ArrayList<AbstractEntityModel>();
		list.add(l);
		return list;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.tmdmaker.model.importer.FileImporter#getImporterName()
	 */
	@Override
	public String getImporterName() {
		return Messages.getString("AttributeFileImporter.ImporterName"); //$NON-NLS-1$
	}

	/**
	 * 
	 * {@inheritDoc}
	 *
	 * @see org.tmdmaker.model.importer.FileImporter#getAvailableExtensions()
	 */
	@Override
	public String[] getAvailableExtensions() {
		return EXTENSION;
	}

}

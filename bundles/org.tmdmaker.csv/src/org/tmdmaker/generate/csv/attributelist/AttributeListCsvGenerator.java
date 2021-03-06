/*
 * Copyright 2009-2021 TMD-Maker Project <https://www.tmdmaker.org>
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
package org.tmdmaker.generate.csv.attributelist;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tmdmaker.core.model.AbstractEntityModel;
import org.tmdmaker.core.model.IAttribute;
import org.tmdmaker.core.model.generate.attributelist.AttributeListModelBuilder;
import org.tmdmaker.core.model.generate.attributelist.EntityAttributePair;
import org.tmdmaker.csv.Messages;
import org.tmdmaker.model.generate.Generator;

import com.opencsv.CSVWriter;



/**
 * アトリビュートリストをCSVで出力するクラス
 * 
 * @author nakaG
 * 
 */
public class AttributeListCsvGenerator implements Generator {
	/** logging */
	private static Logger logger = LoggerFactory
			.getLogger(AttributeListCsvGenerator.class);

	private static final String[] TITLES = {
			Messages.getString("AttributeListCsvGenerator.AttributeName"), //$NON-NLS-1$
			Messages.getString("AttributeListCsvGenerator.Entity"), //$NON-NLS-1$
			Messages.getString("AttributeListCsvGenerator.Summary"), //$NON-NLS-1$
			Messages.getString("AttributeListCsvGenerator.Premise"), //$NON-NLS-1$
			Messages.getString("AttributeListCsvGenerator.Confidentiality"), //$NON-NLS-1$
			Messages.getString("AttributeListCsvGenerator.Formula") //$NON-NLS-1$
	};

	@Override
	public String getGroupName() {
		return "CSV"; //$NON-NLS-1$
	}

	@Override
	public String getGeneratorName() {
		return Messages.getString("AttributeListCsvGenerator.GeneratorName"); //$NON-NLS-1$
	}

	@Override
	public boolean isImplementModelOnly() {
		return false;
	}

	@Override
	public void execute(String rootDir, List<AbstractEntityModel> models) {
		if (models.isEmpty()) {
			return;
		}
		Map<String, EntityAttributePair> attributes = new AttributeListModelBuilder().build(models);
		File file = new File(rootDir, "attribute_list.csv"); //$NON-NLS-1$
		CSVWriter writer = null;
		try {
			writer = new CSVWriter(new FileWriter(file));
			writer.writeNext(TITLES);
			String[] rowData = new String[TITLES.length];
			for (EntityAttributePair pair : attributes.values()) {
				IAttribute attribute =  pair.getAttribute();
				rowData[0] = attribute.getName();
				rowData[1] = pair.getModel().getName();
				rowData[2] = attribute.getDescription();
				rowData[3] = attribute.getValidationRule();
				rowData[4] = attribute.getLock();
				rowData[5] = attribute.getDerivationRule();
				writer.writeNext(rowData);
			}
			writer.close();
		} catch (IOException e) {
			logger.error(e.getMessage());
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					logger.warn(e.getMessage());
				}
			}
		}
	}
}

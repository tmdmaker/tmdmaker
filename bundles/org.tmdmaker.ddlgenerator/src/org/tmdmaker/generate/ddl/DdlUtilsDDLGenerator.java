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
package org.tmdmaker.generate.ddl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.ddlutils.Platform;
import org.apache.ddlutils.PlatformFactory;
import org.apache.ddlutils.model.Database;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tmdmaker.core.model.AbstractEntityModel;
import org.tmdmaker.core.model.Diagram;
import org.tmdmaker.core.model.rule.ImplementRule;
import org.tmdmaker.generate.ddl.internal.DdlUtilsConverter;
import org.tmdmaker.model.generate.Generator;
import org.tmdmaker.model.generate.GeneratorRuntimeException;

/**
 * DdlUtilsを使ったDDLGenerator
 * 
 * @author nakaG
 * 
 */
public class DdlUtilsDDLGenerator implements Generator {
	/** logging */
	private static Logger logger = LoggerFactory
			.getLogger(DdlUtilsDDLGenerator.class);

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.tmdmaker.model.generate.Generator#execute(java.lang.String,
	 *      java.util.List)
	 */
	@Override
	public void execute(String rootDir, List<AbstractEntityModel> models) {
		if (models.isEmpty()) {
			return;
		}
		Diagram diagram = models.get(0).getDiagram();

		String databaseName = diagram.getDatabaseName();
		if (databaseName == null || databaseName.length() == 0) {
			throw new DatabaseNotSelectRuntimeException();
		}
		DdlUtilsConverter converter = new DdlUtilsConverter(ImplementRule.isForeignKeyEnabled());
		Database database = converter.convert(diagram, models);
		converter.addCommonColumns(database, diagram.getCommonAttributes());

		Platform platform = PlatformFactory
				.createNewPlatformInstance(databaseName);
		String sql = platform.getCreateModelSql(database, true, true);
		logger.debug(sql);

		writeSqlFile(rootDir, "ddl.sql", sql); //$NON-NLS-1$

	}

	/**
	 * SQLをファイルへ出力する。
	 * 
	 * @param rootDir
	 *            出力先ディレクトリ
	 * @param fileName
	 *            出力ファイル名
	 * @param sql
	 *            出力するSQL
	 */
	private void writeSqlFile(String rootDir, String fileName, String sql) {
		File file = new File(rootDir, fileName);
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(file);
			out.write(sql.getBytes("UTF-8")); //$NON-NLS-1$
		} catch (FileNotFoundException e) {
			logger.error(Messages.DdlUtilsDDLGenerator_FileNotFound, e);
			throw new GeneratorRuntimeException(e);
		} catch (IOException e) {
			logger.error(Messages.DdlUtilsDDLGenerator_FileOutputError, e);
			throw new GeneratorRuntimeException(e);
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				logger.warn(e.getMessage(), e);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.tmdmaker.model.generate.Generator#getGeneratorName()
	 */
	@Override
	public String getGeneratorName() {

		return Messages.DdlUtilsDDLGenerator_GeneratorName;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.tmdmaker.model.generate.Generator#getGroupName()
	 */
	@Override
	public String getGroupName() {
		return "DDL"; //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.tmdmaker.model.generate.Generator#isImplementModelOnly()
	 */
	@Override
	public boolean isImplementModelOnly() {
		return true;
	}

}

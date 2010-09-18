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
package jp.sourceforge.tmdmaker.generate;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.Attribute;
import jp.sourceforge.tmdmaker.model.DataTypeDeclaration;
import jp.sourceforge.tmdmaker.model.Diagram;
import jp.sourceforge.tmdmaker.model.KeyModel;
import jp.sourceforge.tmdmaker.model.ModelElement;
import jp.sourceforge.tmdmaker.model.StandardSQLDataType;
import jp.sourceforge.tmdmaker.model.rule.ImplementRule;

import org.apache.ddlutils.Platform;
import org.apache.ddlutils.PlatformFactory;
import org.apache.ddlutils.model.Column;
import org.apache.ddlutils.model.Database;
import org.apache.ddlutils.model.Index;
import org.apache.ddlutils.model.IndexColumn;
import org.apache.ddlutils.model.NonUniqueIndex;
import org.apache.ddlutils.model.Table;
import org.apache.ddlutils.model.UniqueIndex;

/**
 * DdlUtilsを使ったDDLGenerator
 * 
 * @author nakaG
 * 
 */
public class DdlUtilsDDLGenerator implements Generator {

	/**
	 * コンストラクタ
	 */
	public DdlUtilsDDLGenerator() {
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.generate.Generator#execute(java.lang.String,
	 *      java.util.List)
	 */
	@Override
	public void execute(String rootDir, List<AbstractEntityModel> models) {
		assert models.size() != 0;
		Diagram diagram = models.get(0).getDiagram();

		String databaseName = diagram.getDatabaseName();
		if (databaseName == null || databaseName.length() == 0) {
			throw new DatabaseNotSelectRuntimeException();
		}
		Database database = convert(diagram, models);
		Platform platform = PlatformFactory
				.createNewPlatformInstance(databaseName);
		String sql = platform.getCreateModelSql(database, true, true);
		System.out.println(sql);
		File file = new File(rootDir, "ddl.sql");
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(file);
			out.write(sql.getBytes("UTF-8"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.generate.Generator#getGeneratorName()
	 */
	@Override
	public String getGeneratorName() {

		return "DDLを出力";
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.generate.Generator#getGroupName()
	 */
	@Override
	public String getGroupName() {
		return "DDL";
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.generate.Generator#isImplementModelOnly()
	 */
	@Override
	public boolean isImplementModelOnly() {
		return true;
	}

	/**
	 * TMD-MakerのモデルをDDLUtilsのデータベースモデルへ変換する
	 * 
	 * @param diagram
	 *            TMD-Makerのルートモデル
	 * @return DDLUtilsのルートモデル
	 */
	private Database convert(Diagram diagram, List<AbstractEntityModel> models) {
		Database database = new Database();
		database.setName(diagram.getName());

		for (AbstractEntityModel model : models) {
			addModel(database, model);
		}
		return database;
	}

	/**
	 * TMD-MakerのモデルをDDLUtilsのテーブルモデルとして追加する
	 * 
	 * @param database
	 *            DDLUtilsのルートモデル
	 * @param model
	 *            TMD-Makerのモデル
	 */
	private void addModel(Database database, ModelElement model) {
		if (model instanceof AbstractEntityModel) {
			AbstractEntityModel entity = (AbstractEntityModel) model;
			if (!entity.isNotImplement()) {
				database.addTable(convert(entity));
			}
		}
	}

	/**
	 * TMD-MakerのモデルをDDLUtilsのテーブルモデルへ変換する
	 * 
	 * @param entity
	 *            TMD-Makerのモデル
	 * @return DDLUtilsのテーブルモデル
	 */
	private Table convert(AbstractEntityModel entity) {
		// テーブル名を指定
		Table table = new Table();
		table.setName(entity.getImplementName());

		// 実装対象のアトリビュートをカラムとして追加
		List<Attribute> attributes = ImplementRule
				.findAllImplementAttributes(entity);
		Map<Attribute, Column> attributeColumnMap = new HashMap<Attribute, Column>();
		for (Attribute a : attributes) {
			Column column = convert(a);
			table.addColumn(column);
			attributeColumnMap.put(a, column);
		}

		// キーをインデックスとして追加
		for (KeyModel idx : entity.getKeyModels()) {
			table.addIndex(convert(idx, attributeColumnMap));
		}

		return table;

	}

	/**
	 * TMD-MakerのキーモデルをDDLUtilsのインデックスモデルへ変換する
	 * 
	 * @param key
	 *            TMD-Makerのアトリビュートモデル
	 * @return DDLUtilsのインデックスモデル
	 */
	private Index convert(KeyModel key,
			Map<Attribute, Column> attributeColumnMap) {
		Index index = null;
		if (key.isUnique()) {
			index = new UniqueIndex();
		} else {
			index = new NonUniqueIndex();
		}
		index.setName(key.getName());

		for (Attribute attr : key.getAttributes()) {
			Column column = attributeColumnMap.get(attr);
			if (column != null) {
				IndexColumn indexColumn = new IndexColumn(column);
				index.addColumn(indexColumn);
			} else {
				System.err.println("column not found." + attr.getName());
			}
		}

		return index;
	}

	/**
	 * TMD-MakerのアトリビュートモデルをDDLUtilsのカラムモデルへ変換する
	 * 
	 * @param entity
	 *            TMD-Makerのアトリビュートモデル
	 * @return DDLUtilsのカラムモデル
	 */
	private Column convert(Attribute attribute) {
		Column column = new Column();
		column.setName(attribute.getImplementName());
		DataTypeDeclaration dtd = attribute.getDataTypeDeclaration();
		if (dtd != null) {
			StandardSQLDataType dataType = dtd.getLogicalType();
			column.setTypeCode(dataType.getSqlType());
			if (dataType.isSupportSize() && dtd.getSize() != null) {
				column.setSize(dtd.getSize().toString());
			}
			if (dataType.isSupportScale() && dtd.getScale() != null) {
				column.setScale(dtd.getScale().intValue());
			}
		}
		column.setRequired(!attribute.isNullable());
		return column;
	}
}

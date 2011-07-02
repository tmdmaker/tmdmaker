/*
 * Copyright 2009-2011 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.DataTypeDeclaration;
import jp.sourceforge.tmdmaker.model.Diagram;
import jp.sourceforge.tmdmaker.model.IAttribute;
import jp.sourceforge.tmdmaker.model.KeyModel;
import jp.sourceforge.tmdmaker.model.ModelElement;
import jp.sourceforge.tmdmaker.model.StandardSQLDataType;
import jp.sourceforge.tmdmaker.model.rule.ImplementRule;

import org.apache.ddlutils.model.Column;
import org.apache.ddlutils.model.Database;
import org.apache.ddlutils.model.Index;
import org.apache.ddlutils.model.IndexColumn;
import org.apache.ddlutils.model.NonUniqueIndex;
import org.apache.ddlutils.model.Table;
import org.apache.ddlutils.model.UniqueIndex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TMのモデルをDdlUtilsのモデルへ変換するクラス
 * 
 * @author nakaG
 * 
 */
public class DdlUtilsConverter {
	/** logging */
	private static Logger logger = LoggerFactory
			.getLogger(DdlUtilsConverter.class);

	/**
	 * TMD-MakerのモデルをDDLUtilsのデータベースモデルへ変換する
	 * 
	 * @param diagram
	 *            TMD-Makerのルートモデル
	 * @return DDLUtilsのルートモデル
	 */
	public Database convert(Diagram diagram, List<AbstractEntityModel> models) {
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
		List<IAttribute> attributes = ImplementRule
				.findAllImplementAttributes(entity);
		Map<IAttribute, Column> attributeColumnMap = new HashMap<IAttribute, Column>();
		for (IAttribute a : attributes) {
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
			Map<IAttribute, Column> attributeColumnMap) {
		Index index = null;
		if (key.isUnique()) {
			index = new UniqueIndex();
		} else {
			index = new NonUniqueIndex();
		}
		index.setName(key.getName());

		for (IAttribute attr : key.getAttributes()) {
			Column column = attributeColumnMap.get(attr);
			if (column != null) {
				IndexColumn indexColumn = new IndexColumn(column);
				index.addColumn(indexColumn);
			} else {
				logger.error("column not found." + attr.getName());
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
	private Column convert(IAttribute attribute) {
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

	/**
	 * 全てのテーブルへ共通属性を追加する。
	 * 
	 * @param database
	 *            全テーブルを保持するデータベースモデル
	 * @param commonAttributes
	 *            共通属性
	 */
	public void addCommonColumns(Database database,
			List<IAttribute> commonAttributes) {

		for (Table t : database.getTables()) {
			addCommonColumns(t, commonAttributes);
		}

	}

	/**
	 * 共通属性をカラムへ変換してテーブルへ追加する。
	 * 
	 * @param t
	 *            テーブル
	 * @param commonAttributes
	 *            共通属性
	 */
	private void addCommonColumns(Table t, List<IAttribute> commonAttributes) {

		for (IAttribute a : commonAttributes) {
			t.addColumn(convert(a));
		}
	}

}

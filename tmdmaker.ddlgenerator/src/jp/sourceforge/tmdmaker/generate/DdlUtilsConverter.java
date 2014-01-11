/*
 * Copyright 2009-2013 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.DataTypeDeclaration;
import jp.sourceforge.tmdmaker.model.Diagram;
import jp.sourceforge.tmdmaker.model.IAttribute;
import jp.sourceforge.tmdmaker.model.IdentifierRef;
import jp.sourceforge.tmdmaker.model.KeyModel;
import jp.sourceforge.tmdmaker.model.ModelElement;
import jp.sourceforge.tmdmaker.model.ReusedIdentifier;
import jp.sourceforge.tmdmaker.model.SarogateKeyRef;
import jp.sourceforge.tmdmaker.model.StandardSQLDataType;
import jp.sourceforge.tmdmaker.model.rule.ImplementRule;

import org.apache.ddlutils.model.Column;
import org.apache.ddlutils.model.Database;
import org.apache.ddlutils.model.ForeignKey;
import org.apache.ddlutils.model.Index;
import org.apache.ddlutils.model.IndexColumn;
import org.apache.ddlutils.model.NonUniqueIndex;
import org.apache.ddlutils.model.Reference;
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
	/** 外部キーのテーブル */
	private Map<Table, Map<String, List<Reference>>> foreignTables;

	/** 外部キーを出力するか */
	private boolean foreignKeyEnabled;

	/**
	 * コンストラクタ
	 */
	public DdlUtilsConverter() {
		this(false);
	}

	/**
	 * コンストラクタ
	 * 
	 * @param foreignKeyEnabled
	 *            外部キーを出力する場合trueを渡す。
	 */
	public DdlUtilsConverter(boolean foreignKeyEnabled) {
		foreignTables = new HashMap<Table, Map<String, List<Reference>>>();
		this.foreignKeyEnabled = foreignKeyEnabled;
	}

	/**
	 * TMD-MakerのモデルをDDLUtilsのデータベースモデルへ変換する
	 * 
	 * @param diagram
	 *            TMD-Makerのルートモデル
	 * @return DDLUtilsのルートモデル
	 */
	public Database convert(Diagram diagram, List<AbstractEntityModel> models) {
		Database database = createDatabase(diagram);

		addModels(database, models);

		if (foreignKeyEnabled) {
			addForeignKeys(database);
		}
		return database;
	}

	private Database createDatabase(Diagram diagram) {
		Database database = new Database();
		database.setName(diagram.getName());
		return database;
	}

	private void addModels(Database database, List<AbstractEntityModel> models) {
		for (AbstractEntityModel model : models) {
			addModel(database, model);
		}
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
		if (!(model instanceof AbstractEntityModel)) {
			return;
		}
		AbstractEntityModel entity = (AbstractEntityModel) model;
		if (entity.isNotImplement()) {
			return;
		}
		database.addTable(convert(entity));
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
		table.setDescription(entity.getName());

		// 実装対象のアトリビュートをカラムとして追加
		Map<IAttribute, Column> attributeColumnMap = new HashMap<IAttribute, Column>();
		addColumns(entity, table, attributeColumnMap);

		// キーをインデックスとして追加
		addIndices(entity, table, attributeColumnMap);

		/*
		 * テーブル名 -> 参照テーブル名 -> リファレンス のリストを作成する。 あとでループして各テーブルで 外部キーを作成して追加する。
		 */
		setupForeignTables(entity, table);

		return table;
	}

	/**
	 * カラムを追加する
	 * 
	 * @param entity
	 *            対象エンティティ
	 * @param table
	 *            対象テーブル
	 * @param attributeColumnMap
	 *            アトリビュートとカラムのマップ
	 */
	private void addColumns(AbstractEntityModel entity, Table table,
			Map<IAttribute, Column> attributeColumnMap) {
		List<IAttribute> attributes = ImplementRule
				.findAllImplementAttributes(entity);
		for (IAttribute a : attributes) {
			Column column = convert(a);
			table.addColumn(column);
			attributeColumnMap.put(a, column);
		}
	}

	private List<String> recursiveTables = new ArrayList<String>();

	/**
	 * 外部キーテーブルを初期化する
	 * 
	 * @param entity
	 *            対象モデル
	 * @param table
	 *            対象テーブル
	 */
	private void setupForeignTables(AbstractEntityModel entity, Table table) {

		Map<String, List<Reference>> foreinReferences = new HashMap<String, List<Reference>>();

		for (Map.Entry<AbstractEntityModel, ReusedIdentifier> reusedMap : entity
				.getReusedIdentifieres().entrySet()) {

			AbstractEntityModel foreignEntity = reusedMap.getKey();
			ReusedIdentifier reused = reusedMap.getValue();

			// Reused でサロゲートキーが2つあるのは再帰のときのみ。
			int count = reused.getSarogateKeys().size();
			if (count == 2) {
				if (!recursiveTables.contains(entity.getImplementName())) {
					recursiveTables.add(entity.getImplementName());
				}
			}

			foreinReferences.put(foreignEntity.getImplementName(), convert(reused));
		}

		foreignTables.put(table, foreinReferences);
	}

	/**
	 * 
	 * Re-Used 列を DdlUtils の Reference のリストに変換する
	 * 
	 */
	private List<Reference> convert(ReusedIdentifier reused) {
		List<Reference> refences = new ArrayList<Reference>();

		if (reused.isSarogateKeyEnabled()) {
			for (SarogateKeyRef sref : reused.getSarogateKeys()) {
				Column localColumn = convert(sref);
				Column originalColumn = convert(sref.getOriginal());
				addReference(refences, localColumn, originalColumn);
			}
		} else {
			for (IdentifierRef iref : reused.getIdentifires()) {
				Column localColumn = convert(iref);
				Column originalColumn = convert(iref.getOriginal());
				addReference(refences, localColumn, originalColumn);
			}
		}
		return refences;
	}

	private void addReference(List<Reference> refences, Column localColumn,
			Column originalColumn) {
		Reference reference = new Reference(localColumn, originalColumn);
		refences.add(reference);
		logger.debug("参照： " + localColumn.getName() + "->"
				+ originalColumn.getName());
	}

	/**
	 * 外部キー制約を設定する
	 * 
	 * 再帰表とその他のテーブルでは外部キーの指定の仕方が異なる
	 * 
	 * @param database
	 *            対象データベース
	 */
	private void addForeignKeys(Database database) {

		for (Map.Entry<Table, Map<String, List<Reference>>> foreignRefrences : foreignTables
				.entrySet()) {
			Table table = foreignRefrences.getKey();

			for (Map.Entry<String, List<Reference>> foreignmap : foreignRefrences
					.getValue().entrySet()) {
				Table foreignTable = database.findTable(foreignmap.getKey());

				if (foreignTable == null)
					continue;

				if (recursiveTables.contains(table.getName())) {

					addRecursiveForeignKey(table, foreignTable,
							foreignmap.getValue());
				} else {
					addForeignKey(table, foreignTable, foreignmap.getValue());
				}
			}
		}
	}

	/*
	 * 再帰表以外の外部キー設定
	 */
	private void addForeignKey(Table table, Table foreignTable,
			List<Reference> references) {
		ForeignKey foreignKey = new ForeignKey("FK_" + foreignTable.getName());

		for (Reference ref : references) {
			foreignKey.addReference(ref);
		}
		foreignKey.setForeignTable(foreignTable);
		table.addForeignKey(foreignKey);
	}

	/*
	 * 再帰表の外部キー設定
	 */
	private void addRecursiveForeignKey(Table table, Table foreignTable,
			List<Reference> references) {
		Integer idx = 0;
		for (Reference ref : references) {
			idx += 1;
			ForeignKey foreignKey = new ForeignKey("FK_"
					+ foreignTable.getName() + idx.toString());
			foreignKey.addReference(ref);
			foreignKey.setForeignTable(foreignTable);
			table.addForeignKey(foreignKey);
		}
	}

	/**
	 * インデックスを追加する
	 * 
	 * @param entity
	 *            対象エンティティ
	 * @param table
	 *            対象テーブル
	 * @param attributeColumnMap
	 *            アトリビュートとカラムのマップ
	 */
	private void addIndices(AbstractEntityModel entity, Table table,
			Map<IAttribute, Column> attributeColumnMap) {
		for (KeyModel idx : entity.getKeyModels()) {

			// マスターキーはプライマリキーとして登録する
			if (idx.isMasterKey()) {
				markPrimaryKeys(table, idx);
			} else {
				table.addIndex(convert(idx, attributeColumnMap));
			}
		}
	}

	/**
	 * マスターキーのカラムにプライマリキーに設定する
	 * 
	 * @param table
	 *            対象テーブル
	 * @param idx
	 *            マスターキー
	 */
	private void markPrimaryKeys(Table table, KeyModel idx) {
		for (IAttribute a : idx.getAttributes()) {
			Column c = table.findColumn(a.getImplementName());
			if (c != null) {
				c.setPrimaryKey(true);
			}
		}
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
		if (commonAttributes == null) {
			return;
		}
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

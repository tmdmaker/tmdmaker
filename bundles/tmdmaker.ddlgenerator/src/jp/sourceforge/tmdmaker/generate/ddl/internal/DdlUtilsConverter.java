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
package jp.sourceforge.tmdmaker.generate.ddl.internal;

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
import jp.sourceforge.tmdmaker.model.SurrogateKeyRef;
import jp.sourceforge.tmdmaker.model.StandardSQLDataType;
import jp.sourceforge.tmdmaker.model.rule.ImplementRule;

import org.apache.commons.lang.StringUtils;
import org.apache.ddlutils.model.Column;
import org.apache.ddlutils.model.Database;
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
	private static Logger logger = LoggerFactory.getLogger(DdlUtilsConverter.class);

	/** 外部キーのテーブル */
	private List<ForeignConstraints> foreignConstraintsList;

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
		this.foreignConstraintsList = new ArrayList<ForeignConstraints>();
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
			for (ForeignConstraints foreignConstraints : foreignConstraintsList) {
				foreignConstraints.addForeignKeys(database);
			}
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
		String tableName = entity.getImplementName();
		if (tableName == null || tableName.length() == 0) {
			tableName = entity.getName();
		}
		table.setName(tableName);
		table.setDescription(entity.getName());

		// 実装対象のアトリビュートをカラムとして追加
		Map<IAttribute, Column> attributeColumnMap = new HashMap<IAttribute, Column>();
		addColumns(entity, table, attributeColumnMap);

		// キーをインデックスとして追加
		addIndices(entity, table, attributeColumnMap);

		/*
		 * テーブル名 -> 参照テーブル名 -> リファレンス のリストを作成する。 あとでループして各テーブルで 外部キーを作成して追加する。
		 */
		this.foreignConstraintsList.add(createForeignConstraints(entity, table));

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
	private void addColumns(AbstractEntityModel entity, Table table, Map<IAttribute, Column> attributeColumnMap) {
		List<IAttribute> attributes = ImplementRule.findAllImplementAttributes(entity);
		for (IAttribute a : attributes) {
			Column column = convert(a);
			table.addColumn(column);
			attributeColumnMap.put(a, column);
		}
	}

	/**
	 * 外部キーテーブルを生成する
	 * 
	 * @param entity
	 *            対象モデル
	 * @param table
	 *            対象テーブル
	 */
	private ForeignConstraints createForeignConstraints(AbstractEntityModel entity, Table table) {
		logger.trace("createForeignConstraints {} {}", entity.getName(), table.getName());
		ForeignConstraints foreignConstraints = new ForeignConstraints(table);

		for (Map.Entry<AbstractEntityModel, ReusedIdentifier> reusedMap : entity.getReusedIdentifiers().entrySet()) {

			AbstractEntityModel foreignEntity = reusedMap.getKey();
			ReusedIdentifier reused = reusedMap.getValue();

			foreignConstraints.addForeignReference(foreignEntity.getImplementName(), convert(reused),
					isRecursive(reused));
		}
		return foreignConstraints;
	}

	private Boolean isRecursive(ReusedIdentifier reused) {
		// Reused でサロゲートキーが2つあるのは再帰のときのみ。
		int count = reused.getSurrogateKeys().size();
		return count == 2;
	}

	/**
	 * 
	 * Re-Used 列を DdlUtils の Reference のリストに変換する
	 * 
	 */
	private List<Reference> convert(ReusedIdentifier reused) {
		List<Reference> refences = new ArrayList<Reference>();

		if (reused.isSurrogateKeyEnabled()) {
			// 再帰表の場合を考慮して1つ目のみを取得
			SurrogateKeyRef sref = reused.getSurrogateKeys().get(0);
			Column localColumn = convert(sref);
			Column originalColumn = convert(sref.getOriginal());
			addReference(refences, localColumn, originalColumn);
			return refences;
		} else {
			List<IdentifierRef> list = reused.getUniqueIdentifiers();
			int reusedCount = list.size();
			// 再帰表は同一Reused×2となっているため1つ目のみを取得する
			if (isRecursive(reused)) {
				reusedCount = reusedCount / 2;
			}

			for (int i = 0; i < reusedCount; i++) {
				IdentifierRef iref = list.get(i);
				Column localColumn = convert(iref);
				Column originalColumn = convert(iref.getOriginal());
				addReference(refences, localColumn, originalColumn);
			}
			return refences;
		}
	}

	private void addReference(List<Reference> refences, Column localColumn, Column originalColumn) {
		Reference reference = new Reference(localColumn, originalColumn);
		refences.add(reference);
		logger.debug("参照： {}->{}",localColumn.getName(), originalColumn.getName());
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
	private void addIndices(AbstractEntityModel entity, Table table, Map<IAttribute, Column> attributeColumnMap) {
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
	private Index convert(KeyModel key, Map<IAttribute, Column> attributeColumnMap) {
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
				logger.error("column not found.{}", attr.getName());
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
		String columnName = attribute.getImplementName();
		if (columnName == null || columnName.length() == 0) {
			columnName = attribute.getName();
		}
		column.setName(columnName);
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
			if (dtd.getAutoIncrement() != null) {
				column.setAutoIncrement(dtd.getAutoIncrement());
			}
			String defaultValue = dtd.getDefaultValue();
			if (StringUtils.isNotEmpty(defaultValue)) {
				column.setDefaultValue(defaultValue);
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
	public void addCommonColumns(Database database, List<IAttribute> commonAttributes) {
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

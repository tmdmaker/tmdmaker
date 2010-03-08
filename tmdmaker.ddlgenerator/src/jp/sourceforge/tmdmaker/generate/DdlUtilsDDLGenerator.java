/**
 * 
 */
package jp.sourceforge.tmdmaker.generate;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.Attribute;
import jp.sourceforge.tmdmaker.model.DataTypeDeclaration;
import jp.sourceforge.tmdmaker.model.Detail;
import jp.sourceforge.tmdmaker.model.Diagram;
import jp.sourceforge.tmdmaker.model.Entity;
import jp.sourceforge.tmdmaker.model.IdentifierRef;
import jp.sourceforge.tmdmaker.model.ModelElement;
import jp.sourceforge.tmdmaker.model.ReusedIdentifier;
import jp.sourceforge.tmdmaker.model.StandardSQLDataType;

import org.apache.ddlutils.Platform;
import org.apache.ddlutils.PlatformFactory;
import org.apache.ddlutils.model.Column;
import org.apache.ddlutils.model.Database;
import org.apache.ddlutils.model.Table;

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
	 *      jp.sourceforge.tmdmaker.model.Diagram)
	 */
	@Override
	public void execute(String rootDir, Diagram diagram) {
		System.out.println(rootDir);

		Database database = convert(diagram);
		Platform platform = PlatformFactory.createNewPlatformInstance(diagram
				.getDatabaseName());
		String sql = platform.getCreateModelSql(database, true, true);
		System.out.println(sql);
		File file = new File(rootDir, "ddl.sql");
		try {
			FileOutputStream out = new FileOutputStream(file);
			out.write(sql.getBytes("UTF-8"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	 * TMD-MakerのモデルをDDLUtilsのデータベースモデルへ変換する
	 * 
	 * @param diagram
	 *            TMD-Makerのルートモデル
	 * @return DDLUtilsのルートモデル
	 */
	private Database convert(Diagram diagram) {
		Database database = new Database();
		database.setName(diagram.getName());

		for (ModelElement model : diagram.getChildren()) {
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
		Table table = new Table();
		table.setName(entity.getImplementName());
		// 個体指定子をカラムとして追加
		addIdentifierAsColumn(table, entity);

		// Re-usedをカラムとして追加
		Map<AbstractEntityModel, ReusedIdentifier> reused = entity
				.getReusedIdentifieres();
		for (Entry<AbstractEntityModel, ReusedIdentifier> entry : reused
				.entrySet()) {
			for (IdentifierRef ref : entry.getValue().getIdentifires()) {
				table.addColumn(convert(ref.getOriginal()));
			}
		}

		// アトリビュートをカラムとして追加
		for (Attribute attribute : entity.getAttributes()) {
			table.addColumn(convert(attribute));
		}
		return table;

	}

	/**
	 * 個体指定子をカラムへ変換してテーブルへ追加する
	 * 
	 * @param table
	 *            DDLUtilsのテーブルモデル
	 * @param model
	 *            TMD-Makerのモデル
	 */
	private void addIdentifierAsColumn(Table table, AbstractEntityModel model) {
		if (model instanceof Entity) {
			Entity entity = (Entity) model;
			table.addColumn(convert(entity.getIdentifier()));
		}
		if (model instanceof Detail) {
			Detail detail = (Detail) model;
			table.addColumn(convert(detail.getDetailIdentifier()));
		}
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

		return column;
	}
}

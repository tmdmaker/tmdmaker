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
package jp.sourceforge.tmdmaker.model.rule;

import jp.sourceforge.tmdmaker.model.Entity;
import jp.sourceforge.tmdmaker.model.EntityType;
import jp.sourceforge.tmdmaker.model.Identifier;
import jp.sourceforge.tmdmaker.model.Laputa;

/**
 * 個体の認知に関するルールをまとめたクラス
 * 
 * @author nakaG
 * 
 */
public class EntityRecognitionRule {
	/** デフォルトインスタンス */
	private static EntityRecognitionRule rule;
	/** 個体指定子サフィックスのデフォルト値 */
	public static final String[] DEFAULT_IDENTIFIER_SUFFIXES = { "ID", "ＩＤ",
			"id", "ｉｄ", "コード", "CD", "cd", "番号", "No", "NO", "no", "NUM",
			"Num", "num" };
	/** 帳票名サフィックスのデフォルト値 */
	public static final String[] DEFAULT_REPORT_SUFFIXES = { "伝票", "報告書", "書",
			"レポート" };
	/** サフィックスの区切り文字 */
	private static final String SEPARATOR = ",";
	/** 個体指定子サフィックスの配列 */
	private String[] identifierSuffixes = DEFAULT_IDENTIFIER_SUFFIXES;
	/** 帳票名サフィックスの配列 */
	private String[] reportSuffixes = DEFAULT_REPORT_SUFFIXES;

	/**
	 * ルールインスタンス取得
	 * 
	 * @return ルールインスタンス
	 */
	public static EntityRecognitionRule getInstance() {
		if (rule == null) {
			rule = new EntityRecognitionRule();
		}
		return rule;
	}

	/**
	 * 個体指定子の名称からエンティティの名称を生成する
	 * 
	 * @param identifierName
	 *            個体指定子名称
	 * @return 生成したエンティティ名称
	 */
	public String generateEntityNameFromIdentifier(String identifierName) {
		String entityName = removeIdentifierSuffixFromIdentifierName(identifierName);
		return removeReportNameSuffixFromEntityName(entityName);
	}

	/**
	 * 個体指定子名を表す文言を個体指定子名から取り除く
	 * 
	 * @param identifierName
	 *            個体指定子名
	 * @return 編集後個体指定子名
	 */
	private String removeIdentifierSuffixFromIdentifierName(
			String identifierName) {
		for (String suffix : identifierSuffixes) {
			if (identifierName.endsWith(suffix)) {
				return identifierName.substring(0,
						identifierName.lastIndexOf(suffix));
			}
		}
		return identifierName;
	}

	/**
	 * レポート名を表す文言をエンティティ名から取り除く
	 * 
	 * @param entityName
	 *            エンティティ名
	 * @return 編集後エンティティ名
	 */
	private String removeReportNameSuffixFromEntityName(String entityName) {
		for (String reportSuffix : reportSuffixes) {
			if (entityName.endsWith(reportSuffix)) {
				return entityName.substring(0,
						entityName.lastIndexOf(reportSuffix));
			}
		}
		return entityName;
	}

	/**
	 * エンティティのモデルを作成する
	 * 
	 * @param entityName
	 *            エンティティ名
	 * @param identifier
	 *            個体指定子
	 * @param entityType
	 *            エンティティ種類
	 * @return 新規エンティティモデル
	 */
	public Entity createEntity(String entityName, Identifier identifier,
			EntityType entityType) {
		Entity entity = new Entity();
		entity.setName(entityName);
		entity.setEntityType(entityType);
		entity.setIdentifier(identifier);
		EntityTypeRule.addDefaultAttribute(entity);
		ImplementRule.setModelDefaultValue(entity);
		ImplementRule.setIdentifierDefaultValue(entity.getIdentifier());

		return entity;
	}

	/**
	 * ラピュタのモデルを作成する
	 * 
	 * @param entityName
	 *            エンティティ名
	 * @param identifier
	 *            個体指定子
	 * @return 新規ラピュタモデル
	 */
	public Laputa createLaputa(String entityName, Identifier identifier) {
		Laputa laputa = new Laputa();
		if (entityName != null && entityName.length() > 0) {
			laputa.setName(entityName);
		} else {
			laputa.setName("ラピュタ");
		}
		if (identifier != null) {
			laputa.setIdentifier(identifier);
		} else {
			laputa.setIdentifier(new Identifier(entityName));
		}
		laputa.setEntityType(EntityType.LAPUTA);
		return laputa;
	}

	/**
	 * ラピュタのモデルを作成する
	 * 
	 * @param entityName
	 *            エンティティ名
	 * @return 新規ラピュタモデル
	 */
	public Laputa createLaputa(String entityName) {
		return createLaputa(entityName, null);
	}

	/**
	 * ラピュタのモデルを作成する
	 * 
	 * @return 新規ラピュタモデル
	 */
	public Laputa createLaputa() {
		return createLaputa(null, null);
	}

	/**
	 * @param suffixes
	 *            the suffixes to set
	 */
	public void setIdentifierSuffixes(String[] suffixes) {
		this.identifierSuffixes = suffixes;
	}

	/**
	 * @param reportSuffixes
	 *            the reportSuffixes to set
	 */
	public void setReportSuffixes(String[] reportSuffixes) {
		this.reportSuffixes = reportSuffixes;
	}

	/**
	 * 個体指定子サフィックスを文字列（区切り文字つき）で返す
	 * 
	 * @return 個体指定子サフィックスの文字列
	 */
	public String getIdentifierSuffixesString() {
		StringBuffer buf = new StringBuffer();
		for (String str : identifierSuffixes) {
			if (buf.length() > 0) {
				buf.append(SEPARATOR);
			}
			buf.append(str);
		}
		return buf.toString();
	}

	/**
	 * 個体指定子サフィックスを文字列（区切り文字つき）を設定する
	 * 
	 * @param suffixes
	 *            　個体指定子サフィックスの文字列
	 */
	public void setIdentifierSuffixesString(String suffixes) {
		setIdentifierSuffixes(suffixes.split(SEPARATOR));
	}

	/**
	 * 帳票名サフィックスを文字列（区切り文字つき）で返す
	 * 
	 * @return 帳票名サフィックスの文字列
	 */
	public String getReportSuffixesString() {
		StringBuffer buf = new StringBuffer();
		for (String str : reportSuffixes) {
			if (buf.length() > 0) {
				buf.append(SEPARATOR);
			}
			buf.append(str);
		}
		return buf.toString();
	}

	/**
	 * 帳票名サフィックスを文字列（区切り文字つき）を設定する
	 * 
	 * @param suffixes
	 *            　帳票名サフィックスの文字列
	 */
	public void setReportSuffixesString(String suffixes) {
		setReportSuffixes(suffixes.split(SEPARATOR));
	}
}

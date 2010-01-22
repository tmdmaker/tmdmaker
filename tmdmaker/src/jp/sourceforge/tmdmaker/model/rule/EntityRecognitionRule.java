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
package jp.sourceforge.tmdmaker.model.rule;

/**
 * 個体の認知に関するルールをまとめたクラス
 * 
 * @author nakaG
 * 
 */
public class EntityRecognitionRule {
	/**
	 * 個体指定子の名称からエンティティの名称を生成する
	 * 
	 * @param identifierName
	 *            個体指定子名称
	 * @return 生成したエンティティ名称
	 */
	public static String generateEntityNameFromIdentifier(String identifierName) {
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
	private static String removeIdentifierSuffixFromIdentifierName(
			String identifierName) {
		String[] suffixes = { "コード", "ID", "ＩＤ", "id", "ｉｄ", "番号", "No" };
		for (String suffix : suffixes) {
			if (identifierName.endsWith(suffix)) {
				return identifierName.substring(0, identifierName
						.lastIndexOf(suffix));
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
	private static String removeReportNameSuffixFromEntityName(String entityName) {
		String[] reportSuffixes = { "伝票", "報告書", "書", "レポート" };
		for (String reportSuffix : reportSuffixes) {
			if (entityName.endsWith(reportSuffix)) {
				return entityName.substring(0, entityName
						.lastIndexOf(reportSuffix));
			}
		}
		return entityName;
	}
}

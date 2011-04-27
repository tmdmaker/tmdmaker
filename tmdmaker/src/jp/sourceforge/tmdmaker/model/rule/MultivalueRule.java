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
package jp.sourceforge.tmdmaker.model.rule;

import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.Attribute;
import jp.sourceforge.tmdmaker.model.DataTypeDeclaration;
import jp.sourceforge.tmdmaker.model.Detail;
import jp.sourceforge.tmdmaker.model.Identifier;
import jp.sourceforge.tmdmaker.model.MultivalueAndSuperset;
import jp.sourceforge.tmdmaker.model.MultivalueOrEntity;
import jp.sourceforge.tmdmaker.model.StandardSQLDataType;

/**
 * 多値に関するルールをまとめたクラス
 * 
 * @author nakaG
 * 
 */
public class MultivalueRule {
	/**
	 * 多値のORのモデルを作成する。
	 * 
	 * @param source
	 *            派生元のモデル
	 * @param typeName
	 *            種別名
	 * @return 多値のORのモデル
	 */
	public static MultivalueOrEntity createMultivalueOrEntity(
			AbstractEntityModel source, String typeName) {
		MultivalueOrEntity target = new MultivalueOrEntity();
		target.setConstraint(source.getConstraint().getTranslated(50, 0));
		target.setName(source.getName() + "." + typeName);
		target.setEntityType(source.getEntityType());
		target.addAttribute(createTypeCode(typeName));

		return target;
	}

	/**
	 * 多値のORの種別コードを作成する。
	 * 
	 * @param typeName
	 *            種別名
	 * @return 種別コードのアトリビュート
	 */
	private static Attribute createTypeCode(String typeName) {
		Attribute attribute = new Attribute();
		attribute.setName(typeName + "コード");
		attribute.setImplementName(attribute.getName());
		attribute.setDataTypeDeclaration(new DataTypeDeclaration(
				StandardSQLDataType.SMALLINT, null, null));

		return attribute;
	}

	/**
	 * 多値のANDのDetailを作成する。
	 * 
	 * @param header
	 *            派生元のモデル
	 * @return 多値のANDのDetail
	 */
	public static Detail createDetail(AbstractEntityModel header) {
		Detail detail = new Detail();
		detail.setName(header.getName() + "DTL");
		detail.setEntityType(header.getEntityType());
		detail.setConstraint(header.getConstraint().getTranslated(100, 0));
		detail.setOriginalReusedIdentifier(header.createReusedIdentifier());
		detail.getDetailIdentifier().copyFrom(
				createDetailIdentifier(header.getName()));

		return detail;
	}

	/**
	 * Detailの個体指定子を作成する
	 * 
	 * @param headerName
	 *            派生元のモデル名
	 * @return Detailの個体指定子
	 */
	private static Identifier createDetailIdentifier(String headerName) {
		Identifier id = new Identifier(headerName + "明細番号");
		ImplementRule.setIdentifierDefaultValue(id);

		return id;
	}

	/**
	 * 多値のANDのSupersetを作成する。
	 * 
	 * @param header
	 *            派生元のモデル
	 * @return 多値のANDのSuperset
	 */
	public static MultivalueAndSuperset createMultivalueAndSuperset(
			AbstractEntityModel header) {
		MultivalueAndSuperset superset = new MultivalueAndSuperset();
		superset.setEntityType(header.getEntityType());
		superset.setConstraint(header.getConstraint().getTranslated(64, -80));
		superset.setName(header.getName());
		superset.addReusedIdentifier(header);

		return superset;
	}

	/**
	 * 多値のANDのHeaderの名称を作成する。
	 * 
	 * @param model
	 * @return
	 */
	public static String createHeaderName(AbstractEntityModel model) {
		return model.getName() + "HDR";
	}
}

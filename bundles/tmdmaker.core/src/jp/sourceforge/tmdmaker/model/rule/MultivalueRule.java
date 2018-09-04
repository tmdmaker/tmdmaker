/*
 * Copyright 2009-2017 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
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
	public static MultivalueOrEntity createMultivalueOrEntity(AbstractEntityModel source,
			String typeName) {
		MultivalueOrEntity target = new MultivalueOrEntity();
		target.setName(source.getName() + "." + typeName);
		target.setEntityType(source.getEntityType());
		target.addAttribute(createTypeCode(typeName));
		ImplementRule.setModelDefaultValue(target);
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
		attribute.setDataTypeDeclaration(
				new DataTypeDeclaration(StandardSQLDataType.SMALLINT, null, null));

		return attribute;
	}



}

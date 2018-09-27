/*
 * Copyright 2009-2018 TMD-Maker Project <https://tmdmaker.osdn.jp/>
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
package jp.sourceforge.tmdmaker.model;

import java.util.Map;

import jp.sourceforge.tmdmaker.model.rule.ImplementRule;

/**
 * 多値のOR
 * 
 * @author nakaG
 * 
 */
@SuppressWarnings("serial")
public class MultivalueOrEntity extends AbstractEntityModel {

	/**
	 * コンストラクタは公開しない.
	 */
	protected MultivalueOrEntity() {
	}

	/**
	 * 多値のORのモデルを作成する。
	 * 
	 * @param source
	 *            派生元のモデル
	 * @param typeName
	 *            種別名
	 * @return 多値のORのモデル
	 */
	protected static MultivalueOrEntity build(AbstractEntityModel source,
			String typeName) {
		MultivalueOrEntity target = new MultivalueOrEntity();
		if (typeName == null || typeName.isEmpty()) {
			typeName = "MO";
		}
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

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.AbstractEntityModel#isEntityTypeEditable()
	 */
	@Override
	public boolean isEntityTypeEditable() {
		return false;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.AbstractEntityModel#createReusedIdentifier()
	 */
	@Override
	public ReusedIdentifier createReusedIdentifier() {
		ReusedIdentifier returnValue = new ReusedIdentifier(keyModels.getSurrogateKey());
		for (Map.Entry<AbstractEntityModel, ReusedIdentifier> rk : this.reusedIdentifiers
				.entrySet()) {
			returnValue.addAll(rk.getValue().getIdentifiers());
		}
		return returnValue;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.AbstractEntityModel#isDeletable()
	 */
	@Override
	public boolean isDeletable() {
		return getModelSourceConnections().size() == 0 && getModelTargetConnections().size() == 1;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.AbstractEntityModel#getCopy()
	 */
	@Override
	public MultivalueOrEntity getCopy() {
		MultivalueOrEntity copy = new MultivalueOrEntity();
		copyTo(copy);
		return copy;
	}

	@Override
	public void accept(IVisitor visitor) {
		visitor.visit(this);
	}
}

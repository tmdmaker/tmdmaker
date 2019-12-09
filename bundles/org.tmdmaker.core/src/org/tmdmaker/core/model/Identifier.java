/*
 * Copyright 2009-2019 TMD-Maker Project <https://www.tmdmaker.org>
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
package org.tmdmaker.core.model;

import org.tmdmaker.core.Configuration;
import org.tmdmaker.core.model.parts.ModelName;

/**
 * 個体指定子モデル
 * 
 * @author nakaG
 * 
 */
@SuppressWarnings("serial")
public class Identifier extends Attribute {
	private AbstractEntityModel parent;

	/**
	 * コンストラクタ
	 * 
	 * @param name
	 *            個体指定子の名称
	 */
	public Identifier(String name) {
		super(name);
	}

	/**
	 * コンストラクタ
	 */
	public Identifier() {
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.tmdmaker.core.model.Attribute#getCopy()
	 */
	@Override
	public IAttribute getCopy() {
		Identifier copy = new Identifier();
		copyTo(copy);
		return copy;
	}

	public ModelName createEntityName() {
		return new ModelName(generateEntityName(getName()));
	}

	/**
	 * 個体指定子の名称からエンティティの名称を生成する
	 * 
	 * @param identifierName
	 *            個体指定子名称
	 * @return 生成したエンティティ名称
	 */
	private String generateEntityName(String identifierName) {
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
	private String removeIdentifierSuffixFromIdentifierName(String identifierName) {
		for (String suffix : Configuration.getDefault().getIdentifierSuffixes()) {
			if (identifierName.endsWith(suffix)) {
				return identifierName.substring(0, identifierName.lastIndexOf(suffix));
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
		for (String reportSuffix : Configuration.getDefault().getReportSuffixes()) {
			if (entityName.endsWith(reportSuffix)) {
				return entityName.substring(0, entityName.lastIndexOf(reportSuffix));
			}
		}
		return entityName;
	}

	@Override
	public void accept(IVisitor visitor) {
		visitor.visit(this);
	}

	AbstractEntityModel getParent() {
		return parent;
	}

	public void setParent(AbstractEntityModel parent) {
		this.parent = parent;
	}

	@Override
	public void setName(String name) {
		super.setName(name);
		if (parent != null) {
			this.parent.fireIdentifierChanged();
		}
	}
}

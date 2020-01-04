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

/**
 * アトリビュート系のモデルが持つべきインターフェース
 * 
 * @author nakaG
 * 
 */
public interface IAttribute {
	/**
	 * 
	 * @return the name
	 */
	String getName();

	/**
	 * 
	 * @param name
	 *            the name to set
	 */
	void setName(String name);

	/**
	 * @return the implementName
	 */
	String getImplementName();

	/**
	 * @param implementName
	 *            the implementName to set
	 */
	void setImplementName(String implementName);

	/**
	 * @return the description
	 */
	String getDescription();

	/**
	 * @param description
	 *            the description to set
	 */
	void setDescription(String description);

	/**
	 * @return the validationRule
	 */
	String getValidationRule();

	/**
	 * @param validationRule
	 *            the validationRule to set
	 */
	void setValidationRule(String validationRule);

	/**
	 * @return the lock
	 */
	String getLock();

	/**
	 * @param lock
	 *            the lock to set
	 */
	void setLock(String lock);

	/**
	 * @return the derivation
	 */
	boolean isDerivation();

	/**
	 * @param derivation
	 *            the derivation
	 */
	void setDerivation(boolean derivation);

	/**
	 * @return the derivationRule
	 */
	String getDerivationRule();

	/**
	 * @param derivationRule
	 *            the derivationRule to set
	 */
	void setDerivationRule(String derivationRule);

	/**
	 * @return the dataType
	 */
	DataTypeDeclaration getDataTypeDeclaration();

	/**
	 * @param dataType
	 *            the dataType to set
	 */
	void setDataTypeDeclaration(DataTypeDeclaration dataType);

	/**
	 * @return the nullable
	 */
	boolean isNullable();

	/**
	 * @param nullable
	 *            the nullable to set
	 */
	void setNullable(boolean nullable);

	/**
	 * fromから自身のフィールド値へコピー（sharrow copy)する。
	 * 
	 * @param from
	 */
	void copyFrom(IAttribute from);

	/**
	 * toへ自身のフィールド値をコピー（sharrow copy)する。
	 * 
	 * @param to
	 */
	void copyTo(IAttribute to);

	/**
	 * 自身のコピーを作成する
	 * 
	 * @return コピー
	 */
	IAttribute getCopy();

}
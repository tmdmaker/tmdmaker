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
package jp.sourceforge.tmdmaker.model;

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
	public abstract String getName();
	/**
	 * 
	 * @param name the name to set
	 */
	public abstract void setName(String name);
	/**
	 * @return the implementName
	 */
	public abstract String getImplementName();

	/**
	 * @param implementName
	 *            the implementName to set
	 */
	public abstract void setImplementName(String implementName);

	/**
	 * @return the description
	 */
	public abstract String getDescription();

	/**
	 * @param description
	 *            the description to set
	 */
	public abstract void setDescription(String description);

	/**
	 * @return the validationRule
	 */
	public abstract String getValidationRule();

	/**
	 * @param validationRule
	 *            the validationRule to set
	 */
	public abstract void setValidationRule(String validationRule);

	/**
	 * @return the lock
	 */
	public abstract String getLock();

	/**
	 * @param lock
	 *            the lock to set
	 */
	public abstract void setLock(String lock);

	/**
	 * @return the derivationRule
	 */
	public abstract String getDerivationRule();

	/**
	 * @param derivationRule
	 *            the derivationRule to set
	 */
	public abstract void setDerivationRule(String derivationRule);

	/**
	 * @return the dataType
	 */
	public abstract DataTypeDeclaration getDataTypeDeclaration();

	/**
	 * @param dataType
	 *            the dataType to set
	 */
	public abstract void setDataTypeDeclaration(DataTypeDeclaration dataType);

	/**
	 * @return the nullable
	 */
	public abstract boolean isNullable();

	/**
	 * @param nullable
	 *            the nullable to set
	 */
	public abstract void setNullable(boolean nullable);

	/**
	 * fromから自身のフィールド値へコピー（sharrow copy)する。
	 * 
	 * @param from
	 */
	public abstract void copyFrom(IAttribute from);

	/**
	 * toへ自身のフィールド値をコピー（sharrow copy)する。
	 * 
	 * @param to
	 */
	public abstract void copyTo(IAttribute to);

	/**
	 * 自身のコピーを作成する
	 * 
	 * @return コピー
	 */
	public abstract IAttribute getCopy();

}
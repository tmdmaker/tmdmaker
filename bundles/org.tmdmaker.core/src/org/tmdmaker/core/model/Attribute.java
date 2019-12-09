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
 * アトリビュート
 * 
 * @author nakaG
 * 
 */
@SuppressWarnings("serial")
public class Attribute extends ModelElement implements IAttribute {
	/** 摘要 */
	private String description = "";
	/** データ属性 */
	private DataTypeDeclaration dataTypeDeclaration;
	/** 前提 */
	private String validationRule;
	/** 機密性 */
	private String lock;
	/** 計算式 */
	private String derivationRule;
	private boolean derivation;
	/** 実装名 */
	protected String implementName;
	/** NULL許可 */
	private boolean nullable = false;

	/**
	 * コンストラクタ
	 */
	public Attribute() {
	}

	/**
	 * コンストラクタ
	 * 
	 * @param name
	 *            アトリビュートの名称
	 */
	public Attribute(String name) {
		setName(name);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.tmdmaker.core.model.IAttribute#getImplementName()
	 */
	public String getImplementName() {
		return implementName;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.tmdmaker.core.model.IAttribute#setImplementName(java.lang.String)
	 */
	public void setImplementName(String implementName) {
		this.implementName = implementName;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.tmdmaker.core.model.IAttribute#getDescription()
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.tmdmaker.core.model.IAttribute#setDescription(java.lang.String)
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.tmdmaker.core.model.IAttribute#getValidationRule()
	 */
	public String getValidationRule() {
		return validationRule;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.tmdmaker.core.model.IAttribute#setValidationRule(java.lang.String)
	 */
	public void setValidationRule(String validationRule) {
		this.validationRule = validationRule;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.tmdmaker.core.model.IAttribute#getLock()
	 */
	public String getLock() {
		return lock;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.tmdmaker.core.model.IAttribute#setLock(java.lang.String)
	 */
	public void setLock(String lock) {
		this.lock = lock;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.tmdmaker.core.model.IAttribute#isDerivation()
	 */
	@Override
	public boolean isDerivation() {
		return derivation;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.tmdmaker.core.model.IAttribute#setDerivation(boolean)
	 */
	@Override
	public void setDerivation(boolean derivation) {
		this.derivation = derivation;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.tmdmaker.core.model.IAttribute#getDerivationRule()
	 */
	public String getDerivationRule() {
		return derivationRule;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.tmdmaker.core.model.IAttribute#setDerivationRule(java.lang.String)
	 */
	public void setDerivationRule(String derivationRule) {
		this.derivationRule = derivationRule;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.tmdmaker.core.model.IAttribute#getDataTypeDeclaration()
	 */
	public DataTypeDeclaration getDataTypeDeclaration() {
		return dataTypeDeclaration;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.tmdmaker.core.model.IAttribute#setDataTypeDeclaration(org.tmdmaker.core.model.DataTypeDeclaration)
	 */
	public void setDataTypeDeclaration(DataTypeDeclaration dataType) {
		this.dataTypeDeclaration = dataType;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.tmdmaker.core.model.IAttribute#isNullable()
	 */
	public boolean isNullable() {
		return nullable;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.tmdmaker.core.model.IAttribute#setNullable(boolean)
	 */
	public void setNullable(boolean nullable) {
		this.nullable = nullable;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.tmdmaker.core.model.IAttribute#copyFrom(org.tmdmaker.core.model.IAttribute)
	 */
	public void copyFrom(IAttribute from) {
		this.setDerivationRule(from.getDerivationRule());
		this.setDerivation(from.isDerivation());
		this.setDescription(from.getDescription());
		this.setLock(from.getLock());
		this.setValidationRule(from.getValidationRule());
		DataTypeDeclaration dtd = from.getDataTypeDeclaration();
		if (dtd != null) {
			this.setDataTypeDeclaration(dtd.getCopy());
		} else {
			this.setDataTypeDeclaration(null);
		}
		this.setImplementName(from.getImplementName());
		this.setNullable(from.isNullable());
		this.setName(from.getName());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.tmdmaker.core.model.IAttribute#copyTo(org.tmdmaker.core.model.IAttribute)
	 */
	public void copyTo(IAttribute to) {
		to.setDerivationRule(derivationRule);
		to.setDerivation(derivation);
		to.setDescription(description);
		to.setLock(lock);
		to.setValidationRule(validationRule);
		if (dataTypeDeclaration != null) {
			to.setDataTypeDeclaration(dataTypeDeclaration.getCopy());
		} else {
			to.setDataTypeDeclaration(null);
		}
		to.setImplementName(implementName);
		to.setNullable(nullable);
		to.setName(getName());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.tmdmaker.core.model.IAttribute#getCopy()
	 */
	public IAttribute getCopy() {
		Attribute copy = new Attribute();
		copyTo(copy);
		return copy;
	}

	@Override
	public void accept(IVisitor visitor) {
		visitor.visit(this);
	}
}

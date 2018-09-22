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
	 * @see jp.sourceforge.tmdmaker.model.IAttribute#getImplementName()
	 */
	public String getImplementName() {
		return implementName;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.IAttribute#setImplementName(java.lang.String)
	 */
	public void setImplementName(String implementName) {
		this.implementName = implementName;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.IAttribute#getDescription()
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.IAttribute#setDescription(java.lang.String)
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.IAttribute#getValidationRule()
	 */
	public String getValidationRule() {
		return validationRule;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.IAttribute#setValidationRule(java.lang.String)
	 */
	public void setValidationRule(String validationRule) {
		this.validationRule = validationRule;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.IAttribute#getLock()
	 */
	public String getLock() {
		return lock;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.IAttribute#setLock(java.lang.String)
	 */
	public void setLock(String lock) {
		this.lock = lock;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.IAttribute#isDerivation()
	 */
	@Override
	public boolean isDerivation() {
		return derivation;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.IAttribute#setDerivation(boolean)
	 */
	@Override
	public void setDerivation(boolean derivation) {
		this.derivation = derivation;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.IAttribute#getDerivationRule()
	 */
	public String getDerivationRule() {
		return derivationRule;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.IAttribute#setDerivationRule(java.lang.String)
	 */
	public void setDerivationRule(String derivationRule) {
		this.derivationRule = derivationRule;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.IAttribute#getDataTypeDeclaration()
	 */
	public DataTypeDeclaration getDataTypeDeclaration() {
		return dataTypeDeclaration;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.IAttribute#setDataTypeDeclaration(jp.sourceforge.tmdmaker.model.DataTypeDeclaration)
	 */
	public void setDataTypeDeclaration(DataTypeDeclaration dataType) {
		this.dataTypeDeclaration = dataType;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.IAttribute#isNullable()
	 */
	public boolean isNullable() {
		return nullable;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.IAttribute#setNullable(boolean)
	 */
	public void setNullable(boolean nullable) {
		this.nullable = nullable;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.IAttribute#copyFrom(jp.sourceforge.tmdmaker.model.IAttribute)
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
	 * @see jp.sourceforge.tmdmaker.model.IAttribute#copyTo(jp.sourceforge.tmdmaker.model.IAttribute)
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
	 * @see jp.sourceforge.tmdmaker.model.IAttribute#getCopy()
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dataTypeDeclaration == null) ? 0 : dataTypeDeclaration.hashCode());
		result = prime * result + (derivation ? 1231 : 1237);
		result = prime * result + ((derivationRule == null) ? 0 : derivationRule.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((implementName == null) ? 0 : implementName.hashCode());
		result = prime * result + ((lock == null) ? 0 : lock.hashCode());
		result = prime * result + (nullable ? 1231 : 1237);
		result = prime * result + ((validationRule == null) ? 0 : validationRule.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Attribute other = (Attribute) obj;
		if (dataTypeDeclaration == null) {
			if (other.dataTypeDeclaration != null)
				return false;
		} else if (!dataTypeDeclaration.equals(other.dataTypeDeclaration))
			return false;
		if (derivation != other.derivation)
			return false;
		if (derivationRule == null) {
			if (other.derivationRule != null)
				return false;
		} else if (!derivationRule.equals(other.derivationRule))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (implementName == null) {
			if (other.implementName != null)
				return false;
		} else if (!implementName.equals(other.implementName))
			return false;
		if (lock == null) {
			if (other.lock != null)
				return false;
		} else if (!lock.equals(other.lock))
			return false;
		if (nullable != other.nullable)
			return false;
		if (validationRule == null) {
			if (other.validationRule != null)
				return false;
		} else if (!validationRule.equals(other.validationRule))
			return false;
		return true;
	}

}

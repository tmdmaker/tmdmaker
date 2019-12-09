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
 * 実装時のサロゲートキーの参照
 * 
 * @author nakaG
 * 
 */
@SuppressWarnings("serial")
public class SurrogateKeyRef extends SurrogateKey {
	private SurrogateKey original;

	/**
	 * コンストラクタ
	 * 
	 * @param original
	 *            参照元のサロゲートキー
	 */
	public SurrogateKeyRef(SurrogateKey original) {
		this.original = original;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.tmdmaker.core.model.Attribute#getImplementName()
	 */
	@Override
	public String getImplementName() {
		String returnName = super.getImplementName();
		if (returnName == null || returnName.length() == 0) {
			returnName = original.getImplementName();
		}
		return returnName;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.tmdmaker.core.model.Attribute#setImplementName(java.lang.String)
	 */
	@Override
	public void setImplementName(String implementName) {
		String oldValue = super.getImplementName();
		if (implementName == null || !implementName.equals(oldValue)) {
			super.setImplementName(implementName);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.tmdmaker.core.model.ModelElement#getName()
	 */
	@Override
	public String getName() {
		String returnName = super.getName();
		if (returnName == null || returnName.length() == 0) {
			returnName = original.getName();
		}
		return returnName;
	}

	/**
	 * @return the original
	 */
	public SurrogateKey getOriginal() {
		return original;
	}

	/**
	 * @param original
	 *            the original to set
	 */
	public void setOriginal(SurrogateKey original) {
		this.original = original;
	}

	/**
	 * @return
	 * @see org.tmdmaker.core.model.SurrogateKey#isEnabled()
	 */
	@Override
	public boolean isEnabled() {
		return original.isEnabled();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.tmdmaker.core.model.Attribute#getDataTypeDeclaration()
	 */
	@Override
	public DataTypeDeclaration getDataTypeDeclaration() {
		DataTypeDeclaration returnValue = super.getDataTypeDeclaration();
		if (returnValue == null) {
			returnValue = original.getDataTypeDeclaration();
		}
		return returnValue;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.tmdmaker.core.model.Attribute#copyTo(org.tmdmaker.core.model.IAttribute)
	 */
	@Override
	public void copyTo(IAttribute to) {
		to.setDerivationRule(getDerivationRule());
		to.setDescription(getDescription());
		to.setLock(getLock());
		to.setValidationRule(getValidationRule());
		if (getDataTypeDeclaration() != null) {
			to.setDataTypeDeclaration(getDataTypeDeclaration().getCopy());
		} else {
			to.setDataTypeDeclaration(null);
		}
		to.setImplementName(getImplementName());
		to.setNullable(isNullable());
	}

}

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
 * Re-usedで参照している個体指定子モデル
 * 
 * @author nakaG
 * 
 */
@SuppressWarnings("serial")
public class IdentifierRef extends Identifier {
	/** 元の個体指定子 */
	private Identifier original;

	/**
	 * コンストラクタ
	 * 
	 * @param identifier
	 *            移送元の個体指定子
	 */
	public IdentifierRef(Identifier identifier) {
		this.original = identifier;
	}

	/**
	 * @return the original
	 */
	public Identifier getOriginal() {
		return original;
	}

	/**
	 * @param original
	 *            the original to set
	 */
	public void setOriginal(Identifier original) {
		this.original = original;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.ModelElement#getName()
	 */
	@Override
	public String getName() {
		String returnName = super.getName();
		if (returnName == null) {
			returnName = original.getName();
		}
		return returnName;
	}
	
	public boolean isSame(IdentifierRef identifierRef) {
		return this.original.equals(identifierRef.getOriginal());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.Attribute#getImplementName()
	 */
	@Override
	public String getImplementName() {
		String returnName = super.getImplementName();
		if (returnName == null) {
			returnName = original.getImplementName();
		}
		return returnName;
	}
	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.Attribute#setImplementName(java.lang.String)
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
	 * @see jp.sourceforge.tmdmaker.model.Attribute#getDataTypeDeclaration()
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
	 * @see jp.sourceforge.tmdmaker.model.Attribute#copyTo(jp.sourceforge.tmdmaker.model.IAttribute)
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
//		to.setName(getName());

	}
	
}

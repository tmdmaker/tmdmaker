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
 * 実装時に個体指定子の代替となるサロゲートキー
 * 
 * @author nakaG
 * 
 */
@SuppressWarnings("serial")
public class SarogateKey extends Attribute {
	private boolean enabled;

	/**
	 * デフォルトコンストラクタ
	 */
	public SarogateKey() {
		setName("");
	}

	/**
	 * @return the enabled
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * @param enabled
	 *            the enabled to set
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.Attribute#copyTo(jp.sourceforge.tmdmaker.model.IAttribute)
	 */
	@Override
	public void copyTo(IAttribute to) {
		SarogateKey to1 = (SarogateKey) to;
		to1.setDerivationRule(getDerivationRule());
		to1.setDescription(getDescription());
		to1.setLock(getLock());
		to1.setValidationRule(getValidationRule());
		if (getDataTypeDeclaration() != null) {
			to1.setDataTypeDeclaration(getDataTypeDeclaration().getCopy());
		} else {
			to1.setDataTypeDeclaration(null);
		}
		to1.setImplementName(getImplementName());
		to1.setNullable(isNullable());
		to1.setEnabled(isEnabled());
		to1.setName(getName());

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.Attribute#getCopy()
	 */
	@Override
	public IAttribute getCopy() {
		SarogateKey copy = new SarogateKey();
		copyTo(copy);
		return copy;
	}

}

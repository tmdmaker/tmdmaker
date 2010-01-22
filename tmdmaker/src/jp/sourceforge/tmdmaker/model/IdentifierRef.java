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
}

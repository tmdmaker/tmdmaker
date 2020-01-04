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
package org.tmdmaker.core.model.other;

import org.tmdmaker.core.model.ConnectableElement;
import org.tmdmaker.core.model.IVisitor;

/**
 * メモモデル
 * 
 * @author nakag
 *
 */
@SuppressWarnings("serial")
public class Memo extends ConnectableElement {
	/** メモ変更のプロパティ */
	public static final String PROPERTY_MEMO = "p_memo";

	/** メモ */
	private String memo;

	/**
	 * @return the memo
	 */
	public String getMemo() {
		return memo;
	}

	/**
	 * @param memo
	 *            the memo to set
	 */
	public void setMemo(String memo) {
		String oldValue = this.memo;
		this.memo = memo;
		firePropertyChange(PROPERTY_MEMO, oldValue, this.memo);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.tmdmaker.core.model.ModelElement#accept(org.tmdmaker.core.model.IVisitor)
	 */
	@Override
	public void accept(IVisitor visitor) {
		visitor.visit(this);
	}
}

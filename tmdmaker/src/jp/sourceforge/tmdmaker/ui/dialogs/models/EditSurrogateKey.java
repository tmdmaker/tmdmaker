/*
 * Copyright 2009-2016 TMD-Maker Project <http://tmdmaker.osdn.jp/>
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
package jp.sourceforge.tmdmaker.ui.dialogs.models;

import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.Attribute;
import jp.sourceforge.tmdmaker.model.IAttribute;
import jp.sourceforge.tmdmaker.model.SurrogateKey;

/**
 * 編集用サロゲートキー
 * 
 * @author nakaG
 * 
 */
public class EditSurrogateKey extends EditImplementAttribute {
	private boolean enabled;
	private boolean added;

	/**
	 * コンストラクタ
	 * 
	 * @param containerModel
	 *            サロゲートキーを保有するエンティティ系モデル
	 * @param original
	 *            サロゲートキー
	 */
	public EditSurrogateKey(AbstractEntityModel containerModel,
			SurrogateKey original) {
		super(containerModel, original);
		if (original == null) {
			enabled = false;
			added = true;
		} else {
			enabled = original.isEnabled();
			added = !enabled;
		}
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
	 * @see jp.sourceforge.tmdmaker.ui.dialogs.models.EditImplementAttribute#isAdded()
	 */
	@Override
	public boolean isAdded() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.ui.dialogs.models.EditImplementAttribute#getOriginalAttribute()
	 */
	@Override
	public Attribute getOriginalAttribute() {
		SurrogateKey sarogeteKey = (SurrogateKey) super.getOriginalAttribute();
		if (added) {
			getEditAttribute().copyTo(sarogeteKey);
		}
		return sarogeteKey;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.ui.dialogs.models.EditImplementAttribute#copyTo(jp.sourceforge.tmdmaker.model.IAttribute)
	 */
	@Override
	public void copyTo(IAttribute to) {
		super.copyTo(to);
		if (to instanceof SurrogateKey) {
			((SurrogateKey) to).setEnabled(enabled);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.ui.dialogs.models.EditImplementAttribute#copyToOriginal()
	 */
	@Override
	public void copyToOriginal() {
		super.copyToOriginal();
		((SurrogateKey) getOriginalAttribute()).setEnabled(enabled);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj instanceof EditSurrogateKey) {
			EditSurrogateKey other = (EditSurrogateKey) obj;
			return super.equals(other) &&
					getEditAttribute().equals(other.getEditAttribute());
		}
		return false;

	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.ui.dialogs.models.EditImplementAttribute#hashCode()
	 */
	@Override
	public int hashCode() {
		return getEditAttribute().hashCode();
	}

}

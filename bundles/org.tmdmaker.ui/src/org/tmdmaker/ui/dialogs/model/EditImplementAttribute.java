/*
 * Copyright 2009-2015 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
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
package org.tmdmaker.ui.dialogs.model;

import java.util.ArrayList;
import java.util.List;

import org.tmdmaker.core.model.AbstractEntityModel;
import org.tmdmaker.core.model.Attribute;
import org.tmdmaker.core.model.IAttribute;
import org.tmdmaker.core.model.KeyModel;
import org.tmdmaker.core.model.StandardSQLDataType;

/**
 * アトリビュートの実装方法の編集用クラス
 * 
 * @author nakaG
 * 
 */
public class EditImplementAttribute {
	/** 編集対象のアトリビュートを保持しているモデル */
	private AbstractEntityModel containerModel;
	/** 編集用アトリビュート */
	private EditAttribute editAttribute;
	/** 編集用キーモデルのリスト */
	private List<KeyModel> keyModels = new ArrayList<KeyModel>();

	/**
	 * コンストラクタ
	 * 
	 * @param containerModel
	 */
	public EditImplementAttribute(AbstractEntityModel containerModel, IAttribute original) {
		this.editAttribute = new EditAttribute(original);
		this.containerModel = containerModel;
	}

	/**
	 * @return the containerModel
	 */
	public AbstractEntityModel getContainerModel() {
		return containerModel;
	}

	public void addKeyModel(KeyModel model) {
		this.keyModels.add(model);
	}

	public int getKeyCount() {
		return keyModels.size();
	}

	public void removeAllKeyModel() {
		this.keyModels.clear();
	}

	public void removeKeyModel(KeyModel model) {
		this.keyModels.remove(model);
	}

	public List<String> getKeyOrders() {
		List<String> keyOrders = new ArrayList<String>();
		for (KeyModel m : keyModels) {
			IAttribute original = getOriginalAttribute();
			if (m.contains(original)) {
				keyOrders.add(String.valueOf(m.indexOf(original) + 1));
			} else {
				keyOrders.add("");
			}
		}
		return keyOrders;
	}

	/**
	 * 
	 * @param newAttribute
	 */
	public void copyTo(IAttribute newAttribute) {
		editAttribute.copyTo(newAttribute);
	}

	/**
	 * 
	 */
	public void copyToOriginal() {
		editAttribute.copyToOriginal();
	}

	/**
	 * 
	 * @return
	 */
	public StandardSQLDataType getDataType() {
		return editAttribute.getDataType();
	}

	/**
	 * 
	 * @return
	 */
	public boolean isDerivation() {
		return editAttribute.isDerivation();
	}

	/**
	 * @param derivation
	 */
	public void setDerivation(boolean derivation) {
		editAttribute.setDerivation(derivation);
	}

	/**
	 * @return
	 */
	public String getDerivationRule() {
		return editAttribute.getDerivationRule();
	}

	/**
	 * @return
	 */
	public String getDescription() {
		return editAttribute.getDescription();
	}

	/**
	 * @return
	 */
	public String getImplementName() {
		return editAttribute.getImplementName();
	}

	/**
	 * @return
	 */
	public String getLock() {
		return editAttribute.getLock();
	}

	/**
	 * @return
	 */
	public String getName() {
		return editAttribute.getName();
	}

	/**
	 * @return
	 */
	public IAttribute getOriginalAttribute() {
		return editAttribute.getOriginalAttribute();
	}

	/**
	 * @return
	 */
	public String getScale() {
		return editAttribute.getScale();
	}

	/**
	 * @return
	 */
	public String getSize() {
		return editAttribute.getSize();
	}

	/**
	 * @return
	 */
	public String getValidationRule() {
		return editAttribute.getValidationRule();
	}

	/**
	 * @return
	 */
	public int hashCode() {
		return editAttribute.hashCode();
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
		if (obj instanceof EditImplementAttribute) {
			EditImplementAttribute other = (EditImplementAttribute) obj;
			return editAttribute.equals(other.getEditAttribute());
		}
		return false;
	}

	/**
	 * @return
	 */
	public boolean isAdded() {
		return editAttribute.isAdded();
	}

	/**
	 * @return
	 */
	public boolean isEdited() {
		return editAttribute.isEdited();
	}

	/**
	 * @return
	 */
	public boolean isNameChanged() {
		return editAttribute.isNameChanged();
	}

	/**
	 * @return
	 */
	public boolean isNullable() {
		return editAttribute.isNullable();
	}

	/**
	 * @param dataType
	 */
	public void setDataType(StandardSQLDataType dataType) {
		editAttribute.setDataType(dataType);
	}

	/**
	 * @param derivationRule
	 */
	public void setDerivationRule(String derivationRule) {
		editAttribute.setDerivationRule(derivationRule);
	}

	/**
	 * @param description
	 */
	public void setDescription(String description) {
		editAttribute.setDescription(description);
	}

	/**
	 * @param edited
	 */
	public void setEdited(boolean edited) {
		editAttribute.setEdited(edited);
	}

	/**
	 * @param implementName
	 */
	public void setImplementName(String implementName) {
		editAttribute.setImplementName(implementName);
	}

	/**
	 * @param lock
	 */
	public void setLock(String lock) {
		editAttribute.setLock(lock);
	}

	/**
	 * @param name
	 */
	public void setName(String name) {
		editAttribute.setName(name);
	}

	/**
	 * @param nullable
	 */
	public void setNullable(boolean nullable) {
		editAttribute.setNullable(nullable);
	}

	/**
	 * @param originalAttribute
	 */
	public void setOriginalAttribute(Attribute originalAttribute) {
		editAttribute.setOriginalAttribute(originalAttribute);
	}

	/**
	 * @param scale
	 */
	public void setScale(String scale) {
		editAttribute.setScale(scale);
	}

	/**
	 * @param size
	 */
	public void setSize(String size) {
		editAttribute.setSize(size);
	}

	/**
	 * @param validationRule
	 */
	public void setValidationRule(String validationRule) {
		editAttribute.setValidationRule(validationRule);
	}

	/**
	 * @return
	 */
	public String toString() {
		return editAttribute.toString();
	}

	/**
	 * @return the editAttribute
	 */
	protected EditAttribute getEditAttribute() {
		return editAttribute;
	}

}

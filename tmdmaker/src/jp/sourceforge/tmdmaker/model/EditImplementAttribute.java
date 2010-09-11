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

import java.util.ArrayList;
import java.util.List;

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
	private List<EditKeyModel> editKeyModels = new ArrayList<EditKeyModel>();

	/**
	 * コンストラクタ
	 * 
	 * @param containerModel
	 */
	public EditImplementAttribute(AbstractEntityModel containerModel,
			Attribute original) {
		this.editAttribute = new EditAttribute(original);
		this.containerModel = containerModel;
	}

	/**
	 * @return the containerModel
	 */
	public AbstractEntityModel getContainerModel() {
		return containerModel;
	}
	public void addEditKeyModel(EditKeyModel model) {
		this.editKeyModels.add(model);
	}
	public void replaceEditKeyModel(int index, EditKeyModel model) {
		this.editKeyModels.remove(index);
		this.editKeyModels.add(index, model);
	}
	public int getKeyCount() {
		return editKeyModels.size();
	}
	public void removeAllEditKeyModel() {
		this.editKeyModels.clear();
	}
	public EditKeyModel removeEditKyeModel(int index) {
		return this.editKeyModels.remove(index);
	}
	public List<String> getKeyOrders() {
		List<String> keyOrders = new ArrayList<String>();
		for (EditKeyModel m : editKeyModels) {
			Attribute original = getOriginalAttribute();
			if (m.contains(original)) {
				keyOrders.add(String.valueOf(m.indexOf(original) + 1));
			} else {
				keyOrders.add("");
			}
		}
		return keyOrders;
	}
	
	/**
	 * @param to
	 * @see jp.sourceforge.tmdmaker.model.EditAttribute#copyTo(jp.sourceforge.tmdmaker.model.Attribute)
	 */
	public void copyTo(Attribute to) {
		editAttribute.copyTo(to);
	}

	/**
	 * 
	 * @see jp.sourceforge.tmdmaker.model.EditAttribute#copyToOriginal()
	 */
	public void copyToOriginal() {
		editAttribute.copyToOriginal();
	}

	/**
	 * @return
	 * @see jp.sourceforge.tmdmaker.model.EditAttribute#getDataType()
	 */
	public StandardSQLDataType getDataType() {
		return editAttribute.getDataType();
	}

	/**
	 * @return
	 * @see jp.sourceforge.tmdmaker.model.EditAttribute#getDerivationRule()
	 */
	public String getDerivationRule() {
		return editAttribute.getDerivationRule();
	}

	/**
	 * @return
	 * @see jp.sourceforge.tmdmaker.model.EditAttribute#getDescription()
	 */
	public String getDescription() {
		return editAttribute.getDescription();
	}

	/**
	 * @return
	 * @see jp.sourceforge.tmdmaker.model.EditAttribute#getImplementName()
	 */
	public String getImplementName() {
		return editAttribute.getImplementName();
	}

	/**
	 * @return
	 * @see jp.sourceforge.tmdmaker.model.EditAttribute#getLock()
	 */
	public String getLock() {
		return editAttribute.getLock();
	}

	/**
	 * @return
	 * @see jp.sourceforge.tmdmaker.model.EditAttribute#getName()
	 */
	public String getName() {
		return editAttribute.getName();
	}

	/**
	 * @return
	 * @see jp.sourceforge.tmdmaker.model.EditAttribute#getOriginalAttribute()
	 */
	public Attribute getOriginalAttribute() {
		return editAttribute.getOriginalAttribute();
	}

	/**
	 * @return
	 * @see jp.sourceforge.tmdmaker.model.EditAttribute#getScale()
	 */
	public String getScale() {
		return editAttribute.getScale();
	}

	/**
	 * @return
	 * @see jp.sourceforge.tmdmaker.model.EditAttribute#getSize()
	 */
	public String getSize() {
		return editAttribute.getSize();
	}

	/**
	 * @return
	 * @see jp.sourceforge.tmdmaker.model.EditAttribute#getValidationRule()
	 */
	public String getValidationRule() {
		return editAttribute.getValidationRule();
	}

	/**
	 * @return
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return editAttribute.hashCode();
	}

	/**
	 * @return
	 * @see jp.sourceforge.tmdmaker.model.EditAttribute#isAdded()
	 */
	public boolean isAdded() {
		return editAttribute.isAdded();
	}

	/**
	 * @return
	 * @see jp.sourceforge.tmdmaker.model.EditAttribute#isEdited()
	 */
	public boolean isEdited() {
		return editAttribute.isEdited();
	}

	/**
	 * @return
	 * @see jp.sourceforge.tmdmaker.model.EditAttribute#isNameChanged()
	 */
	public boolean isNameChanged() {
		return editAttribute.isNameChanged();
	}

	/**
	 * @return
	 * @see jp.sourceforge.tmdmaker.model.EditAttribute#isNullable()
	 */
	public boolean isNullable() {
		return editAttribute.isNullable();
	}

	/**
	 * @param dataType
	 * @see jp.sourceforge.tmdmaker.model.EditAttribute#setDataType(jp.sourceforge.tmdmaker.model.StandardSQLDataType)
	 */
	public void setDataType(StandardSQLDataType dataType) {
		editAttribute.setDataType(dataType);
	}

	/**
	 * @param derivationRule
	 * @see jp.sourceforge.tmdmaker.model.EditAttribute#setDerivationRule(java.lang.String)
	 */
	public void setDerivationRule(String derivationRule) {
		editAttribute.setDerivationRule(derivationRule);
	}

	/**
	 * @param description
	 * @see jp.sourceforge.tmdmaker.model.EditAttribute#setDescription(java.lang.String)
	 */
	public void setDescription(String description) {
		editAttribute.setDescription(description);
	}

	/**
	 * @param edited
	 * @see jp.sourceforge.tmdmaker.model.EditAttribute#setEdited(boolean)
	 */
	public void setEdited(boolean edited) {
		editAttribute.setEdited(edited);
	}

	/**
	 * @param implementName
	 * @see jp.sourceforge.tmdmaker.model.EditAttribute#setImplementName(java.lang.String)
	 */
	public void setImplementName(String implementName) {
		editAttribute.setImplementName(implementName);
	}

	/**
	 * @param lock
	 * @see jp.sourceforge.tmdmaker.model.EditAttribute#setLock(java.lang.String)
	 */
	public void setLock(String lock) {
		editAttribute.setLock(lock);
	}

	/**
	 * @param name
	 * @see jp.sourceforge.tmdmaker.model.EditAttribute#setName(java.lang.String)
	 */
	public void setName(String name) {
		editAttribute.setName(name);
	}

	/**
	 * @param nullable
	 * @see jp.sourceforge.tmdmaker.model.EditAttribute#setNullable(boolean)
	 */
	public void setNullable(boolean nullable) {
		editAttribute.setNullable(nullable);
	}

	/**
	 * @param originalAttribute
	 * @see jp.sourceforge.tmdmaker.model.EditAttribute#setOriginalAttribute(jp.sourceforge.tmdmaker.model.Attribute)
	 */
	public void setOriginalAttribute(Attribute originalAttribute) {
		editAttribute.setOriginalAttribute(originalAttribute);
	}

	/**
	 * @param scale
	 * @see jp.sourceforge.tmdmaker.model.EditAttribute#setScale(java.lang.String)
	 */
	public void setScale(String scale) {
		editAttribute.setScale(scale);
	}

	/**
	 * @param size
	 * @see jp.sourceforge.tmdmaker.model.EditAttribute#setSize(java.lang.String)
	 */
	public void setSize(String size) {
		editAttribute.setSize(size);
	}

	/**
	 * @param validationRule
	 * @see jp.sourceforge.tmdmaker.model.EditAttribute#setValidationRule(java.lang.String)
	 */
	public void setValidationRule(String validationRule) {
		editAttribute.setValidationRule(validationRule);
	}

	/**
	 * @return
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return editAttribute.toString();
	}

}

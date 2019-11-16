/*
 * Copyright 2009-2019 TMD-Maker Project <https://www.tmdmaker.org/>
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

import jp.sourceforge.tmdmaker.model.Attribute;
import jp.sourceforge.tmdmaker.model.DataTypeDeclaration;
import jp.sourceforge.tmdmaker.model.IAttribute;
import jp.sourceforge.tmdmaker.model.Identifier;
import jp.sourceforge.tmdmaker.model.StandardSQLDataType;
import jp.sourceforge.tmdmaker.ui.util.ModelEditUtils;

/**
 * 編集用アトリビュート
 * 
 * @author nakaG
 * 
 */
public class EditAttribute {
	/** 編集元アトリビュート */
	private IAttribute originalAttribute;
	/** 編集用名称 */
	private String name = "";
	/** 編集有無 */
	private boolean edited = false;
	/** 摘要 */
	private String description = "";
	/** データ属性 */
	private StandardSQLDataType dataType = null;
	/** 長さ */
	private String size = "";
	/** 精度 */
	private String scale = "";
	/** オートインクリメント */
	private Boolean autoIncrement;
	/** デフォルト値 */
	private String defaultValue;
	/** 前提 */
	private String validationRule = "";
	/** 機密性 */
	private String lock = "";
	/** 計算式 */
	private boolean derivation = false;
	private String derivationRule = "";
	/** 実装名 */
	private String implementName = "";
	/** NULL許可 */
	private boolean nullable = false;
	/** 新規追加 */
	private boolean added = false;

	/**
	 * コンストラクタ
	 */
	public EditAttribute() {
		this.originalAttribute = new Attribute();
		added = true;
	}

	/**
	 * コンストラクタ
	 * 
	 * @param original
	 *            編集元アトリビュート
	 */
	public EditAttribute(IAttribute original) {
		this.originalAttribute = original;

		this.name = ModelEditUtils.toBlankStringIfNull(original.getName());
		this.description = ModelEditUtils.toBlankStringIfNull(original.getDescription());
		this.derivationRule = ModelEditUtils.toBlankStringIfNull(original.getDerivationRule());
		this.derivation = original.isDerivation();
		this.lock = ModelEditUtils.toBlankStringIfNull(original.getLock());
		this.validationRule = ModelEditUtils.toBlankStringIfNull(original.getValidationRule());
		this.implementName = ModelEditUtils.toBlankStringIfNull(original.getImplementName());
		DataTypeDeclaration dataTypeDeclaration = original.getDataTypeDeclaration();
		if (dataTypeDeclaration != null) {
			this.dataType = dataTypeDeclaration.getLogicalType();
			this.size = ModelEditUtils.toBlankIfNull(dataTypeDeclaration.getSize());
			this.scale = ModelEditUtils.toBlankIfNull(dataTypeDeclaration.getScale());
			this.autoIncrement = dataTypeDeclaration.getAutoIncrement();
			this.defaultValue = dataTypeDeclaration.getDefaultValue();
		} else {
			this.dataType = null;
			this.size = "";
			this.scale = "";
			this.autoIncrement = null;
			this.defaultValue = null;
		}
		this.nullable = original.isNullable();
		this.added = false;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
		setEdited(true);
	}

	public boolean isNameChanged() {
		return !this.name.equals(originalAttribute.getName());
	}

	/**
	 * @return the originalAttribute
	 */
	public IAttribute getOriginalAttribute() {
		if (added) {
			copyToOriginal();
		}
		return originalAttribute;
	}

	/**
	 * @return the edited
	 */
	public boolean isEdited() {
		return edited;
	}

	/**
	 * @param edited
	 *            the edited to set
	 */
	public void setEdited(boolean edited) {
		this.edited = edited;
	}

	/**
	 * @return the added
	 */
	public boolean isAdded() {
		return added;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
		setEdited(true);
	}

	/**
	 * @return the dataType
	 */
	public StandardSQLDataType getDataType() {
		return dataType;
	}

	/**
	 * @param dataType
	 *            the dataType to set
	 */
	public void setDataType(StandardSQLDataType dataType) {
		this.dataType = dataType;
		setEdited(true);
	}

	/**
	 * @return the size
	 */
	public String getSize() {
		return size;
	}

	/**
	 * @param size
	 *            the size to set
	 */
	public void setSize(String size) {
		this.size = size;
		setEdited(true);
	}

	/**
	 * @return the scale
	 */
	public String getScale() {
		return scale;
	}

	/**
	 * @param scale
	 *            the scale to set
	 */
	public void setScale(String scale) {
		this.scale = scale;
		setEdited(true);
	}

	/**
	 * @return the autoIncrement
	 */
	public Boolean getAutoIncrement() {
		return autoIncrement;
	}

	/**
	 * @param autoIncrement
	 *            the autoIncrement to set
	 */
	public void setAutoIncrement(Boolean autoIncrement) {
		this.autoIncrement = autoIncrement;
		setEdited(true);
	}

	/**
	 * @return the defaultValue
	 */
	public String getDefaultValue() {
		return defaultValue;
	}

	/**
	 * @param defaultValue
	 *            the defaultValue to set
	 */
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	/**
	 * @param originalAttribute
	 *            the originalAttribute to set
	 */
	public void setOriginalAttribute(IAttribute originalAttribute) {
		this.originalAttribute = originalAttribute;
	}

	/**
	 * @return the validationRule
	 */
	public String getValidationRule() {
		return validationRule;
	}

	/**
	 * @param validationRule
	 *            the validationRule to set
	 */
	public void setValidationRule(String validationRule) {
		this.validationRule = validationRule;
		setEdited(true);
	}

	/**
	 * @return the lock
	 */
	public String getLock() {
		return lock;
	}

	/**
	 * @param lock
	 *            the lock to set
	 */
	public void setLock(String lock) {
		this.lock = lock;
		setEdited(true);
	}

	/**
	 * @return the derivation
	 */
	public boolean isDerivation() {
		return derivation;
	}

	/**
	 * @param derivation
	 *            the derivation to set
	 */
	public void setDerivation(boolean derivation) {
		this.derivation = derivation;
		setEdited(true);
	}

	/**
	 * @return the derivationRule
	 */
	public String getDerivationRule() {
		return derivationRule;
	}

	/**
	 * @param derivationRule
	 *            the derivationRule to set
	 */
	public void setDerivationRule(String derivationRule) {
		this.derivationRule = derivationRule;
		setEdited(true);
	}

	/**
	 * @return the implementName
	 */
	public String getImplementName() {
		return implementName;
	}

	/**
	 * @param implementName
	 *            the implementName to set
	 */
	public void setImplementName(String implementName) {
		this.implementName = implementName;
		setEdited(true);
	}

	/**
	 * @return the nullable
	 */
	public boolean isNullable() {
		return nullable;
	}

	/**
	 * @param nullable
	 *            the nullable to set
	 */
	public void setNullable(boolean nullable) {
		this.nullable = nullable;
		setEdited(true);
	}

	/**
	 * newAttributeへ自身のフィールド値をコピー（sharrow copy)する。
	 * 
	 * @param newAttribute
	 *            アトリビュート
	 */
	public void copyTo(IAttribute newAttribute) {
		if (dataType != null) {
			newAttribute.setDataTypeDeclaration(copyDataTypeDeclaration());
		} else {
			newAttribute.setDataTypeDeclaration(null);
		}
		if (derivationRule.length() != 0) {
			newAttribute.setDerivationRule(derivationRule);
		}
		newAttribute.setDerivation(derivation);
		if (description.length() != 0) {
			newAttribute.setDescription(description);
		}
		if (lock.length() != 0) {
			newAttribute.setLock(lock);
		}
		if (validationRule.length() != 0) {
			newAttribute.setValidationRule(validationRule);
		}
		if (implementName.length() != 0) {
			newAttribute.setImplementName(implementName);
		} else {
			newAttribute.setImplementName(null);
		}
		newAttribute.setNullable(nullable);
		newAttribute.setName(name);
	}

	private DataTypeDeclaration copyDataTypeDeclaration() {
		Integer newSize = null;
		Integer newScale = null;
		if (dataType.isSupportSize() && this.size.length() != 0) {
			newSize = ModelEditUtils.toInteger(this.size);
		}
		if (dataType.isSupportScale() && this.scale.length() != 0) {
			newScale = ModelEditUtils.toInteger(this.scale);
		}
		return new DataTypeDeclaration(dataType, newSize, newScale, autoIncrement, defaultValue);
	}

	/**
	 * toへ自身のフィールド値をコピー（sharrow copy)する。
	 * 
	 * @param to
	 *            編集用アトリビュート
	 */
	public void copyTo(EditAttribute to) {
		to.setDataType(getDataType());
		to.setDerivation(isDerivation());
		to.setDerivationRule(getDerivationRule());
		to.setDescription(getDescription());
		to.setImplementName(getImplementName());
		to.setLock(getLock());
		to.setName(getName());
		to.setNullable(isNullable());
		to.setScale(getScale());
		to.setSize(getSize());
		to.setAutoIncrement(getAutoIncrement());
		to.setValidationRule(getValidationRule());
	}

	/**
	 * 元のアトリビュートへ自身のフィールド値をコピーする。
	 */
	protected void copyToOriginal() {
		copyTo(originalAttribute);
	}

	public Identifier toIdentifier() {
		Identifier identifier = new Identifier(this.name);
		copyTo(identifier);
		return identifier;
	}

	public boolean isValid() {
		return this.name != null && this.name.length() > 0;
	}
}

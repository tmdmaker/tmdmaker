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
 * 編集用アトリビュート
 * 
 * @author nakaG
 * 
 */
public class EditAttribute {
	/** 編集元アトリビュート */
	private Attribute originalAttribute;
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
	/** 前提 */
	private String validationRule = "";
	/** 機密性 */
	private String lock = "";
	/** 計算式 */
	private String derivationRule = "";

	/**
	 * コンストラクタ
	 */
	public EditAttribute() {
		this.originalAttribute = new Attribute();
	}

	/**
	 * コンストラクタ
	 * 
	 * @param original
	 *            編集元アトリビュート
	 */
	public EditAttribute(Attribute original) {
		this.originalAttribute = original;
		String value = original.getName();
		this.name = value == null ? "" : value;
//		value = original.getDataType();
//		this.dataType = value == null ? "" : value;
		value = original.getDescription();
		this.description = value == null ? "" : value;
//		value = String.valueOf(original.getSize());
//		this.size = value == null ? "" : value;
//		value = String.valueOf(original.getScale());
//		this.scale = value == null ? "" : value;
		value = original.getDerivationRule();
		this.derivationRule = value == null ? "" : value;
		value = original.getLock();
		this.lock = value == null ? "" : value;
		value = original.getValidationRule();
		this.validationRule = value == null ? "" : value;
		DataTypeDeclaration dataTypeDeclaration = original.getDataType();
		if (dataTypeDeclaration != null) {
			this.dataType = dataTypeDeclaration.getLogicalType();
			Integer intValue = dataTypeDeclaration.getSize();
			this.size = intValue == null ? "" : String.valueOf(intValue);
			intValue = dataTypeDeclaration.getScale();
			this.scale = intValue == null ? "" : String.valueOf(intValue);
		} else {
			this.dataType = null;
			this.size = "";
			this.scale = "";
		}
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
	public Attribute getOriginalAttribute() {
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

	public boolean isAdded() {
		return originalAttribute.getName() == null;
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
	 * @param originalAttribute
	 *            the originalAttribute to set
	 */
	public void setOriginalAttribute(Attribute originalAttribute) {
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
	 * toへ自身のフィールド値をコピー（sharrow copy)する。
	 * 
	 * @param to
	 */
	public void copyTo(Attribute to) {
		if (dataType != null) {
			Integer size = null;
			Integer scale = null;
			if (dataType.isSupportScale()) {
				if( this.scale.length() != 0) {
					size = Integer.valueOf(this.size);
				}
			} else {
				size = null;
			}
			if (dataType.isSupportSize()) {
				if (this.size.length() != 0) {
					scale = Integer.valueOf(this.scale);
				}
			} else {
				scale = null;
			}
			DataTypeDeclaration dataTypeDeclaration = new DataTypeDeclaration(dataType, size, scale);
			to.setDataType(dataTypeDeclaration);
		}
		if (derivationRule.length() != 0) {
			to.setDerivationRule(derivationRule);
		}
		if (description.length() != 0) {
			to.setDescription(description);
		}
		if (lock.length() != 0) {
			to.setLock(lock);
		}
		if (validationRule.length() != 0) {
			to.setValidationRule(validationRule);
		}
		to.setName(name);
	}

	/**
	 * 元のアトリビュートへ自身のフィールド値をコピーする。
	 */
	public void copyToOriginal() {
		copyTo(originalAttribute);
	}

	/**
	 * 
	 * @param value
	 *            文字列をintへ変換する。
	 * @return int 変換エラー時は0を返す。
	 */
	private int toInteger(String value) {
		try {
			return Integer.valueOf(value);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return 0;
		}
	}
}

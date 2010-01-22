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
 * アトリビュート
 * 
 * @author nakaG
 * 
 */
@SuppressWarnings("serial")
public class Attribute extends ModelElement {
	private String physicalName = "";
	/** 摘要 */
	private String description = "";
	/** データ属性 */
	private String dataType = "";
	/** 長さ */
	private int size;
	/** 精度 */
	private int scale;
	/** 前提 */
	private String validationRule;
	/** 機密性 */
	private String lock;
	/** 計算式 */
	private String derivationRule;

	// /** 親エンティティ */
	// private AbstractEntityModel parent;

	/**
	 * コンストラクタ
	 */
	public Attribute() {
	}

	/**
	 * コンストラクタ
	 * 
	 * @param name
	 *            アトリビュートの名称
	 */
	public Attribute(String name) {
		setName(name);
	}

	/**
	 * @return the physicalName
	 */
	public String getPhysicalName() {
		return physicalName;
	}

	/**
	 * @param physicalName
	 *            the physicalName to set
	 */
	public void setPhysicalName(String physicalName) {
		this.physicalName = physicalName;
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
	}

	/**
	 * @return the size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * @param size
	 *            the size to set
	 */
	public void setSize(int size) {
		this.size = size;
	}

	/**
	 * @return the scale
	 */
	public int getScale() {
		return scale;
	}

	/**
	 * @param scale
	 *            the scale to set
	 */
	public void setScale(int scale) {
		this.scale = scale;
	}

	/**
	 * @return the dataType
	 */
	public String getDataType() {
		return dataType;
	}

	/**
	 * @param dataType
	 *            the dataType to set
	 */
	public void setDataType(String type) {
		this.dataType = type;
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
	}

	/**
	 * fromから自身のフィールド値へコピー（sharrow copy)する。
	 * 
	 * @param from
	 */
	public void copyFrom(Attribute from) {
		this.setDataType(from.getDataType());
		this.setDerivationRule(from.getDerivationRule());
		this.setDescription(from.getDescription());
		this.setLock(from.getLock());
		this.setScale(from.getScale());
		this.setSize(from.getSize());
		this.setValidationRule(from.getValidationRule());
		this.setName(from.getName());
	}

	/**
	 * toへ自身のフィールド値をコピー（sharrow copy)する。
	 * 
	 * @param to
	 */
	public void copyTo(Attribute to) {
		to.setDataType(dataType);
		to.setDerivationRule(derivationRule);
		to.setDescription(description);
		to.setLock(lock);
		to.setScale(scale);
		to.setSize(size);
		to.setValidationRule(validationRule);
		to.setName(getName());
	}

	// /**
	// * @return the parent
	// */
	// public AbstractEntityModel getParent() {
	// return parent;
	// }
	//
	// /**
	// * @param parent the parent to set
	// */
	// public void setParent(AbstractEntityModel parent) {
	// this.parent = parent;
	// }

}

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
package org.tmdmaker.core.model;

import java.io.Serializable;

/**
 * 宣言データ型
 * 
 * 実際にアトリビュートに使用するデータ型と桁、位を宣言したものを表す。
 * 
 * @author nakag
 * 
 */
@SuppressWarnings("serial")
public class DataTypeDeclaration implements Serializable {

	/**
	 * コンストラクタ
	 * 
	 * @param logicalType
	 *            論理データ型
	 * @param size
	 *            長さ
	 * @param scale
	 *            精度
	 */
	public DataTypeDeclaration(StandardSQLDataType logicalType, Integer size, Integer scale) {
		this(logicalType, size, scale, null, null);
	}

	/**
	 * コンストラクタ
	 * 
	 * @param logicalType
	 *            論理データ型
	 * @param size
	 *            長さ
	 * @param scale
	 *            精度
	 * @param autoIncrement
	 *            オートインクリメント
	 * @param defaultValue
	 *            デフォルト値
	 */
	public DataTypeDeclaration(StandardSQLDataType logicalType, Integer size, Integer scale,
			Boolean autoIncrement, String defaultValue) {
		this.logicalType = logicalType;
		this.size = size;
		this.scale = scale;
		this.autoIncrement = autoIncrement;
		this.defaultValue = defaultValue;
	}

	/** 論理データ型 */
	private StandardSQLDataType logicalType;
	/** 長さ */
	private Integer size;
	/** 位 */
	private Integer scale;
	/** オートインクリメント */
	private Boolean autoIncrement;
	/** デフォルト値 */
	private String defaultValue;

	/**
	 * @return the logicalType
	 */
	public StandardSQLDataType getLogicalType() {
		return logicalType;
	}

	/**
	 * @return the size
	 */
	public Integer getSize() {
		return size;
	}

	/**
	 * @return the scale
	 */
	public Integer getScale() {
		return scale;
	}

	/**
	 * @return the autoIncrement
	 */
	public Boolean getAutoIncrement() {
		return autoIncrement;
	}

	/**
	 * @return the defaultValue
	 */
	public String getDefaultValue() {
		return defaultValue;
	}

	/**
	 * コピー(sharrow copy)を取得する。
	 * 
	 * @return コピーしたオブジェクト
	 */
	public DataTypeDeclaration getCopy() {
		return new DataTypeDeclaration(logicalType, size, scale, autoIncrement, defaultValue);
	}

	public DataTypeDeclaration newLogicalType(StandardSQLDataType newType) {
		DataTypeDeclaration newValue = getCopy();
		newValue.logicalType = newType;
		if (!newType.isSupportSize()) {
			newValue.size = null;
		}
		if (!newType.isSupportScale()) {
			newValue.scale = null;
		}
		return newValue;
	}

	public DataTypeDeclaration newSize(Integer newSize) {
		DataTypeDeclaration newValue = getCopy();
		newValue.size = newSize;
		return newValue;
	}

	public DataTypeDeclaration newScale(Integer newScale) {
		DataTypeDeclaration newValue = getCopy();
		newValue.scale = newScale;
		return newValue;
	}
}

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

import java.io.Serializable;

/**
 * 宣言データ型
 * 
 * 実際にアトリビュートに使用するデータ型と桁、位を宣言したものを表す。
 * 
 * @author nakaG
 * 
 */
@SuppressWarnings("serial")
public class DataTypeDeclaration implements Serializable {
	// 未使用。使うかも
	// enum DataTypeGroup {
	// NUMBER("数値", true, true),
	// CHAR("固定長文字列", true, false),
	// VARCHAR("可変長文字列", true, false),
	// DATE("日付", false, false),
	// DATETIME("日時", false, false);
	//		
	// private String name;
	// private boolean supportSize;
	// private boolean supportScale;
	// private DataTypeGroup(String name, boolean supportSize, boolean
	// supportScale) {
	// this.name = name;
	// this.supportSize = supportSize;
	// this.supportScale = supportScale;
	// }
	// }
	// public static String[] dataTypes = new String[] {"数値", "固定長文字列",
	// "可変長文字列", "日付", "時間", "バイナリ"};

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
	public DataTypeDeclaration(StandardSQLDataType logicalType, Integer size,
			Integer scale) {
		this.logicalType = logicalType;
		this.size = size;
		this.scale = scale;
	}

	/** 論理データ型 */
	private StandardSQLDataType logicalType;
	/** 長さ */
	private Integer size;
	/** 位 */
	private Integer scale;

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
	 * コピー(sharrow copy)を取得する。
	 * 
	 * @return コピーしたオブジェクト
	 */
	public DataTypeDeclaration getCopy() {
		return new DataTypeDeclaration(logicalType, size, scale);
	}
}

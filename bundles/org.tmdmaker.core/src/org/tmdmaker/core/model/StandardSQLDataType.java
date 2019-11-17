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
package org.tmdmaker.core.model;

import java.sql.Types;

/**
 * 標準SQLのデータ型
 * 
 * @author nakaG
 * 
 */
public enum StandardSQLDataType {
	SMALLINT("SMALLINT", false, false, Types.SMALLINT), 
	INTEGER("INTEGER", false, false, Types.INTEGER),
	FLOAT("FLOAT", true, true, Types.FLOAT),
	REAL("REAL", true, true, Types.REAL),
	DOUBLE("DOUBLE PRECISION", true, true, Types.DOUBLE),
	DECIMAL("DECIMAL", true, true, Types.DECIMAL),
	NUMERIC("NUMERIC", true, true, Types.NUMERIC),
	CHARACTER("CHARACTER", true, false, Types.CHAR),
	NATIONAL_CHARACTER("NATIONAL CHARACTER", true, false, Types.NCHAR), 
	CHARACTER_VARYING("CHARACTER VARYING", true, false, Types.VARCHAR), 
	NATIONAL_CHARACTER_VARYING("NATIONAL CHARACTER VARYING", true, false, Types.NVARCHAR), 
	CHARACTER_LARGE_OBJECT("CHARACTER LARGE OBJECT", true, false, Types.CLOB), 
	DATE("DATE", false, false, Types.DATE), 
	TIMESTAMP("TIMESTAMP", false, false, Types.TIMESTAMP), 
	TIME("TIME", false, false, Types.TIME),
	// INTERVAL ("INTERVAL ", false, false, Types.BIGINT),
	BINARY_LARGE_OBJECT("BINARY LARGE OBJECT", true, false, Types.BLOB), 
	BIT("BIT", true, false, Types.BIT), 
	BIT_VARYING("BIT VARYING", true, false, Types.VARBINARY), 
	BOOLEAN("BOOLEAN ", false, false, Types.BOOLEAN);

	/** データ型名称 */
	private String name;
	/** 長さ・精度を持つか */
	private boolean supportSize;
	/** 位取りを持つか */
	private boolean supportScale;
	/** javaのTypesの定数 */
	private int sqlType;
	/**
	 * コンストラクタ
	 * @param name データ型名称
	 * @param supportSize 長さ・精度を持つか
	 * @param supportScale 位取りを持つか
	 * @param sqlType javaのTypesの定数
	 */
	StandardSQLDataType(String name, boolean supportSize,
			boolean supportScale, int sqlType) {
		this.name = name;
		this.supportSize = supportSize;
		this.supportScale = supportScale;
		this.sqlType = sqlType;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the supportSize
	 */
	public boolean isSupportSize() {
		return supportSize;
	}

	/**
	 * @return the supportScale
	 */
	public boolean isSupportScale() {
		return supportScale;
	}

	/**
	 * @return the sqlType
	 */
	public int getSqlType() {
		return sqlType;
	}

	public static StandardSQLDataType getStandardSQLDataType(String name) {
		for (StandardSQLDataType dt : values()) {
			if (dt.getName().equals(name)) {
				return dt;
			}
		}
		return null;
	}
}

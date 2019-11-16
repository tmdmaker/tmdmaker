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
package jp.sourceforge.tmdmaker.ui.util;

/**
 * Utilities for editing model.
 * 
 * @author nakag
 * 
 */
public class ModelEditUtils {
	private ModelEditUtils() {
	}

	/**
	 * 文字列を数値へ変換する。
	 * 
	 * @param value
	 *            数値を表す文字列
	 * 
	 * @return Integer 変換エラー時は0を返す。
	 */
	public static Integer toInteger(String value) {
		try {
			return Integer.valueOf(value);
		} catch (NumberFormatException e) {
			return 0;
		}
	}

	/**
	 * 数値を文字列へ変換する。
	 * 
	 * @param value
	 *            数値を表す文字列
	 * @return NULLの場合に空白を返す
	 */
	public static String toBlankIfNull(Integer value) {
		return value == null ? "" : String.valueOf(value);
	}

	/**
	 * 文字列を返す。
	 * 
	 * @param value
	 *            文字列
	 * @return NULLの場合に空白を返す
	 */
	public static String toBlankStringIfNull(String value) {
		return value == null ? "" : value;
	}
}

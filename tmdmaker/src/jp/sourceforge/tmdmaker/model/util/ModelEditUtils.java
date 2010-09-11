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
package jp.sourceforge.tmdmaker.model.util;

/**
 * モデル編集用ユーティリティ
 * 
 * @author nakaG
 * 
 */
public class ModelEditUtils {
	/**
	 * 
	 * @param value
	 *            文字列をintへ変換する。
	 * @return Integer 変換エラー時は0を返す。
	 */
	public static Integer toInteger(String value) {
		try {
			return Integer.valueOf(value);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return 0;
		}
	}
	public static String toBlankIfNull(Integer value) {
		return value == null ? "" : String.valueOf(value);
	}
	public static String toBlankStringIfNull(String value) {
		return value == null ? "" : value;
	}

}

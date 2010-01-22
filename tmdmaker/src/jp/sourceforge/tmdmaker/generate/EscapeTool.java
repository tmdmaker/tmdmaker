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
package jp.sourceforge.tmdmaker.generate;

import org.apache.commons.lang.StringEscapeUtils;

/**
 * HTMLエスケープ処理用ツール
 * 
 * @author nakaG
 * 
 */
public class EscapeTool {
	/**
	 * htmlエスケープ
	 * 
	 * @param string
	 * @return エスケープ後文字列
	 */
	public String html(Object string) {
		if (string == null) {
			return null;
		}
		return StringEscapeUtils.escapeHtml(String.valueOf(string));
	}

	/**
	 * htmlエスケープ。空文字の場合にスペースに変換する。
	 * 
	 * @param string
	 * @return エスケープ後文字列
	 */
	public String html2(Object string) {
		String converted = html(string);
		if (converted == null || converted.length() == 0) {
			converted = "&nbsp;";
		}
		return converted;
	}
}

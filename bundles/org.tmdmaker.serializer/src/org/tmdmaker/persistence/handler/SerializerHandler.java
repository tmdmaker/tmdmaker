/*
 * Copyright 2009-2012 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
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
package org.tmdmaker.persistence.handler;

import org.tmdmaker.core.model.Diagram;

/**
 * シリアライズ時に適用するハンドラ処理のインターフェース
 * 
 * @author nakaG
 * 
 */
public interface SerializerHandler {
	/**
	 * ファイルから読み込んだ文字列データをデシリアライズ前に操作する。
	 * 
	 * @param in
	 *            変換前文字列
	 * @return 変換後文字列
	 */
	String handleBeforeDeserialize(String in);

	/**
	 * デシリアライズ後のダイアグラムオブジェクトを操作する。
	 * 
	 * @param diagram
	 *            変換前ダイアグラム
	 * @return 変換後ダイアグラム
	 */
	Diagram handleAfterDeserialize(Diagram diagram);

	/**
	 * シリアライズ前のダイアグラムオブジェクトを操作する。
	 * 
	 * @param diagram
	 *            変換前ダイアグラム
	 * @return 変換後ダイアグラム
	 */
	Diagram handleBeforeSerialize(Diagram diagram);

	/**
	 * ファイルから読み込んだ文字列データをデシリアライズ後に操作する。
	 * 
	 * @param in
	 *            変換前文字列
	 * @return 変換後文字列
	 */
	String handleAfterSerialize(String in);
}

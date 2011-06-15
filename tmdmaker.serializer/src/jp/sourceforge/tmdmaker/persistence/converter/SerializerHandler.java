/*
 * Copyright 2009-2011 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
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
package jp.sourceforge.tmdmaker.persistence.converter;

import jp.sourceforge.tmdmaker.model.Diagram;

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
	 *            返還前文字列
	 * @return 変換後文字列
	 */
	String handleBeforeDeserialize(String in);

	/**
	 * デシリアライズ後のダイアグラムオブジェクトを操作する。
	 */
	Diagram handleAfterDeserialize(Diagram in);

	/**
	 * シリアライズ前のダイアグラムオブジェクトを操作する。
	 * 
	 * @param diagram
	 * @return
	 */
	Diagram handleBeforeSerialize(Diagram diagram);
	
	/**
	 * 
	 * @param in
	 * @return
	 */
	String handleAfterSerialize(String in);
}

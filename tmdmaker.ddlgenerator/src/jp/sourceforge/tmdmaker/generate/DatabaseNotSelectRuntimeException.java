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
package jp.sourceforge.tmdmaker.generate;

import jp.sourceforge.tmdmaker.model.generate.GeneratorRuntimeException;

/**
 * 対象データベースが選択されていない場合に発生する例外
 * 
 * @author nakaG
 * 
 */
@SuppressWarnings("serial")
public class DatabaseNotSelectRuntimeException extends
		GeneratorRuntimeException {
	/**
	 * コンストラクタ
	 */
	public DatabaseNotSelectRuntimeException() {
		super("対象データベースが選択されていません。");
	}
}

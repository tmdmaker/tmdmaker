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
package jp.sourceforge.tmdmaker.model.dialect;

import java.util.List;

/**
 * データベース毎に異なる情報を提供するクラス
 * 
 * @author nakaG
 * 
 */
public interface DialectProvider {
	/**
	 * 対応しているデータベース名を取得する
	 * 
	 * @return データベース名のリスト
	 */
	List<String> getDatabaseList();
}

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
package jp.sourceforge.tmdmaker.generate.ddl;

import java.util.ArrayList;
import java.util.List;

import org.apache.ddlutils.PlatformFactory;

/**
 * 物理実装用の情報を提供するクラス
 * 
 * @author nakaG
 * 
 */
public class DatabaseDialectProvider {
	/**
	 * 対応データベース名の一覧を取得する。
	 * 
	 * @return データベース名のリスト
	 */
	public List<String> getDatabaseList() {
		List<String> result = new ArrayList<String>();
		for (String platform : PlatformFactory.getSupportedPlatforms()) {
			result.add(platform);
		}
		return result;
	}
}

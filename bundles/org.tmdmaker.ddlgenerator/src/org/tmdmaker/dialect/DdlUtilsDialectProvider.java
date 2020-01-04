/*
 * Copyright 2009-2019 TMD-Maker Project <https://www.tmdmaker.org>
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
package org.tmdmaker.dialect;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.ddlutils.PlatformFactory;
import org.tmdmaker.model.dialect.DialectProvider;

/**
 * DdlUtilsを使った対応データベースについての情報を提供するクラス
 * 
 * @author nakaG
 * 
 */
public class DdlUtilsDialectProvider implements DialectProvider {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.tmdmaker.model.dialect.DialectProvider#getDatabaseList()
	 */
	@Override
	public List<String> getDatabaseList() {
		List<String> result = new ArrayList<String>();
		for (String platform : PlatformFactory.getSupportedPlatforms()) {
			result.add(platform);
		}
		Collections.sort(result);
		return result;
	}

}

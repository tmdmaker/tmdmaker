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
package jp.sourceforge.tmdmaker.dialect;

import java.util.Arrays;
import java.util.List;

import jp.sourceforge.tmdmaker.TMDPlugin;
import jp.sourceforge.tmdmaker.extension.PluginExtensionPointFactory;

/**
 * DialectProviderを取得するためのFactory
 * 
 * @author nakaG
 * 
 */
public class DialectProviderFactory {
	private static PluginExtensionPointFactory<DialectProvider> factory = new PluginExtensionPointFactory<DialectProvider>(
			TMDPlugin.PLUGIN_ID + ".dialect.provider");

	/**
	 * DialectProviderを取得する。
	 * 
	 * @return 取得したDialectProvider
	 */
	public static DialectProvider getDialectProvider() {
		DialectProvider provider = factory.getInstance();
		if (provider == null) {
			provider = new DialectProvider() {

				/**
				 * {@inheritDoc}
				 * 
				 * @see jp.sourceforge.tmdmaker.dialect.DialectProvider#getDatabaseList()
				 */
				@Override
				public List<String> getDatabaseList() {
					return Arrays.asList("");
				}
			};

		}
		return provider;
	}
}

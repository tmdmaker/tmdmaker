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
package org.tmdmaker.extension;

import java.util.Arrays;
import java.util.List;

import org.tmdmaker.model.dialect.DialectProvider;

/**
 * DialectProviderを取得するためのFactory
 * 
 * @author nakaG
 * 
 */
public class DialectProviderFactory {
	private static PluginExtensionPointFactory<DialectProvider> factory = new PluginExtensionPointFactory<DialectProvider>(
			"tmdmaker.dialect.provider"); //$NON-NLS-1$

	private static final DialectProvider EMPTY = new DialectProvider() {

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.tmdmaker.model.dialect.DialectProvider#getDatabaseList()
		 */
		@Override
		public List<String> getDatabaseList() {
			return Arrays.asList("");
		}
	};

	/**
	 * DialectProviderを取得する。
	 * 
	 * @return 取得したDialectProvider
	 */
	public static DialectProvider getDialectProvider() {
		return factory.getInstance(EMPTY);
	}
}

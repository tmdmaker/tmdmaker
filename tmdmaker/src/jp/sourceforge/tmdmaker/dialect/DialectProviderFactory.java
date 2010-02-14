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
package jp.sourceforge.tmdmaker.dialect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.sourceforge.tmdmaker.TMDPlugin;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;

/**
 * DialectProviderを取得するためのFactory
 * 
 * @author nakaG
 * 
 */
public class DialectProviderFactory {
	/**
	 * DialectProviderを取得する。
	 * 
	 * @return 取得したDialectProvider
	 */
	public static DialectProvider getDialectProvider() {
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IExtensionPoint point = registry.getExtensionPoint(TMDPlugin.PLUGIN_ID
				+ ".dialect.provider");
		IExtension[] extensions = point.getExtensions();

		List<DialectProvider> list = new ArrayList<DialectProvider>();
		for (IExtension ex : extensions) {
			for (IConfigurationElement ce : ex.getConfigurationElements()) {
				try {
					list.add((DialectProvider) ce
							.createExecutableExtension("class"));
				} catch (CoreException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		if (list.size() > 0) {
			return list.get(0);
		} else {
			// DialectProviderが取得できない場合の空クラス
			return new DialectProvider() {

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
	}
}

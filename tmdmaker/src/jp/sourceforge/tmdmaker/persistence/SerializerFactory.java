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
package jp.sourceforge.tmdmaker.persistence;

import java.util.ArrayList;
import java.util.List;

import jp.sourceforge.tmdmaker.TMDPlugin;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;

/**
 * シリアライザのファクトリクラス
 * 
 * @author nakaG
 * 
 */
public class SerializerFactory {
	/**
	 * シリアライザを取得する
	 * 
	 * @return シリアライザのインスタンス
	 */
	public static Serializer getInstance() {
		IExtensionRegistry registry = Platform.getExtensionRegistry();

		IExtensionPoint point = registry.getExtensionPoint(TMDPlugin.PLUGIN_ID
				+ ".persisitence.serializer");
		IExtension[] extensions = point.getExtensions();

		List<Serializer> serializerList = new ArrayList<Serializer>();
		for (IExtension ex : extensions) {
			for (IConfigurationElement ce : ex.getConfigurationElements()) {
				try {
					serializerList.add((Serializer) ce
							.createExecutableExtension("class"));
				} catch (CoreException e) {
					throw new SerializationException(e);
				}
			}
		}
		if (serializerList.size() > 0) {
			return serializerList.get(0);
		} else {
			throw new SerializationException("シリアライザプラグインが取得できませんでした。");
		}
	}
}

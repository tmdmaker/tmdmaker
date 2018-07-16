/*
 * Copyright 2009-2018 TMD-Maker Project <https://tmdmaker.osdn.jp/>
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
package jp.sourceforge.tmdmaker.extension;

import jp.sourceforge.tmdmaker.model.persistence.Serializer;

/**
 * シリアライザのファクトリクラス
 * 
 * @author nakaG
 * 
 */
public class SerializerFactory {
	private static PluginExtensionPointFactory<Serializer> factory = new PluginExtensionPointFactory<Serializer>(
			"tmdmaker.persisitence.serializer"); //$NON-NLS-1$

	/**
	 * シリアライザを取得する
	 * 
	 * @return シリアライザのインスタンス
	 */
	public static Serializer getInstance() {
		return factory.getInstance();
	}
}

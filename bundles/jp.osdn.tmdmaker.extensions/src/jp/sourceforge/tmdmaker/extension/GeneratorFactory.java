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

import java.util.Comparator;
import java.util.List;

import jp.sourceforge.tmdmaker.model.generate.Generator;

/**
 * contributeされているGeneratorを提供するクラス
 * 
 * @author nakaG
 * 
 */
public class GeneratorFactory {
	private static PluginExtensionPointFactory<Generator> factory;

	static {
		factory = new PluginExtensionPointFactory<Generator>(
				"tmdmaker.generators", //$NON-NLS-1$
				new Comparator<Generator>() {

					/**
					 * {@inheritDoc}
					 * 
					 * @see java.util.Comparator#compare(java.lang.Object,
					 *      java.lang.Object)
					 */
					@Override
					public int compare(Generator o1, Generator o2) {
						int compareResult = o1.getGroupName().compareTo(
								o2.getGroupName());
						if (compareResult == 0) {
							return o1.getGeneratorName().compareTo(
									o2.getGeneratorName());
						} else {
							return compareResult;
						}
					}
				});

	}

	/**
	 * Generatorを返す。
	 * 
	 * @return 取得したGeneratorのリスト
	 */
	public static List<Generator> getGenerators() {
		return factory.getInstances();
	}
}

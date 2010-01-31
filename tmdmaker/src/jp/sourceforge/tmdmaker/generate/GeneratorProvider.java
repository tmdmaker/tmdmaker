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
package jp.sourceforge.tmdmaker.generate;

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
 * @author hiro
 *
 */
public class GeneratorProvider {
//	static {
//		IExtensionRegistry registry = Platform.getExtensionRegistry();
//		registry.addRegistryChangeListener(new IRegistryChangeListener() {
//
//			@Override
//			public void registryChanged(IRegistryChangeEvent event) {
//				System.out.println("RegistryChanged.");
//			}
//			
//		}, TMDPlugin.PLUGIN_ID);
//		
//	}
	public static List<Generator> getGenerators() {
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IExtensionPoint point = registry.getExtensionPoint(TMDPlugin.PLUGIN_ID + ".generators");
		IExtension[] extensions = point.getExtensions();

		List<Generator> list = new ArrayList<Generator>();
		for (IExtension ex : extensions) {
			for (IConfigurationElement ce : ex.getConfigurationElements()) {
				try {
					list.add((Generator) ce.createExecutableExtension("class"));
				} catch (CoreException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		return list;
	}
}
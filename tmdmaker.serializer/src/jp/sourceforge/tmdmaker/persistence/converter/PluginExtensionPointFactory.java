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
package jp.sourceforge.tmdmaker.persistence.converter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IRegistryEventListener;
import org.eclipse.core.runtime.Platform;

/**
 * @author nakaG
 * 
 */
public class PluginExtensionPointFactory<T> {
	private String extensionPointId;
	private List<T> instanceList;
	private IExtensionRegistry registry;

	public PluginExtensionPointFactory(String extensionPoint) {
		this.extensionPointId = extensionPoint;
		this.instanceList = new ArrayList<T>();
		this.registry = Platform.getExtensionRegistry();
		this.registry.addListener(new IRegistryEventListener() {
			
			@Override
			public void removed(IExtensionPoint[] extensionPoints) {
				// TODO Auto-generated method stub
				System.out.println("removed(IExtensionPoint[] extensionPoints)");
			}
			
			@Override
			public void removed(IExtension[] extensions) {
				// TODO Auto-generated method stub
				System.out.println("removed(IExtension[] extensions");				
			}
			
			@Override
			public void added(IExtensionPoint[] extensionPoints) {
				// TODO Auto-generated method stub
				System.out.println("added(IExtensionPoint[] extensionPoints)");								
			}
			
			@Override
			public void added(IExtension[] extensions) {
				// TODO Auto-generated method stub
				System.out.println("added(IExtension[] extensions)");								
			}
		}, extensionPointId);
		setup();
	}

	public T getInstance() {
		return instanceList.get(0);
	}

	public List<T> getInstances() {
		return Collections.unmodifiableList(instanceList);
	}

	@SuppressWarnings("unchecked")
	private void setup() {
		instanceList.clear();

		IExtensionPoint point = registry.getExtensionPoint(extensionPointId);
		if (point == null) {
			System.out.println(extensionPointId + " is not exists.");
			return;
		}
		IExtension[] extensions = point.getExtensions();

		for (IExtension ex : extensions) {
			for (IConfigurationElement ce : ex.getConfigurationElements()) {
				try {
					instanceList.add((T) ce.createExecutableExtension("class"));
				} catch (CoreException e) {
					throw new PluginExtensionPointRuntimeException(e);
				}
			}
		}

	}
}

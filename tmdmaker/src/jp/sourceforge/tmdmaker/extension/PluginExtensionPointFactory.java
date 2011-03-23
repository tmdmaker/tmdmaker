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
package jp.sourceforge.tmdmaker.extension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IRegistryEventListener;
import org.eclipse.core.runtime.Platform;

/**
 * TMD-Makerのプラグイン拡張を取得するファクトリクラス
 * 
 * @author nakaG
 * 
 */
public class PluginExtensionPointFactory<T> {
	/** 取得対象のExtension Point ID */
	private String extensionPointId;
	/** 取得したプラグイン拡張のリスト */
	private List<T> instanceList;
	/** registory */
	private IExtensionRegistry registry;
	/** 取得したプラグイン拡張のリストのソート時に利用 */
	private Comparator<T> comparator;

	/**
	 * コンストラクタ
	 * 
	 * @param extensionPoint
	 *            拡張ポイント
	 */
	public PluginExtensionPointFactory(String extensionPoint) {
		this(extensionPoint, null);
	}

	/**
	 * コンストラクタ
	 * 
	 * @param extensionPoint
	 *            拡張ポイント
	 * @param comparator
	 *            ソート用
	 */
	public PluginExtensionPointFactory(String extensionPoint,
			Comparator<T> comparator) {
		this.extensionPointId = extensionPoint;
		this.instanceList = new ArrayList<T>();
		this.registry = Platform.getExtensionRegistry();
		this.comparator = comparator;
		this.registry.addListener(new IRegistryEventListener() {

			@Override
			public void removed(IExtensionPoint[] extensionPoints) {
				System.out
						.println("removed(IExtensionPoint[] extensionPoints)");
				setup();
			}

			@Override
			public void removed(IExtension[] extensions) {
				System.out.println("removed(IExtension[] extensions");
				setup();
			}

			@Override
			public void added(IExtensionPoint[] extensionPoints) {
				System.out.println("added(IExtensionPoint[] extensionPoints)");
				setup();
			}

			@Override
			public void added(IExtension[] extensions) {
				System.out.println("added(IExtension[] extensions)");
				setup();
			}
		}, extensionPointId);
		setup();
	}

	/**
	 * プラグイン拡張を1件取得する。ソート後の最初の1件を返す。
	 * 
	 * @return プラグイン拡張。プラグイン拡張が存在しない場合はnullを返す。
	 */
	public T getInstance() {
		if (instanceList.size() >= 1) {
			return instanceList.get(0);
		} else {
			return null;
		}
	}

	/**
	 * プラグイン拡張を取得する。ソート後のリストを返す。
	 * 
	 * @return プラグイン拡張のリスト。プラグイン拡張が存在しない場合は空のリストを返す。
	 */
	public List<T> getInstances() {
		return Collections.unmodifiableList(instanceList);
	}

	private void setup() {
		instanceList.clear();

		IExtensionPoint point = registry.getExtensionPoint(extensionPointId);
		if (point == null) {
			System.out.println(extensionPointId + " is not exists.");
			return;
		}

		for (IExtension ex : point.getExtensions()) {
			addExsecutableExtensionsFromExtension(ex);
		}
		if (comparator != null) {
			Collections.sort(instanceList, comparator);
		}
	}

	@SuppressWarnings("unchecked")
	private void addExsecutableExtensionsFromExtension(IExtension ex) {
		for (IConfigurationElement ce : ex.getConfigurationElements()) {
			try {
				instanceList.add((T) ce.createExecutableExtension("class"));
			} catch (CoreException e) {
				throw new PluginExtensionPointRuntimeException(e);
			}
		}
	}
}

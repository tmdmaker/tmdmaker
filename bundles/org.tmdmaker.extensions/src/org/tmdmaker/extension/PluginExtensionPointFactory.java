/*
 * Copyright 2009-2019 TMD-Maker Project <https://tmdmaker.osdn.jp/>
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	/** registry */
	private IExtensionRegistry registry;
	/** 取得したプラグイン拡張のリストのソート時に利用 */
	private Comparator<T> comparator;
	/** logging */
	private static Logger logger = LoggerFactory.getLogger(PluginExtensionPointFactory.class);

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
	public PluginExtensionPointFactory(String extensionPoint, Comparator<T> comparator) {
		this.extensionPointId = extensionPoint;
		this.instanceList = new ArrayList<T>();
		this.registry = Platform.getExtensionRegistry();
		this.comparator = comparator;
		this.registry.addListener(new IRegistryEventListener() {

			@Override
			public void removed(IExtensionPoint[] extensionPoints) {
				logger.debug("removed(IExtensionPoint[] extensionPoints)");
				setup();
			}

			@Override
			public void removed(IExtension[] extensions) {
				logger.debug("removed(IExtension[] extensions");
				setup();
			}

			@Override
			public void added(IExtensionPoint[] extensionPoints) {
				logger.debug("added(IExtensionPoint[] extensionPoints)");
				setup();
			}

			@Override
			public void added(IExtension[] extensions) {
				logger.debug("added(IExtension[] extensions)");
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
		if (!instanceList.isEmpty()) {
			return instanceList.get(0);
		} else {
			return null;
		}
	}

	/**
	 * プラグイン拡張を1件取得する。ソート後の最初の1件を返す。 取得できなかった場合は引数の値を返す。
	 * 
	 * @param empty
	 *            インスタンスが取得できなかった場合に返す値
	 * @return プラグイン拡張。プラグイン拡張が存在しない場合はemptyを返す。
	 */
	public T getInstance(T empty) {
		T instance = getInstance();
		if (instance == null) {
			instance = empty;
		}
		return instance;
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
			logger.debug("{} is not exists.", extensionPointId);
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
				instanceList.add((T) ce.createExecutableExtension("class")); //$NON-NLS-1$
			} catch (CoreException e) {
				logger.warn(e.getMessage());
				throw new PluginExtensionPointRuntimeException(e);
			}
		}
	}
}

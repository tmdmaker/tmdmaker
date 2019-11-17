/*
 * Copyright 2016-2019 TMD-Maker Project <https://tmdmaker.osdn.jp/>
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


package org.tmdmaker.sphinx.generate

import org.eclipse.core.runtime.Plugin
import org.osgi.framework.BundleContext

 /**
 * TMDから、sphinx を使用したドキュメントを生成するためのプラグイン
 * 
 * TMDごとに、リレーションシップの検証表、アトリビュートリスト、キーの定義表を生成する。
 * 
 * @author tohosaku
 */
class Activator extends Plugin {
	// The plug-in ID
	public static final String PLUGIN_ID = "tmdmaker.sphinx"
	// The shared instance
	static Activator plugin

	/** 
	 * The constructor
	 */
	new() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	override start(BundleContext context) throws Exception {
		super.start(context)
		plugin = this
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	override stop(BundleContext context) throws Exception {
		plugin = null
		super.stop(context)
	}

	/** 
	 * Returns the shared instance
	 * @return the shared instance
	 */
	def static Activator getDefault() {
		return plugin
	}
}

/*
 * Copyright 2009-2012 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
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
package org.tmdmaker.ui.preferences;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;

/**
 * 設定リスナー
 * 
 * @author nakaG
 * 
 */
public interface IPreferenceListener extends IPropertyChangeListener {
	/**
	 * 起動時の設定読み込み
	 * 
	 * @param store
	 */
	void preferenceStart(IPreferenceStore store);
}

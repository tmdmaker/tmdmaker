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
package org.tmdmaker.ui.preferences.appearance;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.StringConverter;
import org.tmdmaker.ui.Activator;

/**
 * 外観初期化
 * 
 * @author nakaG
 * 
 */
public class AppearancePreferenceInitializer extends
		AbstractPreferenceInitializer {

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
	 */
	@Override
	public void initializeDefaultPreferences() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();

		for (ModelAppearance a : ModelAppearance.values()) {
			store.setDefault(a.getBackgroundColorPropertyName(),
					StringConverter.asString(a.getBackground()));
		}
		for (ModelAppearance a : ModelAppearance.values()) {
			store.setDefault(a.getFontColorPropertyName(),
					StringConverter.asString(a.getFont()));
		}
		store.setDefault(AppearancePreferenceConstants.P_ENTITY_COLOR_ENABLED,
				StringConverter.asString(AppearanceSetting.ENABLE_COLOR));

	}
}

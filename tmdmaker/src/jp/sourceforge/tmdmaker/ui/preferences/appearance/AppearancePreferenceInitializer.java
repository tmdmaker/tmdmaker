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
package jp.sourceforge.tmdmaker.ui.preferences.appearance;

import jp.sourceforge.tmdmaker.TMDPlugin;
import jp.sourceforge.tmdmaker.ui.setting.AppearanceSetting;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.StringConverter;

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
		IPreferenceStore store = TMDPlugin.getDefault().getPreferenceStore();
		store.setDefault(AppearancePreferenceConstants.P_RESOURCE_ENTITY_COLOR,
				StringConverter.asString(AppearanceSetting.RESOURCE_RGB));
		store.setDefault(AppearancePreferenceConstants.P_EVENT_ENTITY_COLOR,
				StringConverter.asString(AppearanceSetting.EVENT_RGB));
		store.setDefault(
				AppearancePreferenceConstants.P_COMBINATION_TABLE_COLOR,
				StringConverter
						.asString(AppearanceSetting.COMBINATION_TABLE_RGB));
		store.setDefault(AppearancePreferenceConstants.P_MAPPING_LIST_COLOR,
				StringConverter.asString(AppearanceSetting.MAPPING_LIST_RGB));
		store.setDefault(AppearancePreferenceConstants.P_RECURSIVE_TABLE_COLOR,
				StringConverter.asString(AppearanceSetting.RECURSIVE_TABLE_RGB));
		store.setDefault(AppearancePreferenceConstants.P_RESOURCE_SUBSET_COLOR,
				StringConverter.asString(AppearanceSetting.RESOURCE_SUBSET_RGB));
		store.setDefault(AppearancePreferenceConstants.P_EVENT_SUBSET_COLOR,
				StringConverter.asString(AppearanceSetting.EVENT_SUBSET_RGB));
		store.setDefault(AppearancePreferenceConstants.P_VIRTUAL_ENTITY_COLOR,
				StringConverter.asString(AppearanceSetting.VIRTUAL_ENTITY_RGB));
		store.setDefault(AppearancePreferenceConstants.P_RESOURCE_VIRTUAL_ENTITY_COLOR,
				StringConverter.asString(AppearanceSetting.RESOURCE_VIRTUAL_ENTITY_RGB));
		store.setDefault(AppearancePreferenceConstants.P_EVENT_VIRTUAL_ENTITY_COLOR,
				StringConverter.asString(AppearanceSetting.EVENT_VIRTUAL_ENTITY_RGB));
		store.setDefault(AppearancePreferenceConstants.P_MULTIVALUE_OR_COLOR,
				StringConverter.asString(AppearanceSetting.MULTIVALUE_OR_RGB));
		store.setDefault(AppearancePreferenceConstants.P_SUPERSET_COLOR,
				StringConverter.asString(AppearanceSetting.SUPERSET_RGB));
		store.setDefault(AppearancePreferenceConstants.P_LAPUTA_COLOR,
				StringConverter.asString(AppearanceSetting.LAPUTA_RGB));

		store.setDefault(AppearancePreferenceConstants.P_ENTITY_COLOR_ENABLED,
				StringConverter.asString(AppearanceSetting.ENABLE_COLOR));

	}

}

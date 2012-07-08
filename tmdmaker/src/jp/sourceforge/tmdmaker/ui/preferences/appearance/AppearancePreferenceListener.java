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

import jp.sourceforge.tmdmaker.ui.preferences.IPreferenceListener;
import jp.sourceforge.tmdmaker.ui.setting.AppearanceSetting;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.StringConverter;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.graphics.RGB;

/**
 * 外観リスナー
 * 
 * @author nakaG
 * 
 */
public class AppearancePreferenceListener implements IPreferenceListener {
	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.util.IPropertyChangeListener#propertyChange(org.eclipse.jface.util.PropertyChangeEvent)
	 */
	@Override
	public void propertyChange(PropertyChangeEvent event) {
		AppearanceSetting config = AppearanceSetting.getInstance();

		if (event.getProperty().equals(
				AppearancePreferenceConstants.P_RESOURCE_ENTITY_COLOR)) {
			config.setResorceRGB(convertRGBIfNeed(event.getNewValue()));
		} else if (event.getProperty().equals(
				AppearancePreferenceConstants.P_EVENT_ENTITY_COLOR)) {
			config.setEventRGB(convertRGBIfNeed(event.getNewValue()));
		} else if (event.getProperty().equals(
				AppearancePreferenceConstants.P_COMBINATION_TABLE_COLOR)) {
			config.setCombinationTableRGB(convertRGBIfNeed(event.getNewValue()));
		} else if (event.getProperty().equals(
				AppearancePreferenceConstants.P_MAPPING_LIST_COLOR)) {
			config.setMappingListRGB(convertRGBIfNeed(event.getNewValue()));
		} else if (event.getProperty().equals(
				AppearancePreferenceConstants.P_RECURSIVE_TABLE_COLOR)) {
			config.setRecursiveTableRGB(convertRGBIfNeed(event.getNewValue()));
		} else if (event.getProperty().equals(
				AppearancePreferenceConstants.P_RESOURCE_SUBSET_COLOR)) {
			config.setResourceSubsetRGB(convertRGBIfNeed(event.getNewValue()));
		} else if (event.getProperty().equals(
				AppearancePreferenceConstants.P_EVENT_SUBSET_COLOR)) {
			config.setEventSubsetRGB(convertRGBIfNeed(event.getNewValue()));
		} else if (event.getProperty().equals(
				AppearancePreferenceConstants.P_VIRTUAL_ENTITY_COLOR)) {
			config.setVirtualEntityRGB(convertRGBIfNeed(event.getNewValue()));
		} else if (event.getProperty().equals(
				AppearancePreferenceConstants.P_SUPERSET_COLOR)) {
			config.setSupersetRGB(convertRGBIfNeed(event.getNewValue()));
		} else if (event.getProperty().equals(
				AppearancePreferenceConstants.P_MULTIVALUE_OR_COLOR)) {
			config.setMultivalueOrRGB(convertRGBIfNeed(event.getNewValue()));
		} else if (event.getProperty().equals(
				AppearancePreferenceConstants.P_LAPUTA_COLOR)) {
			config.setLaputaRGB(convertRGBIfNeed(event.getNewValue()));
		} else if (event.getProperty().equals(
				AppearancePreferenceConstants.P_ENTITY_COLOR_ENABLED)) {
			System.out.println("P_ENTITY_COLOR_ENABLED");
			config.setColorEnabled(convertBooleanIfNeed(event.getNewValue()));
		}
	}

	private RGB convertRGBIfNeed(Object value) {
		if (value instanceof RGB) {
			return (RGB) value;
		}
		return StringConverter.asRGB((String) value);
	}

	private boolean convertBooleanIfNeed(Object value) {
		System.out.println(value);
		if (value instanceof Boolean) {
			return (Boolean) value;
		}
		return StringConverter.asBoolean((String) value);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.ui.preferences.IPreferenceListener#preferenceStart(org.eclipse.jface.preference.IPreferenceStore)
	 */
	@Override
	public void preferenceStart(IPreferenceStore store) {
		AppearanceSetting config = AppearanceSetting.getInstance();
		config.setResorceRGB(StringConverter.asRGB(store
				.getString(AppearancePreferenceConstants.P_RESOURCE_ENTITY_COLOR)));
		config.setEventRGB(StringConverter.asRGB(store
				.getString(AppearancePreferenceConstants.P_EVENT_ENTITY_COLOR)));
		config.setCombinationTableRGB(StringConverter.asRGB(store
				.getString(AppearancePreferenceConstants.P_COMBINATION_TABLE_COLOR)));
		config.setMappingListRGB(StringConverter.asRGB(store
				.getString(AppearancePreferenceConstants.P_MAPPING_LIST_COLOR)));
		config.setRecursiveTableRGB(StringConverter.asRGB(store
				.getString(AppearancePreferenceConstants.P_RECURSIVE_TABLE_COLOR)));
		config.setResourceSubsetRGB(StringConverter.asRGB(store
				.getString(AppearancePreferenceConstants.P_RESOURCE_SUBSET_COLOR)));
		config.setEventSubsetRGB(StringConverter.asRGB(store
				.getString(AppearancePreferenceConstants.P_EVENT_SUBSET_COLOR)));
		config.setMultivalueOrRGB(StringConverter.asRGB(store
				.getString(AppearancePreferenceConstants.P_MULTIVALUE_OR_COLOR)));
		config.setVirtualEntityRGB(StringConverter.asRGB(store
				.getString(AppearancePreferenceConstants.P_VIRTUAL_ENTITY_COLOR)));
		String colorString = store
				.getString(AppearancePreferenceConstants.P_SUPERSET_COLOR);
		// if (colorString != null && colorString.length() > 0) {
		config.setSupersetRGB(StringConverter.asRGB(colorString));
		// }
		config.setLaputaRGB(StringConverter.asRGB(store
				.getString(AppearancePreferenceConstants.P_LAPUTA_COLOR)));
		config.setColorEnabled(store
				.getBoolean(AppearancePreferenceConstants.P_ENTITY_COLOR_ENABLED));
	}
}

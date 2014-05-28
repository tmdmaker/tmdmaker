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
import jp.sourceforge.tmdmaker.ui.preferences.IPreferenceListener;

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
				AppearancePreferenceConstants.P_ENTITY_COLOR_ENABLED)) {
			System.out.println("P_ENTITY_COLOR_ENABLED");
			config.setColorEnabled(convertBooleanIfNeed(event.getNewValue()));
			TMDPlugin.getDefault().update();
			return;
		}
		for (ModelAppearance a : ModelAppearance.values()) {
			if (event.getProperty().equals(a.getBackgroundColorPropertyName())) {
				System.out.println(a.getBackgroundColorPropertyName());
				RGB background = convertRGBIfNeed(event.getNewValue());
				config.setBackground(a, background);
				TMDPlugin.getDefault().update();
			} else if (event.getProperty().equals(a.getFontColorPropertyName())) {
				System.out.println(a.getFontColorPropertyName());
				RGB font = convertRGBIfNeed(event.getNewValue());
				config.setFont(a, font);
				TMDPlugin.getDefault().update();
			}
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
		for (ModelAppearance a : ModelAppearance.values()) {
			RGB background = StringConverter.asRGB(store.getString(a
					.getBackgroundColorPropertyName()));
			RGB font = StringConverter.asRGB(store.getString(a
					.getFontColorPropertyName()));
			config.setColors(a, background, font);
		}
		config.setColorEnabled(store
				.getBoolean(AppearancePreferenceConstants.P_ENTITY_COLOR_ENABLED));
		TMDPlugin.getDefault().update();
	}
}

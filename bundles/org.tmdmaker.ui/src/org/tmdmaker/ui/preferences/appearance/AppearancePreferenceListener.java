/*
 * Copyright 2009-2019 TMD-Maker Project <https://www.tmdmaker.org>
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

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.StringConverter;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.graphics.RGB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tmdmaker.ui.Activator;
import org.tmdmaker.ui.preferences.IPreferenceListener;

/**
 * 外観リスナー
 * 
 * @author nakaG
 * 
 */
public class AppearancePreferenceListener implements IPreferenceListener {
	/** logging */
	private static Logger logger = LoggerFactory.getLogger(AppearancePreferenceListener.class);

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
			logger.debug("P_ENTITY_COLOR_ENABLED");
			config.setColorEnabled(convertBooleanIfNeed(event.getNewValue()));
			Activator.getDefault().update();
			return;
		}
		for (ModelAppearance a : ModelAppearance.values()) {
			if (event.getProperty().equals(a.getBackgroundColorPropertyName())) {
				logger.debug(a.getBackgroundColorPropertyName());
				RGB background = convertRGBIfNeed(event.getNewValue());
				config.setBackground(a, background);
				Activator.getDefault().update();
			} else if (event.getProperty().equals(a.getFontColorPropertyName())) {
				logger.debug(a.getFontColorPropertyName());
				RGB font = convertRGBIfNeed(event.getNewValue());
				config.setFont(a, font);
				Activator.getDefault().update();
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
		logger.debug("original value = {}", value);
		if (value instanceof Boolean) {
			return (Boolean) value;
		}
		return StringConverter.asBoolean((String) value);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.tmdmaker.ui.preferences.IPreferenceListener#preferenceStart(org.eclipse.jface.preference.IPreferenceStore)
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
		Activator.getDefault().update();
	}
}

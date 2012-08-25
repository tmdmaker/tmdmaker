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
package jp.sourceforge.tmdmaker.ui.setting;

import java.util.HashMap;
import java.util.Map;

import jp.sourceforge.tmdmaker.ui.preferences.appearance.ModelAppearance;

import org.eclipse.swt.graphics.RGB;

/**
 * 外観の設定
 * 
 * @author nakaG
 */
public class AppearanceSetting {
	private static AppearanceSetting setting;

	public static final boolean ENABLE_COLOR = false;
	private boolean colorEnabled = ENABLE_COLOR;

	public static AppearanceSetting getInstance() {
		if (setting == null) {
			setting = new AppearanceSetting();
		}
		return setting;
	}

	/**
	 * @return the colorEnabled
	 */
	public boolean isColorEnabled() {
		return colorEnabled;
	}

	/**
	 * @param colorEnabled
	 *            the colorEnabled to set
	 */
	public void setColorEnabled(boolean colorEnabled) {
		this.colorEnabled = colorEnabled;
		System.out.println("setColorEnabled = " + colorEnabled);
	}

	private Map<ModelAppearance, Colors> colorMap = new HashMap<ModelAppearance, Colors>();

	public void setColors(ModelAppearance appearance, RGB background, RGB font) {
		Colors value = colorMap.get(appearance);
		if (value == null) {
			value = new Colors();
		}
		value.background = background;
		value.font = font;
		colorMap.put(appearance, value);
	}

	public void setBackground(ModelAppearance appearance, RGB background) {
		Colors value = colorMap.get(appearance);
		if (value == null) {
			value = new Colors();
		}
		value.background = background;
		colorMap.put(appearance, value);
	}

	public void setFont(ModelAppearance appearance, RGB font) {
		Colors value = colorMap.get(appearance);
		if (value == null) {
			value = new Colors();
		}
		value.font = font;
		colorMap.put(appearance, value);
	}

	public RGB getBackground(ModelAppearance appearance) {
		Colors value = colorMap.get(appearance);
		if (value != null) {
			return value.background;
		}
		return null;
	}

	public RGB getFont(ModelAppearance appearance) {
		Colors value = colorMap.get(appearance);
		if (value != null) {
			return value.font;
		}
		return null;
	}

	private static class Colors {
		public RGB background;
		public RGB font;
	}

}

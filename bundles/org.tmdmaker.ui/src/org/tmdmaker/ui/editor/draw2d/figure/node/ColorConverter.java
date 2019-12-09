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
package org.tmdmaker.ui.editor.draw2d.figure.node;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.swt.graphics.Color;
import org.tmdmaker.ui.preferences.appearance.AppearanceSetting;
import org.tmdmaker.ui.preferences.appearance.ModelAppearance;

/**
 * モデルの色設定を元にColorを取得するクラス.
 * 
 * @author nakag
 *
 */
public class ColorConverter {
	private ModelAppearance appearance;
	private AppearanceSetting config;

	public ColorConverter(ModelAppearance appearance) {
		super();
		this.appearance = appearance;
		this.config = AppearanceSetting.getInstance();
	}

	public Color createForegroundColor() {
		if (config.isColorEnabled() && appearance != null) {
			return new Color(null, config.getFont(appearance));
		} else {
			return ColorConstants.black;
		}
	}

	public Color createBackgroundColor() {
		if (config.isColorEnabled() && appearance != null) {
			return new Color(null, config.getBackground(appearance));
		} else {
			return ColorConstants.white;
		}
	}
}

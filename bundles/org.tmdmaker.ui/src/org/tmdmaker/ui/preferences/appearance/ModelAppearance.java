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

import org.eclipse.swt.graphics.RGB;

import org.tmdmaker.ui.Messages;

/**
 * モデルの外観設定
 * 
 * @author nakaG
 * 
 */
public enum ModelAppearance {
	RESOURCE(Messages.Resource, "resourceEntity", new RGB(0, 255, 255), new RGB(0, 0, 0)), //$NON-NLS-2$ //$NON-NLS-1$
	EVENT(Messages.Event, "eventEntity", new RGB(255, 204, 204), new RGB(0, 0, 0)), //$NON-NLS-2$ //$NON-NLS-1$
	COMBINATION_TABLE(Messages.CombinationTable, "combinationTable", new RGB(153, 255, 153), new RGB(0, 0, 0)), //$NON-NLS-2$ //$NON-NLS-1$
	MAPPING_LIST(Messages.MappingList, "mappingList", new RGB(0, 102, 204),	 new RGB(0, 0, 0)), //$NON-NLS-2$ //$NON-NLS-1$
	RECURSIVE_TABLE(Messages.RecursiveTable, "recursiveTable", new RGB(255, 153, 51), new RGB(0, 0, 0)), //$NON-NLS-2$ //$NON-NLS-1$
	RESOURCE_SUBSET(Messages.ResourceSubset, "resourceSubset", new RGB(0, 204, 204), new RGB(0, 0, 0)), //$NON-NLS-2$ //$NON-NLS-1$
	EVENT_SUBSET(Messages.EventSubset, "eventSubset", new RGB(255, 153, 153), new RGB(0, 0, 0)), //$NON-NLS-2$ //$NON-NLS-1$
	MULTIVALUE_OR(Messages.MultivalueOR, "multivalueOr", new RGB(255, 255, 102), new RGB(0, 0, 0)), //$NON-NLS-2$ //$NON-NLS-1$
	VIRTUAL_ENTITY(Messages.VirtualEntity, "virtualEntity", new RGB(255, 255, 255), new RGB(0, 0, 0)), //$NON-NLS-2$ //$NON-NLS-1$
	RESOURCE_VIRTUAL_ENTITY(Messages.ResourceVirtualEntity, "resourceVirtualEntity", new RGB(204, 204, 255), new RGB(0, 0, 0)), //$NON-NLS-2$ //$NON-NLS-1$
	EVENT_VIRTUAL_ENTITY(Messages.EventVirtualEntity, "eventVirtualEntity", new RGB(255, 204, 255), new RGB(0, 0, 0)), //$NON-NLS-2$ //$NON-NLS-1$
	SUPERSET_COLOR(Messages.VirtualSuperset, "supersetColor", new RGB(255, 255, 255), new RGB(0, 0, 0)), //$NON-NLS-2$ //$NON-NLS-1$
	LAPUTA_COLOR(Messages.Laputa, "laputaColor", new RGB(255, 255, 255), new RGB(0, 0, 0)), //$NON-NLS-2$ //$NON-NLS-1$
	TURBO_FILE_COLOR(Messages.TurboFile, "turboFileColor", new RGB(255, 255, 255), new RGB(0, 0, 0)), //$NON-NLS-2$ //$NON-NLS-1$
	MEMO_COLOR(Messages.Memo, "memoColor", new RGB(204, 255, 255), new RGB(0, 0, 0)); //$NON-NLS-1$

	/** 設定ページ用のラベル */
	private String label;
	/** 保存時のプロパティ名のプリフィックス */
	private String propertyPrefix;
	/** 罫線・フォント */
	private RGB font;
	/** 拝啓 */
	private RGB background;

	/**
	 * コンストラクタ
	 *
	 * @param label
	 *            ラベル
	 * @param propertyPrefix
	 *            プリフィックス
	 * @param background
	 *            背景
	 * @param font
	 *            罫線・フォント
	 */
	ModelAppearance(String label, String propertyPrefix, RGB background, RGB font) {
		this.label = label;
		this.propertyPrefix = propertyPrefix;
		this.background = background;
		this.font = font;
	}

	/**
	 * @return the propertyPrefix
	 */
	public String getPropertyPrefix() {
		return propertyPrefix;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @return the font
	 */
	public RGB getFont() {
		return font;
	}

	/**
	 * @return the background
	 */
	public RGB getBackground() {
		return background;
	}

	/**
	 * 背景色のプロパティ名取得
	 *
	 * @return 背景色のプロパティ名
	 */
	public String getBackgroundColorPropertyName() {
		return propertyPrefix + "Preference"; //$NON-NLS-1$
	}

	/**
	 * 罫線・フォントのプロパティ名取得
	 *
	 * @return 罫線・フォントのプロパティ名
	 */
	public String getFontColorPropertyName() {
		return propertyPrefix + "FontPreference"; //$NON-NLS-1$
	}
}

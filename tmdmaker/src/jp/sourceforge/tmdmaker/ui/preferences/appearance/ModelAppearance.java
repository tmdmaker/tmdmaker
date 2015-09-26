/*
 * Copyright 2009-2015 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
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

import org.eclipse.swt.graphics.RGB;

/**
 * モデルの外観設定
 * 
 * @author nakaG
 * 
 */
public enum ModelAppearance {
	RESOURCE("リソース :", "resourceEntity", new RGB(0, 255, 255), new RGB(0, 0, 0)),
	EVENT("イベント :", "eventEntity", new RGB(255, 204, 204), new RGB(0, 0, 0)),
	COMBINATION_TABLE("対照表 :", "combinationTable", new RGB(153, 255, 153), new RGB(0, 0, 0)),
	MAPPING_LIST("対応表 :", "mappingList", new RGB(0, 102, 204),	 new RGB(0, 0, 0)),
	RECURSIVE_TABLE("再帰表 :", "recursiveTable", new RGB(255, 153, 51), new RGB(0, 0, 0)),
	RESOURCE_SUBSET("サブセット（リソース） :", "resourceSubset", new RGB(0, 204, 204), new RGB(0, 0, 0)),
	EVENT_SUBSET("サブセット（イベント） :", "eventSubset", new RGB(255, 153, 153), new RGB(0, 0, 0)),
	MULTIVALUE_OR("多値のOR :", "multivalueOr", new RGB(255, 255, 102), new RGB(0, 0, 0)),
	VIRTUAL_ENTITY("みなしエンティティ :", "virtualEntity", new RGB(255, 255, 255), new RGB(0, 0, 0)),
	RESOURCE_VIRTUAL_ENTITY("みなしエンティティ（リソース） :", "resourceVirtualEntity", new RGB(204, 204, 255), new RGB(0, 0, 0)),
	EVENT_VIRTUAL_ENTITY("みなしエンティティ（イベント） :", "eventVirtualEntity", new RGB(255, 204, 255), new RGB(0, 0, 0)),
	SUPERSET_COLOR("みなしスーパーセット:", "supersetColor", new RGB(255, 255, 255), new RGB(0, 0, 0)),
	LAPUTA_COLOR("ラピュタ:", "laputaColor", new RGB(255, 255, 255), new RGB(0, 0, 0)),
	MEMO_COLOR("メモ：", "memoColor", new RGB(204, 255, 255), new RGB(0, 0, 0));

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
	private ModelAppearance(String label, String propertyPrefix, RGB background, RGB font) {
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
		return propertyPrefix + "Preference";
	}

	/**
	 * 罫線・フォントのプロパティ名取得
	 *
	 * @return 罫線・フォントのプロパティ名
	 */
	public String getFontColorPropertyName() {
		return propertyPrefix + "FontPreference";
	}
}

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

import jp.sourceforge.tmdmaker.TMDPlugin;

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

	// リソース
	public static final RGB RESOURCE_RGB = new RGB(0, 255, 255);
	private RGB resorceRGB = RESOURCE_RGB;

	// イベント
	public static final RGB EVENT_RGB = new RGB(255, 204, 204);
	private RGB eventRGB = EVENT_RGB;

	// 対照表
	public static final RGB COMBINATION_TABLE_RGB = new RGB(153, 255, 153);
	private RGB combinationTableRGB = COMBINATION_TABLE_RGB;

	// 対応表
	public static final RGB MAPPING_LIST_RGB = new RGB(0, 102, 204);
	private RGB mappingListRGB = MAPPING_LIST_RGB;

	// 再帰表
	public static final RGB RECURSIVE_TABLE_RGB = new RGB(255, 153, 51);
	private RGB recursiveTableRGB = RECURSIVE_TABLE_RGB;

	// サブセット
	public static final RGB RESOURCE_SUBSET_RGB = new RGB(0, 204, 204);
	private RGB resourceSubsetRGB = RESOURCE_SUBSET_RGB;

	public static final RGB EVENT_SUBSET_RGB = new RGB(255, 153, 153);
	private RGB eventSubsetRGB = EVENT_SUBSET_RGB;

	// MO
	public static final RGB MULTIVALUE_OR_RGB = new RGB(255, 255, 102);
	private RGB multivalueOrRGB = MULTIVALUE_OR_RGB;

	// スーパーセット
	public static final RGB SUPERSET_RGB = new RGB(255, 255, 255);
	private RGB supersetRGB = SUPERSET_RGB;

	// Normal VE
	public static final RGB VIRTUAL_ENTITY_RGB = new RGB(255, 255, 255);
	private RGB virtualEntityRGB = VIRTUAL_ENTITY_RGB;

	// ラピュタ
	public static final RGB LAPUTA_RGB = new RGB(255, 255, 255);
	private RGB laputaRGB = LAPUTA_RGB;

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
		apply();
	}

	/**
	 * @return the resorceRGB
	 */
	public RGB getResorceRGB() {
		return resorceRGB;
	}

	/**
	 * @param resorceRGB
	 *            the resorceRGB to set
	 */
	public void setResorceRGB(RGB resorceRGB) {
		this.resorceRGB = resorceRGB;
		apply();
	}

	/**
	 * @return the eventRGB
	 */
	public RGB getEventRGB() {
		return eventRGB;
	}

	/**
	 * @param eventRGB
	 *            the eventRGB to set
	 */
	public void setEventRGB(RGB event) {
		this.eventRGB = event;
		apply();
	}

	/**
	 * @return the combinationTableRGB
	 */
	public RGB getCombinationTableRGB() {
		return combinationTableRGB;
	}

	/**
	 * @param combinationTableRGB
	 *            the combinationTableRGB to set
	 */
	public void setCombinationTableRGB(RGB combinationTable) {
		this.combinationTableRGB = combinationTable;
		apply();
	}

	/**
	 * @return the mappingListRGB
	 */
	public RGB getMappingListRGB() {
		return mappingListRGB;
	}

	/**
	 * @param mappingListRGB
	 *            the mappingListRGB to set
	 */
	public void setMappingListRGB(RGB mappingList) {
		this.mappingListRGB = mappingList;
		apply();
	}

	/**
	 * @return the multivalueOrRGB
	 */
	public RGB getMultivalueOrRGB() {
		return multivalueOrRGB;
	}

	/**
	 * @param multivalueOrRGB
	 *            the multivalueOrRGB to set
	 */
	public void setMultivalueOrRGB(RGB multivalueOr) {
		this.multivalueOrRGB = multivalueOr;
		apply();
	}

	/**
	 * @return the recursiveTableRGB
	 */
	public RGB getRecursiveTableRGB() {
		return recursiveTableRGB;
	}

	/**
	 * @param recursiveTableRGB
	 *            the recursiveTableRGB to set
	 */
	public void setRecursiveTableRGB(RGB recursiveTable) {
		this.recursiveTableRGB = recursiveTable;
		apply();
	}

	/**
	 * @return the supersetRGB
	 */
	public RGB getSupersetRGB() {
		return supersetRGB;
	}

	/**
	 * @param supersetRGB
	 *            the supersetRGB to set
	 */
	public void setSupersetRGB(RGB superset) {
		this.supersetRGB = superset;
		apply();
	}

	/**
	 * @return the virtualEntityRGB
	 */
	public RGB getVirtualEntityRGB() {
		return virtualEntityRGB;
	}

	/**
	 * @param virtualEntityRGB
	 *            the virtualEntityRGB to set
	 */
	public void setVirtualEntityRGB(RGB ve) {
		this.virtualEntityRGB = ve;
		apply();
	}

	/**
	 * @return the laputaRGB
	 */
	public RGB getLaputaRGB() {
		return laputaRGB;
	}

	/**
	 * @param laputaRGB
	 *            the laputaRGB to set
	 */
	public void setLaputaRGB(RGB laputa) {
		this.laputaRGB = laputa;
		apply();
	}

	/**
	 * @return the resourceSubsetRGB
	 */
	public RGB getResourceSubsetRGB() {
		return resourceSubsetRGB;
	}

	/**
	 * @param resourceSubsetRGB the resourceSubsetRGB to set
	 */
	public void setResourceSubsetRGB(RGB resourceSubsetRGB) {
		this.resourceSubsetRGB = resourceSubsetRGB;
		apply();
	}

	/**
	 * @return the eventSubsetRGB
	 */
	public RGB getEventSubsetRGB() {
		return eventSubsetRGB;
	}

	/**
	 * @param eventSubsetRGB the eventSubsetRGB to set
	 */
	public void setEventSubsetRGB(RGB eventSubsetRGB) {
		this.eventSubsetRGB = eventSubsetRGB;
		apply();
	}
	private void apply() {
		TMDPlugin.getDefault().update();
	}
}

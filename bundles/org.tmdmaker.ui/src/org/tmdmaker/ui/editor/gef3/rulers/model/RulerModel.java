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
package org.tmdmaker.ui.editor.gef3.rulers.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.rulers.RulerProvider;

/**
 * TMDエディタに表示するルーラーのモデル
 * 
 * @author nakaG
 * 
 */
public class RulerModel implements Serializable {
	public static final String PROPERTY_CHILDREN = "property_children"; //$NON-NLS-1$
	public static final String PROPERTY_UNIT = "property_unit"; //$NON-NLS-1$
	private static final long serialVersionUID = 1L;
	/** プロパティ変更通知リスナー */
	protected PropertyChangeSupport listeners = new PropertyChangeSupport(this);

	/** ガイドのリスト */
	@SuppressWarnings("rawtypes")
	private List guides = new ArrayList();

	/** ユニット */
	private int unit;

	/**
	 * コンストラクタ
	 */
	public RulerModel() {
		this(RulerProvider.UNIT_INCHES);
	}

	/**
	 * コンストラクタ
	 * 
	 * @param unit
	 *            ユニット
	 */
	public RulerModel(int unit) {
		this.unit = unit;
	}

	/**
	 * ユニットを取得する
	 * 
	 * @return ユニット
	 */
	public int getUnit() {
		return unit;
	}

	/**
	 * ユニットを設定する
	 * 
	 * @param unit
	 *            ユニット
	 */
	public void setUnit(int unit) {
		int oldUnit = this.unit;
		this.unit = unit;
		listeners.firePropertyChange(PROPERTY_UNIT, oldUnit, unit);
	}

	/**
	 * ガイドを追加する
	 * 
	 * @param guide
	 *            ガイド
	 */
	@SuppressWarnings("unchecked")
	public void addGuide(GuideModel guide) {
		if (!guides.contains(guide)) {
			guides.add(guide);
			listeners.firePropertyChange(PROPERTY_CHILDREN, null, guide);
		}
	}

	/**
	 * ガイドを削除する
	 * 
	 * @param guide
	 *            ガイド
	 */
	public void removeGuide(GuideModel guide) {
		if (guides.remove(guide)) {
			listeners.firePropertyChange(PROPERTY_CHILDREN, guide, null);
		}
	}

	/**
	 * ガイドのリストを取得する。
	 * 
	 * @return ガイドのリスト
	 */
	@SuppressWarnings("rawtypes")
	public List getGuides() {
		return guides;
	}

	/**
	 * プロパティ変更通知リスナーを追加する。
	 * 
	 * @param listener
	 *            リスナー
	 */
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		listeners.addPropertyChangeListener(listener);
	}

	/**
	 * プロパティ変更通知リスナーを削除する。
	 * 
	 * @param listener
	 *            リスナー
	 */
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		listeners.removePropertyChangeListener(listener);
	}
}

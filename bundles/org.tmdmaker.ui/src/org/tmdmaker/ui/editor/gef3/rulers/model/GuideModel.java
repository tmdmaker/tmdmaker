/*
 * Copyright 2009-2011 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
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

/**
 * ルーラーに追加するガイドのモデル
 * 
 * @author nakaG
 * 
 */
public class GuideModel implements Serializable {
	public static final String PROPERTY_POSITION = "property_position"; //$NON-NLS-1$
	private static final long serialVersionUID = 1L;
	/** ガイドの位置 */
	int position;
	/** プロパティ変更通知リスナー */
	protected PropertyChangeSupport listeners = new PropertyChangeSupport(this);

	/**
	 * ガイドの位置を取得する
	 * 
	 * @return ガイドの位置
	 */
	public int getPosition() {
		return position;
	}

	/**
	 * ガイドの位置を設定する
	 * 
	 * @param position
	 *            ガイドの位置
	 */
	public void setPosition(int position) {
		if (this.position != position) {
			int oldPosition = this.position;
			this.position = position;
			listeners.firePropertyChange(PROPERTY_POSITION, oldPosition,
					position);
		}
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

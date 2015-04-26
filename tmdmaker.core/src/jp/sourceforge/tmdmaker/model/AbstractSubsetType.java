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
package jp.sourceforge.tmdmaker.model;

import java.util.List;

/**
 * サブセットとスーパーセットとの接点クラス
 *
 * @author nakag
 *
 */
@SuppressWarnings("serial")
public abstract class AbstractSubsetType<T extends AbstractEntityModel> extends ConnectableElement {

	/** サブセットタイプの向き */
	public static final String PROPERTY_DIRECTION = "_property_direction";
	/** モデルの向き（縦） */
	private boolean vertical = false;

	/**
	 * コンストラクタ.
	 */
	public AbstractSubsetType() {
		setConstraint(new Constraint());
	}

	/**
	 * 向き（縦）
	 *
	 * @return 縦の場合にtrueを返す
	 */
	public boolean isVertical() {
		return vertical;
	}

	/**
	 * 向き（縦）
	 *
	 * @param vertical
	 *            縦の場合にtrueを設定する。
	 */
	public void setVertical(boolean vertical) {
		boolean oldValue = this.vertical;
		this.vertical = vertical;
		firePropertyChange(PROPERTY_DIRECTION, oldValue, this.vertical);
	}

	/**
	 * スーパーセット取得.
	 *
	 * @return スーパーセット
	 */
	public abstract T getSuperset();

	/**
	 * サブセットのリスト取得.
	 *
	 * @return サブセットのリストを返す。サブセットを持たない場合は空のリストを返す.
	 */
	public abstract List<? extends AbstractEntityModel> getSubsetList();
}

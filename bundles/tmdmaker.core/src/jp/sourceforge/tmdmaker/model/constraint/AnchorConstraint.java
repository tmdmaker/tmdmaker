/*
 * Copyright 2009-2019 TMD-Maker Project <https://tmdmaker.osdn.jp/>
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
package jp.sourceforge.tmdmaker.model.constraint;

import java.io.Serializable;

/**
 * リレーションシップモデルの接続先（アンカー）制約.
 * 
 * @author nakag
 *
 */
public class AnchorConstraint implements Serializable {
	private static final long serialVersionUID = 1L;
	/** x point */
	private int x = -1;
	/** y point */
	private int y = -1;

	/**
	 * コンストラクタ
	 */
	public AnchorConstraint() {
	}

	/**
	 * コンストラクタ
	 * 
	 * @param x
	 *            x point
	 * @param y
	 *            y point
	 */
	public AnchorConstraint(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	/**
	 * 初期値か？
	 * 
	 * @return 初期値の場合にtrue
	 */
	public boolean isInitialPoint() {
		return x == -1 && y == -1;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof AnchorConstraint)) {
			return false;
		}
		AnchorConstraint other = (AnchorConstraint) obj;

		return other.x == x && other.y == y;
	}
}

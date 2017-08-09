/*
 * Copyright 2009-2017 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
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
 * モデルの制約
 *
 * @author nakaG
 *
 */
public class Constraint implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * The x value
	 */
	public int x;

	/**
	 * The y value
	 */
	public int y;

	/**
	 * The width
	 */
	public int width;

	/**
	 * The height
	 */
	public int height;

	/**
	 * コンストラクタ.
	 */
	public Constraint() {
		this(0, 0, -1, -1);
	}

	public Constraint(int x, int y, int width, int height) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public Constraint(Constraint constraint) {
		this.x = constraint.x;
		this.y = constraint.y;
		this.width = constraint.width;
		this.height = constraint.height;
	}

	public Constraint getCopy() {
		return new Constraint(this);
	}

	public Constraint getTranslated(int dx, int dy) {
		return getCopy().translate(dx, dy);
	}

	public Constraint translate(int dx, int dy) {
		x += dx;
		y += dy;
		return this;
	}

	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer();
		buf.append("x=" + x);
		buf.append(",");
		buf.append("y=" + y);
		buf.append(",");
		buf.append("h=" + height);
		buf.append(",");
		buf.append("w=" + width);
		return buf.toString();
	}
}

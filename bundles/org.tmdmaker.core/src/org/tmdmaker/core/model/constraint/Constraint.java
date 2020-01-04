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
package org.tmdmaker.core.model.constraint;

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
	private int x;

	/**
	 * The y value
	 */
	private int y;

	/**
	 * The width
	 */
	private int width;

	/**
	 * The height
	 */
	private int height;

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
		return "Constraint [x=" + x + ", y=" + y + ", width=" + width + ", height=" + height + "]";
	}

	/**
	 * 初期位置か？
	 * 
	 * @return 生成直後の初期位置の場合にtrueを返す.
	 */
	public boolean isInitialPosition() {
		return x == 0 && y == 0;
	}

	public Constraint newPosition(int x, int y) {
		Constraint c = this.getCopy();
		c.x = x;
		c.y = y;
		return c;
	}
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

}

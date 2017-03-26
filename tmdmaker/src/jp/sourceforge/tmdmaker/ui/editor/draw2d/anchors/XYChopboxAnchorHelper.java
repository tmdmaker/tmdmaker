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
package jp.sourceforge.tmdmaker.ui.editor.draw2d.anchors;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;

import jp.sourceforge.tmdmaker.model.constraint.AnchorConstraint;

/**
 * XYChopboxAnchor用のヘルパークラス
 * 
 * @author nakaG
 * 
 */
public class XYChopboxAnchorHelper {
	private int rightSide;
	private int leftSide;
	private int topSide;
	private int bottomSide;
	private Rectangle bounds;

	/**
	 * コンストラクタ
	 * 
	 * @param r
	 *            モデルの領域
	 */
	public XYChopboxAnchorHelper(Rectangle r) {
		this.bounds = r;
		rightSide = r.x + r.width;
		topSide = r.y + r.height;
		leftSide = r.x;
		bottomSide = r.y;
	}

	private boolean isCloseToLeft(Point s) {
		int x1 = s.x - leftSide;
		int x2 = rightSide - s.x;
		return x1 < x2;
	}

	private boolean isCloseToTop(Point s) {
		int y1 = s.y - bottomSide;
		int y2 = topSide - s.y;
		return y1 >= y2;
	}

	private boolean isCloseToX(int dx, int dy) {
		return dx >= dy;
	}

	/**
	 * AchorConstraintの取得
	 * 
	 * @param location
	 *            カーソル地点
	 * @return モデルの領域とカーソル地点を考慮したAchorConstraint
	 */
	public AnchorConstraint calculateAnchorConstraint(Point location) {
		Rectangle centerRectangle = RectangleUtils.calculateCenter(bounds);
		if (centerRectangle.contains(location)) {
			return new AnchorConstraint();
		}
		return calculateIntersectAnchorConstraint(location);
	}

	/**
	 * 交点のAchorConstraintの取得
	 * 
	 * @param s
	 *            カーソル地点
	 * @return モデルの領域とカーソル地点の交点のAchorConstraint
	 */
	private AnchorConstraint calculateIntersectAnchorConstraint(Point s) {
		Point point = getIntersectionPoint(s);
		int xp = 100 * (point.x - bounds.x) / bounds.width;
		int yp = 100 * (point.y - bounds.y) / bounds.height;
		return new AnchorConstraint(xp, yp);
	}

	/**
	 * 交点の取得
	 * 
	 * @param s
	 *            カーソル地点
	 * @return モデルの領域とカーソル地点との交点
	 */
	private Point getIntersectionPoint(Point s) {

		int x = 0;
		int dx = 0;
		// 左右の判定
		if (isCloseToLeft(s)) {
			x = leftSide;
			dx = s.x - leftSide;
		} else {
			x = rightSide;
			dx = rightSide - s.x;
		}

		int y = 0;
		int dy = 0;
		// 上下の判定
		if (isCloseToTop(s)) {
			y = topSide;
			dy = topSide - s.y;

		} else {
			y = bottomSide;
			dy = s.y - bottomSide;
		}
		// 左右と上下のどちらに近いか？
		if (isCloseToX(dx, dy)) {
			x = s.x;
		} else {
			y = s.y;
		}

		return new Point(x, y);
	}

}

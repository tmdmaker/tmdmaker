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
package jp.sourceforge.tmdmaker.editpart;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;

/**
 * XY地点を中心以外に移動できるAnchor
 * 
 * @author nakaG
 * 
 */
public class XYChopboxAnchor extends ChopboxAnchor {
	private int xp = -1;
	private int yp = -1;

	public XYChopboxAnchor(IFigure owner) {
		super(owner);
	}

	public XYChopboxAnchor(IFigure owner, int xp, int yp) {
		super(owner);
		this.xp = xp;
		this.yp = yp;
	}

	public void setLocation(int xp, int yp) {
		this.xp = xp;
		this.yp = yp;
		fireAnchorMoved();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.draw2d.ChopboxAnchor#getLocation(org.eclipse.draw2d.geometry.Point)
	 */
	@Override
	public Point getLocation(Point reference) {
		if (xp != -1 && yp != -1) {
			Rectangle bounds = getBox();
			Point point = new Point(bounds.x + (bounds.width * xp / 100),
					bounds.y + (bounds.height * yp / 100));
			getOwner().translateToAbsolute(point);
			return point;
		}

		return super.getLocation(reference);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.draw2d.ChopboxAnchor#getReferencePoint()
	 */
	@Override
	public Point getReferencePoint() {
		if (xp != -1 && yp != -1) {
			Rectangle bounds = getBox();
			Point point = new Point(bounds.x + (bounds.width * xp / 100),
					bounds.y + (bounds.height * yp / 100));
			getOwner().translateToAbsolute(point);
			return point;
		}

		return super.getReferencePoint();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.draw2d.ChopboxAnchor#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj instanceof XYChopboxAnchor) {
			XYChopboxAnchor other = (XYChopboxAnchor) obj;
			return other.getOwner() == getOwner()
					&& other.getBox().equals(getBox()) && other.xp == xp
					&& other.yp == yp && super.equals(other)
					&& hashCode() == other.hashCode();
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.draw2d.ChopboxAnchor#hashCode()
	 */
	@Override
	public int hashCode() {
		if (getOwner() != null)
			return getOwner().hashCode();
		else
			return super.hashCode();
	}

}

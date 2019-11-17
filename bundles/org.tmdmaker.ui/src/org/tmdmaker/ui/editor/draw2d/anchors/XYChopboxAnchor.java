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
package org.tmdmaker.ui.editor.draw2d.anchors;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.tmdmaker.core.model.constraint.AnchorConstraint;

/**
 * XY地点を中心以外に移動できるAnchor
 * 
 * @author nakaG
 * 
 */
public class XYChopboxAnchor extends ChopboxAnchor {
	private AnchorConstraint anchorConstraint = new AnchorConstraint();

	public XYChopboxAnchor(IFigure owner) {
		super(owner);
	}

	public XYChopboxAnchor(IFigure owner, AnchorConstraint anchorConstraint) {
		super(owner);
		this.anchorConstraint = anchorConstraint;
	}

	public void setLocation(AnchorConstraint anchorConstraint) {
		this.anchorConstraint = anchorConstraint;
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
		if (!anchorConstraint.isInitialPoint()) {
			return calculateNewPoint();
		}

		return super.getLocation(reference);
	}

	private Point calculateNewPoint() {
		Rectangle bounds = getBox();
		Point point = new Point(bounds.x + (bounds.width * anchorConstraint.getX() / 100),
				bounds.y + (bounds.height * anchorConstraint.getY() / 100));
		getOwner().translateToAbsolute(point);
		return point;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.draw2d.ChopboxAnchor#getReferencePoint()
	 */
	@Override
	public Point getReferencePoint() {
		if (!anchorConstraint.isInitialPoint()) {
			return calculateNewPoint();
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
			return other.getOwner() == getOwner() && other.getBox().equals(getBox())
					&& other.anchorConstraint.equals(anchorConstraint) && super.equals(other)
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

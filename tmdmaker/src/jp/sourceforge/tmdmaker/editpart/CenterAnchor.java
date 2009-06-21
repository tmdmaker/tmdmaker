package jp.sourceforge.tmdmaker.editpart;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;

/**
 * モデルの中心に接続するアンカー
 * 
 * @author nakaG
 * 
 */
public class CenterAnchor extends ChopboxAnchor {
		/**
		 * コンストラクタ
		 * 
		 * @param owner
		 *            親Figure
		 */
		public CenterAnchor(IFigure owner) {
			super(owner);
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.draw2d.ChopboxAnchor#getLocation(org.eclipse.draw2d.geometry.Point)
		 */
		@Override
		public Point getLocation(Point reference) {
			Point loc = getBox().getCenter();
			getOwner().translateToAbsolute(loc);
			return loc;
		}
}

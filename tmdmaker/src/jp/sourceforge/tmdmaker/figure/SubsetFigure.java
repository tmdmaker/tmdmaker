package jp.sourceforge.tmdmaker.figure;

import org.eclipse.draw2d.AbstractBorder;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Insets;

public class SubsetFigure extends Figure {
	private boolean sameType;

	public SubsetFigure(boolean sameType) {
		super();
		this.sameType = sameType;
		setBorder(new SubsetBorder(sameType));
		setSize(30, 10);
		setOpaque(false);
	}

	public static class SubsetBorder extends AbstractBorder {
		private int width = 1;
		private boolean sameType;
		
		public SubsetBorder(boolean sameType) {
			super();
			this.sameType = sameType;
		}
		/**
		 * @param sameType the sameType to set
		 */
		public void setSameType(boolean sameType) {
			this.sameType = sameType;
		}
		/**
		 * @return the width
		 */
		public int getWidth() {
			return width;
		}
		@Override
		public Insets getInsets(IFigure figure) {
			return new Insets(getWidth());
		}

		@Override
		public void paint(IFigure figure, Graphics graphics, Insets insets) {
			if (sameType) {
				paintSubsetSameType(figure, graphics, insets);
			} else {
				paintSubsetDifferenceType(figure, graphics, insets);
			}
		}
		private void paintSubsetSameType(IFigure figure, Graphics graphics, Insets insets) {
			tempRect.setBounds(getPaintRectangle(figure, insets));
			if (getWidth() % 2 == 1) {
				tempRect.width--;
				tempRect.height--;
			}
			tempRect.shrink(getWidth() / 2, getWidth() / 2);
			graphics.setLineWidth(getWidth());

			// 同一サブセット
			graphics.drawLine(tempRect.getTopLeft(), tempRect.getTopRight());
			graphics.drawLine(tempRect.getLeft(), tempRect.getRight());
			graphics.drawLine(getPaintRectangle(figure, insets).getTop(), tempRect.getCenter());			
		}
		private void paintSubsetDifferenceType(IFigure figure, Graphics graphics, Insets insets) {
			// 相違サブセット
			tempRect.setBounds(getPaintRectangle(figure, insets));
			graphics.drawLine(tempRect.getTopLeft(), tempRect.getBottomRight());
			graphics.drawLine(tempRect.getBottomLeft(), tempRect.getTopRight());
		}
	}
}

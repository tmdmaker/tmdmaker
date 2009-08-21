package jp.sourceforge.tmdmaker.figure;

import jp.sourceforge.tmdmaker.model.SubsetType.SubsetTypeValue;

import org.eclipse.draw2d.AbstractBorder;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Insets;

/**
 * 
 * @author nakaG
 *
 */
public class SubsetTypeFigure extends Figure {
	/**
	 * デフォルトコンストラクタ
	 */
	public SubsetTypeFigure() {
		this(SubsetTypeValue.SAME);
	}
	/**
	 * コンストラクタ
	 * @param subsetTypeValue サブセットタイプ
	 */
	public SubsetTypeFigure(SubsetTypeValue subsetTypeValue) {
		super();
		setBorder(new SubsetBorder(subsetTypeValue));
		setSize(30, 10);
		setOpaque(false);
	}

	/**
	 * サブセットのタイプを設定する
	 * @param subsetTypeValue サブセットタイプ値
	 */
	public void setSameType(SubsetTypeValue subsetTypeValue) {
		setBorder(new SubsetBorder(subsetTypeValue));		
	}
	/**
	 * サブセットタイプのBorder
	 * @author nakaG
	 *
	 */
	private static class SubsetBorder extends AbstractBorder {
		/** Figureの長さ */
		private int width = 1;
		/** サブセットタイプ値 */
		private SubsetTypeValue subsetTypeValue;
		/**
		 * コンストラクタ
		 * @param subsetTypeValue サブセットタイプ値
		 */
		public SubsetBorder(SubsetTypeValue subsetTypeValue) {
			super();
			this.subsetTypeValue = subsetTypeValue;
		}
		/**
		 * @return the width
		 */
		public int getWidth() {
			return width;
		}
		/**
		 * 
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.draw2d.Border#getInsets(org.eclipse.draw2d.IFigure)
		 */
		@Override
		public Insets getInsets(IFigure figure) {
			return new Insets(getWidth());
		}

		/**
		 * 
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.draw2d.Border#paint(org.eclipse.draw2d.IFigure, org.eclipse.draw2d.Graphics, org.eclipse.draw2d.geometry.Insets)
		 */
		@Override
		public void paint(IFigure figure, Graphics graphics, Insets insets) {
			if (subsetTypeValue.equals(SubsetTypeValue.SAME)) {
				paintSubsetSameType(figure, graphics, insets);
			} else {
				paintSubsetDifferenceType(figure, graphics, insets);
			}
		}
		/**
		 * 同一サブセットを描画する
		 * @param figure The figure this border belongs to
		 * @param graphics The graphics object used for painting
		 * @param insets The insets
		 */
		private void paintSubsetSameType(IFigure figure, Graphics graphics, Insets insets) {
			tempRect.setBounds(getPaintRectangle(figure, insets));
			if (getWidth() % 2 != 0) {
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
		/**
		 * 相違サブセットを描画する
		 * @param figure The figure this border belongs to
		 * @param graphics The graphics object used for painting
		 * @param insets The insets
		 */
		private void paintSubsetDifferenceType(IFigure figure, Graphics graphics, Insets insets) {
			// 相違サブセット
			tempRect.setBounds(getPaintRectangle(figure, insets));
			graphics.drawLine(tempRect.getTopLeft(), tempRect.getBottomRight());
			graphics.drawLine(tempRect.getBottomLeft(), tempRect.getTopRight());
		}
	}
}

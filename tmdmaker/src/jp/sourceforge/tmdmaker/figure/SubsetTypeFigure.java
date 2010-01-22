/*
 * Copyright 2009-2010 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
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
package jp.sourceforge.tmdmaker.figure;

import org.eclipse.draw2d.AbstractBorder;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Insets;

/**
 * サブセット種類のFigure
 * 
 * @author nakaG
 * 
 */
public class SubsetTypeFigure extends Figure {

	/**
	 * コンストラクタ
	 */
	public SubsetTypeFigure() {
		this(true);
	}

	/**
	 * コンストラクタ
	 * 
	 * @param subsetTypeValue
	 *            サブセットタイプ
	 */
	public SubsetTypeFigure(boolean sameType) {
		super();
		setBorder(new SubsetBorder(sameType));
		setOpaque(false);
	}

	/**
	 * サブセットのタイプを設定する
	 * 
	 * @param subsetTypeValue
	 *            サブセットタイプ値
	 */
	public void setSameType(boolean sameType) {
		if (sameType) {
			setSize(30, 5);
		} else {
			setSize(30, 10);
		}
		((SubsetBorder)getBorder()).setSameType(sameType);
	}

	/**
	 * サブセットタイプのBorder
	 * 
	 * @author nakaG
	 * 
	 */
	private static class SubsetBorder extends AbstractBorder {
		/** Figureの長さ */
		private int width = 1;
		/** 同一タイプか？ */
		private boolean sameType;

		/**
		 * コンストラクタ
		 * 
		 * @param subsetType
		 *            サブセットタイプ値
		 */
		public SubsetBorder(boolean sameType) {
			super();
			this.sameType = sameType;
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
		 * @see org.eclipse.draw2d.Border#paint(org.eclipse.draw2d.IFigure,
		 *      org.eclipse.draw2d.Graphics, org.eclipse.draw2d.geometry.Insets)
		 */
		@Override
		public void paint(IFigure figure, Graphics graphics, Insets insets) {
			if (sameType) {
				paintSubsetSameType(figure, graphics, insets);
			} else {
				paintSubsetDifferenceType(figure, graphics, insets);
			}
		}

		/**
		 * 同一サブセットを描画する
		 * 
		 * @param figure
		 *            The figure this border belongs to
		 * @param graphics
		 *            The graphics object used for painting
		 * @param insets
		 *            The insets
		 */
		private void paintSubsetSameType(IFigure figure, Graphics graphics,
				Insets insets) {
			tempRect.setBounds(getPaintRectangle(figure, insets));
			if (getWidth() % 2 != 0) {
				tempRect.width--;
				tempRect.height--;
			}
			tempRect.shrink(getWidth() / 2, getWidth() / 2);
			graphics.setLineWidth(getWidth());

			// 同一サブセット
			graphics.drawLine(tempRect.getTopLeft(), tempRect.getTopRight());
			graphics.drawLine(tempRect.getBottomLeft(), tempRect
					.getBottomRight());
			graphics.drawLine(getPaintRectangle(figure, insets).getTop(),
					tempRect.getCenter());
		}

		/**
		 * 相違サブセットを描画する
		 * 
		 * @param figure
		 *            The figure this border belongs to
		 * @param graphics
		 *            The graphics object used for painting
		 * @param insets
		 *            The insets
		 */
		private void paintSubsetDifferenceType(IFigure figure,
				Graphics graphics, Insets insets) {
			// 相違サブセット
			tempRect.setBounds(getPaintRectangle(figure, insets));
			graphics.drawLine(tempRect.getTopLeft(), tempRect.getBottomRight());
			graphics.drawLine(tempRect.getBottomLeft(), tempRect.getTopRight());
		}

		/**
		 * @param sameType the sameType to set
		 */
		public void setSameType(boolean sameType) {
			this.sameType = sameType;
		}
		
	}
}

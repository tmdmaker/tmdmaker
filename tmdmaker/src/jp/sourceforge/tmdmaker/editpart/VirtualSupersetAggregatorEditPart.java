/*
 * Copyright 2009 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
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

import java.beans.PropertyChangeEvent;

import jp.sourceforge.tmdmaker.model.VirtualSupersetAggregator;

import org.eclipse.draw2d.AbstractBorder;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Insets;

/**
 * みなしスーパーセットの同一(=)/相違マーク(×)のコントローラ
 * 
 * @author nakaG
 * 
 */
public class VirtualSupersetAggregatorEditPart extends AbstractEntityEditPart {
	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.editpart.AbstractEntityEditPart#updateFigure(org.eclipse.draw2d.IFigure)
	 */
	@Override
	protected void updateFigure(IFigure figure) {
		VirtualSupersetAggregator model = (VirtualSupersetAggregator) getModel();
		((AggregatorBorder)figure.getBorder()).setSameType(model.isApplyAttribute());
		if (model.isApplyAttribute()) {
			figure.setSize(30, 5);
		} else {
			figure.setSize(30, 10);			
		}
	}
	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.editpart.AbstractTMDEditPart#onDoubleClicked()
	 */
	@Override
	protected void onDoubleClicked() {
		// TODO Auto-generated method stub

	}
	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#createFigure()
	 */
	@Override
	protected IFigure createFigure() {
		Figure figure = new Figure();
		figure.setSize(20, 10);
		figure.setOpaque(false);
		figure.setBorder(new AggregatorBorder());
		updateFigure(figure);
		return figure;
	}
	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#createEditPolicies()
	 */
	@Override
	protected void createEditPolicies() {
		// TODO Auto-generated method stub

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.editpart.AbstractEntityEditPart#getConnectionAnchor()
	 */
	@Override
	protected ConnectionAnchor getConnectionAnchor() {
		return new CenterAnchor(getFigure());
	}

	
	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.editpart.AbstractEntityEditPart#propertyChange(java.beans.PropertyChangeEvent)
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals(VirtualSupersetAggregator.PROPERTY_SUPERSET_TYPE)) {
			refreshVisuals();
		} else {
			super.propertyChange(evt);
		}
	}


	/**
	 * 
	 * @author nakaG
	 * 
	 */
	private static class AggregatorBorder extends AbstractBorder {
		/** Figureの長さ */
		private int width = 1;
		/** 同一タイプか？ */
		private boolean sameType = false;
		
		/**
		 * @return the width
		 */
		public int getWidth() {
			return width;
		}

		/**
		 * @return the sameType
		 */
		public boolean isSameType() {
			return sameType;
		}

		/**
		 * @param sameType the sameType to set
		 */
		public void setSameType(boolean sameType) {
			this.sameType = sameType;
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
			if (isSameType()) {
				paintSameType(figure, graphics, insets);
			} else {
				paintDifferenceType(figure, graphics, insets);
			}
		}
		/**
		 * 同一タイプを描画する
		 * 
		 * @param figure
		 *            The figure this border belongs to
		 * @param graphics
		 *            The graphics object used for painting
		 * @param insets
		 *            The insets
		 */
		private void paintSameType(IFigure figure, Graphics graphics,
				Insets insets) {
			tempRect.setBounds(getPaintRectangle(figure, insets));
			if (getWidth() % 2 != 0) {
				tempRect.width--;
				tempRect.height--;
			}
			tempRect.shrink(getWidth() / 2, getWidth() / 2);
			graphics.setLineWidth(getWidth());

			// 同一
			graphics.drawLine(tempRect.getTopLeft(), tempRect.getTopRight());
			graphics.drawLine(tempRect.getBottomLeft(), tempRect
					.getBottomRight());
			graphics.drawLine(getPaintRectangle(figure, insets).getTop(),
					tempRect.getCenter());
		}

		/**
		 * 相違タイプを描画する
		 * 
		 * @param figure
		 *            The figure this border belongs to
		 * @param graphics
		 *            The graphics object used for painting
		 * @param insets
		 *            The insets
		 */
		private void paintDifferenceType(IFigure figure,
				Graphics graphics, Insets insets) {
			// 相違
			tempRect.setBounds(getPaintRectangle(figure, insets));
			graphics.drawLine(tempRect.getTopLeft(), tempRect.getBottomRight());
			graphics.drawLine(tempRect.getBottomLeft(), tempRect.getTopRight());
		}
	}

}

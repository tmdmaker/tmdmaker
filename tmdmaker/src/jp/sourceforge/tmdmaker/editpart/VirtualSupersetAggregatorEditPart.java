package jp.sourceforge.tmdmaker.editpart;

import org.eclipse.draw2d.AbstractBorder;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Insets;

public class VirtualSupersetAggregatorEditPart extends AbstractEntityEditPart {

	@Override
	protected void updateFigure(IFigure figure) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onDoubleClicked() {
		// TODO Auto-generated method stub

	}

	@Override
	protected IFigure createFigure() {
		Figure figure = new Figure();
		figure.setSize(20, 10);
		figure.setOpaque(false);
		figure.setBorder(new AggregatorBorder());
		return figure;
	}

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
	 * 
	 * @author nakaG
	 * 
	 */
	private static class AggregatorBorder extends AbstractBorder {
		/** Figureの長さ */
		private int width = 1;

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
			tempRect.setBounds(getPaintRectangle(figure, insets));
			graphics.drawLine(tempRect.getTopLeft(), tempRect.getBottomRight());
			graphics.drawLine(tempRect.getBottomLeft(), tempRect.getTopRight());

		}

	}

}

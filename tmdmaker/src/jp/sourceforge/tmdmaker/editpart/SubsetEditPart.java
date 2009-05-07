package jp.sourceforge.tmdmaker.editpart;

import jp.sourceforge.tmdmaker.figure.SubsetFigure;
import jp.sourceforge.tmdmaker.model.Subset;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;

public class SubsetEditPart extends AbstractEntityEditPart {

	@Override
	protected IFigure createFigure() {
//		Figure figure = new Label("=");
		Figure figure = new SubsetFigure(true);
		updateFigure(figure);
		return figure;
	}

	private void updateFigure(Figure figure) {
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.gef.editparts.AbstractEditPart#refreshVisuals()
	 */
	@Override
	protected void refreshVisuals() {
		System.out.println(getClass().toString() + "#refreshVisuals()");
		super.refreshVisuals();
		Object model = getModel();
		Rectangle bounds = new Rectangle(((Subset) model)
				.getConstraint());
		((GraphicalEditPart) getParent()).setLayoutConstraint(this,
				getFigure(), bounds);
		updateFigure((Figure) getFigure());
		refreshChildren();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.editparts.AbstractEditPart#createEditPolicies()
	 */
	@Override
	protected void createEditPolicies() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * {@inheritDoc}
	 * @see jp.sourceforge.tmdmaker.editpart.AbstractEntityEditPart#getSourceConnectionAnchor(org.eclipse.gef.ConnectionEditPart)
	 */
	@Override
	public ConnectionAnchor getSourceConnectionAnchor(
			ConnectionEditPart connection) {
		return new CenterAnchor(getFigure());
	}

	/**
	 * {@inheritDoc}
	 * @see jp.sourceforge.tmdmaker.editpart.AbstractEntityEditPart#getSourceConnectionAnchor(org.eclipse.gef.Request)
	 */
	@Override
	public ConnectionAnchor getSourceConnectionAnchor(Request request) {
		return new CenterAnchor(getFigure());
	}
	private static class CenterAnchor extends ChopboxAnchor {
		public CenterAnchor(IFigure owner) {
			super(owner);
			// TODO Auto-generated constructor stub
		}
		

		/**
		 * {@inheritDoc}
		 * @see org.eclipse.draw2d.ChopboxAnchor#getLocation(org.eclipse.draw2d.geometry.Point)
		 */
		@Override
		public Point getLocation(Point reference) {
			Point loc = getBox().getCenter();
			getOwner().translateToAbsolute(loc);
			return loc;
		}

	}
}

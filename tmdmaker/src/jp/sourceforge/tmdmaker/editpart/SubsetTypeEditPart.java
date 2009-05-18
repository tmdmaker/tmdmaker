package jp.sourceforge.tmdmaker.editpart;

import java.beans.PropertyChangeEvent;

import jp.sourceforge.tmdmaker.figure.SubsetTypeFigure;
import jp.sourceforge.tmdmaker.model.SubsetType;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;

public class SubsetTypeEditPart extends AbstractEntityEditPart {

	@Override
	protected IFigure createFigure() {
//		Figure figure = new Label("=");
		Figure figure = new SubsetTypeFigure(true);
		updateFigure(figure);
		return figure;
	}

	private void updateFigure(Figure figure) {
		SubsetTypeFigure sf = (SubsetTypeFigure)figure;
		SubsetType model = (SubsetType) getModel();
		sf.setBorder(new SubsetTypeFigure.SubsetBorder(model.subsettype.equals(SubsetType.SubsetTypeValue.SAME)));
	}


	@Override
	protected void updateFigure(IFigure figure) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.gef.editparts.AbstractEditPart#refreshVisuals()
	 */
//	@Override
//	protected void refreshVisuals() {
//		System.out.println(getClass().toString() + "#refreshVisuals()");
//		super.refreshVisuals();
//		updateFigure((Figure) getFigure());
//		Object model = getModel();
//		Rectangle bounds = new Rectangle(((SubsetType) model)
//				.getConstraint());
//		((GraphicalEditPart) getParent()).setLayoutConstraint(this,
//				getFigure(), bounds);
//		refreshChildren();
//	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.editparts.AbstractEditPart#createEditPolicies()
	 */
	@Override
	protected void createEditPolicies() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * {@inheritDoc}
	 * @see jp.sourceforge.tmdmaker.editpart.AbstractEntityEditPart#getConnectionAnchor()
	 */
	@Override
	protected ConnectionAnchor getConnectionAnchor() {
		return new CenterAnchor(getFigure());
	}

	/**
	 * {@inheritDoc}
	 * @see jp.sourceforge.tmdmaker.editpart.AbstractEntityEditPart#propertyChange(java.beans.PropertyChangeEvent)
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals(SubsetType.PROPERTY_TYPE)) {
			refreshVisuals();
		} else {
			super.propertyChange(evt);
		}
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

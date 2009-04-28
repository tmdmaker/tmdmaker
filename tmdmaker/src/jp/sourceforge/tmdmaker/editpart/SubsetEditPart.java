package jp.sourceforge.tmdmaker.editpart;

import jp.sourceforge.tmdmaker.model.Subset;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.GraphicalEditPart;

public class SubsetEditPart extends AbstractEntityEditPart {

	@Override
	protected IFigure createFigure() {
		Figure figure = new Label("=");
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

}

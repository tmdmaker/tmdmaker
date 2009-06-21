package jp.sourceforge.tmdmaker.editpart;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;

public class MultivalueAndAggregatorEditPart extends AbstractEntityEditPart {

	@Override
	protected void onDoubleClicked() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void updateFigure(IFigure figure) {
		// TODO Auto-generated method stub

	}

	@Override
	protected IFigure createFigure() {
		Figure figure = new Figure();
		figure.setSize(20, 10);
		figure.setOpaque(false);
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

}

package jp.sourceforge.tmdmaker.editpolicy;

import org.eclipse.draw2d.FlowLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.LayoutManager;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.editpolicies.FlowLayoutEditPolicy;

public abstract class ToolbarLayoutEditPolicy extends FlowLayoutEditPolicy {

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.gef.editpolicies.FlowLayoutEditPolicy#isHorizontal()
	 */
	@Override
	protected boolean isHorizontal() {
		IFigure figure = ((GraphicalEditPart) getHost()).getContentPane();
		LayoutManager layout = figure.getLayoutManager();
		if (layout instanceof FlowLayout)
			return ((FlowLayout) figure.getLayoutManager()).isHorizontal();
		if (layout instanceof ToolbarLayout)
			return ((ToolbarLayout) figure.getLayoutManager()).isHorizontal();
		return false;
	}
	
}

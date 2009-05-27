package jp.sourceforge.tmdmaker.editpart;

import java.beans.PropertyChangeEvent;

import jp.sourceforge.tmdmaker.figure.SubsetTypeFigure;
import jp.sourceforge.tmdmaker.model.SubsetType;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;

/**
 * サブセット種類のEditPart
 * @author nakaG
 *
 */
public class SubsetTypeEditPart extends AbstractEntityEditPart {

	/**
	 * 
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#createFigure()
	 */
	@Override
	protected IFigure createFigure() {
		// Figure figure = new Label("=");
		Figure figure = new SubsetTypeFigure(true);
		updateFigure(figure);
		return figure;
	}
	/**
	 * 
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.editpart.AbstractEntityEditPart#updateFigure(org.eclipse.draw2d.IFigure)
	 */
	@Override
	protected void updateFigure(IFigure figure) {
		SubsetTypeFigure sf = (SubsetTypeFigure) figure;
		SubsetType model = (SubsetType) getModel();
		sf.setBorder(new SubsetTypeFigure.SubsetBorder(model.subsettype
				.equals(SubsetType.SubsetTypeValue.SAME)));
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
		if (evt.getPropertyName().equals(SubsetType.PROPERTY_TYPE)) {
			refreshVisuals();
		} else {
			super.propertyChange(evt);
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.editpart.AbstractEntityEditPart#onDoubleClicked()
	 */
	@Override
	protected void onDoubleClicked() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * モデルの中心に接続するアンカー
	 * 
	 * @author nakaG
	 * 
	 */
	private static class CenterAnchor extends ChopboxAnchor {
		/**
		 * コンストラクタ
		 * 
		 * @param owner
		 *            親Figure
		 */
		public CenterAnchor(IFigure owner) {
			super(owner);
		}

		/**
		 * {@inheritDoc}
		 * 
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

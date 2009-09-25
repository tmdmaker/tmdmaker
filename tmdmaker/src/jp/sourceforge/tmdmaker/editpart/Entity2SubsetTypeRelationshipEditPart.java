package jp.sourceforge.tmdmaker.editpart;

import java.beans.PropertyChangeEvent;

import jp.sourceforge.tmdmaker.figure.Entity2SubsetRelationshipFigure;
import jp.sourceforge.tmdmaker.model.Attribute;
import jp.sourceforge.tmdmaker.model.Entity2SubsetTypeRelationship;

import org.eclipse.draw2d.IFigure;

/**
 * 
 * @author nakaG
 *
 */
public class Entity2SubsetTypeRelationshipEditPart extends
		AbstractRelationshipEditPart {

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.gef.editparts.AbstractConnectionEditPart#createFigure()
	 */
	@Override
	protected IFigure createFigure() {
		Entity2SubsetRelationshipFigure figure = new Entity2SubsetRelationshipFigure();
		updateFigure(figure);
		return figure;
	}

	/**
	 * 
	 * @param figure 再描画対象のFigure
	 */
	private void updateFigure(Entity2SubsetRelationshipFigure figure) {
		Entity2SubsetTypeRelationship relationship = (Entity2SubsetTypeRelationship) getModel();
		
//		figure.createPartitionAttributeNameDecoration("test");
		
		Attribute partitionAttribute = relationship.getPartitionAttribute();
		if (partitionAttribute != null) {
			String name = null;
			if (relationship.isExceptNull()) {
				name = "NULL(" + partitionAttribute.getName() + ")";
			} else {
				name = partitionAttribute.getName();
			}
			figure.createPartitionAttributeNameDecoration(name);
		}	
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
	 * @see jp.sourceforge.tmdmaker.editpart.AbstractRelationshipEditPart#refreshVisuals()
	 */
	@Override
	protected void refreshVisuals() {
		Entity2SubsetRelationshipFigure figure = (Entity2SubsetRelationshipFigure)getFigure();
		updateFigure(figure);
		super.refreshVisuals();
	}

	/**
	 * {@inheritDoc}
	 * @see jp.sourceforge.tmdmaker.editpart.AbstractRelationshipEditPart#propertyChange(java.beans.PropertyChangeEvent)
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals(Entity2SubsetTypeRelationship.PROPERTY_PARTITION)) {
			refreshVisuals();
		} else {
			super.propertyChange(evt);
		}
	}
}

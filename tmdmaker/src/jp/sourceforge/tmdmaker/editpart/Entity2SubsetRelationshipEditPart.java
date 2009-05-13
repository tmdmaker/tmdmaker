package jp.sourceforge.tmdmaker.editpart;

import java.beans.PropertyChangeEvent;

import jp.sourceforge.tmdmaker.figure.Entity2SubsetRelationshipFigure;
import jp.sourceforge.tmdmaker.model.Attribute;
import jp.sourceforge.tmdmaker.model.Entity2SubsetRelationship;
import jp.sourceforge.tmdmaker.model.Subset;

import org.eclipse.draw2d.IFigure;

public class Entity2SubsetRelationshipEditPart extends
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

	private void updateFigure(Entity2SubsetRelationshipFigure figure) {
		Entity2SubsetRelationship relationship = (Entity2SubsetRelationship) getModel();
		
//		figure.createPartitionAttributeNameDecoration("test");
		
		Attribute partitionAttribute = relationship.getPartitionAttribute();
		if (partitionAttribute != null) {
			figure.createPartitionAttributeNameDecoration(partitionAttribute.getName());
		}	
	}
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
		System.out.println(getClass() + "refreshVisuals()");
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
		if (evt.getPropertyName().equals(Entity2SubsetRelationship.PROPERTY_PARTITION)) {
			refreshVisuals();
		} else {
			super.propertyChange(evt);
		}
	}
	
}

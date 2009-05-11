package jp.sourceforge.tmdmaker.editpart;

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
		Subset model = (Subset) relationship.getTarget();
		figure.createPartitionAttributeNameDecoration("test");
		
		Attribute partitionAttribute = model.getPartitionAttribute();
		if (partitionAttribute != null) {
			figure.createPartitionAttributeNameDecoration(partitionAttribute.getName());
		}	
	}
	@Override
	protected void createEditPolicies() {
		// TODO Auto-generated method stub

	}

}

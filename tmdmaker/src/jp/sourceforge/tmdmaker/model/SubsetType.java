package jp.sourceforge.tmdmaker.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.geometry.Rectangle;

public class SubsetType extends ConnectableElement {
//	/**  */
//	private Rectangle constraint;
	private Diagram diagram;
//	private AbstractEntityModel original;
//	private List<SubsetEntity> subsetEntityList = new ArrayList<SubsetEntity>();
	public enum SubsetTypeValue {SAME, DIFFERENT};
	public SubsetTypeValue subsettype = SubsetTypeValue.SAME;
	public static final String PROPERTY_TYPE = "_property_type";
	private Attribute partitionAttribute;
	
	public SubsetType(AbstractEntityModel model) {
		this.diagram = model.getDiagram();
//		this.original = model;
	}
//	public void setConstraint(Rectangle constraint) {
//		Rectangle oldConstraint = this.constraint;
//		this.constraint = constraint;
//		firePropertyChange(PROPERTY_CONSTRAINT, oldConstraint, constraint);
//	}

	public void setDiagram(Diagram diagram) {
		this.diagram = diagram;
	}

//	public Rectangle getConstraint() {
//		return constraint;
//	}

	public List<SubsetEntity> findSubsetEntityList() {
		List<SubsetEntity> results = new ArrayList<SubsetEntity>();
		for (AbstractConnectionModel<?> c : getModelSourceConnections()) {
			if (c instanceof RelatedRelationship) {
				results.add((SubsetEntity) c.getTarget());
			}
		}
		return results;
	}
//	/**
//	 * @return the subsetEntity
//	 */
//	public List<SubsetEntity> getSubsetEntityList() {
//		getModelSourceConnections().
//		return subsetEntityList;
//	}
	
//	/**
//	 * @param subsetEntityList the subsetEntityList to set
//	 */
//	public void setSubsetEntityList(List<SubsetEntity> subsetEntityList) {
//		this.subsetEntityList = subsetEntityList;
//	}
	
	/**
	 * @return the subsettype
	 */
	public SubsetTypeValue getSubsettype() {
		return subsettype;
	}
	/**
	 * @param subsettype the subsettype to set
	 */
	public void setSubsettype(SubsetTypeValue subsettype) {
		SubsetTypeValue oldValue = this.subsettype;
		this.subsettype = subsettype;
		firePropertyChange(PROPERTY_TYPE, oldValue, this.subsettype);
	}
	
//	/**
//	 * @return the partitionAttribute
//	 */
//	public Attribute getPartitionAttribute() {
//		return partitionAttribute;
//	}
//	/**
//	 * @param partitionAttribute the partitionAttribute to set
//	 */
//	public void setPartitionAttribute(Attribute partitionAttribute) {
//		Attribute oldValue = this.partitionAttribute;
//		this.partitionAttribute = partitionAttribute;
//		Entity2SubsetRelationship relationship = (Entity2SubsetRelationship) getModelTargetConnections().get(0);
//		
////		firePropertyChange(PROPERTY_PARTITION, oldValue, partitionAttribute);
//	}
	public void dispose() {
		// TODO 
	}
}

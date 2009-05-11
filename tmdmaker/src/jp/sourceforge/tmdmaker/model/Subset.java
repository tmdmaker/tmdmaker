package jp.sourceforge.tmdmaker.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.geometry.Rectangle;

public class Subset extends ConnectableElement {
	private Rectangle constraint;
	private Diagram diagram;
	private AbstractEntityModel original;
	private List<SubsetEntity> subsetEntityList = new ArrayList<SubsetEntity>();
	public enum SubsetType {SAME, DIFFERENT};
	public SubsetType subsettype = SubsetType.SAME;
	public static final String PROPERTY_TYPE = "_property_type";
	private Attribute partitionAttribute;
	
	public Subset(AbstractEntityModel model) {
		this.diagram = model.getDiagram();
		this.original = model;
	}
	public void setConstraint(Rectangle constraint) {
		Rectangle oldConstraint = this.constraint;
		this.constraint = constraint;
		firePropertyChange(PROPERTY_CONSTRAINT, oldConstraint, constraint);
	}

	public void setDiagram(Diagram diagram) {
		this.diagram = diagram;
	}

	public Rectangle getConstraint() {
		return constraint;
	}
	
	/**
	 * @return the subsetEntity
	 */
	public List<SubsetEntity> getSubsetEntityList() {
		return subsetEntityList;
	}
	
	/**
	 * @param subsetEntityList the subsetEntityList to set
	 */
	public void setSubsetEntityList(List<SubsetEntity> subsetEntityList) {
		this.subsetEntityList = subsetEntityList;
	}
	
	/**
	 * @return the subsettype
	 */
	public SubsetType getSubsettype() {
		return subsettype;
	}
	/**
	 * @param subsettype the subsettype to set
	 */
	public void setSubsettype(SubsetType subsettype) {
		SubsetType oldValue = this.subsettype;
		this.subsettype = subsettype;
		firePropertyChange(PROPERTY_TYPE, oldValue, this.subsettype);
	}
	
	/**
	 * @return the partitionAttribute
	 */
	public Attribute getPartitionAttribute() {
		return partitionAttribute;
	}
	/**
	 * @param partitionAttribute the partitionAttribute to set
	 */
	public void setPartitionAttribute(Attribute partitionAttribute) {
		this.partitionAttribute = partitionAttribute;
	}
	public void dispose() {
		// TODO 
	}
}

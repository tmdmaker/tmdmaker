package jp.sourceforge.tmdmaker.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.geometry.Rectangle;

public class Subset extends ConnectableElement {
	private Rectangle constraint;
	private Diagram diagram;
	private AbstractEntityModel original;
	private List<SubsetEntity> subsetEntityList = new ArrayList<SubsetEntity>();

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
	public void dispose() {
		// TODO 
	}
}

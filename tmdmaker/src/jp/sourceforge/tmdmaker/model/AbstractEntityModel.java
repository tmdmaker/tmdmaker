package jp.sourceforge.tmdmaker.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.draw2d.geometry.Rectangle;

public abstract class AbstractEntityModel extends ConnectableElement {
	public enum EntityType {R, E};

	/** 親モデル */
	private Diagram diagram;
	public static final String PROPERTY_CONSTRAINT = "p_constraint";
	public static final String PROPERTY_ATTRIBUTE = "p_attribute";
	public static final String PROPERTY_REUSEKEY = "p_reusekey";
	public static final String P_ATTRIBUTES = "p_attributes";
	protected Rectangle constraint;
	protected Map<AbstractEntityModel, ReuseKey> reuseKeys = new LinkedHashMap<AbstractEntityModel, ReuseKey>();
	protected List<Attribute> attributes = new ArrayList<Attribute>();
	protected EntityType entityType = EntityType.R;
	
	/**
	 * @return the diagram
	 */
	public Diagram getDiagram() {
		return diagram;
	}

	/**
	 * @param diagram the diagram to set
	 */
	public void setDiagram(Diagram diagram) {
		this.diagram = diagram;
	}

	/**
	 * @return the constraint
	 */
	public Rectangle getConstraint() {
		return constraint;
	}

	/**
	 * @param constraint the constraint to set
	 */
	public void setConstraint(Rectangle constraint) {
		this.constraint = constraint;
		firePropertyChange(PROPERTY_CONSTRAINT, null, constraint);
	}

	/**
	 * @return the reuseKeys
	 */
	public Map<AbstractEntityModel, ReuseKey> getReuseKeys() {
		return Collections.unmodifiableMap(reuseKeys);
	}

	/**
	 * @param reuseKeys the reuseKeys to set
	 */
	public void setReuseKeys(Map<AbstractEntityModel, ReuseKey> reuseKeys) {
		this.reuseKeys = reuseKeys;
	}

//	public void addReuseKey(Identifier reuseKey) {
//		this.reuseKeys.add(new ReuseKey(reuseKey, this));
//		firePropertyChange(P_ATTRIBUTE, null, reuseKey);
//	}

//	public void removeReuseKey(Identifier reuseKey) {
////		if (this.reuseKeys.remove(reuseKey)) {
////			firePropertyChange(P_CONSTRAINT, reuseKey, null);
////		}
//		for (ReuseKey rk : reuseKeys) {
//			if (rk.hasSameIdentifier(reuseKey)) {
//				rk.invalidate();
//				reuseKeys.remove(rk);
//				break;
//			}
//		}
//	}
	public void addReuseKey(AbstractEntityModel source) {
		ReuseKey added= this.reuseKeys.put(source, source.getMyReuseKey());
		firePropertyChange(PROPERTY_REUSEKEY, null, added);
	}
	public void removeReuseKey(AbstractEntityModel source) {
		ReuseKey removed = this.reuseKeys.remove(source);
		if (removed != null) {
			removed.dispose();
		}
		firePropertyChange(PROPERTY_REUSEKEY, removed, null);
	}
//	public abstract void addReuseKey(AbstractEntityModel target);
//	public abstract void removeReuseKey(AbstractEntityModel target);
	public abstract ReuseKey getMyReuseKey();

	/**
	 * @return the attributes
	 */
	public List<Attribute> getAttributes() {
		return attributes;
	}

	/**
	 * @param attributes the attributes to set
	 */
	public void setAttributes(List<Attribute> attributes) {
		this.attributes = attributes;
	}
	public void addAttribute(Attribute attribute) {
		this.attributes.add(attribute);
	}

	/**
	 * @return the entityType
	 */
	public EntityType getEntityType() {
		return entityType;
	}

	/**
	 * @param entityType the entityType to set
	 */
	public void setEntityType(EntityType entityType) {
		this.entityType = entityType;
	}
}


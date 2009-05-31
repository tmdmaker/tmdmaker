package jp.sourceforge.tmdmaker.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractEntityModel extends ConnectableElement {
	public enum EntityType {
		R, E
	};

	/** 親モデル */
	private Diagram diagram;
	public static final String PROPERTY_ATTRIBUTE = "p_attribute";
	public static final String PROPERTY_REUSEKEY = "p_reusekey";
	public static final String P_ATTRIBUTES = "p_attributes";
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
	 * @param diagram
	 *            the diagram to set
	 */
	public void setDiagram(Diagram diagram) {
		this.diagram = diagram;
	}

	/**
	 * @return the reuseKeys
	 */
	public Map<AbstractEntityModel, ReuseKey> getReuseKeys() {
		return Collections.unmodifiableMap(reuseKeys);
	}

	/**
	 * @param reuseKeys
	 *            the reuseKeys to set
	 */
	public void setReuseKeys(Map<AbstractEntityModel, ReuseKey> reuseKeys) {
		this.reuseKeys = reuseKeys;
	}

	// public void addReuseKey(Identifier reuseKey) {
	// this.reuseKeys.add(new ReuseKey(reuseKey, this));
	// firePropertyChange(P_ATTRIBUTE, null, reuseKey);
	// }

	// public void removeReuseKey(Identifier reuseKey) {
	// // if (this.reuseKeys.remove(reuseKey)) {
	// // firePropertyChange(P_CONSTRAINT, reuseKey, null);
	// // }
	// for (ReuseKey rk : reuseKeys) {
	// if (rk.hasSameIdentifier(reuseKey)) {
	// rk.invalidate();
	// reuseKeys.remove(rk);
	// break;
	// }
	// }
	// }
	public void addReuseKey(AbstractEntityModel source) {
		ReuseKey added = this.reuseKeys.put(source, source.getMyReuseKey());
		firePropertyChange(PROPERTY_REUSEKEY, null, added);
	}

	public void removeReuseKey(AbstractEntityModel source) {
		ReuseKey removed = this.reuseKeys.remove(source);
		if (removed != null) {
			removed.dispose();
		}
		firePropertyChange(PROPERTY_REUSEKEY, removed, null);
	}

	// public abstract void addReuseKey(AbstractEntityModel target);
	// public abstract void removeReuseKey(AbstractEntityModel target);
	public abstract ReuseKey getMyReuseKey();

	/**
	 * @return the attributes
	 */
	public List<Attribute> getAttributes() {
		return attributes;
	}

	/**
	 * @param attributes
	 *            the attributes to set
	 */
	public void setAttributes(List<Attribute> attributes) {
		List<Attribute> oldValue = this.attributes;
		this.attributes = attributes;
		// firePropertyChange(PROPERTY_ATTRIBUTE, oldValue, attributes);
	}

	/**
	 * @param attribute
	 *            the attribute to set
	 */
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
	 * @param entityType
	 *            the entityType to set
	 */
	public void setEntityType(EntityType entityType) {
		this.entityType = entityType;
	}

	/**
	 * サブセット種類を取得する
	 * 
	 * @return SubsetType。存在しない場合はnullを返す。
	 */
	public SubsetType findSubset() {
		for (AbstractConnectionModel<?> connection : getModelSourceConnections()) {
			if (connection instanceof Entity2SubsetTypeRelationship) {
				return (SubsetType) ((Entity2SubsetTypeRelationship) connection)
						.getTarget();
			}
		}
		return null;
	}

	/**
	 * エンティティ種類が編集可能か判定する
	 * 
	 * @return 編集可能な場合にtrueを返す。
	 */
	public abstract boolean canEntityTypeEditable();
}

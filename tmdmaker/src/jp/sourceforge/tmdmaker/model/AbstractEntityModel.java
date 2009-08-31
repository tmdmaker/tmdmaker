package jp.sourceforge.tmdmaker.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author nakaG
 * 
 */
@SuppressWarnings("serial")
public abstract class AbstractEntityModel extends ConnectableElement {

	/** 親モデル */
	private Diagram diagram;
	public static final String PROPERTY_ATTRIBUTE_REORDER = "p_attribute_reorder";
	public static final String PROPERTY_ATTRIBUTE = "p_attribute";
	public static final String PROPERTY_REUSEKEY = "p_reusekey";
	public static final String PROPERTY_ATTRIBUTES = "p_attributes";
	public static final String PROPERTY_NOT_IMPLEMENT = "p_notImplement";
	protected Map<AbstractEntityModel, ReusedIdentifier> reuseKey = new LinkedHashMap<AbstractEntityModel, ReusedIdentifier>();
	protected List<Attribute> attributes = new ArrayList<Attribute>();
	/** エンティティ種類 */
	protected EntityType entityType = EntityType.RESOURCE;
	/** 物理実装しない */
	protected boolean notImplement = false;

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
	 * @return the reuseKey
	 */
	public Map<AbstractEntityModel, ReusedIdentifier> getReuseKey() {
		return Collections.unmodifiableMap(reuseKey);
	}

	/**
	 * @param reuseKey
	 *            the reuseKey to set
	 */
	public void setReuseKey(Map<AbstractEntityModel, ReusedIdentifier> reuseKeys) {
		this.reuseKey = reuseKeys;
	}

	// public void addReuseKey(Identifier reuseKey) {
	// this.reuseKeys.add(new ReuseKey(reuseKey, this));
	// firePropertyChange(P_ATTRIBUTE, null, reuseKey);
	// }

	// public void removeReuseKey(Identifier reuseKey) {
	// // if (this.reuseKeys.remove(reuseKey)) {
	// // firePropertyChange(P_CONSTRAINT, reuseKey, null);
	// // }
	// for (ReuseKey rk : reuseKey) {
	// if (rk.hasSameIdentifier(reuseKey)) {
	// rk.invalidate();
	// reuseKey.remove(rk);
	// break;
	// }
	// }
	// }
	public void addReuseKey(AbstractEntityModel source) {
		ReusedIdentifier added = this.reuseKey.put(source, source.getMyReuseKey());
		firePropertyChange(PROPERTY_REUSEKEY, null, added);
	}

	public void removeReuseKey(AbstractEntityModel source) {
		ReusedIdentifier removed = this.reuseKey.remove(source);
		if (removed != null) {
			removed.dispose();
		}
		firePropertyChange(PROPERTY_REUSEKEY, removed, null);
	}

	// public abstract void addReuseKey(AbstractEntityModel target);
	// public abstract void removeReuseKey(AbstractEntityModel target);
	public abstract ReusedIdentifier getMyReuseKey();

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
		 firePropertyChange(PROPERTY_ATTRIBUTE_REORDER, oldValue, attributes);
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
		// for (AbstractConnectionModel<?> connection :
		// getModelSourceConnections()) {
		// if (connection instanceof Entity2SubsetTypeRelationship) {
		// return (SubsetType) ((Entity2SubsetTypeRelationship) connection)
		// .getTarget();
		// }
		// }
		// return null;
		List<AbstractConnectionModel> results = findRelationship(
				getModelSourceConnections(),
				Entity2SubsetTypeRelationship.class);
		if (results.size() != 0) {
			return (SubsetType) ((Entity2SubsetTypeRelationship) results.get(0))
					.getTarget();
		}
		return null;
	}

	/**
	 * 指定したクラスのリレーションシップを取得する
	 * 
	 * @param connections
	 *            検索対象の全リレーションシップ
	 * @param clazz
	 *            取得したいリレーションシップのクラス
	 * @return clazzで指定したクラスのリレーションシップのリスト
	 */
	protected List<AbstractConnectionModel> findRelationship(
			List<AbstractConnectionModel> connections, Class<?> clazz) {
		List<AbstractConnectionModel> results = new ArrayList<AbstractConnectionModel>();
		for (AbstractConnectionModel connection : connections) {
			if (clazz.equals(connection.getClass())) {
				results.add(connection);
			}
		}
		return results;
	}

	/**
	 * ソースコネクションから指定したクラスのリレーションシップを取得する
	 * 
	 * @param clazz
	 *            取得したいリレーションシップのクラス
	 * @return clazzで指定したクラスのリレーションシップのリスト
	 */
	public List<AbstractConnectionModel> findRelationshipFromSourceConnections(
			Class<?> clazz) {
		return findRelationship(getModelSourceConnections(), clazz);
	}

	/**
	 * ターゲットコネクションから指定したクラスのリレーションシップを取得する
	 * 
	 * @param clazz
	 *            取得したいリレーションシップのクラス
	 * @return clazzで指定したクラスのリレーションシップのリスト
	 */
	public List<AbstractConnectionModel> findRelationshipFromTargetConnections(
			Class<?> clazz) {
		return findRelationship(getModelTargetConnections(), clazz);
	}
	
	/**
	 * 
	 * @param callConnection
	 */
	public void fireIdentifierChanged(AbstractConnectionModel callConnection) {
		firePropertyChange(AbstractEntityModel.PROPERTY_REUSEKEY, null, null);
		if (getEntityType().equals(EntityType.RESOURCE)) {
			notifyIdentifierChangedToConnections(getModelSourceConnections(), callConnection);
			notifyIdentifierChangedToConnections(getModelTargetConnections(), callConnection);
//			for (AbstractConnectionModel<?> con : getModelTargetConnections()) {
//	
//				if (con instanceof IdentifierChangeListener && con != callConnection ) {
//					((IdentifierChangeListener) con).awareReUseKeysChanged();
//				}
//			}
		} else {
			notifyIdentifierChangedToConnections(getModelSourceConnections(), callConnection);
			for (AbstractConnectionModel con : getModelTargetConnections()) {
				if (con instanceof IdentifierChangeListener && con instanceof Event2EventRelationship && con != callConnection) {
					((IdentifierChangeListener) con).identifierChanged();
				}
			}			
		}

	}
	private void notifyIdentifierChangedToConnections(List<AbstractConnectionModel> connections, AbstractConnectionModel callConnection) {
		for (AbstractConnectionModel con : connections) {
			if (con instanceof IdentifierChangeListener && con != callConnection) {
				((IdentifierChangeListener) con).identifierChanged();
			}			
		}
	}
	
	/**
	 * @return the notImplement
	 */
	public boolean isNotImplement() {
		return notImplement;
	}

	/**
	 * @param notImplement the notImplement to set
	 */
	public void setNotImplement(boolean notImplement) {
		this.notImplement = notImplement;
	}

	/**
	 * エンティティ種類が編集可能か判定する
	 * 
	 * @return 編集可能な場合にtrueを返す。
	 */
	public abstract boolean isEntityTypeEditable();

	/**
	 * エンティティが削除可能か判定する
	 * 
	 * @return 削除可能な場合にtrueを返す。
	 */
	public abstract boolean isDeletable();
}

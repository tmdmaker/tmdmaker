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
	public static final String PROPERTY_ATTRIBUTE = "p_attribute";
	public static final String PROPERTY_REUSEKEY = "p_reusekey";
	public static final String PROPERTY_ATTRIBUTES = "p_attributes";
	protected Map<AbstractEntityModel, ReUseKeys> reuseKeys = new LinkedHashMap<AbstractEntityModel, ReUseKeys>();
	protected List<Attribute> attributes = new ArrayList<Attribute>();
	protected EntityType entityType = EntityType.RESOURCE;

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
	public Map<AbstractEntityModel, ReUseKeys> getReuseKeys() {
		return Collections.unmodifiableMap(reuseKeys);
	}

	/**
	 * @param reuseKeys
	 *            the reuseKeys to set
	 */
	public void setReuseKeys(Map<AbstractEntityModel, ReUseKeys> reuseKeys) {
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
		ReUseKeys added = this.reuseKeys.put(source, source.getMyReuseKey());
		firePropertyChange(PROPERTY_REUSEKEY, null, added);
	}

	public void removeReuseKey(AbstractEntityModel source) {
		ReUseKeys removed = this.reuseKeys.remove(source);
		if (removed != null) {
			removed.dispose();
		}
		firePropertyChange(PROPERTY_REUSEKEY, removed, null);
	}

	// public abstract void addReuseKey(AbstractEntityModel target);
	// public abstract void removeReuseKey(AbstractEntityModel target);
	public abstract ReUseKeys getMyReuseKey();

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
		// List<Attribute> oldValue = this.attributes;
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
		for (AbstractConnectionModel<?> connection : connections) {
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
	 */
	public void notifyReUseKeyChange(AbstractConnectionModel<?> callConnection) {
		firePropertyChange(AbstractEntityModel.PROPERTY_REUSEKEY, null, null);
		if (getEntityType().equals(EntityType.RESOURCE)) {
			fireReUseKeyChange(getModelSourceConnections(), callConnection);
			fireReUseKeyChange(getModelTargetConnections(), callConnection);
//			for (AbstractConnectionModel<?> con : getModelTargetConnections()) {
//	
//				if (con instanceof ReUseKeysChangeListener && con != callConnection ) {
//					((ReUseKeysChangeListener) con).awareReUseKeysChanged();
//				}
//			}
		} else {
			fireReUseKeyChange(getModelSourceConnections(), callConnection);
			for (AbstractConnectionModel<?> con : getModelTargetConnections()) {
				if (con instanceof ReUseKeysChangeListener && con instanceof Event2EventRelationship && con != callConnection ) {
					((ReUseKeysChangeListener) con).awareReUseKeysChanged();
				}
			}			
		}

	}
	private void fireReUseKeyChange(List<AbstractConnectionModel> connections, AbstractConnectionModel<?> callConnection) {
		for (AbstractConnectionModel<?> con : connections) {
			if (con instanceof ReUseKeysChangeListener && con != callConnection ) {
				((ReUseKeysChangeListener) con).awareReUseKeysChanged();
			}			
		}
	}
	/**
	 * エンティティ種類が編集可能か判定する
	 * 
	 * @return 編集可能な場合にtrueを返す。
	 */
	public abstract boolean canEntityTypeEditable();

	/**
	 * エンティティが削除可能か判定する
	 * 
	 * @return 削除可能な場合にtrueを返す。
	 */
	public abstract boolean canDeletable();
}

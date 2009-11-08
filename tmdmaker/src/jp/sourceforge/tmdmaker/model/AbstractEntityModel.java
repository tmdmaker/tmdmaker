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
	public static final String PROPERTY_REUSED = "p_reused";
	public static final String PROPERTY_ATTRIBUTES = "p_attributes";
	public static final String PROPERTY_NOT_IMPLEMENT = "p_notImplement";
	/** 個体指定子プロパティ定数 */
	public static final String PROPERTY_IDENTIFIER = "_property_identifier";
	protected Map<AbstractEntityModel, ReusedIdentifier> reusedIdentifieres = new LinkedHashMap<AbstractEntityModel, ReusedIdentifier>();
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
	 * @return the reusedIdentifieres
	 */
	public Map<AbstractEntityModel, ReusedIdentifier> getReusedIdentifieres() {
		return Collections.unmodifiableMap(reusedIdentifieres);
	}

	/**
	 * @param reusedIdentifieres
	 *            the reusedIdentifieres to set
	 */
	public void setReusedIdentifieres(
			Map<AbstractEntityModel, ReusedIdentifier> reuseKeys) {
		this.reusedIdentifieres = reuseKeys;
	}

	/**
	 * 取得元モデルからReused個体指定子を追加する
	 * 
	 * @param source
	 *            個体指定子取得元
	 */
	public void addReusedIdentifier(AbstractEntityModel source) {
		ReusedIdentifier added = this.reusedIdentifieres.put(source, source
				.createReusedIdentifier());
		firePropertyChange(PROPERTY_REUSED, null, added);
	}

	/**
	 * 取得元モデルから得たReused個体指定子を削除する
	 * 
	 * @param source
	 *            個体指定子取得元
	 */
	public void removeReusedIdentifier(AbstractEntityModel source) {
		ReusedIdentifier removed = this.reusedIdentifieres.remove(source);
		if (removed != null) {
			removed.dispose();
		}
		firePropertyChange(PROPERTY_REUSED, removed, null);
	}

	/**
	 * Reused個体指定子を作成する
	 * 
	 * @return 作成したReused個体指定子
	 */
	public abstract ReusedIdentifier createReusedIdentifier();

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
	 * @param index
	 *            the index to add attribute
	 * @param attribute
	 *            the attribute to add
	 */
	public void addAttribute(int index, Attribute attribute) {
		this.attributes.add(index, attribute);
		firePropertyChange(PROPERTY_ATTRIBUTE, null, attribute);
	}
	/**
	 * 
	 * @param attribute the attribute to remove
	 */
	public void removeAttribute(Attribute attribute) {
		this.attributes.remove(attribute);
		firePropertyChange(PROPERTY_ATTRIBUTE, attribute, null);
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
		firePropertyChange(AbstractEntityModel.PROPERTY_REUSED, null, null);
		if (getEntityType().equals(EntityType.RESOURCE)) {
			notifyIdentifierChangedToConnections(getModelSourceConnections(),
					callConnection);
			notifyIdentifierChangedToConnections(getModelTargetConnections(),
					callConnection);
			// for (AbstractConnectionModel<?> con :
			// getModelTargetConnections()) {
			//	
			// if (con instanceof IdentifierChangeListener && con !=
			// callConnection ) {
			// ((IdentifierChangeListener) con).awareReUseKeysChanged();
			// }
			// }
		} else {
			notifyIdentifierChangedToConnections(getModelSourceConnections(),
					callConnection);
			for (AbstractConnectionModel con : getModelTargetConnections()) {
				if (con instanceof IdentifierChangeListener
						&& con instanceof Event2EventRelationship
						&& con != callConnection) {
					((IdentifierChangeListener) con).identifierChanged();
				}
			}
		}

	}

	/**
	 * 個体指定子が変更されたことをコネクションへ通知する
	 * 
	 * @param connections
	 *            通知対象コネクション
	 * @param callConnection
	 *            通知元コネクション。通知元がコネクションで無い場合はnullが設定される。
	 */
	private void notifyIdentifierChangedToConnections(
			List<AbstractConnectionModel> connections,
			AbstractConnectionModel callConnection) {
		for (AbstractConnectionModel con : connections) {
			if (con instanceof IdentifierChangeListener
					&& con != callConnection) {
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
	 * @param notImplement
	 *            the notImplement to set
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

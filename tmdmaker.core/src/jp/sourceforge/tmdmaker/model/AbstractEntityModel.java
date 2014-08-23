/*
 * Copyright 2009-2014 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jp.sourceforge.tmdmaker.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * エンティティ系モデルの基底クラス
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
	/** アトリビュート */
	protected List<IAttribute> attributes = new ArrayList<IAttribute>();
	/** エンティティ種類 */
	protected EntityType entityType = EntityType.RESOURCE;
	/** 物理実装しない */
	protected boolean notImplement = false;
	/** 実装名 */
	protected String implementName = "";
	/** 派生元に戻して実装するモデルのリスト */
	protected List<AbstractEntityModel> implementDerivationModels = new ArrayList<AbstractEntityModel>();
	/** キー定義情報 */
	protected KeyModels keyModels = new KeyModels();

	public AbstractEntityModel() {
		setConstraint(new Constraint(0, 0, -1, -1));
	}

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
	public void setReusedIdentifieres(Map<AbstractEntityModel, ReusedIdentifier> reuseKeys) {
		this.reusedIdentifieres = reuseKeys;
	}

	/**
	 * 取得元モデルからReused個体指定子を追加する
	 * 
	 * @param source
	 *            個体指定子取得元
	 */
	public void addReusedIdentifier(AbstractEntityModel source) {
		addReusedIdentifier(source, source.createReusedIdentifier());
	}

	/**
	 * 取得元モデルからReused個体指定子を追加する
	 * 
	 * @param source
	 *            個体指定子取得元
	 * @param reused
	 *            取得元モデルから得たReused
	 */
	protected void addReusedIdentifier(AbstractEntityModel source, ReusedIdentifier reused) {
		ReusedIdentifier added = this.reusedIdentifieres.put(source, reused);
		firePropertyChange(PROPERTY_REUSED, null, added);
	}

	/**
	 * 取得元モデルから得たReused個体指定子を削除する
	 * 
	 * @param source
	 *            個体指定子取得元
	 * @return 削除したReused個体指定子
	 */
	public ReusedIdentifier removeReusedIdentifier(AbstractEntityModel source) {
		ReusedIdentifier removed = this.reusedIdentifieres.remove(source);
		firePropertyChange(PROPERTY_REUSED, removed, null);
		return removed;
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
	public List<IAttribute> getAttributes() {
		return attributes;
	}

	/**
	 * @param attributes
	 *            the attributes to set
	 */
	public void setAttributes(List<IAttribute> attributes) {
		List<IAttribute> oldValue = this.attributes;
		// for (Attribute attribute : oldValue) {
		// attribute.setParent(null);
		// }
		this.attributes = attributes;
		// for (Attribute attribute : attributes) {
		// attribute.setParent(this);
		// }
		firePropertyChange(PROPERTY_ATTRIBUTE_REORDER, oldValue, attributes);
	}

	/**
	 * @param attribute
	 *            the attribute to set
	 */
	public void addAttribute(Attribute attribute) {
		this.attributes.add(attribute);
		// attribute.setParent(this);
	}

	/**
	 * @param index
	 *            the index to add attribute
	 * @param attribute
	 *            the attribute to add
	 */
	public void addAttribute(int index, Attribute attribute) {
		this.attributes.add(index, attribute);
		// attribute.setParent(this);
		firePropertyChange(PROPERTY_ATTRIBUTE, null, attribute);
	}

	/**
	 * 
	 * @param attribute
	 *            the attribute to remove
	 */
	public void removeAttribute(Attribute attribute) {
		if (this.attributes.remove(attribute)) {
			// attribute.setParent(null);
			firePropertyChange(PROPERTY_ATTRIBUTE, attribute, null);
		}
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
	public SubsetType findSubsetType() {
		List<AbstractConnectionModel> results = findRelationship(getModelSourceConnections(),
				Entity2SubsetTypeRelationship.class);
		if (results.size() != 0) {
			return (SubsetType) ((Entity2SubsetTypeRelationship) results.get(0)).getTarget();
		}
		return null;
	}

	/**
	 * みなしスーパーセット種類を取得する
	 * 
	 * @return VirtualSupersetType。存在しない場合はnullを返す。
	 */
	public VirtualSupersetType findVirtualSupersetType() {
		List<AbstractConnectionModel> results = findRelationshipFromSourceConnections(Entity2VirtualSupersetTypeRelationship.class);
		if (results.size() != 0) {
			return (VirtualSupersetType) ((Entity2VirtualSupersetTypeRelationship) results.get(0))
					.getTarget();
		}
		results = findRelationshipFromTargetConnections(Entity2VirtualSupersetTypeRelationship.class);
		if (results.size() != 0) {
			return (VirtualSupersetType) ((Entity2VirtualSupersetTypeRelationship) results.get(0))
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
	public List<AbstractConnectionModel> findRelationshipFromSourceConnections(Class<?> clazz) {
		return findRelationship(getModelSourceConnections(), clazz);
	}

	/**
	 * ターゲットコネクションから指定したクラスのリレーションシップを取得する
	 * 
	 * @param clazz
	 *            取得したいリレーションシップのクラス
	 * @return clazzで指定したクラスのリレーションシップのリスト
	 */
	public List<AbstractConnectionModel> findRelationshipFromTargetConnections(Class<?> clazz) {
		return findRelationship(getModelTargetConnections(), clazz);
	}

	/**
	 * 
	 * @param callConnection
	 */
	public void fireIdentifierChanged(AbstractConnectionModel callConnection) {
		firePropertyChange(AbstractEntityModel.PROPERTY_REUSED, null, null);
		if (getEntityType().equals(EntityType.RESOURCE)) {
			notifyIdentifierChangedToConnections(getModelSourceConnections(), callConnection);
			notifyIdentifierChangedToConnections(getModelTargetConnections(), callConnection);
			// for (AbstractConnectionModel<?> con :
			// getModelTargetConnections()) {
			//
			// if (con instanceof IdentifierChangeListener && con !=
			// callConnection ) {
			// ((IdentifierChangeListener) con).awareReUseKeysChanged();
			// }
			// }
		} else {
			notifyIdentifierChangedToConnections(getModelSourceConnections(), callConnection);
			for (AbstractConnectionModel con : getModelTargetConnections()) {
				if (con instanceof IdentifierChangeListener
						&& con instanceof Event2EventRelationship && con != callConnection) {
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
	private void notifyIdentifierChangedToConnections(List<AbstractConnectionModel> connections,
			AbstractConnectionModel callConnection) {
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

	/**
	 * @return the implementName
	 */
	public String getImplementName() {
		return implementName;
	}

	/**
	 * @param implementName
	 *            the implementName to set
	 */
	public void setImplementName(String implementName) {
		this.implementName = implementName;
	}

	/**
	 * 引数で渡されたモデルへ自身の値をコピーする。
	 * 
	 * @param to
	 *            値を設定するモデル
	 */
	public void copyTo(AbstractEntityModel to) {
		to.setEntityType(getEntityType());
		to.setNotImplement(isNotImplement());
		to.setImplementName(getImplementName());
		to.setAttributes(getAttributes());
		to.setImplementDerivationModels(getImplementDerivationModels());
		to.setKeyModels(getKeyModels());
		to.setName(getName());
	}

	/**
	 * 引数で渡されたモデルへ自身の値をコピーする。アトリビュートもコピーする。
	 * 
	 * @param to
	 *            値を設定するモデル
	 */
	public void copyWithAttributesTo(AbstractEntityModel to) {
		copyTo(to);
		to.setAttributes(getCopyAttributes());
	}

	/**
	 * attributeをコピーしたリストを作成する。
	 * 
	 * @return コピーしたアトリビュート
	 */
	private List<IAttribute> getCopyAttributes() {
		List<IAttribute> copies = new ArrayList<IAttribute>(getAttributes().size());
		for (IAttribute a : getAttributes()) {
			IAttribute copy = new Attribute();
			copy.copyFrom(a);
			copies.add(copy);
		}
		return copies;
	}

	/**
	 * 自身のコピーを作成する。deepコピー。
	 * 
	 * @return 自身のコピー
	 */
	public AbstractEntityModel getCopyWithAttributes() {
		AbstractEntityModel model = getCopy();
		model.setAttributes(getCopyAttributes());
		return model;
	}

	/**
	 * @return the implementDerivationModels
	 */
	public List<AbstractEntityModel> getImplementDerivationModels() {
		if (implementDerivationModels == null) {
			return new ArrayList<AbstractEntityModel>();
		}
		return new ArrayList<AbstractEntityModel>(implementDerivationModels);
	}

	/**
	 * @param implementDerivationModels
	 *            the implementDerivationModels to set
	 */
	public void setImplementDerivationModels(List<AbstractEntityModel> implementDerivationModels) {
		this.implementDerivationModels = implementDerivationModels;
	}

	/**
	 * 自身のコピーを作成する。shallowコピー。
	 * 
	 * @return 自身のコピー
	 */
	public abstract AbstractEntityModel getCopy();

	/**
	 * @return the keyModels
	 */
	public KeyModels getKeyModels() {
		if (keyModels == null) {
			keyModels = new KeyModels();
		}
		return keyModels;
	}

	/**
	 * @param keyModels
	 *            the keyModels to set
	 */
	public void setKeyModels(KeyModels keyModels) {
		this.keyModels = keyModels;
	}

	public void move(int x, int y) {
		Constraint oldPosition = getConstraint();
		Constraint newPosition = oldPosition.getCopy();
		newPosition.x = x;
		newPosition.y = y;
		setConstraint(newPosition);
	}

	public int calcurateMaxIdentifierRefSize() {
		int rx = 0;
		final int RMARK_SIZE = 3;
		for (Map.Entry<AbstractEntityModel, ReusedIdentifier> e : getReusedIdentifieres()
				.entrySet()) {
			ReusedIdentifier ri = e.getValue();
			for (IdentifierRef i : ri.getUniqueIdentifieres()) {
				rx = Math.max(i.getName().length() + RMARK_SIZE, rx);
			}
		}
		return rx;
	}

	public int calcurateMaxAttributeNameSize() {
		int ax = 0;
		for (IAttribute a : getAttributes()) {
			ax = Math.max(a.getName().length(), ax);
		}
		return ax;
	}

	/**
	 * HDR-DTLか？
	 * 
	 * @return HDR-DTLの場合にtrueを返す。
	 */
	public boolean isHeaderDetail() {
		Header2DetailRelationship r = getHeader2DetailRelationship();
		return r != null;
	}

	/**
	 * HDR-DTL間のリレーションシップを取得する。
	 * 
	 * @return HDR-DTLリレーションシップ
	 */
	public Header2DetailRelationship getHeader2DetailRelationship() {
		for (AbstractConnectionModel c : getModelSourceConnections()) {
			if (c instanceof Header2DetailRelationship) {
				return (Header2DetailRelationship) c;
			}
		}
		return null;
	}
}

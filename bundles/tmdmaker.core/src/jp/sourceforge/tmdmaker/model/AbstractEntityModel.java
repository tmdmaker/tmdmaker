/*
 * Copyright 2009-2018 TMD-Maker Project <https://tmdmaker.osdn.jp/>
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jp.sourceforge.tmdmaker.model.constraint.Constraint;
import jp.sourceforge.tmdmaker.model.multivalue.MultivalueAnd;
import jp.sourceforge.tmdmaker.model.multivalue.MultivalueOr;
import jp.sourceforge.tmdmaker.model.subset.Subsets;

/**
 * エンティティ系モデルの基底クラス
 *
 * @author nakaG
 *
 */
@SuppressWarnings("serial")
public abstract class AbstractEntityModel extends ConnectableElement {

	private static Logger logger = LoggerFactory.getLogger(AbstractEntityModel.class);

	/** 親モデル */
	private Diagram diagram;
	public static final String PROPERTY_ATTRIBUTE_REORDER = "p_attribute_reorder";
	public static final String PROPERTY_ATTRIBUTE = "p_attribute";
	public static final String PROPERTY_REUSED = "p_reused";
	public static final String PROPERTY_ATTRIBUTES = "p_attributes";
	public static final String PROPERTY_NOT_IMPLEMENT = "p_notImplement";
	/** 個体指定子プロパティ定数 */
	public static final String PROPERTY_IDENTIFIER = "_property_identifier";
	protected Map<AbstractEntityModel, ReusedIdentifier> reusedIdentifiers = new LinkedHashMap<AbstractEntityModel, ReusedIdentifier>();
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

	/**
	 * コンストラクタ.
	 */
	public AbstractEntityModel() {
		setConstraint(new Constraint());
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
	 * @return the reusedIdentifiers
	 */
	public Map<AbstractEntityModel, ReusedIdentifier> getReusedIdentifiers() {
		return Collections.unmodifiableMap(reusedIdentifiers);
	}

	/**
	 * @param reusedIdentifiers
	 *            the reusedIdentifiers to set
	 */
	public void setReusedIdentifiers(Map<AbstractEntityModel, ReusedIdentifier> reusedIdentifiers) {
		this.reusedIdentifiers = reusedIdentifiers;
	}

	/**
	 * 取得元モデルからReused個体指定子を追加する
	 * 
	 * @param source
	 *            個体指定子取得元
	 */
	protected void addReusedIdentifier(AbstractEntityModel source) {
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
		ReusedIdentifier added = this.reusedIdentifiers.put(source, reused);
		firePropertyChange(PROPERTY_REUSED, null, added);
	}

	/**
	 * 取得元モデルから得たReused個体指定子を削除する
	 * 
	 * @param source
	 *            個体指定子取得元
	 * @return 削除したReused個体指定子
	 */
	protected ReusedIdentifier removeReusedIdentifier(AbstractEntityModel source) {
		ReusedIdentifier removed = this.reusedIdentifiers.remove(source);
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
		List<Entity2SubsetTypeRelationship> results = findRelationship(getModelSourceConnections(),
				Entity2SubsetTypeRelationship.class);
		if (results.size() != 0) {
			return ((Entity2SubsetTypeRelationship) results.get(0)).getSubsetType();
		}
		return null;
	}

	/**
	 * みなしスーパーセット種類を取得する
	 * 
	 * @return VirtualSupersetType。存在しない場合はnullを返す。
	 */
	public VirtualSupersetType findVirtualSupersetType() {
		List<Entity2VirtualSupersetTypeRelationship> results = findRelationshipFromSourceConnections(
				Entity2VirtualSupersetTypeRelationship.class);
		if (results.size() != 0) {
			return (VirtualSupersetType) ((Entity2VirtualSupersetTypeRelationship) results.get(0))
					.getTarget();
		}
		results = findRelationshipFromTargetConnections(
				Entity2VirtualSupersetTypeRelationship.class);
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
	@SuppressWarnings("unchecked")
	protected <T extends AbstractConnectionModel> List<T> findRelationship(
			List<AbstractConnectionModel> connections, Class<T> clazz) {
		List<T> results = new ArrayList<T>();
		for (AbstractConnectionModel connection : connections) {
			if (clazz.equals(connection.getClass())) {
				results.add((T) connection);
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
	public <T extends AbstractConnectionModel> List<T> findRelationshipFromSourceConnections(
			Class<T> clazz) {
		return findRelationship(getModelSourceConnections(), clazz);
	}

	/**
	 * ターゲットコネクションから指定したクラスのリレーションシップを取得する
	 * 
	 * @param clazz
	 *            取得したいリレーションシップのクラス
	 * @return clazzで指定したクラスのリレーションシップのリスト
	 */
	public <T extends AbstractConnectionModel> List<T> findRelationshipFromTargetConnections(
			Class<T> clazz) {
		return findRelationship(getModelTargetConnections(), clazz);
	}

	/**
	 * 
	 * @param callConnection
	 */
	protected void fireIdentifierChanged(AbstractConnectionModel callConnection) {
		firePropertyChange(AbstractEntityModel.PROPERTY_REUSED, null, null);
		if (getEntityType().equals(EntityType.RESOURCE)) {
			notifyIdentifierChangedToConnections(getModelSourceConnections(), callConnection);
			notifyIdentifierChangedToConnections(getModelTargetConnections(), callConnection);
		} else {
			notifyIdentifierChangedToConnections(getModelSourceConnections(), callConnection);
			List<Event2EventRelationship> targetcons = findRelationshipFromTargetConnections(
					Event2EventRelationship.class);
			notifyIdentifierChangedToConnections(targetcons, callConnection);
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
	private <T extends AbstractConnectionModel> void notifyIdentifierChangedToConnections(
			List<T> connections, AbstractConnectionModel callConnection) {
		for (AbstractConnectionModel con : connections) {
			if (con instanceof IdentifierChangeListener && con != callConnection) {
				((IdentifierChangeListener) con).identifierChanged();
				logger.debug(getName() + "から" + con.getClass().toString() + "に通知しました。");
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

	public int calcurateMaxIdentifierRefSize() {
		int rx = 0;
		final int RMARK_SIZE = 3;
		for (Map.Entry<AbstractEntityModel, ReusedIdentifier> e : getReusedIdentifiers()
				.entrySet()) {
			ReusedIdentifier ri = e.getValue();
			for (IdentifierRef i : ri.getUniqueIdentifiers()) {
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

	/**
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.model.ModelElement#accept(jp.sourceforge.tmdmaker.model.IVisitor)
	 */
	@Override
	public void accept(IVisitor visitor) {
		visitor.visit(this);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.model.ModelElement#canCreateSubset()
	 */
	@Override
	public boolean canCreateSubset() {
		return true;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.model.ModelElement#canCreateMultivalueOr()
	 */
	@Override
	public boolean canCreateMultivalueOr() {
		return true;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.model.ModelElement#canCreateVirtualEntity()
	 */
	@Override
	public boolean canCreateVirtualEntity() {
		return true;
	}

	/**
	 * リソース系モデルか？
	 * 
	 * @return リソース系の場合にtrueを返す.
	 */
	public boolean isResource() {
		return getEntityType().equals(EntityType.RESOURCE);
	}

	/**
	 * イベント系モデルか？
	 * 
	 * @return イベント系の場合にtrueを返す.
	 */
	public boolean isEvent() {
		return getEntityType().equals(EntityType.EVENT);
	}

	/**
	 * 指定したアトリビュートを持っているか？
	 * 
	 * @param attributeName
	 *            アトリビュート名
	 * @return 指定したアトリビュートを持っている場合にtrueを返す.
	 */
	protected boolean hasAttribute(String attributeName) {
		for (IAttribute a : getAttributes()) {
			if (a.getName().equals(attributeName)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * サブセット集約を返す.
	 * 
	 * サブセット生成等の起点となるオブジェクト.
	 * 
	 * @return
	 */
	public Subsets subsets() {
		return new Subsets(this);
	}

	/**
	 * 多値のANDの集約を返す.
	 * 
	 * 多値のAND生成等の起点となるオブジェクト.
	 * 
	 * @return
	 */
	public MultivalueAnd multivalueAnd() {
		return new MultivalueAnd(this);
	}

	/**
	 * 多値のORの集約を返す.
	 * 
	 * 多値のOR生成等の起点となるオブジェクト.
	 * 
	 * @return
	 */
	public MultivalueOr multivalueOr() {
		return new MultivalueOr(this);
	}
}

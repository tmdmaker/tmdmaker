/*
 * Copyright 2009-2019 TMD-Maker Project <https://www.tmdmaker.org/>
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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * みなしスーパーセット種類（同一(=)/相違マーク(×）).
 *
 * @author nakaG
 *
 */
@SuppressWarnings("serial")
public class VirtualSupersetType extends AbstractSubsetType<VirtualSuperset> {
	/** スーパーセットタイプ */
	public static final String PROPERTY_SUPERSET_TYPE = "_property_superset_type";
	/** 個体指定子プロパティ定数 */
	protected Map<AbstractEntityModel, ReusedIdentifier> reusedIdentifiers = new LinkedHashMap<AbstractEntityModel, ReusedIdentifier>();

	/** アトリビュートに適用するか */
	private boolean applyAttribute = false;

	/**
	 * コンストラクタ.
	 */
	public VirtualSupersetType() {
		super();
		// デフォルト値
		setApplyAttribute(true);
	}

	/**
	 * @return the applyAttribute.
	 */
	public boolean isApplyAttribute() {
		return applyAttribute;
	}

	/**
	 * @param applyAttribute
	 *            the applyAttribute to set.
	 */
	public void setApplyAttribute(boolean applyAttribute) {
		boolean oldValue = this.applyAttribute;
		this.applyAttribute = applyAttribute;
		firePropertyChange(PROPERTY_SUPERSET_TYPE, oldValue, this.applyAttribute);
	}

	/**
	 * 取得元モデルからReused個体指定子を追加する.
	 *
	 * @param source
	 *            個体指定子取得元.
	 */
	public void addReusedIdentifier(AbstractEntityModel source) {
		addReusedIdentifier(source, source.createReusedIdentifier());
	}

	/**
	 * 取得元モデルからReused個体指定子を追加する.
	 *
	 * @param source
	 *            個体指定子取得元.
	 * @param reused
	 *            取得元モデルから得たReused.
	 */
	protected void addReusedIdentifier(AbstractEntityModel source, ReusedIdentifier reused) {
		this.reusedIdentifiers.put(source, reused);
		notifyRelationshipChanged();
	}

	/**
	 * 取得元モデルから得たReused個体指定子を削除する.
	 *
	 * @param source
	 *            個体指定子取得元.
	 * @return 削除したReused.
	 */
	public ReusedIdentifier removeReusedIdentifier(AbstractEntityModel source) {
		ReusedIdentifier removed = this.reusedIdentifiers.remove(source);
		notifyRelationshipChanged();
		return removed;
	}

	/**
	 * みなしスーパーセットにリレーションシップの変更を通知する.
	 */
	private void notifyRelationshipChanged() {
		((AbstractEntityModel) getModelSourceConnections().get(0).getTarget())
				.fireIdentifierChanged();
	}

	/**
	 * @return the reusedIdentifiers.
	 */
	public Map<AbstractEntityModel, ReusedIdentifier> getReusedIdentifiers() {
		return reusedIdentifiers;
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
	 * @see jp.sourceforge.tmdmaker.model.AbstractSubsetType#getSuperset()
	 */
	@Override
	public VirtualSuperset getSuperset() {
		if (!getModelSourceConnections().isEmpty()) {
			AbstractConnectionModel r = getModelSourceConnections().get(0);
			return (VirtualSuperset) r.getTarget();
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.model.AbstractSubsetType#getSubsetList()
	 */
	@Override
	public List<AbstractEntityModel> getSubsetList() {
		List<AbstractEntityModel> list = new ArrayList<AbstractEntityModel>();
		for (AbstractConnectionModel c : getModelTargetConnections()) {
			ConnectableElement m = c.getSource();
			if (m instanceof AbstractEntityModel) {
				list.add((AbstractEntityModel) m);
			}
		}
		return list;
	}
}

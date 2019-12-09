/*
 * Copyright 2009-2019 TMD-Maker Project <https://www.tmdmaker.org>
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
package org.tmdmaker.core.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.tmdmaker.core.model.parts.ModelName;
import org.tmdmaker.core.model.rule.ImplementRule;
import org.tmdmaker.core.model.virtual.VirtualSubsets;

/**
 * みなしスーパーセット.
 *
 * @author nakaG
 *
 */
@SuppressWarnings("serial")
public class VirtualSuperset extends AbstractEntityModel {

	/**
	 * コンストラクタは公開しない.
	 */
	protected VirtualSuperset() {
	}

	/**
	 * みなしスーパーセットを作成する。
	 * 
	 * @param name
	 *            みなしスーパーセット名
	 * @return みなしスーパーセット
	 */
	public static VirtualSuperset of(ModelName name) {
		VirtualSuperset superset = new VirtualSuperset();
		superset.setName(name.getValue());
		superset.setNotImplement(true);
		ImplementRule.setModelDefaultValue(superset);

		return superset;
	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see org.tmdmaker.core.model.AbstractEntityModel#createReusedIdentifier()
	 */
	@Override
	protected ReusedIdentifier createReusedIdentifier() {
		return null;
	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see org.tmdmaker.core.model.AbstractEntityModel#isDeletable()
	 */
	@Override
	public boolean isDeletable() {
		return getModelSourceConnections().isEmpty();
	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see org.tmdmaker.core.model.AbstractEntityModel#isEntityTypeEditable()
	 */
	@Override
	public boolean isEntityTypeEditable() {
		return false;
	}

	/**
	 * みなしサブセット（みなしスーパーセットに設定されたモデル）を取得する.
	 *
	 * @return みなしサブセットのリスト.
	 */
	public List<AbstractEntityModel> getVirtualSubsetList() {
		VirtualSupersetType type = getVirtualSupersetType();
		if (type == null) {
			return new ArrayList<AbstractEntityModel>();
		}
		return type.getSubsetList();
	}

	/**
	 * みなしスーパーセット種類とみなしサブセット間のリレーションシップを取得する.
	 *
	 * @return みなしスーパーセット種類とみなしサブセット間のリレーションシップ.
	 */
	public List<Entity2VirtualSupersetTypeRelationship> getVirtualSubsetRelationshipList() {
		VirtualSupersetType2VirtualSupersetRelationship r = getCreationRelationship();
		if (r != null) {
			return r.getSubset2typeRelationshipList();
		}
		return Collections.emptyList();
	}

	/**
	 * 対応するみなしスーパーセット種類を取得する.
	 *
	 * @return みなしスーパーセット種類.
	 */
	public VirtualSupersetType getVirtualSupersetType() {
		VirtualSupersetType2VirtualSupersetRelationship r = getCreationRelationship();
		if (r != null) {
			return r.getVirtualSupersetType();
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.tmdmaker.core.model.AbstractEntityModel#getCopy()
	 */
	@Override
	public VirtualSuperset getCopy() {
		VirtualSuperset copy = new VirtualSuperset();
		copyTo(copy);
		return copy;
	}

	/**
	 * 
	 * {@inheritDoc}
	 *
	 * @see org.tmdmaker.core.model.AbstractEntityModel#accept(org.tmdmaker.core.model.IVisitor)
	 */
	@Override
	public void accept(IVisitor visitor) {
		visitor.visit(this);
	}

	public boolean hasSubset() {
		return getVirtualSupersetType() != null;
	}

	/**
	 * 
	 * {@inheritDoc}
	 *
	 * @see org.tmdmaker.core.model.AbstractEntityModel#canCreateSubset()
	 */
	@Override
	public boolean canCreateSubset() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.tmdmaker.core.model.AbstractEntityModel#canCreateMultivalueOr()
	 */
	@Override
	public boolean canCreateMultivalueOr() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.tmdmaker.core.model.AbstractEntityModel#canCreateVirtualEntity()
	 */
	@Override
	public boolean canCreateVirtualEntity() {
		return false;
	}

	/**
	 * みなしスーパーセット作成時のリレーションシップを返す.
	 * 
	 * @return
	 */
	public VirtualSupersetType2VirtualSupersetRelationship getCreationRelationship() {
		if (!getModelTargetConnections().isEmpty()) {
			return (VirtualSupersetType2VirtualSupersetRelationship) getModelTargetConnections()
					.get(0);
		}
		return null;
	}

	public VirtualSubsets virtualSubsets() {
		return new VirtualSubsets(this);
	}
}

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
import java.util.List;

import org.tmdmaker.core.model.parts.ModelName;

/**
 * みなしスーパーセットとサブセット間のリレーションシップ.
 * 
 * @author nakag
 *
 */
@SuppressWarnings("serial")
public class VirtualSupersetType2VirtualSupersetRelationship extends AbstractConnectionModel {
	/** みなしスーパーセット */
	@SuppressWarnings("unused")
	@Deprecated
	private VirtualSuperset superset;
	/** みなしスーパーセットタイプ */
	@SuppressWarnings("unused")
	@Deprecated
	private VirtualSupersetType type;
	/** みなしスーパーセットタイプとみなしサブセットとのリレーションシップ */
	private List<Entity2VirtualSupersetTypeRelationship> subset2typeRelationshipList;

	/**
	 * Patch040SerializerHandler用
	 */
	@Deprecated
	public VirtualSupersetType2VirtualSupersetRelationship() {

	}

	public VirtualSupersetType2VirtualSupersetRelationship(VirtualSuperset superset) {
		setSource(new VirtualSupersetType());
		setTarget(superset);
	}

	public VirtualSupersetType2VirtualSupersetRelationship(VirtualSuperset superset,
			List<AbstractEntityModel> subsets) {
		setSource(new VirtualSupersetType());
		setTarget(superset);
		setSubsetList(subsets);

	}

	/**
	 * Constructor.
	 * 
	 * @param virtualSupersetName
	 *            みなしスーパセット名.
	 * @param subsets
	 *            みなしサブセット.
	 */
	public VirtualSupersetType2VirtualSupersetRelationship(String virtualSupersetName,
			List<AbstractEntityModel> subsets) {
		setSource(new VirtualSupersetType());
		setTarget(VirtualSuperset.of(new ModelName(virtualSupersetName)));
		setSubsetList(subsets);
	}

	public void setSubsetList(List<AbstractEntityModel> subsets) {
		this.subset2typeRelationshipList = new ArrayList<Entity2VirtualSupersetTypeRelationship>();
		for (AbstractEntityModel s : subsets) {
			Entity2VirtualSupersetTypeRelationship r = new Entity2VirtualSupersetTypeRelationship(s,
					getVirtualSupersetType());
			subset2typeRelationshipList.add(r);
		}
	}

	/**
	 * 
	 * {@inheritDoc}
	 *
	 * @see org.tmdmaker.core.model.AbstractConnectionModel#connect()
	 */
	@Override
	public void connect() {
		super.connect();
		connectSubsetList();
		Diagram diagram = getDiagram();
		if (diagram != null) {
			diagram.addChild(getVirtualSuperset());
			diagram.addChild(getVirtualSupersetType());
		}
	}

	private Diagram getDiagram() {
		return ((AbstractEntityModel) subset2typeRelationshipList.get(0).getSource()).getDiagram();
	}

	private void connectSubsetList() {
		for (Entity2VirtualSupersetTypeRelationship r : subset2typeRelationshipList) {
			r.connect();
		}
	}

	/**
	 * 
	 * {@inheritDoc}
	 *
	 * @see org.tmdmaker.core.model.AbstractConnectionModel#disconnect()
	 */
	@Override
	public void disconnect() {
		Diagram diagram = getDiagram();
		if (diagram != null) {
			diagram.removeChild(getVirtualSupersetType());
			diagram.removeChild(getVirtualSuperset());
		}
		disconnectSubsetList();
		super.disconnect();
	}

	private void disconnectSubsetList() {
		for (Entity2VirtualSupersetTypeRelationship r : subset2typeRelationshipList) {
			r.disconnect();
		}
	}

	/**
	 * 
	 * {@inheritDoc}
	 *
	 * @see org.tmdmaker.core.model.AbstractConnectionModel#isDeletable()
	 */
	@Override
	public boolean isDeletable() {
		return true;
	}

	/**
	 * 
	 * {@inheritDoc}
	 *
	 * @see org.tmdmaker.core.model.AbstractConnectionModel#getSourceName()
	 */
	@Override
	public String getSourceName() {
		return getTarget().getName();
	}

	/**
	 * 
	 * {@inheritDoc}
	 *
	 * @see org.tmdmaker.core.model.AbstractConnectionModel#getTargetName()
	 */
	@Override
	public String getTargetName() {
		StringBuilder builder = new StringBuilder();
		boolean first = true;
		VirtualSupersetType vsType = getVirtualSupersetType();
		for (AbstractEntityModel m : vsType.getSubsetList()) {
			if (first) {
				first = false;
			} else {
				builder.append(',');
			}
			builder.append(m.getName());
		}
		return builder.toString();
	}

	/**
	 * 
	 * {@inheritDoc}
	 *
	 * @see org.tmdmaker.core.model.ModelElement#accept(org.tmdmaker.core.model.IVisitor)
	 */
	@Override
	public void accept(IVisitor visitor) {
		visitor.visit(this);
	}

	private List<AbstractEntityModel> getSubsetList() {
		List<AbstractEntityModel> list = new ArrayList<AbstractEntityModel>();
		for (Entity2VirtualSupersetTypeRelationship r : this.subset2typeRelationshipList) {
			list.add((AbstractEntityModel) r.getSource());
		}
		return list;
	}

	public void reconnect(List<AbstractEntityModel> subsets) {
		List<AbstractEntityModel> currentSubsetList = getSubsetList();
		List<Entity2VirtualSupersetTypeRelationship> newSubsetList = new ArrayList<Entity2VirtualSupersetTypeRelationship>();

		for (AbstractEntityModel s : subsets) {

			if (currentSubsetList.contains(s)) {
				int index = currentSubsetList.indexOf(s);
				newSubsetList.add(subset2typeRelationshipList.get(index));
			} else {
				newSubsetList.add(
						new Entity2VirtualSupersetTypeRelationship(s, getVirtualSupersetType()));
			}
		}
		disconnectSubsetList();
		subset2typeRelationshipList = newSubsetList;
		connectSubsetList();
	}

	public List<Entity2VirtualSupersetTypeRelationship> getSubset2typeRelationshipList() {
		return subset2typeRelationshipList;
	}

	public void setSubset2typeRelationshipList(
			List<Entity2VirtualSupersetTypeRelationship> subset2typeRelationshipList) {
		this.subset2typeRelationshipList = subset2typeRelationshipList;
	}

	public VirtualSuperset getVirtualSuperset() {
		return (VirtualSuperset) getTarget();
	}

	public VirtualSupersetType getVirtualSupersetType() {
		return (VirtualSupersetType) getSource();
	}
}

/*
 * Copyright 2009-2017 TMD-Maker Project <http://tmdmaker.osdn.jp/>
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
import java.util.List;

import jp.sourceforge.tmdmaker.model.rule.VirtualEntityRule;

/**
 * みなしスーパーセットとサブセット間のリレーションシップ.
 * 
 * @author nakag
 *
 */
@SuppressWarnings("serial")
public class VirtualSupersetType2VirtualSupersetRelationship extends AbstractConnectionModel {
	/** みなしスーパーセット */
	private VirtualSuperset superset;
	/** みなしスーパーセットタイプ */
	private VirtualSupersetType type;
	/** みなしスーパーセットタイプとみなしサブセットとのリレーションシップ */
	private List<Entity2VirtualSupersetTypeRelationship> subset2typeRelationshipList;

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
		this.superset = VirtualEntityRule.createVirtualSuperset(virtualSupersetName);
		this.type = new VirtualSupersetType();
		setSource(type);
		setTarget(superset);
		this.subset2typeRelationshipList = new ArrayList<Entity2VirtualSupersetTypeRelationship>();
		for (AbstractEntityModel s : subsets) {
			Entity2VirtualSupersetTypeRelationship r = new Entity2VirtualSupersetTypeRelationship(s,
					type);
			subset2typeRelationshipList.add(r);
		}
	}

	/**
	 * 
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.model.AbstractConnectionModel#connect()
	 */
	@Override
	public void connect() {
		super.connect();
		connectSubsetList();
		Diagram diagram = getDiagram();
		diagram.addChild(superset);
		diagram.addChild(type);
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
	 * @see jp.sourceforge.tmdmaker.model.AbstractConnectionModel#disconnect()
	 */
	@Override
	public void disconnect() {
		Diagram diagram = getDiagram();
		diagram.removeChild(type);
		diagram.removeChild(superset);
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
	 * @see jp.sourceforge.tmdmaker.model.AbstractConnectionModel#isDeletable()
	 */
	@Override
	public boolean isDeletable() {
		return true;
	}

	/**
	 * 
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.model.AbstractConnectionModel#getSourceName()
	 */
	@Override
	public String getSourceName() {
		return getTarget().getName();
	}

	/**
	 * 
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.model.AbstractConnectionModel#getTargetName()
	 */
	@Override
	public String getTargetName() {
		StringBuilder builder = new StringBuilder();
		boolean first = true;
		VirtualSupersetType type = (VirtualSupersetType) getSource();
		for (AbstractEntityModel m : type.getSubsetList()) {
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
	 * @see jp.sourceforge.tmdmaker.model.ModelElement#accept(jp.sourceforge.tmdmaker.model.IVisitor)
	 */
	@Override
	public void accept(IVisitor visitor) {
		visitor.visit(this);
	}

	public VirtualSuperset getSuperset() {
		return superset;
	}

	public VirtualSupersetType getType() {
		return type;
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
			int index = currentSubsetList.indexOf(s);
			if (index > 0) {
				newSubsetList.add(subset2typeRelationshipList.get(index));
			} else {
				newSubsetList.add(new Entity2VirtualSupersetTypeRelationship(s, type));
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

}

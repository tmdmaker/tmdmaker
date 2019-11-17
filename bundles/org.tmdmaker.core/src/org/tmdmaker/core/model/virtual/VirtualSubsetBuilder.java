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
package org.tmdmaker.core.model.virtual;

import java.util.List;

import org.tmdmaker.core.model.AbstractEntityModel;
import org.tmdmaker.core.model.Entity2VirtualSupersetTypeRelationship;
import org.tmdmaker.core.model.VirtualSuperset;
import org.tmdmaker.core.model.VirtualSupersetType2VirtualSupersetRelationship;

/**
 * みなしサブセットを生成するクラス.
 * 
 * @author nakag
 *
 */
public class VirtualSubsetBuilder {
	private VirtualSuperset parent;
	private VirtualSupersetType2VirtualSupersetRelationship relationship;
	private List<AbstractEntityModel> addSubsetList;
	private boolean creation = true;
	private List<Entity2VirtualSupersetTypeRelationship> oldSubsetRelationshipList;

	public VirtualSubsetBuilder(VirtualSuperset parent) {
		this.parent = parent;
		this.relationship = this.parent.getCreationRelationship();
		this.creation = this.relationship == null;
		if (!this.creation) {
			this.oldSubsetRelationshipList = this.relationship.getSubset2typeRelationshipList();
		}
	}

	public VirtualSubsetBuilder subsetList(List<AbstractEntityModel> addSubsetList) {
		this.addSubsetList = addSubsetList;
		return this;
	}

	public void build() {
		if (this.creation) {
			if (addSubsetList != null && !addSubsetList.isEmpty()) {
				if (this.relationship == null) {
					this.relationship = new VirtualSupersetType2VirtualSupersetRelationship(this.parent, this.addSubsetList);
				}
				this.relationship.connect();
			}
			return;
		}

		if (addSubsetList != null && !addSubsetList.isEmpty()) {
			this.relationship.reconnect(addSubsetList);
		} else {
			this.relationship.disconnect();
		}
	}
	
	public void rollback() {
		if (this.creation) {
			if (this.relationship != null) {
				this.relationship.disconnect();
			}
		} else {
			this.relationship.setSubset2typeRelationshipList(this.oldSubsetRelationshipList);
			this.relationship.connect();
		}
	}
}

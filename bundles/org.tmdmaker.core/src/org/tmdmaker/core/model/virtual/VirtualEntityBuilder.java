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

import org.tmdmaker.core.model.AbstractEntityModel;
import org.tmdmaker.core.model.Entity2VirtualEntityRelationship;
import org.tmdmaker.core.model.VirtualEntity;
import org.tmdmaker.core.model.VirtualEntityType;
import org.tmdmaker.core.model.parts.ModelName;

/**
 * みなしエンティティを生成するクラス.
 * 
 * @author nakag
 *
 */
public class VirtualEntityBuilder {
	private AbstractEntityModel source;
	private ModelName virtualEntityName = new ModelName("VE");
	private VirtualEntityType type = VirtualEntityType.NORMAL;
	private Entity2VirtualEntityRelationship relationship;

	protected VirtualEntityBuilder(AbstractEntityModel source) {
		this.source = source;
	}

	public VirtualEntityBuilder virtualEntityName(ModelName virtualEntityName) {
		this.virtualEntityName = virtualEntityName;
		return this;
	}

	public VirtualEntityBuilder virtualEntityType(VirtualEntityType type) {
		this.type = type;
		return this;
	}

	public void build() {
		if (this.relationship == null) {
			this.relationship = new Entity2VirtualEntityRelationship(this.source,
					this.virtualEntityName.getValue(), this.type);
		}
		this.relationship.connect();
	}

	public void rollback() {
		this.relationship.disconnect();
	}

	public VirtualEntity getVirtualEntity() {
		return this.relationship.getVirtualEntity();
	}
}

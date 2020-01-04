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
package org.tmdmaker.core.model.virtual;

import java.util.Collections;
import java.util.List;

import org.tmdmaker.core.model.AbstractEntityModel;
import org.tmdmaker.core.model.VirtualSuperset;
import org.tmdmaker.core.model.VirtualSupersetType;

/**
 * みなしサブセットの集約クラス.
 * 
 * みなしサブセット操作の起点になる.
 * 
 * @author nakag
 *
 */
public class VirtualSubsets {
	private VirtualSuperset parent;

	public VirtualSubsets(VirtualSuperset parent) {
		this.parent = parent;
	}

	public VirtualSubsetBuilder builder() {
		return new VirtualSubsetBuilder(this.parent);
	}
	
	public List<AbstractEntityModel> all() {
		VirtualSupersetType type = this.parent.getVirtualSupersetType();
		if (type != null) {
			return type.getSubsetList();
		}
		return Collections.emptyList();
	}
}

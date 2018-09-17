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
package jp.sourceforge.tmdmaker.model.virtual;

import java.util.ArrayList;
import java.util.List;

import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.Entity2VirtualEntityRelationship;
import jp.sourceforge.tmdmaker.model.ModelQuery;
import jp.sourceforge.tmdmaker.model.VirtualEntity;
import jp.sourceforge.tmdmaker.model.parts.ModelName;

/**
 * みなしエンティティを検索するクラス.
 * 
 * @author nakag
 *
 */
public class VirtualEntityQuery implements ModelQuery<VirtualEntity> {
	private AbstractEntityModel source;

	protected VirtualEntityQuery(AbstractEntityModel source) {
		this.source = source;
	}

	@Override
	public List<VirtualEntity> findByName(ModelName name) {
		List<VirtualEntity> results = new ArrayList<VirtualEntity>();
		for (Entity2VirtualEntityRelationship r : this.source
				.findRelationshipFromSourceConnections(Entity2VirtualEntityRelationship.class)) {
			VirtualEntity ve = r.getVirtualEntity();
			if (ve.getName().equals(name.getValue())) {
				results.add(ve);
			}
		}
		return results;
	}

}

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
package org.tmdmaker.core.model.multivalue;

import java.util.ArrayList;
import java.util.List;

import org.tmdmaker.core.model.AbstractEntityModel;
import org.tmdmaker.core.model.ModelQuery;
import org.tmdmaker.core.model.MultivalueOrEntity;
import org.tmdmaker.core.model.MultivalueOrRelationship;
import org.tmdmaker.core.model.filter.NameMatchFilter;
import org.tmdmaker.core.model.parts.ModelName;

/**
 * 多値のORを検索するクラス.
 * 
 * @author nakag
 *
 */
public class MultivalueOrQuery implements ModelQuery<MultivalueOrEntity> {
	private AbstractEntityModel source;

	public MultivalueOrQuery(AbstractEntityModel source) {
		this.source = source;
	}

	@Override
	public List<MultivalueOrEntity> findByName(ModelName name) {
		List<MultivalueOrEntity> results = new ArrayList<MultivalueOrEntity>();
		for (MultivalueOrRelationship r : this.source
				.findRelationshipFromSourceConnections(MultivalueOrRelationship.class)) {
			MultivalueOrEntity target = new NameMatchFilter(name).filter(r.getMultivalueOrEntity());
			if (target != null) {
				results.add(target);
			}
		}
		return results;
	}

}

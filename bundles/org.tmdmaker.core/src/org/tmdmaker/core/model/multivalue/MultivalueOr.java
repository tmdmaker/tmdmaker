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
package org.tmdmaker.core.model.multivalue;

import java.util.ArrayList;
import java.util.List;

import org.tmdmaker.core.model.AbstractConnectionModel;
import org.tmdmaker.core.model.AbstractEntityModel;
import org.tmdmaker.core.model.MultivalueOrEntity;
import org.tmdmaker.core.model.MultivalueOrRelationship;

/**
 * 多値のORの集約クラス
 * 
 * @author nakag
 *
 */
public class MultivalueOr {
	private AbstractEntityModel source;

	public MultivalueOr(AbstractEntityModel source) {
		this.source = source;
	}

	public MultivalueOrBuilder builder() {
		return new MultivalueOrBuilder(this.source);
	}

	public List<MultivalueOrEntity> all() {
		List<MultivalueOrRelationship> relationships = this.source
				.findRelationshipFromSourceConnections(MultivalueOrRelationship.class);
		List<MultivalueOrEntity> results = new ArrayList<MultivalueOrEntity>();
		for (AbstractConnectionModel r : relationships) {
			results.add((MultivalueOrEntity) r.getTarget());
		}
		return results;
	}

	public MultivalueOrQuery query() {
		return new MultivalueOrQuery(this.source);
	}

}

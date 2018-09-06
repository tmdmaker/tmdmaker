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
package jp.sourceforge.tmdmaker.model.multivalue;

import java.util.ArrayList;
import java.util.List;

import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.ModelQuery;
import jp.sourceforge.tmdmaker.model.MultivalueOrEntity;
import jp.sourceforge.tmdmaker.model.MultivalueOrRelationship;
import jp.sourceforge.tmdmaker.model.parts.ModelName;

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
			MultivalueOrEntity target = r.getMultivalueOrEntity();
			if (name.getValue().equals(target.getName())) {
				results.add(target);
			}
		}
		return results;
	}

}

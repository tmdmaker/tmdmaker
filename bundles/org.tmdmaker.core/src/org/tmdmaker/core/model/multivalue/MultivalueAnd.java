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

import org.tmdmaker.core.model.AbstractEntityModel;
import org.tmdmaker.core.model.Detail;
import org.tmdmaker.core.model.Header2DetailRelationship;
import org.tmdmaker.core.model.MultivalueAndAggregator;
import org.tmdmaker.core.model.MultivalueAndSuperset;

/**
 * 多値のANDの集約クラス
 * 
 * @author nakag
 *
 */
public class MultivalueAnd {
	private AbstractEntityModel model;

	public MultivalueAnd(AbstractEntityModel model) {
		this.model = model;
	}

	public Detail detail() {
		Header2DetailRelationship relationship = this.model.getHeader2DetailRelationship();
		if (relationship != null) {
			return relationship.getDetail();
		}
		return null;
	}

	public MultivalueAndAggregator aggregator() {
		Header2DetailRelationship relationship = this.model.getHeader2DetailRelationship();
		if (relationship != null) {
			return relationship.getAggregator();
		}
		return null;
	}

	public MultivalueAndSuperset superset() {
		Header2DetailRelationship relationship = this.model.getHeader2DetailRelationship();
		if (relationship != null) {
			return relationship.getMultivalueAndSuperset();
		}
		return null;

	}

	public MultivalueAndBuilder builder() {
		return new MultivalueAndBuilder(this.model);
	}
}

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

import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.Detail;
import jp.sourceforge.tmdmaker.model.Header2DetailRelationship;
import jp.sourceforge.tmdmaker.model.MultivalueAndAggregator;
import jp.sourceforge.tmdmaker.model.MultivalueAndSuperset;

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
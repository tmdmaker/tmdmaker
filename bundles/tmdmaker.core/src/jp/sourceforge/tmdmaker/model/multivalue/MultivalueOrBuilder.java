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
import jp.sourceforge.tmdmaker.model.MultivalueOrEntity;
import jp.sourceforge.tmdmaker.model.MultivalueOrRelationship;

/**
 * 多値のORを生成するクラス.
 * 
 * @author nakag
 * 
 */
public class MultivalueOrBuilder {
	private AbstractEntityModel source;
	private String typeName;
	private MultivalueOrRelationship relationship;

	protected MultivalueOrBuilder(AbstractEntityModel source) {
		this.source = source;
	}

	public MultivalueOrBuilder typeName(String typeName) {
		this.typeName = typeName;
		return this;
	}

	public void build() {
		if (this.relationship == null) {
			this.relationship = new MultivalueOrRelationship(this.source, this.typeName);
		}
		this.relationship.connect();
	}

	public void rollback() {
		this.relationship.disconnect();
	}

	public MultivalueOrEntity getMultivalueOrEntity() {
		return this.relationship.getMultivalueOrEntity();
	}
}

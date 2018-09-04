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

import jp.sourceforge.tmdmaker.model.AbstractConnectionModel;
import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.AbstractRelationship;
import jp.sourceforge.tmdmaker.model.Cardinality;
import jp.sourceforge.tmdmaker.model.Header2DetailRelationship;
import jp.sourceforge.tmdmaker.model.relationship.Relationship;

/**
 * 多値のANDを生成するクラス.
 * 
 * @author nakag
 *
 */
public class MultivalueAndBuilder {
	private AbstractEntityModel header;
	private Header2DetailRelationship relationship;
	private List<AbstractRelationship> removedRelationshipList = new ArrayList<AbstractRelationship>();
	private List<AbstractRelationship> reconnectedRelationshipList = new ArrayList<AbstractRelationship>();

	/**
	 * コンストラクタは公開しない.
	 * 
	 * @param header
	 */
	protected MultivalueAndBuilder(AbstractEntityModel header) {
		this.header = header;
		this.relationship = new Header2DetailRelationship(header);
	}

	public void build() {
		this.relationship.connect();
		if (header.isEvent()) {
			for (AbstractConnectionModel con : header.getModelTargetConnections()) {
				if (con instanceof AbstractRelationship) {
					AbstractRelationship relation = (AbstractRelationship) con;
					if (relation.isMultiValue()) {
						this.removedRelationshipList.add(relation);
						AbstractEntityModel source = relation.getSource();
						AbstractRelationship reconnectedRelationship = Relationship.of(source,
								this.relationship.getDetail());
						reconnectedRelationship.setTargetCardinality(Cardinality.MANY);
						this.reconnectedRelationshipList.add(reconnectedRelationship);
					}
				}
			}
			if (!this.removedRelationshipList.isEmpty()) {
				for (AbstractRelationship removed : this.removedRelationshipList) {
					removed.disconnect();
				}
				for (AbstractRelationship reconnected : this.reconnectedRelationshipList) {
					reconnected.connect();
				}
			}

		}

	}

	public void rollback() {
		this.relationship.disconnect();
		if (!this.removedRelationshipList.isEmpty()) {
			for (AbstractRelationship removed : this.removedRelationshipList) {
				removed.connect();
			}
			for (AbstractRelationship reconnected : this.reconnectedRelationshipList) {
				reconnected.disconnect();
			}
		}
	}
}

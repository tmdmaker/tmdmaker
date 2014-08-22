/*
 * Copyright 2009-2011 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
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
package jp.sourceforge.tmdmaker.model;

import jp.sourceforge.tmdmaker.model.rule.MultivalueRule;

/**
 * エンティティ系モデルと多値のORとのリレーションシップ
 * 
 * @author nakaG
 * 
 */
@SuppressWarnings("serial")
public class MultivalueOrRelationship extends
		TransfarReuseKeysToTargetRelationship {
	/** MO */
	private MultivalueOrEntity table;

	/**
	 * コンストラクタ
	 * 
	 * @param source
	 *            MOの元エンティティ
	 * @param typeName
	 *            MO名
	 */
	public MultivalueOrRelationship(AbstractEntityModel source, String typeName) {
		setSource(source);

		MultivalueOrEntity target = MultivalueRule.createMultivalueOrEntity(
				getSource(), typeName);

		setTargetCardinality(Cardinality.MANY);

		setTarget(target);
		this.table = target;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.AbstractConnectionModel#connect()
	 */
	@Override
	public void connect() {
		Diagram diagram = getSource().getDiagram();
		diagram.addChild(getTarget());
		super.connect();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.AbstractConnectionModel#disconnect()
	 */
	@Override
	public void disconnect() {
		super.disconnect();
		getSource().getDiagram().removeChild(table);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.TransfarReuseKeysToTargetRelationship#isDeletable()
	 */
	@Override
	public boolean isDeletable() {
		return getTarget().isDeletable();
	}

}
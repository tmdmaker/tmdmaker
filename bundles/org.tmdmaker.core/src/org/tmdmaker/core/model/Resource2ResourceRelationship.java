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
package org.tmdmaker.core.model;

import org.tmdmaker.core.model.rule.ImplementRule;

/**
 * リソース系エンティティとリソース系エンティティとのリレーションシップ
 * 
 * @author nakaG
 * 
 */
@SuppressWarnings("serial")
public class Resource2ResourceRelationship extends AbstractRelationship {

	/**
	 * 対照表
	 */
	private CombinationTable table;

	/** 対照表とのコネクション */
	private RelatedRelationship combinationTableConnection;
	/** ソース移送先から削除したReused */
	private ReusedIdentifier sourceReuseIdentifier;
	/** ターゲット移送先から削除したReused */
	private ReusedIdentifier targetReuseIdentifier;

	/**
	 * コンストラクタ
	 * 
	 * @param source
	 *            ソース
	 * @param target
	 *            ターゲット
	 */
	public Resource2ResourceRelationship(AbstractEntityModel source, AbstractEntityModel target) {
		setSource(source);
		setTarget(target);

		this.table = createCombinationTable(source, target);

		this.combinationTableConnection = new RelatedRelationship(this, this.table);
		this.setCenterMark(true);
	}

	private CombinationTable createCombinationTable(AbstractEntityModel source,
			AbstractEntityModel target) {
		CombinationTable ctable = new CombinationTable();
		ctable.setName(createCombinationTableName(source, target));
		ImplementRule.setModelDefaultValue(ctable);

		return ctable;
	}

	private static String createCombinationTableName(AbstractEntityModel source,
			AbstractEntityModel target) {
		return source.getName().replace(CombinationTable.COMBINATION_TABLE_SUFFIX, "") + "."
				+ target.getName().replace(CombinationTable.COMBINATION_TABLE_SUFFIX, "")
				+ CombinationTable.COMBINATION_TABLE_SUFFIX;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.tmdmaker.core.model.AbstractConnectionModel#connect()
	 */
	@Override
	public void connect() {
		super.connect();
		Diagram diagram = getSource().getDiagram();
		if (diagram != null) {
			diagram.addChild(this.table);
		}
		this.combinationTableConnection.connect();
		if (sourceReuseIdentifier == null) {
			this.table.addReusedIdentifier(getSource());
		} else {
			this.table.addReusedIdentifier(getSource(), sourceReuseIdentifier);
			sourceReuseIdentifier = null;
		}
		if (targetReuseIdentifier == null) {
			this.table.addReusedIdentifier(getTarget());
		} else {
			this.table.addReusedIdentifier(getTarget(), targetReuseIdentifier);
			targetReuseIdentifier = null;
		}
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.tmdmaker.core.model.AbstractConnectionModel#disconnect()
	 */
	@Override
	public void disconnect() {
		sourceReuseIdentifier = this.table.removeReusedIdentifier(getSource());
		targetReuseIdentifier = this.table.removeReusedIdentifier(getTarget());
		this.combinationTableConnection.disconnect();
		Diagram diagram = getSource().getDiagram();
		if (diagram != null) {
			diagram.removeChild(this.table);
		}
		super.disconnect();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.tmdmaker.core.model.AbstractRelationship#isDeletable()
	 */
	@Override
	public boolean isDeletable() {
		return table.isDeletable();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.tmdmaker.core.model.AbstractRelationship#identifierChanged()
	 */
	@Override
	public void identifierChanged() {
		table.fireIdentifierChanged(this);
	}

	/**
	 * 
	 * {@inheritDoc}
	 *
	 * @see org.tmdmaker.core.model.AbstractRelationship#getTable()
	 */
	@Override
	public CombinationTable getTable() {
		return table;
	}

	/**
	 * 
	 * {@inheritDoc}
	 *
	 * @see org.tmdmaker.core.model.AbstractRelationship#hasTable()
	 */
	@Override
	public boolean hasTable() {
		return true;
	}
}

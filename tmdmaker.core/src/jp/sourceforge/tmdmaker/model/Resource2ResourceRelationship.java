/*
 * Copyright 2009-2017 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
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

import jp.sourceforge.tmdmaker.model.rule.RelationshipRule;

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

		this.table = RelationshipRule.createCombinationTable(source, target);

		this.combinationTableConnection = new RelatedRelationship(this, this.table);
		this.setCenterMark(true);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.AbstractConnectionModel#connect()
	 */
	@Override
	public void connect() {
		super.connect();
		getSource().getDiagram().addChild(this.table);
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
	 * @see jp.sourceforge.tmdmaker.model.AbstractConnectionModel#disconnect()
	 */
	@Override
	public void disconnect() {
		sourceReuseIdentifier = this.table.removeReusedIdentifier(getSource());
		targetReuseIdentifier = this.table.removeReusedIdentifier(getTarget());
		this.combinationTableConnection.disconnect();
		getSource().getDiagram().removeChild(this.table);
		super.disconnect();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.AbstractRelationship#isDeletable()
	 */
	@Override
	public boolean isDeletable() {
		return table.isDeletable();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.AbstractRelationship#identifierChanged()
	 */
	@Override
	public void identifierChanged() {
		table.fireIdentifierChanged(this);
	}

	public CombinationTable getTable() {
		return table;
	}
}

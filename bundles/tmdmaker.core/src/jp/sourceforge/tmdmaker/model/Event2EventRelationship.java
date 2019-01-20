/*
 * Copyright 2009-2019 TMD-Maker Project <https://tmdmaker.osdn.jp/>
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

import jp.sourceforge.tmdmaker.model.rule.ImplementRule;

/**
 * イベント系エンティティとイベント系エンティティとのリレーションシップ
 * 
 * @author nakaG
 * 
 */
@SuppressWarnings("serial")
public class Event2EventRelationship extends AbstractRelationship {
	/**
	 * 対応表
	 */
	private MappingList table;

	/** 接続しているか */
	private boolean connected = false;

	/**
	 * 対応表とのコネクション
	 */
	private RelatedRelationship mappingListConnection;
	/** 対応表のソースから削除したReused */
	private ReusedIdentifier sourceMappingListReuseIdentifier;
	/** 対応表のターゲットから削除したReused */
	private ReusedIdentifier targetMappingListReuseIdentifier;
	/** ターゲットから削除したReused */
	private ReusedIdentifier targetReuseIdentifier;

	/**
	 * コンストラクタ
	 * 
	 * @param source
	 *            ソース
	 * @param target
	 *            ターゲット
	 */
	public Event2EventRelationship(AbstractEntityModel source, AbstractEntityModel target) {
		setSource(source);
		setTarget(target);
		this.setCenterMark(true);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.AbstractRelationship#setSourceCardinality(jp.sourceforge.tmdmaker.model.Cardinality)
	 */
	@Override
	public void setSourceCardinality(Cardinality sourceCardinality) {
		Cardinality oldValue = getSourceCardinality();
		super.setSourceCardinality(sourceCardinality);
		this.setCenterMark(hasTable());
		if (connected && !oldValue.equals(sourceCardinality)) {
			createRelationship();
		}
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
		connected = true;
		createRelationship();
	}

	/**
	 * リレーションシップを作成する。
	 * <ul>
	 * <li>ソースのカーディナリティがNの場合は対応表を作成する</li>
	 * <li>ソースのカーディナリティがN以外の場合はターゲットにキーを移送する</li>
	 * </ul>
	 */
	private void createRelationship() {
		if (hasTable()) {
			removeTargetRelationship();
			createMappingList();
		} else {
			removeMappingList();
			createTargetRelationship();
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
		if (hasTable()) {
			removeMappingList();
		} else {
			removeTargetRelationship();
		}
		super.disconnect();
		connected = false;
	}

	/**
	 * ターゲットにキーを移送する
	 */
	private void createTargetRelationship() {
		setCenterMark(false);
		if (targetReuseIdentifier == null) {
			getTarget().addReusedIdentifier(getSource());
		} else {
			getTarget().addReusedIdentifier(getSource(), targetReuseIdentifier);
			targetReuseIdentifier = null;
		}
	}

	/**
	 * ターゲットからキーを削除する
	 */
	private void removeTargetRelationship() {
		targetReuseIdentifier = getTarget().removeReusedIdentifier(getSource());
	}

	/**
	 * 対応表を作成する
	 */
	private void createMappingList() {
		AbstractEntityModel sourceEntity = getSource();
		AbstractEntityModel targetEntity = getTarget();

		if (table == null) {
			table = createMappingList(sourceEntity, targetEntity);
		}
		setCenterMark(true);

		Diagram diagram = sourceEntity.getDiagram();
		if (diagram != null) {
			diagram.addChild(table);
		}
		if (sourceMappingListReuseIdentifier == null) {
			table.addReusedIdentifier(sourceEntity);
		} else {
			table.addReusedIdentifier(sourceEntity, sourceMappingListReuseIdentifier);
			sourceMappingListReuseIdentifier = null;
		}
		if (targetMappingListReuseIdentifier == null) {
			table.addReusedIdentifier(targetEntity);
		} else {
			table.addReusedIdentifier(targetEntity, targetMappingListReuseIdentifier);
			targetMappingListReuseIdentifier = null;
		}
		mappingListConnection = new RelatedRelationship(this, table);
		mappingListConnection.connect();
	}

	/**
	 * 対応表を作成する。
	 * 
	 * @param source
	 *            接続元
	 * @param target
	 *            接続先
	 * @return 対応表
	 */
	private MappingList createMappingList(AbstractEntityModel source, AbstractEntityModel target) {
		MappingList ctable = new MappingList();
		ctable.setName(createMappingListName(source, target));
		ImplementRule.setModelDefaultValue(ctable);

		return ctable;
	}

	/**
	 * 対応表の名前を生成する.
	 * 
	 * @param source
	 *            接続元
	 * @param target
	 *            接続先
	 * @return 対応表名
	 */
	private static String createMappingListName(AbstractEntityModel source,
			AbstractEntityModel target) {
		return source.getName() + "." + target.getName() + "." + "対応表";
	}

	/**
	 * 対応表を削除する。undo()を考慮して実際はコネクションを切ってキーを削除するだけで表は残している
	 */
	private void removeMappingList() {
		if (mappingListConnection != null) {
			setCenterMark(false);
			mappingListConnection.disconnect();
		}
		if (table != null) {
			AbstractEntityModel sourceEntity = getSource();
			sourceMappingListReuseIdentifier = table.removeReusedIdentifier(sourceEntity);
			targetMappingListReuseIdentifier = table.removeReusedIdentifier(getTarget());
			Diagram diagram = sourceEntity.getDiagram();
			if (diagram != null) {
				diagram.removeChild(table);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.AbstractRelationship#isDeletable()
	 */
	@Override
	public boolean isDeletable() {
		if (hasTable()) {
			return table.isDeletable();
		} else {
			return true;
		}
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.IdentifierChangeListener#identifierChanged()
	 */
	@Override
	public void identifierChanged() {
		createRelationship(); // Detailの明細番号の有無を切換えるため。
		if (hasTable()) {
			table.fireIdentifierChanged(this);
		} else {
			getTarget().fireIdentifierChanged(this);
		}
	}

	/**
	 * 
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.model.AbstractRelationship#hasTable()
	 */
	@Override
	public boolean hasTable() {
		return getSourceCardinality().equals(Cardinality.MANY);
	}

	/**
	 * 
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.model.AbstractRelationship#accept(jp.sourceforge.tmdmaker.model.IVisitor)
	 */
	@Override
	public void accept(IVisitor visitor) {
		visitor.visit(this);
	}

	/**
	 * 
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.model.AbstractRelationship#getTable()
	 */
	@Override
	public MappingList getTable() {
		return this.table;
	}
}

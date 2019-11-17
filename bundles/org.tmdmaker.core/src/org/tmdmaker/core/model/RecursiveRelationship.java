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
package org.tmdmaker.core.model;

import org.tmdmaker.core.model.rule.ImplementRule;

/**
 * エンティティ系モデルと再帰表とのリレーションシップ
 * 
 * @author nakaG
 * 
 */
@SuppressWarnings("serial")
public class RecursiveRelationship extends AbstractRelationship {
	/** 再帰表 */
	private RecursiveTable table;
	/** 親 */
	private Diagram diagram;

	/**
	 * コンストラクタ
	 * 
	 * @param source
	 *            再帰元エンティティ
	 */
	public RecursiveRelationship(AbstractEntityModel source) {
		setSource(source);
		this.diagram = getSource().getDiagram();
		this.table = createRecursiveTable(source);
		setTarget(table);
	}
	/**
	 * 再帰表を作成する
	 * 
	 * @param model
	 *            再帰元モデル
	 * @return 再帰表
	 */
	private static RecursiveTable createRecursiveTable(AbstractEntityModel model) {
		RecursiveTable table = new RecursiveTable();
		table.setEntityType(model.getEntityType());
		table.setName(createRecursiveTableName(model));
		ImplementRule.setModelDefaultValue(table);
		table.addCreationIdentifier(model);

		return table;
	}
	/**
	 * 再帰表の名称を作成する
	 * 
	 * @param model
	 *            再帰元モデル
	 * @return 再帰表名
	 */
	private static String createRecursiveTableName(AbstractEntityModel model) {
		String name = model.getName();
		return name + "." + name + "." + "再帰表";
	}
	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.tmdmaker.core.model.AbstractConnectionModel#connect()
	 */
	@Override
	public void connect() {
		if (diagram != null) {
			diagram.addChild(table);
		}
		AbstractEntityModel sourceEntity = getSource();
		if (!sourceEntity.getModelSourceConnections().contains(this)) {
			sourceEntity.addSourceConnection(this);
		}
		table.addTargetConnection(this);
		attachSource();
		attachTarget();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.tmdmaker.core.model.AbstractConnectionModel#disconnect()
	 */
	@Override
	public void disconnect() {
		getSource().removeSourceConnection(this);
		table.removeTargetConnection(this);
		if (diagram != null) {
			diagram.removeChild(table);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.tmdmaker.core.model.AbstractRelationship#isDeletable()
	 */
	@Override
	public boolean isDeletable() {
		return table.isDeletable();
	}

	/**
	 * 
	 * Target の名前を返す。TM上、再帰表のSourceとTargetは同一となるが、プログラム上ではTargetが再帰表となっている。
	 * このため、ツールチップでFromとToが同一entityとなるようにした。
	 * 
	 */
	@Override
	public String getTargetName() {
		return getSourceName();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.tmdmaker.core.model.AbstractRelationship#identifierChanged()
	 */
	@Override
	public void identifierChanged() {
		table.removeReusedIdentifier(getSource());
		table.addCreationIdentifier(getSource());
		table.fireIdentifierChanged(this);
	}

	/**
	 * 
	 * {@inheritDoc}
	 *
	 * @see org.tmdmaker.core.model.AbstractRelationship#accept(org.tmdmaker.core.model.IVisitor)
	 */
	@Override
	public void accept(IVisitor visitor) {
		visitor.visit(this);
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

	/**
	 * 
	 * {@inheritDoc}
	 *
	 * @see org.tmdmaker.core.model.AbstractRelationship#getTable()
	 */
	@Override
	public RecursiveTable getTable() {
		return table;
	}
}

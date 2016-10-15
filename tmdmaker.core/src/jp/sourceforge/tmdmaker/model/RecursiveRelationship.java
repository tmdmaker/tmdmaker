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

import jp.sourceforge.tmdmaker.model.rule.RelationshipRule;

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
		this.table = RelationshipRule.createRecursiveTable(source);
		table.setConstraint(source.getConstraint().getTranslated(100, 0));
		setTarget(table);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.AbstractConnectionModel#connect()
	 */
	@Override
	public void connect() {
		diagram.addChild(table);
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
	 * @see jp.sourceforge.tmdmaker.model.AbstractConnectionModel#disconnect()
	 */
	@Override
	public void disconnect() {
		getSource().removeSourceConnection(this);
		table.removeTargetConnection(this);
		diagram.removeChild(table);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.AbstractRelationship#isDeletable()
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
	 * @see jp.sourceforge.tmdmaker.model.AbstractRelationship#identifierChanged()
	 */
	@Override
	public void identifierChanged() {
		table.removeReusedIdentifier(getSource());
		table.addCreationIdentifier(getSource());
		table.fireIdentifierChanged(this);
	}

	@Override
	public void accept(IVisitor visitor) {
		visitor.visit(this);
	}
}

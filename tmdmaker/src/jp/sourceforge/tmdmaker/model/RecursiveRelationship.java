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

import org.eclipse.draw2d.geometry.Rectangle;

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
		// this.connection = new RecursiveMarkConnection();
		this.table = new RecursiveTable();
		Rectangle constraint = source.getConstraint().getTranslated(100, 0);
		this.table.setConstraint(constraint);
		setTarget(table);
		this.diagram = getSource().getDiagram();
		// this.connection.setSource(this);
		// this.connection.setTarget(source);
		// 再帰表の類別は元エンティティを引き継ぐ
		table.setEntityType(source.getEntityType());
		table.setConstraint(source.getConstraint().getTranslated(100, 0));
		table.setName(source.getName() + "." + source.getName() + "." + "再帰表");

		table.addCreationIdentifier(source);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see tm.tmdiagram.tmdeditor.model.AbstractConnectionModel#connect()
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
		// table.setDiagram(null);
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
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.AbstractRelationship#identifierChanged()
	 */
	@Override
	public void identifierChanged() {
		// table.firePropertyChange(AbstractEntityModel.PROPERTY_REUSED, null,
		// null);
		table.fireIdentifierChanged(this);
	}

}

/*
 * Copyright 2009-2010 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
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

	// /** 接続しているか */
	// private boolean connected = false;

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

		MultivalueOrEntity target = new MultivalueOrEntity();
		target.setConstraint(source.getConstraint().getTranslated(50, 0));
		target.setName(source.getName() + "." + typeName);
		Attribute attribute = new Attribute();
		attribute.setName(typeName + "コード");
		target.addAttribute(attribute);
		target.setEntityType(source.getEntityType());

		setTargetCardinality(Cardinality.MANY);

		setTarget(target);
		this.table = target;
	}

	// @Override
	// public void attachTarget() {
	// super.attachTarget();
	// ((AbstractEntityModel)getTarget()).addReuseKey((AbstractEntityModel)getSource());
	// }
	// @Override
	// public void detachTarget() {
	// ((AbstractEntityModel)getTarget()).removeReuseKey((AbstractEntityModel)getSource());
	// super.detachTarget();
	// }

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.AbstractConnectionModel#connect()
	 */
	@Override
	public void connect() {
		Diagram diagram = getSource().getDiagram();
		diagram.addChild(getTarget());
		// target.setDiagram(diagram);
		super.connect();
	}

	//
	// private void createMultivalueOrEntity() {
	// if (table == null) {
	// table = new MultivalueOrEntity();
	// }
	// AbstractEntityModel sourceEntity = this.source;
	// Rectangle constraint = sourceEntity.getConstraint().getTranslated(100,
	// 100);
	// table.setConstraint(constraint);
	// Diagram diagram = sourceEntity.getDiagram();
	// diagram.addChild(table);
	// table.setDiagram(diagram);
	// table.setName(source.getName() + "." + "種別");
	// }
	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.AbstractConnectionModel#disconnect()
	 */
	@Override
	public void disconnect() {
		super.disconnect();
		getSource().getDiagram().removeChild(table);
		// target.setDiagram(null);
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

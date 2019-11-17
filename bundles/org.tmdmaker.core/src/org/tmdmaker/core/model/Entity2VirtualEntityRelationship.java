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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * エンティティ系モデルとみなしエンティティとのリレーションシップ
 * 
 * @author nakaG
 * 
 */
@SuppressWarnings("serial")
public class Entity2VirtualEntityRelationship extends TransfarReuseKeysToTargetRelationship {

	private static Logger logger = LoggerFactory.getLogger(Entity2VirtualEntityRelationship.class);

	/** みなしエンティティ */
	private VirtualEntity ve;

	/**
	 * コンストラクタ
	 * 
	 * @param source
	 *            みなしエンティティ作成対象
	 */
	public Entity2VirtualEntityRelationship(AbstractEntityModel source, String virtualEntityName,
			VirtualEntityType type) {
		setSource(source);
		ve = VirtualEntity.build(source, virtualEntityName, type);
		ve.setVirtualEntityType(type);
		setTarget(ve);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.tmdmaker.core.model.AbstractConnectionModel#connect()
	 */
	@Override
	public void connect() {
		Diagram diagram = getSource().getDiagram();
		if (diagram != null) {
			diagram.addChild(getTarget());
		}
		super.connect();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.tmdmaker.core.model.AbstractConnectionModel#disconnect()
	 */
	@Override
	public void disconnect() {
		super.disconnect();
		Diagram diagram = getSource().getDiagram();
		if (diagram != null) {
			diagram.removeChild(getTarget());
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.tmdmaker.core.model.TransfarReuseKeysToTargetRelationship#isDeletable()
	 */
	@Override
	public boolean isDeletable() {
		return getTarget().isDeletable();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.tmdmaker.core.model.IdentifierChangeListener#identifierChanged()
	 */
	@Override
	public void identifierChanged() {
		logger.debug("VEの生成元の個体識別子が変更になったため再設定。");
		ve.setOriginalReusedIdentifier(getSource().createReusedIdentifier());
		for (IdentifierRef r : getSource().createReusedIdentifier().getIdentifiers()) {
			logger.debug(r.getName());
		}
	}

	public VirtualEntity getVirtualEntity() {
		return (VirtualEntity) getTarget();
	}
}

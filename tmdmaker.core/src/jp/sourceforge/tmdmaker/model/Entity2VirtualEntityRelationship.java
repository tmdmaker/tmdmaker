/*
 * Copyright 2009-2012 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
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

import jp.sourceforge.tmdmaker.model.rule.VirtualEntityRule;

/**
 * エンティティ系モデルとみなしエンティティとのリレーションシップ
 * 
 * @author nakaG
 * 
 */
@SuppressWarnings("serial")
public class Entity2VirtualEntityRelationship extends
		TransfarReuseKeysToTargetRelationship {
	/** みなしエンティティ */
	private VirtualEntity ve;

	/**
	 * コンストラクタ
	 * 
	 * @param source
	 *            みなしエンティティ作成対象
	 */
	public Entity2VirtualEntityRelationship(AbstractEntityModel source,
			String virtualEntityName, VirtualEntityType type) {
		setSource(source);
		ve = VirtualEntityRule.createVirtualEntity(source, virtualEntityName);
		ve.setConstraint(source.getConstraint().getTranslated(100, 0));
		ve.setVirtualEntityType(type);
		setTarget(ve);

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.TransfarReuseKeysToTargetRelationship#attachTarget()
	 */
	@Override
	public void attachTarget() {
		getSource().getDiagram().addChild(ve);
		super.attachTarget();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.TransfarReuseKeysToTargetRelationship#detachTarget()
	 */
	@Override
	public void detachTarget() {
		super.detachTarget();
		ve.getDiagram().removeChild(ve);
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

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

/**
 * ソースのRe-usedをターゲットへ移送するリレーションシップ
 * 
 * @author nakaG
 * 
 */
@SuppressWarnings("serial")
public class TransfarReuseKeysToTargetRelationship extends AbstractRelationship {
	/** 移送先から削除したReused */
	private ReusedIdentifier targetReusedIdentifier;

	/**
	 * デフォルトコンストラクタ
	 */
	public TransfarReuseKeysToTargetRelationship() {
	}

	/**
	 * コンストラクタ
	 * 
	 * @param source
	 *            from
	 * @param target
	 *            to
	 */
	public TransfarReuseKeysToTargetRelationship(AbstractEntityModel source,
			AbstractEntityModel target) {
		AbstractEntityModel from = null;
		AbstractEntityModel to = null;
		if (source.isResource()) {
			from = source;
			to = target;
		} else {
			from = target;
			to = source;
		}
		setSource(from);
		setTarget(to);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.AbstractConnectionModel#attachTarget()
	 */
	@Override
	public void attachTarget() {
		super.attachTarget();
		if (targetReusedIdentifier == null) {
			getTarget().addReusedIdentifier(getSource());
		} else {
			getTarget().addReusedIdentifier(getSource(), targetReusedIdentifier);
			targetReusedIdentifier = null;
		}
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.AbstractConnectionModel#detachTarget()
	 */
	@Override
	public void detachTarget() {
		targetReusedIdentifier = getTarget().removeReusedIdentifier(getSource());
		super.detachTarget();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.AbstractRelationship#isDeletable()
	 */
	@Override
	public boolean isDeletable() {
		return true;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.AbstractRelationship#identifierChanged()
	 */
	@Override
	public void identifierChanged() {
		getTarget().fireIdentifierChanged(this);
	}

	/**
	 * 
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.model.AbstractRelationship#hasTable()
	 */
	@Override
	public boolean hasTable() {
		return false;
	}

	/**
	 * 
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.model.AbstractRelationship#getTable()
	 */
	@Override
	public AbstractEntityModel getTable() {
		return null;
	}

}

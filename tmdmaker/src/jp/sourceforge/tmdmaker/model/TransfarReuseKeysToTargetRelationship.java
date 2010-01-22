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

import jp.sourceforge.tmdmaker.model.rule.EntityTypeRule;

/**
 * ソースのRe-usedをターゲットへ移送するリレーションシップ
 * 
 * @author nakaG
 * 
 */
@SuppressWarnings("serial")
public class TransfarReuseKeysToTargetRelationship extends AbstractRelationship {
	
	public TransfarReuseKeysToTargetRelationship() {	
	}
	
	public TransfarReuseKeysToTargetRelationship(AbstractEntityModel source,
			AbstractEntityModel target) {
		AbstractEntityModel from = null;
		AbstractEntityModel to = null;
		if (EntityTypeRule.isResource(source)) {
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
		((AbstractEntityModel) getTarget())
				.addReusedIdentifier((AbstractEntityModel) getSource());
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.AbstractConnectionModel#detachTarget()
	 */
	@Override
	public void detachTarget() {
		((AbstractEntityModel) getTarget())
				.removeReusedIdentifier((AbstractEntityModel) getSource());
		super.detachTarget();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.AbstractRelationship#isDeletable()
	 */
	@Override
	public boolean isDeletable() {
//		return getTarget().isDeletable();
		return true;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.model.AbstractRelationship#identifierChanged()
	 */
	@Override
	public void identifierChanged() {
//		getTarget().firePropertyChange(AbstractEntityModel.PROPERTY_REUSED, null, null);
		getTarget().fireIdentifierChanged(this);
	}
}

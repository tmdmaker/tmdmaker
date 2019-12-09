/*
 * Copyright 2009-2019 TMD-Maker Project <https://www.tmdmaker.org>
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

import java.util.ArrayList;
import java.util.List;

/**
 * 多値のANDの相違マーク(×)を表すモデル.
 * 
 * @author nakaG
 * 
 */
@SuppressWarnings("serial")
public class MultivalueAndAggregator extends AbstractSubsetType<MultivalueAndSuperset> {
	/**
	 * コンストラクタは非公開.
	 */
	protected MultivalueAndAggregator() {
		super();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.tmdmaker.core.model.ModelElement#accept(org.tmdmaker.core.model.IVisitor)
	 */
	@Override
	public void accept(IVisitor visitor) {
		visitor.visit(this);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.tmdmaker.core.model.AbstractSubsetType#getSuperset()
	 */
	@Override
	public MultivalueAndSuperset getSuperset() {
		if (!getModelTargetConnections().isEmpty()) {
			AbstractConnectionModel r = getModelTargetConnections().get(0);
			return (MultivalueAndSuperset) r.getSource();
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.tmdmaker.core.model.AbstractSubsetType#getSubsetList()
	 */
	@Override
	public List<AbstractEntityModel> getSubsetList() {
		List<AbstractEntityModel> list = new ArrayList<AbstractEntityModel>();
		list.add(getHeader());
		list.add(getDetail());
		return list;
	}

	public AbstractEntityModel getHeader() {
		RelatedRelationship header2aggregator = (RelatedRelationship) getModelTargetConnections()
				.get(1);
		return (AbstractEntityModel) header2aggregator.getSource();
	}

	public Detail getDetail() {
		RelatedRelationship detail2aggregator = (RelatedRelationship) getModelTargetConnections()
				.get(2);
		return (Detail) detail2aggregator.getSource();
	}
}

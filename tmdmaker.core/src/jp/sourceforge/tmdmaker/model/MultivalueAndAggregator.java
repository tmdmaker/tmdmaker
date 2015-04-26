/*
 * Copyright 2009-2015 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
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
	 * コンストラクタ.
	 */
	public MultivalueAndAggregator() {
		super();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.ModelElement#accept(jp.sourceforge.tmdmaker.model.IVisitor)
	 */
	@Override
	public void accept(IVisitor visitor) {
		visitor.visit(this);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.model.AbstractSubsetType#getSuperset()
	 */
	@Override
	public MultivalueAndSuperset getSuperset() {
		if (getModelTargetConnections().size() > 0) {
			AbstractConnectionModel r = getModelTargetConnections().get(0);
			return (MultivalueAndSuperset) r.getSource();
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.model.AbstractSubsetType#getSubsetList()
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

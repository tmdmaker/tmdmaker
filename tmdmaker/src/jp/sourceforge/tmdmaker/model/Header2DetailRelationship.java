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
 * 多値のANDのヘッダとディテールとのリレーションシップ
 * 
 * @author nakaG
 * 
 */
@SuppressWarnings("serial")
public class Header2DetailRelationship extends
		TransfarReuseKeysToTargetRelationship {
	/** 概念的 スーパーセット */
	private MultivalueAndSuperset superset;

	/** DTL */
	private Detail detail;

	/** MAエンティティのリレーションシップの集約箇所 */
	private MultivalueAndAggregator aggregator;

	private RelatedRelationship superset2aggregator;
	private RelatedRelationship header2aggregator;
	private RelatedRelationship detail2aggregator;

	private String oldHeaderName;
	private String newHeaderName;
	
	/**
	 * コンストラクタ
	 * 
	 * @param header
	 *            HDRとなるエンティティ
	 */
	public Header2DetailRelationship(AbstractEntityModel header) {
		setSource(header);
		detail = new Detail();
		detail.setName(header.getName() + "DTL");
		detail.setEntityType(header.getEntityType());
		detail.setConstraint(header.getConstraint().getTranslated(100, 0));
		detail.setOriginalReusedIdentifier(header.createReusedIdentifier());
		detail.setDetailIdentifierName(header.getName() + "明細番号");
		setTarget(detail);

		superset = new MultivalueAndSuperset();
		superset.setEntityType(header.getEntityType());
		superset.setConstraint(header.getConstraint().getTranslated(64, -80));
		superset.setName(header.getName());
		superset.addReusedIdentifier(header);
		superset.setDetail(detail);

		aggregator = new MultivalueAndAggregator();
		aggregator.setConstraint(header.getConstraint().getTranslated(75, -30));

		superset2aggregator = new RelatedRelationship(superset, aggregator);

		header2aggregator = new RelatedRelationship(header, aggregator);

		detail2aggregator = new RelatedRelationship(detail, aggregator);
		oldHeaderName = header.getName();
		newHeaderName = oldHeaderName + "HDR";
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.AbstractConnectionModel#connect()
	 */
	@Override
	public void connect() {
		super.connect();
		getSource().getDiagram().addChild(superset);
		getSource().getDiagram().addChild(aggregator);
		superset2aggregator.connect();
		header2aggregator.connect();
		detail2aggregator.connect();
		getSource().setName(newHeaderName);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.AbstractConnectionModel#disconnect()
	 */
	@Override
	public void disconnect() {
		getSource().setName(oldHeaderName);
		detail2aggregator.disconnect();
		header2aggregator.disconnect();
		superset2aggregator.disconnect();
		getSource().getDiagram().removeChild(aggregator);
		getSource().getDiagram().removeChild(superset);
		super.disconnect();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.TransfarReuseKeysToTargetRelationship#attachTarget()
	 */
	@Override
	public void attachTarget() {
		getSource().getDiagram().addChild(detail);
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
		getSource().getDiagram().removeChild(detail);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.TransfarReuseKeysToTargetRelationship#identifierChanged()
	 */
	@Override
	public void identifierChanged() {
		super.identifierChanged();
		superset.fireIdentifierChanged(this);

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

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

/**
 * 多値のANDのヘッダとディテールとのリレーションシップ
 * 
 * @author nakaG
 * 
 */
@SuppressWarnings("serial")
public class Header2DetailRelationship extends TransfarReuseKeysToTargetRelationship {
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
		detail = Detail.build(header);
		setTarget(detail);
		setTargetCardinality(Cardinality.MANY);

		superset = MultivalueAndSuperset.build(header);
		superset.setDetail(detail);

		aggregator = new MultivalueAndAggregator();

		superset2aggregator = new RelatedRelationship(superset, aggregator);

		header2aggregator = new RelatedRelationship(header, aggregator);

		detail2aggregator = new RelatedRelationship(detail, aggregator);
		oldHeaderName = header.getName();
		newHeaderName = createHeaderName(header);
	}

	/**
	 * 多値のANDのHeaderの名称を作成する。
	 * 
	 * @param model
	 * @return
	 */
	private String createHeaderName(AbstractEntityModel model) {
		return model.getName() + "HDR";
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.tmdmaker.core.model.AbstractConnectionModel#connect()
	 */
	@Override
	public void connect() {
		super.connect();
		connectSuperset();
		getSource().setName(newHeaderName);
	}

	/**
	 * スーパーセットを接続して表示する。
	 */
	public void connectSuperset() {
		Diagram diagram = getSource().getDiagram();
		if (diagram != null) {
			diagram.addChild(superset);
			diagram.addChild(aggregator);
		}
		superset2aggregator.connect();
		header2aggregator.connect();
		detail2aggregator.connect();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.tmdmaker.core.model.AbstractConnectionModel#disconnect()
	 */
	@Override
	public void disconnect() {
		getSource().setName(oldHeaderName);
		disconnectSuperset();
		super.disconnect();
	}

	/**
	 * スーパーセットの接続を解除し、非表示にする。
	 */
	public void disconnectSuperset() {
		detail2aggregator.disconnect();
		header2aggregator.disconnect();
		superset2aggregator.disconnect();
		Diagram diagram = getSource().getDiagram();
		if (diagram != null) {
			diagram.removeChild(aggregator);
			diagram.removeChild(superset);
		}
	}

	/**
	 * スーパーセットが接続されているか？
	 * 
	 * @return スーパーセットが接続されている場合にtrueを返す。
	 */
	public boolean isSupersetConnected() {
		return header2aggregator.isConnected();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.tmdmaker.core.model.TransfarReuseKeysToTargetRelationship#attachTarget()
	 */
	@Override
	public void attachTarget() {
		Diagram diagram = getSource().getDiagram();
		if (diagram != null) {
			diagram.addChild(detail);
		}
		super.attachTarget();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.tmdmaker.core.model.TransfarReuseKeysToTargetRelationship#detachTarget()
	 */
	@Override
	public void detachTarget() {
		super.detachTarget();
		Diagram diagram = getSource().getDiagram();
		if (diagram != null) {
			diagram.removeChild(detail);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.tmdmaker.core.model.TransfarReuseKeysToTargetRelationship#identifierChanged()
	 */
	@Override
	public void identifierChanged() {
		super.identifierChanged();
		superset.fireIdentifierChanged(this);

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

	public MultivalueAndSuperset getMultivalueAndSuperset() {
		return superset;
	}

	public MultivalueAndAggregator getAggregator() {
		return aggregator;
	}

	public Detail getDetail() {
		return detail;
	}
}

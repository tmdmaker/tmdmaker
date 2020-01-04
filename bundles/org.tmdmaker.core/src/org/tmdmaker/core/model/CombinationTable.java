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

import java.util.Iterator;
import java.util.Map;

/**
 * 対照表
 * 
 * @author nakaG
 * 
 */
@SuppressWarnings("serial")
public class CombinationTable extends AbstractEntityModel {
	/** 対照表名のサフィックス */
	public static final String COMBINATION_TABLE_SUFFIX = ".対照表";
	/** 対照表種類 */
	private CombinationTableType combinationTableType = CombinationTableType.L_TRUTH;

	/**
	 * コンストラクタ.
	 */
	protected CombinationTable() {
		super();
		setEntityType(EntityType.RESOURCE);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.tmdmaker.core.model.AbstractEntityModel#createReusedIdentifier()
	 */
	@Override
	public ReusedIdentifier createReusedIdentifier() {
		ReusedIdentifier returnValue = new ReusedIdentifier(keyModels.getSurrogateKey());
		checkDuplicateTargetReusedIdentifiers();
		for (Map.Entry<AbstractEntityModel, ReusedIdentifier> rk : this.reusedIdentifiers
				.entrySet()) {
			returnValue.addAll(rk.getValue().getIdentifiers());
		}
		return returnValue;
	}

	@Override
	public Map<AbstractEntityModel, ReusedIdentifier> getReusedIdentifiers() {
		checkDuplicateTargetReusedIdentifiers();
		return super.getReusedIdentifiers();
	}

	private Map.Entry<AbstractEntityModel, ReusedIdentifier> getSource() {
		Iterator<Map.Entry<AbstractEntityModel, ReusedIdentifier>> it = super.getReusedIdentifiers()
				.entrySet().iterator();
		if (it.hasNext()) {
			return it.next();
		}
		return null;
	}

	private Map.Entry<AbstractEntityModel, ReusedIdentifier> getTarget() {
		Iterator<Map.Entry<AbstractEntityModel, ReusedIdentifier>> it = super.getReusedIdentifiers()
				.entrySet().iterator();

		// sourceは読み飛ばす
		if (!it.hasNext()) {
			return null;
		}
		it.next();

		if (it.hasNext()) {
			return it.next();
		}
		return null;
	}

	private void checkDuplicateTargetReusedIdentifiers() {
		Map.Entry<AbstractEntityModel, ReusedIdentifier> source = getSource();
		Map.Entry<AbstractEntityModel, ReusedIdentifier> target = getTarget();
		if (source == null || target == null) {
			return;
		}
		for (IdentifierRef i : target.getValue().getIdentifiers()) {
			i.setDuplicate(containIdentifier(source.getValue(), i));
		}
	}

	private boolean containIdentifier(ReusedIdentifier source, IdentifierRef target) {

		for (IdentifierRef s : source.getIdentifiers()) {
			// sourceに存在するIdentifierと同じのIdentifierがtargetに存在する場合は、
			// そのIdentifier省略する。
			// TODO 現状では名称の一致をもって同一Identifierとみなす
			if (target.getName().equals(s.getName())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.tmdmaker.core.model.AbstractEntityModel#isEntityTypeEditable()
	 */
	@Override
	public boolean isEntityTypeEditable() {
		// return getModelTargetConnections().size() == 1
		// && getModelSourceConnections().size() == 0;
		return false;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.tmdmaker.core.model.AbstractEntityModel#isDeletable()
	 */
	@Override
	public boolean isDeletable() {
		return getModelSourceConnections().isEmpty() && getModelTargetConnections().size() == 1;
	}

	/**
	 * 対象表作成時のリレーションシップを取得する
	 * 
	 * @return 対象表作成時のリレーションシップ（リレーションシップへのリレーションシップ）
	 */
	public RelatedRelationship findCreationRelationship() {
		AbstractConnectionModel r = getModelTargetConnections().get(0);
		assert r instanceof RelatedRelationship;
		return (RelatedRelationship) r;
	}

	/**
	 * @return the combinationTableType
	 */
	public CombinationTableType getCombinationTableType() {
		return combinationTableType;
	}

	/**
	 * @param combinationTableType
	 *            the combinationTableType to set
	 */
	public void setCombinationTableType(CombinationTableType combinationTableType) {
		this.combinationTableType = combinationTableType;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.tmdmaker.core.model.AbstractEntityModel#getCopy()
	 */
	@Override
	public CombinationTable getCopy() {
		CombinationTable copy = new CombinationTable();
		copyTo(copy);
		return copy;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.tmdmaker.core.model.AbstractEntityModel#copyTo(org.tmdmaker.core.model.AbstractEntityModel)
	 */
	@Override
	public void copyTo(AbstractEntityModel to) {
		if (to instanceof CombinationTable) {
			((CombinationTable) to).setCombinationTableType(getCombinationTableType());
		}
		super.copyTo(to);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.tmdmaker.core.model.AbstractEntityModel#fireIdentifierChanged
	 *      (org.tmdmaker.core.model.AbstractConnectionModel)
	 */
	@Override
	public void fireIdentifierChanged(AbstractConnectionModel callConnection) {
		checkDuplicateTargetReusedIdentifiers();
		super.fireIdentifierChanged(callConnection);
	}

	@Override
	public void accept(IVisitor visitor) {
		visitor.visit(this);
	}
}

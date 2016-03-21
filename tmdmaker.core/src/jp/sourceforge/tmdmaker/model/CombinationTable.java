/*
 * Copyright 2009-2016 TMD-Maker Project <http://tmdmaker.osdn.jp/>
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
	 * 
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.AbstractEntityModel#createReusedIdentifier()
	 */
	@Override
	public ReusedIdentifier createReusedIdentifier() {
		ReusedIdentifier returnValue = new ReusedIdentifier(keyModels.getSurrogateKey());
		checkDuplicateTargetReusedIdentifieres();
		for (Map.Entry<AbstractEntityModel, ReusedIdentifier> rk : this.reusedIdentifieres
				.entrySet()) {
			returnValue.addAll(rk.getValue().getIdentifires());
		}
		return returnValue;
	}

	@Override
	public Map<AbstractEntityModel, ReusedIdentifier> getReusedIdentifieres() {
		checkDuplicateTargetReusedIdentifieres();
		return super.getReusedIdentifieres();
	}

	private Map.Entry<AbstractEntityModel, ReusedIdentifier> getSource() {
		Iterator<Map.Entry<AbstractEntityModel, ReusedIdentifier>> it = super.getReusedIdentifieres()
				.entrySet().iterator();
		if (it.hasNext()) {
			return it.next();
		}
		return null;
	}

	private Map.Entry<AbstractEntityModel, ReusedIdentifier> getTarget() {
		Iterator<Map.Entry<AbstractEntityModel, ReusedIdentifier>> it = super.getReusedIdentifieres()
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

	private void checkDuplicateTargetReusedIdentifieres() {
		Map.Entry<AbstractEntityModel, ReusedIdentifier> source = getSource();
		Map.Entry<AbstractEntityModel, ReusedIdentifier> target = getTarget();
		if (source == null || target == null) {
			return;
		}
		for (IdentifierRef i : target.getValue().getIdentifires()) {
			if (containIdentifier(source.getValue(), i)) {
				i.setDuplicate(true);
			} else {
				i.setDuplicate(false);
			}
		}
	}

	private boolean containIdentifier(ReusedIdentifier source, IdentifierRef target) {

		for (IdentifierRef s : source.getIdentifires()) {
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
	 * @see jp.sourceforge.tmdmaker.model.AbstractEntityModel#isEntityTypeEditable()
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
	 * @see jp.sourceforge.tmdmaker.model.AbstractEntityModel#isDeletable()
	 */
	@Override
	public boolean isDeletable() {
		return getModelSourceConnections().size() == 0 && getModelTargetConnections().size() == 1;
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
	 * @see jp.sourceforge.tmdmaker.model.AbstractEntityModel#getCopy()
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
	 * @see jp.sourceforge.tmdmaker.model.AbstractEntityModel#copyTo(jp.sourceforge.tmdmaker.model.AbstractEntityModel)
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
	 * @see jp.sourceforge.tmdmaker.model.AbstractEntityModel#fireIdentifierChanged
	 *      (jp.sourceforge.tmdmaker.model.AbstractConnectionModel)
	 */
	@Override
	public void fireIdentifierChanged(AbstractConnectionModel callConnection) {
		checkDuplicateTargetReusedIdentifieres();
		super.fireIdentifierChanged(callConnection);
	}

	@Override
	public void accept(IVisitor visitor) {
		visitor.visit(this);
	}
}

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

import java.util.ArrayList;
import java.util.List;

/**
 * みなしスーパーセット
 * 
 * @author nakaG
 * 
 */
@SuppressWarnings("serial")
public class VirtualSuperset extends AbstractEntityModel {
	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.AbstractEntityModel#createReusedIdentifier()
	 */
	@Override
	public ReusedIdentifier createReusedIdentifier() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.AbstractEntityModel#isDeletable()
	 */
	@Override
	public boolean isDeletable() {
		return true;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.AbstractEntityModel#isEntityTypeEditable()
	 */
	@Override
	public boolean isEntityTypeEditable() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * みなしサブセット（みなしスーパーセットに設定されたモデル）を取得する
	 * 
	 * @return みなしサブセットのリスト
	 */
	public List<AbstractEntityModel> getVirtualSubsetList() {
		List<AbstractEntityModel> results = new ArrayList<AbstractEntityModel>();
		for (AbstractConnectionModel con : getVirtualSupersetType()
				.getModelTargetConnections()) {
			results.add((AbstractEntityModel) con.getSource());
		}
		return results;
	}

	/**
	 * みなしスーパーセット種類とみなしサブセット間のリレーションシップを取得する
	 * 
	 * @return みなしスーパーセット種類とみなしサブセット間のリレーションシップ
	 */
	public List<AbstractConnectionModel> getVirtualSubsetRelationshipList() {
		List<AbstractConnectionModel> results = new ArrayList<AbstractConnectionModel>();
		for (AbstractConnectionModel con : getVirtualSupersetType()
				.getModelTargetConnections()) {
			results.add(con);
		}
		return results;
	}

	/**
	 * 対応するみなしスーパーセット種類を取得する
	 * 
	 * @return みなしスーパーセット種類
	 */
	public VirtualSupersetType getVirtualSupersetType() {
		return (VirtualSupersetType) getModelTargetConnections().get(0)
				.getSource();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.AbstractEntityModel#getCopy()
	 */
	@Override
	public VirtualSuperset getCopy() {
		VirtualSuperset copy = new VirtualSuperset();
		copyTo(copy);
		return copy;
	}

}

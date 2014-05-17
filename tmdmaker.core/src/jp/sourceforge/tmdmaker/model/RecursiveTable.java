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

import java.util.Map;

/**
 * 再帰表
 * 
 * @author nakaG
 * 
 */
@SuppressWarnings("serial")
public class RecursiveTable extends AbstractEntityModel {
	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.AbstractEntityModel#createReusedIdentifier()
	 */
	@Override
	public ReusedIdentifier createReusedIdentifier() {
		ReusedIdentifier returnValue = new ReusedIdentifier(keyModels.getSarogateKey());
		for (Map.Entry<AbstractEntityModel, ReusedIdentifier> rk : this.reusedIdentifieres
				.entrySet()) {
			returnValue.addAll(rk.getValue().getIdentifires());
		}
		return returnValue;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.AbstractEntityModel#addReusedIdentifier(jp.sourceforge.tmdmaker.model.AbstractEntityModel)
	 */
	@Override
	public void addReusedIdentifier(AbstractEntityModel source) {
		ReusedIdentifier added = new ReusedIdentifier(keyModels.getSarogateKey());
		added.addAll(source.createReusedIdentifier().getIdentifires());
		this.reusedIdentifieres.put(source, added);
		firePropertyChange(PROPERTY_REUSED, null, added);
	}

	/**
	 * 再帰表作成時にReusedを追加する
	 * 
	 * @param source
	 *            再帰表の元
	 */
	public void addCreationIdentifier(AbstractEntityModel source) {
		SarogateKey sarogateKey = source.getKeyModels().getSarogateKey();
		ReusedIdentifier added = new ReusedIdentifier(sarogateKey, sarogateKey);
		added.addAll(source.createReusedIdentifier().getIdentifires());
		added.addAll(source.createReusedIdentifier().getIdentifires());
		this.reusedIdentifieres.put(source, added);
		firePropertyChange(PROPERTY_REUSED, null, added);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.AbstractEntityModel#isEntityTypeEditable()
	 */
	@Override
	public boolean isEntityTypeEditable() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.AbstractEntityModel#isDeletable()
	 */
	@Override
	public boolean isDeletable() {
		return getModelTargetConnections().size() == 1
				&& getModelSourceConnections().size() == 0;
	}

	/**
	 * リレーションシップ元のエンティティ系モデルを返す
	 * 
	 * @return リレーションシップ元のエンティティ系モデル
	 */
	private AbstractEntityModel getSource() {
		return (AbstractEntityModel) getModelTargetConnections().get(0)
				.getSource();
	}

	/**
	 * リレーションシップ元のエンティティ系モデルか判定する
	 * 
	 * @param source
	 *            判定対象
	 * @return 判定対象がリレーションシップ元である場合にtrueを返す
	 */
	public boolean isSource(AbstractEntityModel source) {
		return source == getSource();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.AbstractEntityModel#getCopy()
	 */
	@Override
	public RecursiveTable getCopy() {
		RecursiveTable copy = new RecursiveTable();
		copyTo(copy);
		return copy;
	}

}

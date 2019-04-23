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
		ReusedIdentifier returnValue = new ReusedIdentifier(keyModels.getSurrogateKey());
		for (Map.Entry<AbstractEntityModel, ReusedIdentifier> rk : this.reusedIdentifiers
				.entrySet()) {
			returnValue.addAll(rk.getValue().getIdentifiers());
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
		ReusedIdentifier added = new ReusedIdentifier(keyModels.getSurrogateKey());
		added.addAll(source.createReusedIdentifier().getIdentifiers());
		this.reusedIdentifiers.put(source, added);
		firePropertyChange(PROPERTY_REUSED, null, added);
	}

	/**
	 * 再帰表作成時にReusedを追加する
	 * 
	 * @param source
	 *            再帰表の元
	 */
	public void addCreationIdentifier(AbstractEntityModel source) {
		SurrogateKey surrogateKey = source.getKeyModels().getSurrogateKey();
		ReusedIdentifier added = new ReusedIdentifier(surrogateKey, surrogateKey);
		added.addAll(source.createReusedIdentifier().getIdentifiers());
		added.addAll(source.createReusedIdentifier().getIdentifiers());
		this.reusedIdentifiers.put(source, added);
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
		return getModelTargetConnections().size() == 1 && getModelSourceConnections().isEmpty();
	}

	/**
	 * リレーションシップ元のエンティティ系モデルを返す
	 * 
	 * @return リレーションシップ元のエンティティ系モデル
	 */
	private AbstractEntityModel getSource() {
		return (AbstractEntityModel) getModelTargetConnections().get(0).getSource();
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

	@Override
	public void accept(IVisitor visitor) {
		visitor.visit(this);
	}
}

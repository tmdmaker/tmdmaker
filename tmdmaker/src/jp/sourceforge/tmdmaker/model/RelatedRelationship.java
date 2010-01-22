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
 * リレーションシップを表すコネクションとのリレーションシップ
 * 
 * @author nakaG
 * 
 */
@SuppressWarnings("serial")
public class RelatedRelationship extends AbstractConnectionModel {
	/**
	 * コンストラクタ
	 * 
	 * @param source
	 *            接続元
	 * @param target
	 *            接続先
	 */
	public RelatedRelationship(ConnectableElement source,
			ConnectableElement target) {
		setSource(source);
		setTarget(target);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.AbstractConnectionModel#isDeletable()
	 */
	@Override
	public boolean isDeletable() {
		// TODO Auto-generated method stub
		return true;
	}

}

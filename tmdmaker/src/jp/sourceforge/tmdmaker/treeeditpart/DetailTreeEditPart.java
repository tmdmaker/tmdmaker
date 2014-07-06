/*
 * Copyright 2009,2014 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
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
package jp.sourceforge.tmdmaker.treeeditpart;

import java.beans.PropertyChangeListener;

import jp.sourceforge.tmdmaker.model.Detail;
import jp.sourceforge.tmdmaker.model.Identifier;
import jp.sourceforge.tmdmaker.model.IdentifierRef;
import jp.sourceforge.tmdmaker.model.ReusedIdentifier;

/**
 * DTLのtree用EditPart
 * 
 * @author nakaG
 *
 */
public class DetailTreeEditPart extends AbstractEntityModelTreeEditPart implements
		PropertyChangeListener {
	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.treeeditpart.AbstractEntityModelTreeEditPart#getModel()
	 */
	@Override
	public Detail getModel() {
		return (Detail) super.getModel();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.treeeditpart.AbstractEntityModelTreeEditPart#setIdentifiers()
	 */
	@Override
	protected void setIdentifiers() {
		Detail detail = getModel();
		IdentifierRef original = detail.getOriginalReusedIdentifier().getIdentifires().get(0);
		addOriginalIdentifier(original);
		identifiers.add(detail.getDetailIdentifier());

		for (ReusedIdentifier r : detail.getReusedIdentifieres().values()) {
			for (IdentifierRef i : r.getIdentifires()) {
				if (!i.isSame(original)) {
					identifiers.add(i);
				}
			}
		}
		if (identifiers != null && identifiers.size() != 0) {
			children.add(identifiers);
		}
	}

	/**
	 * 元のHDR個体指定子に(R)をつけないためにIdentifierとして元の個体指定子を追加する。
	 * 
	 * @param original
	 *            元のHDRの個体指定子
	 */
	private void addOriginalIdentifier(IdentifierRef original) {
		Identifier copyOriginal = new Identifier();
		copyOriginal.copyFrom(original);
		identifiers.add(0, copyOriginal);
	}

}

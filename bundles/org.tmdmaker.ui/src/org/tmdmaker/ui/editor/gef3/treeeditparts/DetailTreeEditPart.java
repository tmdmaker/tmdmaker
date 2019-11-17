/*
 * Copyright 2009-2019 TMD-Maker Project <https://www.tmdmaker.org/>
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
package org.tmdmaker.ui.editor.gef3.treeeditparts;

import java.beans.PropertyChangeListener;

import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.ui.views.properties.IPropertySource;
import org.tmdmaker.core.model.Detail;
import org.tmdmaker.core.model.Identifier;
import org.tmdmaker.core.model.IdentifierRef;
import org.tmdmaker.core.model.ReusedIdentifier;
import org.tmdmaker.ui.views.properties.gef3.DetailPropertySource;

/**
 * DTLのtree用EditPart
 * 
 * @author nakaG
 *
 */
public class DetailTreeEditPart extends AbstractEntityModelTreeEditPart<Detail>
		implements PropertyChangeListener {

	/**
	 * コンストラクタ
	 * 
	 * @param model
	 */
	public DetailTreeEditPart(Detail model, EditPolicy policy) {
		super(model, policy);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.tmdmaker.ui.editor.gef3.treeeditparts.AbstractEntityModelTreeEditPart#setIdentifiers()
	 */
	@Override
	protected void setIdentifiers() {
		Detail detail = getModel();
		IdentifierRef original = detail.getOriginalReusedIdentifier().getUniqueIdentifiers().get(0);
		addOriginalIdentifier(original);
		if (detail.isDetailIdentifierEnabled()) {
			identifiers.add(detail.getDetailIdentifier());
		}

		for (ReusedIdentifier r : detail.getReusedIdentifiers().values()) {
			for (IdentifierRef i : r.getUniqueIdentifiers()) {
				if (!i.isSame(original)) {
					identifiers.add(i);
				}
			}
		}
		if (identifiers != null && !identifiers.isEmpty()) {
			modelChildren.add(identifiers);
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

	@Override
	public IPropertySource getPropertySource(CommandStack commandStack) {
		return new DetailPropertySource(commandStack, this.getModel());
	}
}

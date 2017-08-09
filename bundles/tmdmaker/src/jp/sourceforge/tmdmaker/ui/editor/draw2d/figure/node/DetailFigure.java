/*
 * Copyright 2009-2017 TMD-Maker Project <http://tmdmaker.osdn.jp/>
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
package jp.sourceforge.tmdmaker.ui.editor.draw2d.figure.node;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.Detail;
import jp.sourceforge.tmdmaker.model.IdentifierRef;
import jp.sourceforge.tmdmaker.model.ReusedIdentifier;

/**
 * DetailのFigure.
 * 
 * @author nakag
 *
 */
public class DetailFigure extends AbstractModelFigure<Detail> {

	/**
	 * 
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.ui.editor.draw2d.figure.node.AbstractModelFigure#setupIdentifierList(jp.sourceforge.tmdmaker.model.AbstractEntityModel)
	 */
	@Override
	protected List<String> setupIdentifierList(Detail model) {
		List<String> identifierList = new ArrayList<String>(2);
		IdentifierRef original = model.getOriginalUniqueIdentifierRef();
		identifierList.add(original.getName());
		if (model.isDetailIdentifierEnabled()) {
			identifierList.add(model.getDetailIdentifier().getName());
		}
		return identifierList;
	}

	/**
	 * 
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.ui.editor.draw2d.figure.node.AbstractModelFigure#setupRelationshipList(jp.sourceforge.tmdmaker.model.AbstractEntityModel)
	 */
	@Override
	protected List<String> setupRelationshipList(Detail model) {
		List<String> relationshipList = new ArrayList<String>();
		IdentifierRef original = model.getOriginalUniqueIdentifierRef();
		for (Map.Entry<AbstractEntityModel, ReusedIdentifier> rk : model.getReusedIdentifiers()
				.entrySet()) {
			for (IdentifierRef i : rk.getValue().getUniqueIdentifiers()) {
				if (i.isSame(original)) {
					// nothing
				} else {
					relationshipList.add(i.getName());
				}
			}
		}
		return relationshipList;
	}

	/**
	 * 
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.ui.editor.draw2d.figure.node.AbstractModelFigure#getTypeLabel(jp.sourceforge.tmdmaker.model.AbstractEntityModel)
	 */
	@Override
	protected String getTypeLabel(Detail model) {
		return null;
	}

}

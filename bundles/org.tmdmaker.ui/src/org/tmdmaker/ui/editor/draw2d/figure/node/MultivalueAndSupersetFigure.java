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
package org.tmdmaker.ui.editor.draw2d.figure.node;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.tmdmaker.core.model.EntityType;
import org.tmdmaker.core.model.IdentifierRef;
import org.tmdmaker.core.model.MultivalueAndSuperset;

/**
 * MultivalueAndSupersetのFigure.
 * 
 * @author nakag
 *
 */
public class MultivalueAndSupersetFigure extends AbstractModelFigure<MultivalueAndSuperset> {

	/**
	 * 
	 * {@inheritDoc}
	 *
	 * @see org.tmdmaker.ui.editor.draw2d.figure.node.AbstractModelFigure#setupIdentifierList(org.tmdmaker.core.model.AbstractEntityModel)
	 */
	@Override
	protected List<String> setupIdentifierList(MultivalueAndSuperset model) {
		IdentifierRef identifierRef = model.getReusedIdentifiers().entrySet().iterator().next()
				.getValue().getUniqueIdentifiers().get(0);
		return Arrays.asList(identifierRef.getName());
	}

	/**
	 * 
	 * {@inheritDoc}
	 *
	 * @see org.tmdmaker.ui.editor.draw2d.figure.node.AbstractModelFigure#isNotImplement(org.tmdmaker.core.model.AbstractEntityModel)
	 */
	@Override
	protected boolean isNotImplement(MultivalueAndSuperset model) {
		return model.isNotImplement();
	}

	/**
	 * 
	 * {@inheritDoc}
	 *
	 * @see org.tmdmaker.ui.editor.draw2d.figure.node.AbstractModelFigure#getTypeLabel(org.tmdmaker.core.model.AbstractEntityModel)
	 */
	@Override
	protected String getTypeLabel(MultivalueAndSuperset model) {
		return EntityType.MA.getLabel();
	}

	@Override
	protected List<String> setupRelationshipList(MultivalueAndSuperset model) {
		return Collections.emptyList();
	}
	
	
}

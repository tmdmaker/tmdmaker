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

import java.util.ArrayList;
import java.util.List;

import org.tmdmaker.core.model.EntityType;
import org.tmdmaker.core.model.Identifier;
import org.tmdmaker.core.model.VirtualEntity;
import org.tmdmaker.core.model.VirtualEntityType;
import org.tmdmaker.ui.preferences.appearance.ModelAppearance;

/**
 * VirtualEntityのFigure.
 * 
 * @author nakag
 *
 */
public class VirtualEntityFigure extends AbstractModelFigure<VirtualEntity> {

	/**
	 * 
	 * {@inheritDoc}
	 *
	 * @see org.tmdmaker.ui.editor.draw2d.figure.node.AbstractModelFigure#getAppearance(org.tmdmaker.core.model.AbstractEntityModel)
	 */
	@Override
	protected ModelAppearance getAppearance(VirtualEntity model) {
		ModelAppearance appearance = null;
		if (model.getVirtualEntityType().equals(VirtualEntityType.RESOURCE)) {
			appearance = ModelAppearance.RESOURCE_VIRTUAL_ENTITY;
		} else if (model.getVirtualEntityType().equals(VirtualEntityType.EVENT)) {
			appearance = ModelAppearance.EVENT_SUBSET;
		} else {
			appearance = ModelAppearance.VIRTUAL_ENTITY;
		}
		return appearance;
	}

	/**
	 * 
	 * {@inheritDoc}
	 *
	 * @see org.tmdmaker.ui.editor.draw2d.figure.node.AbstractModelFigure#getTypeLabel(org.tmdmaker.core.model.AbstractEntityModel)
	 */
	@Override
	protected String getTypeLabel(VirtualEntity model) {
		return EntityType.VE.getLabel();
	}

	/**
	 * 
	 * {@inheritDoc}
	 *
	 * @see org.tmdmaker.ui.editor.draw2d.figure.node.AbstractModelFigure#setupRelationshipList(org.tmdmaker.core.model.AbstractEntityModel)
	 */
	@Override
	protected List<String> setupRelationshipList(VirtualEntity model) {
		List<String> relationship = new ArrayList<String>();

		for (Identifier i : model.getOriginalReusedIdentifier().getUniqueIdentifiers()) {
			relationship.add(i.getName());
		}
		return relationship;
	}
}

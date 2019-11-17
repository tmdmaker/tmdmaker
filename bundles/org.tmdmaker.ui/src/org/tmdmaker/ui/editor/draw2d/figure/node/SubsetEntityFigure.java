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
package org.tmdmaker.ui.editor.draw2d.figure.node;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.tmdmaker.core.model.EntityType;
import org.tmdmaker.core.model.Identifier;
import org.tmdmaker.core.model.SubsetEntity;
import org.tmdmaker.ui.preferences.appearance.ModelAppearance;

/**
 * SubsetEntityのFigure.
 * 
 * @author nakag
 *
 */
public class SubsetEntityFigure extends AbstractModelFigure<SubsetEntity> {

	/**
	 * 
	 * {@inheritDoc}
	 *
	 * @see org.tmdmaker.ui.editor.draw2d.figure.node.AbstractModelFigure#setupIdentifierList(org.tmdmaker.core.model.AbstractEntityModel)
	 */
	@Override
	protected List<String> setupIdentifierList(SubsetEntity model) {
		if (model.isSameSubset() && model.getAttributes().isEmpty() && !model.hasRelationship()) {
			return Collections.emptyList();
		}
		if (model.isSupersetAnEntity()) {
			return Arrays.asList(
					model.getOriginalReusedIdentifier().getUniqueIdentifiers().get(0).getName());
		}
		return Collections.emptyList();
	}

	/**
	 * 
	 * {@inheritDoc}
	 *
	 * @see org.tmdmaker.ui.editor.draw2d.figure.node.AbstractModelFigure#setupRelationshipList(org.tmdmaker.core.model.AbstractEntityModel)
	 */
	@Override
	protected List<String> setupRelationshipList(SubsetEntity model) {
		List<String> relationships = new ArrayList<String>();
		if (model.isSameSubset() && model.getAttributes().isEmpty() && !model.hasRelationship()) {
			// do nothing
		} else {
			if (!model.isSupersetAnEntity()) {
				for (Identifier i : model.getOriginalReusedIdentifier().getUniqueIdentifiers()) {
					relationships.add(i.getName());
				}
			}
		}
		List<String> reusedList = super.setupRelationshipList(model);
		relationships.addAll(reusedList);
		return relationships;
	}

	/**
	 * 
	 * {@inheritDoc}
	 *
	 * @see org.tmdmaker.ui.editor.draw2d.figure.node.AbstractModelFigure#getAppearance(org.tmdmaker.core.model.AbstractEntityModel)
	 */
	@Override
	protected ModelAppearance getAppearance(SubsetEntity model) {
		ModelAppearance appearance = null;
		if (model.getEntityType().equals(EntityType.RESOURCE)) {
			appearance = ModelAppearance.RESOURCE_SUBSET;
		} else if (model.getEntityType().equals(EntityType.EVENT)) {
			appearance = ModelAppearance.EVENT_SUBSET;
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
	protected String getTypeLabel(SubsetEntity model) {
		return null;
	}
}

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
package org.tmdmaker.ui.dialogs.model;

import org.tmdmaker.core.model.AbstractEntityModel;
import org.tmdmaker.core.model.DataTypeDeclaration;
import org.tmdmaker.core.model.Entity;
import org.tmdmaker.core.model.EntityType;
import org.tmdmaker.core.model.Identifier;
import org.tmdmaker.core.model.Laputa;
import org.tmdmaker.core.model.StandardSQLDataType;
import org.tmdmaker.core.model.parts.ModelName;

/**
 * エンティティ作成用.
 * 
 * @author nakag
 *
 */
public class EntityCreationModel {
	private EditAttribute identifier;
	private ModelName entityName;
	private EntityType entityType;
	private boolean entityNameAutoGeneration;

	public EntityCreationModel() {
		this.identifier = new EditAttribute();
		this.entityName = new ModelName();
		this.entityType = EntityType.RESOURCE;
		this.entityNameAutoGeneration = true;
	}

	public boolean isEntityNameAutoGeneration() {
		return entityNameAutoGeneration;
	}

	public void setEntityNameAutoGeneration(boolean entityNameAutoGeneration) {
		this.entityNameAutoGeneration = entityNameAutoGeneration;
		if (isEntityNameAutoGeneration()) {
			generateEntityNameFromIdentifier();
		}
	}

	public EntityType getEntityType() {
		return entityType;
	}

	public void setEntityType(EntityType entityType) {
		this.entityType = entityType;
	}

	public void setIdentifierName(String identifierName) {
		this.identifier.setName(identifierName);
		if (isEntityNameAutoGeneration()) {
			generateEntityNameFromIdentifier();
		}
	}

	private void generateEntityNameFromIdentifier() {
		this.entityName = new Identifier(identifier.getName()).createEntityName();
	}

	public EditAttribute getIdentifier() {
		return identifier;
	}

	public ModelName getEntityName() {
		return entityName;
	}

	public void setEntityName(ModelName entityName) {
		this.entityName = entityName;
	}

	private boolean isLaputa() {
		return this.entityType.equals(EntityType.LAPUTA);
	}

	private boolean isEvent() {
		return this.entityType.equals(EntityType.EVENT);
	}

	private boolean isValidEntity() {
		return !this.entityName.isEmpty() && this.identifier.isValid();
	}

	/**
	 * 検証
	 * 
	 * @return 必須事項が全て入力されている場合にtrueを返す
	 */
	public boolean isValid() {
		return isLaputa() || isValidEntity();
	}

	public AbstractEntityModel getCreateModel() {
		Identifier newIdentifier = this.identifier.toIdentifier();
		newIdentifier.setDataTypeDeclaration(
				new DataTypeDeclaration(StandardSQLDataType.CHARACTER_VARYING, 10, null));
		if (isLaputa()) {
			return Laputa.of(entityName, newIdentifier);
		} else {
			Entity entity = null;
			if (isEvent()) {
				entity = Entity.ofEvent(entityName, newIdentifier);
			} else {
				entity = Entity.ofResource(entityName, newIdentifier);
			}
			entity.withDefaultAttribute();
			return entity;
		}
	}
}

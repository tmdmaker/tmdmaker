/*
 * Copyright 2009 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
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
package jp.sourceforge.tmdmaker.model.command;

import java.util.List;
import java.util.Map;

import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.Attribute;
import jp.sourceforge.tmdmaker.model.Entity;
import jp.sourceforge.tmdmaker.model.EntityType;
import jp.sourceforge.tmdmaker.model.Identifier;
import jp.sourceforge.tmdmaker.model.ReusedIdentifier;

import org.eclipse.gef.commands.Command;

/**
 * エンティティ編集Command
 * 
 * @author nakaG
 * 
 */
public class EntityEditCommand extends Command {
	private String identifierName;
	private String entityName;
	private EntityType entityType;
	private List<Attribute> attributes;

	private Entity entity;
	private String oldIdentifierName;
	private EntityType oldEntityType;

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		entity.setEntityType(this.entityType);
		entity.setIdentifierName(this.identifierName);

		Map<AbstractEntityModel, ReusedIdentifier> ids = entity
				.getReusedIdentifieres();
		// TODO identifierの更新伝播
		for (int i = 0; i < ids.size(); i++) {
			// ids.get(i).setName(reusedIdentifieres.get(i).getName());
		}
		entity.setAttributes(this.attributes);
		// firePropertyChange
		entity.setName(this.entityName);
	}


	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		entity.setEntityType(this.oldEntityType);
		entity.setIdentifierName(this.oldIdentifierName);
		Map<AbstractEntityModel, ReusedIdentifier> ids = entity
				.getReusedIdentifieres();
		for (int i = 0; i < ids.size(); i++) {
			// ids.get(i).setName(oldReuseKeys.get(i).getName());
		}
	}

	/**
	 * @param identifierName
	 *            the identifierName to set
	 */
	public void setIdentifierName(String identifierName) {
		this.identifierName = identifierName;
	}

	/**
	 * @param entityName
	 *            the entityName to set
	 */
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	/**
	 * @param entityType
	 *            the entityType to set
	 */
	public void setEntityType(EntityType entityType) {
		this.entityType = entityType;
	}

	/**
	 * @param reusedIdentifieres
	 *            the reusedIdentifieres to set
	 */
	public void setReuseKey(List<Identifier> reuseKeys) {
	}

	/**
	 * @param attributes
	 *            the attributes to set
	 */
	public void setAttributes(List<Attribute> attributes) {
		this.attributes = attributes;
	}

	/**
	 * @param entity
	 *            the entity to set
	 */
	public void setEntity(Entity entity) {
		this.entity = entity;
		entity.getName();
		this.oldEntityType = entity.getEntityType();
		this.oldIdentifierName = entity.getIdentifier().getName();
		// Map<AbstractEntityModel, ReusedIdentifier> rk = entity.getReuseKey();
		// for (Map.Entry<AbstractEntityModel, ReusedIdentifier> k :
		// rk.entrySet()) {
		//			
		// // this.oldReuseKeys.add(new Identifier(id.getName()));
		// }
		entity.getAttributes();
	}
}

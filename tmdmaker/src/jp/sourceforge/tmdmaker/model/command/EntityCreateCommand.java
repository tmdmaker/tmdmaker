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

import jp.sourceforge.tmdmaker.model.Attribute;
import jp.sourceforge.tmdmaker.model.Diagram;
import jp.sourceforge.tmdmaker.model.Entity;
import jp.sourceforge.tmdmaker.model.EntityType;
import jp.sourceforge.tmdmaker.model.Identifier;

import org.eclipse.gef.commands.Command;

/**
 * エンティティ作成Command
 * 
 * @author nakaG
 * 
 */
public class EntityCreateCommand extends Command {
	/** 親 */
	private Diagram diagram;
	/** 作成対象 */
	private Entity model;
	/** エンティティ名 */
	private String entityName;
	/** 個体指定子 */
	private Identifier identifier;

	/** エンティティ種類 */
	private EntityType entityType;
	/** デフォルトで追加するアトリビュート名 */
	private String defaultAttributeName;

	/**
	 * コンストラクタ
	 * 
	 * @param diagram
	 *            親
	 * @param entityName
	 *            エンティティ名
	 * @param identifierName
	 *            個体指定子名称
	 * @param entityType
	 *            エンティティ種類
	 */
	public EntityCreateCommand(Diagram diagram, Entity model) {
		this.diagram = diagram;
		this.model = model;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		if (defaultAttributeName != null && model.getAttributes().size() == 0) {
			model.addAttribute(new Attribute(defaultAttributeName));
		}
		if (entityName != null && entityName.length() > 0) {
			model.setName(entityName);
			// model.setPhysicalName(model.getName());
			model.setEntityType(entityType);
			model.setIdentifier(identifier);
			diagram.addChild(model);
		}
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		diagram.removeChild(model);
	}

	/**
	 * @param defaultAttributeName
	 *            the defaultAttributeName to set
	 */
	public void setDefaultAttributeName(String transactionDate) {
		this.defaultAttributeName = transactionDate;
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
	 * @return the entityName
	 */
	public String getEntityName() {
		return entityName;
	}

	/**
	 * @param identifier
	 *            the identifier to set
	 */
	public void setIdentifier(Identifier identifier) {
		this.identifier = identifier;
	}

}

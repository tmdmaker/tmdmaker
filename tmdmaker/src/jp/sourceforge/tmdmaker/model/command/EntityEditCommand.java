package jp.sourceforge.tmdmaker.model.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.Attribute;
import jp.sourceforge.tmdmaker.model.Entity;
import jp.sourceforge.tmdmaker.model.EntityType;
import jp.sourceforge.tmdmaker.model.Identifier;
import jp.sourceforge.tmdmaker.model.ReUseKeys;

import org.eclipse.gef.commands.Command;

public class EntityEditCommand extends Command {
	private String identifierName;
	private String entityName;
	private EntityType entityType;
	private List<Identifier> reuseKeys;
	private List<Attribute> attributes;

	private Entity entity;
	private String oldIdentifierName;
	private String oldEntityName;
	private EntityType oldEntityType;
	private List<Identifier> oldReuseKeys = new ArrayList<Identifier>();
	private List<Attribute> oldAttributes;

	
	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		entity.setEntityType(this.entityType);
		entity.getIdentifier().setName(this.identifierName);
		Map<AbstractEntityModel, ReUseKeys> ids = entity.getReuseKeys();
		// TODO identifierの更新伝播
		for (int i = 0; i < ids.size(); i++) {
//			ids.get(i).setName(reuseKeys.get(i).getName());
		}
		entity.setAttributes(this.attributes);
		// firePropertyChange
		entity.setName(this.entityName);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		entity.setEntityType(this.oldEntityType);
		entity.getIdentifier().setName(this.oldIdentifierName);
		Map<AbstractEntityModel, ReUseKeys> ids = entity.getReuseKeys();
		for (int i = 0; i < ids.size(); i++) {
//			ids.get(i).setName(oldReuseKeys.get(i).getName());
		}
	}

	/**
	 * @param identifierName the identifierName to set
	 */
	public void setIdentifierName(String identifierName) {
		this.identifierName = identifierName;
	}
	/**
	 * @param entityName the entityName to set
	 */
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
	/**
	 * @param entityType the entityType to set
	 */
	public void setEntityType(EntityType entityType) {
		this.entityType = entityType;
	}
	/**
	 * @param reuseKeys the reuseKeys to set
	 */
	public void setReuseKeys(List<Identifier> reuseKeys) {
		this.reuseKeys = reuseKeys;
	}
	/**
	 * @param attributes the attributes to set
	 */
	public void setAttributes(List<Attribute> attributes) {
		this.attributes = attributes;
	}
	/**
	 * @param entity the entity to set
	 */
	public void setEntity(Entity entity) {
		this.entity = entity;
		this.oldEntityName = entity.getName();
		this.oldEntityType = entity.getEntityType();
		this.oldIdentifierName = entity.getIdentifier().getName();
		Map<AbstractEntityModel, ReUseKeys> rk = entity.getReuseKeys();
		for (Map.Entry<AbstractEntityModel, ReUseKeys> k : rk.entrySet()) {
			
//			this.oldReuseKeys.add(new Identifier(id.getName()));
		}
		this.oldAttributes = entity.getAttributes();
	}
}


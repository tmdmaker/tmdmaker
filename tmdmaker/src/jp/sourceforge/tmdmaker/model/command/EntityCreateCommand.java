package jp.sourceforge.tmdmaker.model.command;

import jp.sourceforge.tmdmaker.model.Attribute;
import jp.sourceforge.tmdmaker.model.Diagram;
import jp.sourceforge.tmdmaker.model.Entity;
import jp.sourceforge.tmdmaker.model.Identifier;
import jp.sourceforge.tmdmaker.model.AbstractEntityModel.EntityType;

import org.eclipse.gef.commands.Command;

public class EntityCreateCommand extends Command {
	private Diagram diagram;
	private Entity model;
	private String entityName;
	private String identifierName;
	private EntityType entityType;
	private String transactionDate;

		/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		System.out.println(getClass().toString() + "#execute()");
		if (transactionDate != null) {
			model.addAttribute(new Attribute(transactionDate));
		}
		if (entityName != null && entityName.length() > 0) {
			System.out.println(getClass().toString() + "#execute():entityName not null");
			model.setName(entityName);
			model.setPhysicalName(model.getName());
			model.setEntityType(entityType);
			Identifier identifier = new Identifier(identifierName);
			model.setIdentifier(identifier);
			model.setDiagram(diagram);
			diagram.addChild(model);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		diagram.removeChild(model);
		model.setDiagram(null);
	}

	public void setDiagram(Object diagram) {
		this.diagram = (Diagram) diagram;
	}

	public void setModel(Object model) {
		this.model = (Entity) model;
	}

	/**
	 * @return the entityName
	 */
	public String getEntityName() {
		return entityName;
	}

	/**
	 * @param entityName the entityName to set
	 */
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	/**
	 * @param identifierName the identifierName to set
	 */
	public void setIdentifierName(String identifierName) {
		this.identifierName = identifierName;
	}

	/**
	 * @param entityType the entityType to set
	 */
	public void setEntityType(EntityType entityType) {
		this.entityType = entityType;
	}

	/**
	 * @param transactionDate the transactionDate to set
	 */
	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}
	
}

package jp.sourceforge.tmdmaker.model.command;

import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.Attribute;

import org.eclipse.gef.commands.Command;
/**
 * 
 * @author nakaG
 *
 */
public class AttributeEditCommand extends Command {
	private Attribute attribute;
	private Attribute editedValueAttribute;
	private String newAttributeName;
	private String oldAttributeName;
	private AbstractEntityModel entity;

//	public AttributeEditCommand(Attribute attribute, String newAttributeName) {
//		this.attribute = attribute;
//		this.oldAttributeName = attribute.getName();
//		this.newAttributeName = newAttributeName;
//	}
	public AttributeEditCommand(Attribute attribute, Attribute editedValueAttribute, AbstractEntityModel entity) {
		this.attribute = attribute;
		this.oldAttributeName = attribute.getName();
		this.newAttributeName = editedValueAttribute.getName();
		this.editedValueAttribute = editedValueAttribute;
		this.entity = entity;
	}
	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		this.attribute.setName(newAttributeName);
		this.entity.setName(this.entity.getName());
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		this.attribute.setName(oldAttributeName);
		this.entity.setName(this.entity.getName());
	}
	
}

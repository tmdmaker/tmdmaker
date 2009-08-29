package jp.sourceforge.tmdmaker.model.command;

import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.Attribute;

import org.eclipse.gef.commands.Command;
/**
 * 
 * @author nakaG
 *
 */
public class AttributeAddCommand extends Command {
	private AbstractEntityModel entity;
	private Attribute attribute;
	public AttributeAddCommand(AbstractEntityModel entity, Attribute attribute) {
		this.entity = entity;
		this.attribute = attribute;
	}
	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
//		entity.addAttribute(attribute);
	}
	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		
	}
	
}

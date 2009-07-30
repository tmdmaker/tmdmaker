package jp.sourceforge.tmdmaker.model.command;

import jp.sourceforge.tmdmaker.model.Attribute;

import org.eclipse.gef.commands.Command;

public class AttributeEditCommand extends Command {
	private Attribute attribute;
	private String newAttributeName;
	private String oldAttributeName;

	public AttributeEditCommand(Attribute attribute, String newAttributeName) {
		this.attribute = attribute;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		this.attribute.setName(newAttributeName);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		this.attribute.setName(oldAttributeName);
	}
	
}

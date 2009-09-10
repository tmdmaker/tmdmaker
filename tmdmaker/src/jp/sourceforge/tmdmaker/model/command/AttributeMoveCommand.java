package jp.sourceforge.tmdmaker.model.command;

import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.Attribute;

import org.eclipse.gef.commands.Command;

public class AttributeMoveCommand extends Command {
	private AbstractEntityModel parent;
	private Attribute child;
	private int oldIndex;
	private int newIndex;
	public AttributeMoveCommand(Attribute child, AbstractEntityModel parent,
			int oldIndex, int newIndex) {
		super();
		this.child = child;
		this.parent = parent;
        if (newIndex > oldIndex)
            newIndex--;
		this.oldIndex = oldIndex;
		this.newIndex = newIndex;
	}
	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		parent.removeAttribute(child);
		parent.addAttribute(newIndex, child);
	}
	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		parent.removeAttribute(child);
		parent.addAttribute(oldIndex, child);
	}
	
	
}

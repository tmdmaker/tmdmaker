package jp.sourceforge.tmdmaker.model.command;

import jp.sourceforge.tmdmaker.model.AbstractEntityModel;

import org.eclipse.gef.commands.Command;

/**
 * 
 * @author nakaG
 *
 */
public class VirtualSupersetEditCommand extends Command {
	private String oldName;
	private String newName;
	private AbstractEntityModel model;
	
	public VirtualSupersetEditCommand(AbstractEntityModel model, String newName) {
		this.model = model;
		this.oldName = model.getName();
		this.newName = newName;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		model.setName(newName);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		model.setName(oldName);
	}
	
}
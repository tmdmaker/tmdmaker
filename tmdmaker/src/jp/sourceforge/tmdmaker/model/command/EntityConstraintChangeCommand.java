package jp.sourceforge.tmdmaker.model.command;

import jp.sourceforge.tmdmaker.model.AbstractEntityModel;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.commands.Command;

public class EntityConstraintChangeCommand extends Command {
	private AbstractEntityModel model;
	private Rectangle constraint;
	private Rectangle oldConstraint;
	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		System.out.println(getClass().toString() + "#execute()");
		model.setConstraint(constraint);
	}
	public void setConstraint(Rectangle constraint) {
		this.constraint = constraint;
	}
	public void setModel(AbstractEntityModel model) {
		this.model = model;
		this.oldConstraint = model.getConstraint();
	}
	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		model.setConstraint(oldConstraint);
	}
}


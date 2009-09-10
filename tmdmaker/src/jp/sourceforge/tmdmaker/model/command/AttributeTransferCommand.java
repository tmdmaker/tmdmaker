package jp.sourceforge.tmdmaker.model.command;

import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.Attribute;

import org.eclipse.gef.commands.Command;

public class AttributeTransferCommand extends Command {
	private Attribute attributeToMove;
	private AbstractEntityModel entityFrom;
	private AbstractEntityModel entityTo;
	private int indexFrom;
	private int indexTo;

	/**
	 * コンストラクタ
	 * 
	 * @param attributeToMove
	 * @param entityFrom
	 * @param indexFrom
	 * @param entityTo
	 * @param indexTo
	 */
	public AttributeTransferCommand(Attribute attributeToMove,
			AbstractEntityModel entityFrom, int indexFrom,
			AbstractEntityModel entityTo, 
			int indexTo) {
		this.entityFrom = entityFrom;
		this.attributeToMove = attributeToMove;
		this.indexFrom = indexFrom;
		this.entityTo = entityTo;
		this.indexTo = indexTo;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		entityFrom.removeAttribute(attributeToMove);
		entityTo.addAttribute(indexTo, attributeToMove);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		entityTo.removeAttribute(attributeToMove);
		entityFrom.addAttribute(indexFrom, attributeToMove);
	}

}

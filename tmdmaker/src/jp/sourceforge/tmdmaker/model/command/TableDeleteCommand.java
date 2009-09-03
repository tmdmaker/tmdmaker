package jp.sourceforge.tmdmaker.model.command;

import jp.sourceforge.tmdmaker.model.AbstractConnectionModel;
import jp.sourceforge.tmdmaker.model.AbstractEntityModel;

import org.eclipse.gef.commands.Command;

/**
 * 
 * @author nakaG
 * 
 */
public class TableDeleteCommand extends Command {
	/** 削除対象の表モデル */
	private AbstractEntityModel model;
	/** 削除対象の表モデルを作成する契機となったリレーションシップ */
	private AbstractConnectionModel creationRelationship;

	/**
	 * 
	 * @param model
	 * @param creationRelationship
	 */
	public TableDeleteCommand(AbstractEntityModel model,
			AbstractConnectionModel creationRelationship) {
		this.model = model;
		this.creationRelationship = creationRelationship;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.commands.Command#canExecute()
	 */
	@Override
	public boolean canExecute() {
		return model.isDeletable();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		creationRelationship.disconnect();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		creationRelationship.connect();
	}

}

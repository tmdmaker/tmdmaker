package jp.sourceforge.tmdmaker.model.command;

import jp.sourceforge.tmdmaker.model.AbstractConnectionModel;

import org.eclipse.gef.commands.Command;

/**
 * 
 * @author nakaG
 * 
 */
public class ConnectionDeleteCommand extends Command {
	/** 削除対象モデル */
	private AbstractConnectionModel connection;

	/**
	 * コンストラクタ
	 */
	public ConnectionDeleteCommand(AbstractConnectionModel connection) {
		super();
		this.connection = connection;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.commands.Command#canExecute()
	 */
	@Override
	public boolean canExecute() {
		return connection.canDeletable();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		connection.disconnect();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		connection.connect();
	}

	/**
	 * @param connection
	 *            the connection to set
	 */
	public void setConnection(Object connection) {
		this.connection = (AbstractConnectionModel) connection;
	}
}

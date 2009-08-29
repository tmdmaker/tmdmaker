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
		// if (connection instanceof AbstractRelationship) {
		// AbstractRelationship r = (AbstractRelationship) connection;
		// reuseKey = r.getReuseKeys();
		// }
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
		System.out.println(getClass().toString() + "#execute()");
		// connection.detachSource();
		// connection.detachTarget();
		connection.disconnect();
		// if (connection instanceof AbstractRelationship) {
		// AbstractRelationship r = (AbstractRelationship) connection;
		// AbstractEntityModel source = (AbstractEntityModel) r.getSource();
		// AbstractEntityModel target = (AbstractEntityModel) r.getTarget();
		// source.removeReuseKey(target);
		// target.removeReuseKey(source);
		// }
		// if (reuseKey != null) {
		// AbstractRelationship r = (AbstractRelationship) connection;
		// AbstractEntityModel model = (AbstractEntityModel) r.getTarget();
		// for (Identifier id : reuseKey) {
		// model.removeReuseKey(id);
		// }
		// }
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		System.out.println(getClass().toString() + "#undo()");
		connection.connect();
		// connection.attachSource();
		// connection.attachTarget();
		// if (connection instanceof AbstractRelationship) {
		// AbstractRelationship r = (AbstractRelationship) connection;
		// AbstractEntityModel source = (AbstractEntityModel) r.getSource();
		// AbstractEntityModel target = (AbstractEntityModel) r.getTarget();
		// target.addReuseKey(source);
		// }
		// if (reuseKey != null) {
		// AbstractRelationship r = (AbstractRelationship) connection;
		// AbstractEntityModel model = (AbstractEntityModel) r.getTarget();
		// for (Identifier id : reuseKey) {
		// model.addReuseKey(id);
		// }
		// }
	}

	/**
	 * @param connection
	 *            the connection to set
	 */
	public void setConnection(Object connection) {
		this.connection = (AbstractConnectionModel) connection;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.commands.Command#chain(org.eclipse.gef.commands.Command)
	 */
	@Override
	public Command chain(Command command) {
		System.out.println(getClass().toString() + "#chain()");
		return super.chain(command);
	}

}

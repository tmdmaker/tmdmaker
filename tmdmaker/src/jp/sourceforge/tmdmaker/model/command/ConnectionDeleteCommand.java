package jp.sourceforge.tmdmaker.model.command;

import jp.sourceforge.tmdmaker.model.AbstractConnectionModel;
import jp.sourceforge.tmdmaker.model.AbstractRelationship;

import org.eclipse.gef.commands.Command;
/**
 * 
 * @author nakaG
 *
 */
public class ConnectionDeleteCommand extends Command {
	/** 削除対象モデル */
	private AbstractConnectionModel<?> connection;

	/**
	 * コンストラクタ
	 */
	public ConnectionDeleteCommand(AbstractConnectionModel connection) {
		super();
		this.connection = connection;
//		if (connection instanceof AbstractRelationship) {
//			AbstractRelationship r = (AbstractRelationship) connection;
//			reuseKeys = r.getReuseKeys();
//		}
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
//		connection.detachSource();
//		connection.detachTarget();
		connection.disconnect();
//		if (connection instanceof AbstractRelationship) {
//			AbstractRelationship r = (AbstractRelationship) connection;
//			AbstractEntityModel source = (AbstractEntityModel) r.getSource();
//			AbstractEntityModel target = (AbstractEntityModel) r.getTarget();
//			source.removeReuseKey(target);
//			target.removeReuseKey(source);
//		}
//		if (reuseKeys != null) {
//			AbstractRelationship r = (AbstractRelationship) connection;
//			AbstractEntityModel model = (AbstractEntityModel) r.getTarget();
//			for (Identifier id : reuseKeys) {
//				model.removeReuseKey(id);
//			}
//		}
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
//		connection.attachSource();
//		connection.attachTarget();
//		if (connection instanceof AbstractRelationship) {
//			AbstractRelationship r = (AbstractRelationship) connection;
//			AbstractEntityModel source = (AbstractEntityModel) r.getSource();
//			AbstractEntityModel target = (AbstractEntityModel) r.getTarget();
//			target.addReuseKey(source);
//		}
//		if (reuseKeys != null) {
//			AbstractRelationship r = (AbstractRelationship) connection;
//			AbstractEntityModel model = (AbstractEntityModel) r.getTarget();
//			for (Identifier id : reuseKeys) {
//				model.addReuseKey(id);
//			}
//		}
	}
	
	public void setConnection(Object connection) {
		this.connection = (AbstractConnectionModel) connection;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#chain(org.eclipse.gef.commands.Command)
	 */
	@Override
	public Command chain(Command command) {
		System.out.println(getClass().toString() + "#chain()");
		return super.chain(command);
	}
	
}


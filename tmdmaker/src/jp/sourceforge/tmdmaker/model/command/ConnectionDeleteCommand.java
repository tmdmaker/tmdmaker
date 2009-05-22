package jp.sourceforge.tmdmaker.model.command;

import jp.sourceforge.tmdmaker.model.AbstractConnectionModel;
import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.Relationship;

import org.eclipse.gef.commands.Command;

public class ConnectionDeleteCommand extends Command {
	private AbstractConnectionModel connection;
//	private List<Identifier> reuseKeys = null;
	
	public ConnectionDeleteCommand(AbstractConnectionModel connection) {
		super();
		this.connection = connection;
//		if (connection instanceof Relationship) {
//			Relationship r = (Relationship) connection;
//			reuseKeys = r.getReuseKeys();
//		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		System.out.println(getClass().toString() + "#execute()");
//		connection.detachSource();
//		connection.detachTarget();
		connection.disConnect();
		if (connection instanceof Relationship) {
//			Relationship r = (Relationship) connection;
//			AbstractEntityModel source = (AbstractEntityModel) r.getSource();
//			AbstractEntityModel target = (AbstractEntityModel) r.getTarget();
//			source.removeReuseKey(target);
//			target.removeReuseKey(source);
		}
//		if (reuseKeys != null) {
//			Relationship r = (Relationship) connection;
//			AbstractEntityModel model = (AbstractEntityModel) r.getTarget();
//			for (Identifier id : reuseKeys) {
//				model.removeReuseKey(id);
//			}
//		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		System.out.println(getClass().toString() + "#undo()");
		connection.connect();
//		connection.attachSource();
//		connection.attachTarget();
		if (connection instanceof Relationship) {
//			Relationship r = (Relationship) connection;
//			AbstractEntityModel source = (AbstractEntityModel) r.getSource();
//			AbstractEntityModel target = (AbstractEntityModel) r.getTarget();
//			target.addReuseKey(source);
		}
//		if (reuseKeys != null) {
//			Relationship r = (Relationship) connection;
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


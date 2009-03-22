package jp.sourceforge.tmdmaker.model.command;

import jp.sourceforge.tmdmaker.model.AbstractConnectionModel;
import jp.sourceforge.tmdmaker.model.ConnectableElement;

import org.eclipse.gef.commands.Command;

public class ConnectionCreateCommand extends Command {
	private ConnectableElement source, target;
	private AbstractConnectionModel connection;

	@Override
	public boolean canExecute() {
		return source != null && target != null;
	}
	@Override
	public void execute() {
		System.out.println(getClass().toString() + "#execute()");
		connection.connect();
//		connection.attachSource();
//		connection.attachTarget();
	}
	@Override
	public void undo() {
//		connection.detachSource();
//		connection.detachTarget();
		connection.disConnect();
	}
	
	public void setConnection(Object connection) {
		this.connection = (AbstractConnectionModel) connection;
	}
	
	public void setSource(Object source) {
		this.source = (ConnectableElement) source;
		this.connection.setSource(this.source);
	}
	
	public void setTarget(Object target) {
		this.target = (ConnectableElement) target;
		this.connection.setTarget(this.target);
	}
	/**
	 * @return the connection
	 */
	public AbstractConnectionModel getConnection() {
		return connection;
	}
	/**
	 * @return the source
	 */
	public ConnectableElement getSource() {
		return source;
	}
	/**
	 * @return the target
	 */
	public ConnectableElement getTarget() {
		return target;
	}
}

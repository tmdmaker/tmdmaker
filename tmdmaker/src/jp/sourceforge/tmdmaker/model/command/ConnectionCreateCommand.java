package jp.sourceforge.tmdmaker.model.command;

import jp.sourceforge.tmdmaker.model.AbstractConnectionModel;
import jp.sourceforge.tmdmaker.model.ConnectableElement;

import org.eclipse.gef.commands.Command;

/**
 * 
 * @author nakaG
 * @param <T> ノード
 */
public class ConnectionCreateCommand<T extends ConnectableElement> extends
		Command {

	/**
	 * ソース
	 */
	private T source;

	/**
	 * ターゲット
	 */
	private T target;

	/**
	 * コネクション
	 */
	private AbstractConnectionModel<T> connection;

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#canExecute()
	 */
	@Override
	public boolean canExecute() {
		return source != null && target != null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		System.out.println(getClass().toString() + "#execute()");
		connection.connect();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		connection.disConnect();
	}

	/**
	 * 
	 * @param connection
	 */
	public void setConnection(AbstractConnectionModel connection) {
		this.connection = connection;
	}

	public void setSource(Object source) {
		this.source = (T) source;
		this.connection.setSource(this.source);
	}

	/**
	 * 
	 * @param target
	 */
	public void setTarget(Object target) {
		this.target = (T) target;
		this.connection.setTarget(this.target);
	}

	/**
	 * @return the connection
	 */
	public AbstractConnectionModel<T> getConnection() {
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

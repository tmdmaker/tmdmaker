package jp.sourceforge.tmdmaker.model.command;

import jp.sourceforge.tmdmaker.model.AbstractConnectionModel;
import jp.sourceforge.tmdmaker.model.ConnectableElement;
import jp.sourceforge.tmdmaker.model.command.ConnectingModelSwitchStrategy.ModelPair;

import org.eclipse.gef.commands.Command;

/**
 * 
 * @author nakaG
 *            ノード
 */
public class ConnectionCreateCommand extends
		Command {

	/**
	 * ソース
	 */
	private ConnectableElement source;

	/**
	 * ターゲット
	 */
	private ConnectableElement target;

	/**
	 * コネクション
	 */
	private AbstractConnectionModel connection;

	/** ソースとターゲットの入れ替え処理 */
	private ConnectingModelSwitchStrategy strategy;

	/**
	 * 
	 * {@inheritDoc}
	 * 
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
		if (connection.getSource() == null || connection.getTarget() == null) {
			if (strategy != null) {
				ModelPair pair = strategy.switchModel(source, target);
				source = pair.source;
				target = pair.target;
			}
			connection.setSource(source);
			connection.setTarget(target);
		}
		connection.connect();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		connection.disconnect();
	}

	/**
	 * 
	 * @param connection
	 */
	public void setConnection(AbstractConnectionModel connection) {
		this.connection = connection;
	}

	public void setSource(ConnectableElement source) {
		this.source = source;
	}

	/**
	 * 
	 * @param target
	 */
	public void setTarget(ConnectableElement target) {
		this.target = target;
	}

	/**
	 * @return the connection
	 */
	public AbstractConnectionModel<?> getConnection() {
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

	/**
	 * @param strategy the strategy to set
	 */
	public void setStrategy(ConnectingModelSwitchStrategy strategy) {
		this.strategy = strategy;
	}
	
}

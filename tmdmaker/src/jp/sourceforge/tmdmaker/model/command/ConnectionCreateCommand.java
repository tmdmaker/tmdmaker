/*
 * Copyright 2009-2010 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jp.sourceforge.tmdmaker.model.command;

import jp.sourceforge.tmdmaker.model.AbstractConnectionModel;
import jp.sourceforge.tmdmaker.model.ConnectableElement;

import org.eclipse.gef.commands.Command;

/**
 * リレーションシップ等のコネクションを作成するCommand
 * 
 * @author nakaG
 */
public class ConnectionCreateCommand extends Command {

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

	/**
	 * デフォルトコンストラクタ
	 */
	public ConnectionCreateCommand() {
	}

	public ConnectionCreateCommand(AbstractConnectionModel connection,
			ConnectableElement source, ConnectableElement target) {
		setConnection(connection);
		setSource(source);
		setTarget(target);
	}

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

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		connection.connect();
	}

	/**
	 * 
	 * {@inheritDoc}
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

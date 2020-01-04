/*
 * Copyright 2009-2019 TMD-Maker Project <https://www.tmdmaker.org>
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
package org.tmdmaker.ui.editor.gef3.commands;

import org.eclipse.gef.commands.Command;
import org.tmdmaker.core.model.AbstractConnectionModel;
import org.tmdmaker.core.model.ConnectableElement;
import org.tmdmaker.ui.editor.draw2d.ConstraintAdjuster;
import org.tmdmaker.ui.editor.draw2d.ConstraintAdjusterFactory;

/**
 * GEFのコネクションツールを利用してリレーションシップ等のコネクションを作成するCommand
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

	public ConnectionCreateCommand(AbstractConnectionModel connection, ConnectableElement source,
			ConnectableElement target) {
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
		return source != null && target != null && connection != null;
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
		ConstraintAdjuster adjuster = ConstraintAdjusterFactory.getAdjuster(connection);
		adjuster.adjust();
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

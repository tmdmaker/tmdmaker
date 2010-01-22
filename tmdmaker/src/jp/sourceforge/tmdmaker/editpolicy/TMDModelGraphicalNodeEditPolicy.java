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
package jp.sourceforge.tmdmaker.editpolicy;

import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.command.ConnectionCreateCommand;
import jp.sourceforge.tmdmaker.model.rule.RelationshipRule;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gef.requests.ReconnectRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * エンティティ系モデル間のリレーションシップ作成EditPolicy
 * 
 * @author nakaG
 * 
 */
public class TMDModelGraphicalNodeEditPolicy extends GraphicalNodeEditPolicy {
	/** logging */
	private static Logger logger = LoggerFactory
			.getLogger(TMDModelGraphicalNodeEditPolicy.class);

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy#getConnectionCreateCommand(org.eclipse.gef.requests.CreateConnectionRequest)
	 */
	@Override
	protected Command getConnectionCreateCommand(CreateConnectionRequest request) {
		logger.debug(getClass() + "#getConnectionCreateCommand()");
		ConnectionCreateCommand command = new ConnectionCreateCommand();
		// command.setConnection((AbstractConnectionModel)
		// request.getNewObject());
		command.setSource(getAbstractEntityModel());
		request.setStartCommand(command);
		return command;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy#getConnectionCompleteCommand(org.eclipse.gef.requests.CreateConnectionRequest)
	 */
	protected Command getConnectionCompleteCommand(
			CreateConnectionRequest request) {
		logger.debug(getClass() + "#getConnectionCompleteCommand()");

		ConnectionCreateCommand startCommand = (ConnectionCreateCommand) request
				.getStartCommand();
		AbstractEntityModel source = (AbstractEntityModel) startCommand
				.getSource();
		AbstractEntityModel target = (AbstractEntityModel) getHost().getModel();
		startCommand.setTarget(target);
		startCommand.setConnection(RelationshipRule.createRelationship(source,
				target));

		return startCommand;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy#getReconnectSourceCommand(org.eclipse.gef.requests.ReconnectRequest)
	 */
	@Override
	protected Command getReconnectSourceCommand(ReconnectRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy#getReconnectTargetCommand(org.eclipse.gef.requests.ReconnectRequest)
	 */
	@Override
	protected Command getReconnectTargetCommand(ReconnectRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * エンティティモデルを取得する。
	 * 
	 * @return モデル
	 */
	protected AbstractEntityModel getAbstractEntityModel() {
		return (AbstractEntityModel) getHost().getModel();
	}
}
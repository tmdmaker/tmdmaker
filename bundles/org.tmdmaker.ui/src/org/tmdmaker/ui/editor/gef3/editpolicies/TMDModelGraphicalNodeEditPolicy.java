/*
 * Copyright 2009-2019 TMD-Maker Project <https://tmdmaker.osdn.jp/>
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
package org.tmdmaker.ui.editor.gef3.editpolicies;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.tmdmaker.core.model.AbstractEntityModel;
import org.tmdmaker.core.model.relationship.Relationship;
import org.tmdmaker.ui.editor.gef3.commands.ConnectionCreateCommand;

/**
 * エンティティ系モデル間のリレーションシップ作成EditPolicy
 * 
 * @author nakaG
 * 
 */
public class TMDModelGraphicalNodeEditPolicy extends ReconnectableNodeEditPolicy {
	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy#getConnectionCreateCommand(org.eclipse.gef.requests.CreateConnectionRequest)
	 */
	@Override
	protected Command getConnectionCreateCommand(CreateConnectionRequest request) {
		logger.debug("{}#getConnectionCreateCommand()", getClass());
		ConnectionCreateCommand command = new ConnectionCreateCommand();
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
	@Override
	protected Command getConnectionCompleteCommand(CreateConnectionRequest request) {
		logger.debug("{}#getConnectionCompleteCommand()", getClass());

		ConnectionCreateCommand startCommand = (ConnectionCreateCommand) request.getStartCommand();
		AbstractEntityModel source = (AbstractEntityModel) startCommand.getSource();
		AbstractEntityModel target = (AbstractEntityModel) getHost().getModel();
		startCommand.setTarget(target);
		startCommand.setConnection(Relationship.of(source, target));

		return startCommand;
	}
}
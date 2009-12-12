/*
 * Copyright 2009 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
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
import jp.sourceforge.tmdmaker.model.AbstractRelationship;
import jp.sourceforge.tmdmaker.model.Event2EventRelationship;
import jp.sourceforge.tmdmaker.model.RecursiveRelationship;
import jp.sourceforge.tmdmaker.model.Resource2ResourceRelationship;
import jp.sourceforge.tmdmaker.model.TransfarReuseKeysToTargetRelationship;
import jp.sourceforge.tmdmaker.model.command.ConnectionCreateCommand;
import jp.sourceforge.tmdmaker.model.command.strategy.ResourceAndEventEntitiesSwitchStrategy;
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

		Command command = null;
		// 再帰
		if (RelationshipRule.isRecursive(source, target)) {
			logger.debug("Recursive");
			command = createRecursiveTableCommand(request);
			command.setLabel("Recursive");
		} else if (RelationshipRule.isR2E(source, target)) {
			logger.debug("RESOURCE:EVENT");
			command = createR2ERelationshipCommand(request);
			command.setLabel("RESOURCE:EVENT");
		} else if (RelationshipRule.isR2R(source, target)) {
			logger.debug("RESOURCE:RESOURCE");
			/* 対照表作成 */
			command = createCombinationTableCommand(request);
			command.setLabel("RESOURCE:RESOURCE");
		} else if (RelationshipRule.isE2E(source, target)) {
			logger.debug("EVENT:EVENT");
			/* 通常コネクション */
			command = createE2ERelationshipCommand(request);
			command.setLabel("EVENT:EVENT");
		} // else 対応表とのリレーションシップ
		return command;
	}

	/**
	 * 対照表を作成する。
	 * 
	 * @param request
	 * @return
	 */
	private Command createCombinationTableCommand(
			CreateConnectionRequest request) {

		// 元エンティティと先エンティティを接続
		ConnectionCreateCommand startCommand = (ConnectionCreateCommand) request
				.getStartCommand();

		startCommand.setTarget(getAbstractEntityModel());
		((AbstractRelationship) startCommand.getConnection())
				.setCenterMark(true);

		AbstractEntityModel source = (AbstractEntityModel) startCommand
				.getSource();
		AbstractEntityModel target = (AbstractEntityModel) startCommand
				.getTarget();
		Resource2ResourceRelationship relationship = new Resource2ResourceRelationship(
				source, target);
		startCommand.setConnection(relationship);
		startCommand.setStrategy(null);
		return startCommand;
	}

	/**
	 * 再帰表を作成する。
	 * 
	 * @param request
	 * @return
	 */
	private Command createRecursiveTableCommand(CreateConnectionRequest request) {
		logger.debug(getClass() + "#createRecursiveTableCommand() start");

		// 元エンティティと先エンティティを接続
		ConnectionCreateCommand startCommand = (ConnectionCreateCommand) request
				.getStartCommand();
		AbstractEntityModel source = (AbstractEntityModel) startCommand
				.getSource();
		RecursiveRelationship relationship = new RecursiveRelationship(source);

		startCommand.setConnection(relationship);
		startCommand.setSource(source);
		startCommand.setTarget(getAbstractEntityModel());
		startCommand.setStrategy(null);

		logger.debug(getClass() + "#createRecursiveTableCommand() end");
		return startCommand;

	}

	/**
	 * EventとEventのリレーションシップを作成する。
	 * 
	 * @param request
	 * @return
	 */
	private Command createE2ERelationshipCommand(CreateConnectionRequest request) {
		ConnectionCreateCommand startCommand = (ConnectionCreateCommand) request
				.getStartCommand();
		Event2EventRelationship relationship = new Event2EventRelationship(
				(AbstractEntityModel) startCommand.getSource(),
				getAbstractEntityModel());
		startCommand.setConnection(relationship);
		startCommand.setTarget(getAbstractEntityModel());
		startCommand.setStrategy(null);
		return startCommand;
	}

	/**
	 * ResourceとEventのリレーションシップを作成する。
	 * 
	 * @param command
	 * @return
	 */
	protected Command createR2ERelationshipCommand(
			CreateConnectionRequest request) {
		ConnectionCreateCommand startCommand = (ConnectionCreateCommand) request
				.getStartCommand();
		startCommand.setConnection(new TransfarReuseKeysToTargetRelationship());
		startCommand.setTarget(getAbstractEntityModel());
		startCommand.setStrategy(new ResourceAndEventEntitiesSwitchStrategy());
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
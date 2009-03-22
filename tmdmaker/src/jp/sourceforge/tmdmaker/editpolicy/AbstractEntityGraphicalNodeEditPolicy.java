package jp.sourceforge.tmdmaker.editpolicy;

import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.Event2EventRelationship;
import jp.sourceforge.tmdmaker.model.RecursiveRelationship;
import jp.sourceforge.tmdmaker.model.Relationship;
import jp.sourceforge.tmdmaker.model.Resource2EventRelationship;
import jp.sourceforge.tmdmaker.model.Resource2ResourceRelationship;
import jp.sourceforge.tmdmaker.model.AbstractEntityModel.EntityType;
import jp.sourceforge.tmdmaker.model.command.ConnectionCreateCommand;
import jp.sourceforge.tmdmaker.model.command.Resource2EventConnectionCreateCommand;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy;
import org.eclipse.gef.requests.CreateConnectionRequest;

public abstract class AbstractEntityGraphicalNodeEditPolicy extends
		GraphicalNodeEditPolicy {

	protected Command getConnectionCompleteCommand(
			CreateConnectionRequest request) {
		System.out.println(getClass().toString()
				+ "#getConnectionCompleteCommand()");

		ConnectionCreateCommand startCommand = (ConnectionCreateCommand) request
				.getStartCommand();
		AbstractEntityModel source = (AbstractEntityModel) startCommand
				.getSource();
		AbstractEntityModel target = (AbstractEntityModel) getHost().getModel();

		Command command = null;
		// 再帰
		if (source == target) {
			System.out.println("Recursive");
			command = createRecursiveTableCommand(request);
		} else if (isR2E(source, target)) {
			System.out.println("R:E");
			command = createR2ERelationshipCommand(startCommand);
			command.setLabel("R:E");
		} else if (isR2R(source, target)) {
			System.out.println("R:R");
			/* 対照表作成 */
			command = createCombinationTableCommand(request);
			command.setLabel("R:R");
		} else if (isE2E(source, target)) {
			System.out.println("E:E");
			/* 通常コネクション */
			command = createE2ERelationshipCommand(request);
			command.setLabel("E:E");
		} // else 対応表とのリレーションシップ
		return command;
	}

	/**
	 * エンティティの関連がR:Rかを判定する。
	 * 
	 * @param source
	 * @param target
	 * @return R:Rの場合にtrueを返す。
	 */
	protected boolean isR2R(AbstractEntityModel source,
			AbstractEntityModel target) {
		return source.getEntityType().equals(EntityType.R)
				&& target.getEntityType().equals(EntityType.R);
	}

	/**
	 * エンティティの関連がR:Eかを判定する。
	 * 
	 * @param source
	 * @param target
	 * @return R:Eの場合にtrueを返す。
	 */
	protected boolean isR2E(AbstractEntityModel source,
			AbstractEntityModel target) {
		return (source.getEntityType().equals(EntityType.E) && target
				.getEntityType().equals(EntityType.R))
				|| (source.getEntityType().equals(EntityType.R) && target
						.getEntityType().equals(EntityType.E));
	}

	/**
	 * エンティティの関連がE:Eかを判定する。
	 * 
	 * @param source
	 * @param target
	 * @return E:Eの場合にtrueを返す。
	 */
	protected boolean isE2E(AbstractEntityModel source,
			AbstractEntityModel target) {
		return source.getEntityType().equals(EntityType.E)
				&& target.getEntityType().equals(EntityType.E);
	}

	/**
	 * 対照表を作成する。
	 * 
	 * @param request
	 * @return
	 */
	protected Command createCombinationTableCommand(
			CreateConnectionRequest request) {

		// 元エンティティと先エンティティを接続
		ConnectionCreateCommand startCommand = (ConnectionCreateCommand) request
				.getStartCommand();

		startCommand.setTarget(getHost().getModel());
		((Relationship) startCommand.getConnection()).setCenterMard(true);

		AbstractEntityModel source = (AbstractEntityModel) startCommand
				.getSource();
		AbstractEntityModel target = (AbstractEntityModel) startCommand
				.getTarget();
		Resource2ResourceRelationship relationship = new Resource2ResourceRelationship(
				source, target);
		startCommand.setConnection(relationship);
		return startCommand;
	}

	@Override
	protected Command getConnectionCreateCommand(CreateConnectionRequest request) {
		System.out.println(getClass().toString()
				+ "#getConnectionCreateCommand()");
		ConnectionCreateCommand command = new ConnectionCreateCommand();
		command.setConnection(request.getNewObject());
		command.setSource(getHost().getModel());
		request.setStartCommand(command);
		return command;
	}

	/**
	 * 再帰表を作成する。
	 * 
	 * @param request
	 * @return
	 */
	protected Command createRecursiveTableCommand(
			CreateConnectionRequest request) {
		System.out.println(getClass() + "#createRecursiveTableCommand() start");

		// 元エンティティと先エンティティを接続
		ConnectionCreateCommand startCommand = (ConnectionCreateCommand) request
				.getStartCommand();
		AbstractEntityModel source = (AbstractEntityModel) startCommand
				.getSource();
		RecursiveRelationship relationship = new RecursiveRelationship(source);

		startCommand.setConnection(relationship);
		startCommand.setSource(source);
		startCommand.setTarget(getHost().getModel());

		System.out.println(getClass() + "#createRecursiveTableCommand() end");
		return startCommand;

	}

	/**
	 * EventとEventのリレーションシップを作成する。
	 * 
	 * @param request
	 * @return
	 */
	protected Command createE2ERelationshipCommand(
			CreateConnectionRequest request) {
		ConnectionCreateCommand startCommand = (ConnectionCreateCommand) request
				.getStartCommand();
		Event2EventRelationship relationship = new Event2EventRelationship(
				(AbstractEntityModel) startCommand.getSource(),
				(AbstractEntityModel) getHost().getModel());
		startCommand.setConnection(relationship);
		startCommand.setTarget(getHost().getModel());
		startCommand.setLabel("E2E");
		return startCommand;
	}

	/**
	 * ResourceとEventのリレーションシップを作成する。
	 * 
	 * @param command
	 * @return
	 */
	protected Command createR2ERelationshipCommand(
			ConnectionCreateCommand command) {
		Resource2EventConnectionCreateCommand newCommand = new Resource2EventConnectionCreateCommand(
				(AbstractEntityModel) command.getSource(),
				(AbstractEntityModel) getHost().getModel(),
				new Resource2EventRelationship());
		return newCommand;
	}
}
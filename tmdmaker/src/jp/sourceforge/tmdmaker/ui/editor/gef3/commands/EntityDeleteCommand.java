package jp.sourceforge.tmdmaker.ui.editor.gef3.commands;

import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.Diagram;


/**
 * エンティティ削除Command
 * 
 * @author nakaG
 * 
 */
public class EntityDeleteCommand<T extends AbstractEntityModel> extends ConnectableElementDeleteCommand {
	private T model;

	public EntityDeleteCommand(Diagram diagram, T model) {
		super();
		this.diagram = diagram;
		this.model = model;
		this.sourceConnections.addAll(model.getModelSourceConnections());
		this.targetConnections.addAll(model.getModelTargetConnections());
	}

	@Override
	public boolean canExecute() {
		return model.isDeletable();
	}

	@Override
	public void execute() {
		diagram.removeChild(model);

		detachConnections(sourceConnections);
		detachConnections(targetConnections);
	}

	@Override
	public void undo() {
		diagram.addChild(model);
		attathConnections(sourceConnections);
		attathConnections(targetConnections);
	}

}

package jp.sourceforge.tmdmaker.model.command;

import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.Relationship;
import jp.sourceforge.tmdmaker.model.AbstractEntityModel.EntityType;

import org.eclipse.gef.commands.Command;

public class Resource2EventConnectionCreateCommand extends Command {
	private AbstractEntityModel source, target;
	private Relationship connection;

	public Resource2EventConnectionCreateCommand(AbstractEntityModel source,
			AbstractEntityModel target, Relationship connection) {
		super();
		this.connection = connection;
		
		this.source = source;
		this.target = target;
		switchEntity();
		this.connection.setSource(this.source);
		this.connection.setTarget(this.target);
	}

	@Override
	public boolean canExecute() {
		return source != null && target != null;
	}

	@Override
	public void execute() {
		System.out.println(getClass().toString() + "#execute()");
		this.connection.attachSource();
		this.connection.attachTarget();
	}

	private void switchEntity() {
		if (source.getEntityType().equals(EntityType.E)) {
			AbstractEntityModel originalSource = source;
			this.source = this.target;
			this.target = originalSource;
		}
	}

	@Override
	public void undo() {
		connection.detachSource();
		connection.detachTarget();
	}
}


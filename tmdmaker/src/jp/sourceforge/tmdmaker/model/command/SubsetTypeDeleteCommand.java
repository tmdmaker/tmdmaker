package jp.sourceforge.tmdmaker.model.command;

import jp.sourceforge.tmdmaker.model.Diagram;
import jp.sourceforge.tmdmaker.model.Entity2SubsetTypeRelationship;
import jp.sourceforge.tmdmaker.model.SubsetType;

import org.eclipse.gef.commands.Command;

public class SubsetTypeDeleteCommand extends Command {
	private Diagram diagram;
	private SubsetType model;
	private Entity2SubsetTypeRelationship relationship;

	public SubsetTypeDeleteCommand(Diagram diagram, SubsetType model) {
		this.diagram = diagram;
		this.model = model;
		this.relationship = (Entity2SubsetTypeRelationship) model
				.getModelTargetConnections().get(0);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		if (model.getModelSourceConnections().size() == 0) {
			relationship.disConnect();
			model.setDiagram(null);
			diagram.removeChild(model);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		if (model.getModelSourceConnections().size() == 0) {
			diagram.addChild(model);
			model.setDiagram(diagram);
			relationship.connect();
		}
	}

	
	
}

package jp.sourceforge.tmdmaker.model.command;

import jp.sourceforge.tmdmaker.model.AbstractRelationship;
import jp.sourceforge.tmdmaker.model.Cardinality;

import org.eclipse.gef.commands.Command;

public class RelationshipEditCommand extends Command {
	private AbstractRelationship model;
	private Cardinality sourceCardinality = Cardinality.ONE;
	private Cardinality targetCardinality = Cardinality.ONE;
	private boolean sourceNoInstance = false;
	private boolean targetNoInstance = false;

	private Cardinality oldSourceCardinality = null;
	private Cardinality oldTargetCardinality = null;
	private boolean oldSourceNoInstance = false;
	private boolean oldTargetNoInstance = false;

	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		this.model.setSourceNoInstance(this.sourceNoInstance);
		this.model.setSourceCardinality(this.sourceCardinality);
		this.model.setTargetNoInstance(this.targetNoInstance);
		this.model.setTargetCardinality(this.targetCardinality);
	}
	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		this.model.setSourceNoInstance(this.oldSourceNoInstance);
		this.model.setSourceCardinality(this.oldSourceCardinality);
		this.model.setTargetNoInstance(this.oldTargetNoInstance);
		this.model.setTargetCardinality(this.oldTargetCardinality);
	}
	/**
	 * @param model the model to set
	 */
	public void setModel(AbstractRelationship model) {
		this.model = model;
		this.oldSourceCardinality = model.getSourceCardinality();
		this.oldSourceNoInstance = model.isSourceNoInstance();
		this.oldTargetCardinality = model.getTargetCardinality();
		this.oldTargetNoInstance = model.isTargetNoInstance();
	}
	/**
	 * @param sourceCardinality the sourceCardinality to set
	 */
	public void setSourceCardinality(Cardinality sourceCardinality) {
		this.sourceCardinality = sourceCardinality;
	}
	/**
	 * @param targetCardinality the targetCardinality to set
	 */
	public void setTargetCardinality(Cardinality targetCardinality) {
		this.targetCardinality = targetCardinality;
	}
	/**
	 * @param sourceNoInstance the sourceNoInstance to set
	 */
	public void setSourceNoInstance(boolean sourceNoInstance) {
		this.sourceNoInstance = sourceNoInstance;
	}
	/**
	 * @param targetNoInstance the targetNoInstance to set
	 */
	public void setTargetNoInstance(boolean targetNoInstance) {
		this.targetNoInstance = targetNoInstance;
	}
}

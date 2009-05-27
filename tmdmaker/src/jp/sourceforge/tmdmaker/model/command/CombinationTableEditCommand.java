package jp.sourceforge.tmdmaker.model.command;

import java.util.ArrayList;
import java.util.List;

import jp.sourceforge.tmdmaker.model.Attribute;
import jp.sourceforge.tmdmaker.model.CombinationTable;
import jp.sourceforge.tmdmaker.model.Identifier;

import org.eclipse.gef.commands.Command;

public class CombinationTableEditCommand extends Command {
	private String tableName;
	private List<Identifier> reuseKeys;
	private List<Attribute> attributes;

	private CombinationTable model;
	private String oldTableName;
	private List<Identifier> oldReuseKeys = new ArrayList<Identifier>();
	private List<Attribute> oldAttributes;

	public CombinationTableEditCommand(CombinationTable model, String name, List<Identifier> reuseKeys, List<Attribute> attributes) {
		this.tableName = name;
		this.reuseKeys = reuseKeys;
		this.attributes = attributes;
		this.oldTableName = model.getName();
//		this.oldReuseKeys = model.getReuseKeys();
		this.oldAttributes = model.getAttributes();
		this.model = model;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		// TODO identifier更新伝播
		model.setAttributes(attributes);
		model.setName(tableName);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		model.setAttributes(oldAttributes);
		model.setName(oldTableName);
	}
	
	
}

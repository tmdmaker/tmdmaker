package jp.sourceforge.tmdmaker.model.command;

import java.util.ArrayList;
import java.util.List;

import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.Attribute;
import jp.sourceforge.tmdmaker.model.Identifier;

import org.eclipse.gef.commands.Command;

/**
 * 
 * @author nakaG
 *
 * @param <T> AbstractEntityModelを継承したXX表のクラスを
 */
public class TableEditCommand<T extends AbstractEntityModel> extends Command {
	private String name;
	private List<Identifier> reuseKeys;
	private List<Attribute> attributes;

	private T model;
	private String oldName;
	private List<Identifier> oldReuseKeys = new ArrayList<Identifier>();
	private List<Attribute> oldAttributes;

	/**
	 * 
	 * @param model
	 * @param name
	 * @param reuseKeys
	 * @param attributes
	 */
	public TableEditCommand(T model, String name, List<Identifier> reuseKeys, List<Attribute> attributes) {
		this.name = name;
		this.reuseKeys = reuseKeys;
		this.attributes = attributes;
		this.oldName = model.getName();
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
		model.setName(name);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		model.setAttributes(oldAttributes);
		model.setName(oldName);
	}
	

}

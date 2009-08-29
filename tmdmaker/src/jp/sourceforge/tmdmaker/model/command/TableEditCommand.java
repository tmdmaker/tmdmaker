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
	private String newName;
	private List<Identifier> reuseKey;
	private List<Attribute> newAttributes;
	private boolean newNotImplement;
	
	private T model;
	private T newValue;
	
	private String oldName;
	private List<Identifier> oldReuseKeys = new ArrayList<Identifier>();
	private List<Attribute> oldAttributes;
	private boolean oldNotImplement;
	
	/**
	 * 
	 * @param model
	 * @param name
	 * @param reuseKey
	 * @param newAttributes
	 */
	public TableEditCommand(T model, String name, List<Identifier> reuseKey, List<Attribute> attributes) {
		this.newName = name;
		this.reuseKey = reuseKey;
		this.newAttributes = attributes;
		this.oldName = model.getName();
//		this.oldReuseKeys = model.getReuseKeys();
		this.oldAttributes = model.getAttributes();
		this.model = model;
	}
	public TableEditCommand(T toBeEdit, T newValue) {
		this.model = toBeEdit;
		this.newValue = newValue;
		this.oldName = toBeEdit.getName();
		this.oldAttributes = toBeEdit.getAttributes();
		this.oldNotImplement = toBeEdit.isNotImplement();
		this.newName = newValue.getName();
		this.newAttributes = newValue.getAttributes();
		this.newNotImplement = newValue.isNotImplement();
	}
	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		// TODO identifier更新伝播
		model.setAttributes(newAttributes);
		model.setNotImplement(newNotImplement);
		model.setName(newName);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		model.setAttributes(oldAttributes);
		model.setNotImplement(oldNotImplement);
		model.setName(oldName);
	}
	

}

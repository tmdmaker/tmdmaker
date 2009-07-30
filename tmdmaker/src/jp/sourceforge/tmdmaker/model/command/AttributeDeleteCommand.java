package jp.sourceforge.tmdmaker.model.command;

import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.Attribute;

import org.eclipse.gef.commands.Command;

public class AttributeDeleteCommand extends Command {
	private AbstractEntityModel entity;
	private Attribute attribute;
	
	public AttributeDeleteCommand(AbstractEntityModel entity, Attribute attribute) {
		
	}
}

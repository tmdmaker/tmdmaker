package jp.sourceforge.tmdmaker.model.command;

import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.Attribute;

import org.eclipse.gef.commands.Command;

/**
 * アトリビュート編集コマンド
 * 
 * @author nakaG
 * 
 */
public class AttributeEditCommand extends Command {
	/** 編集対象モデル */
	private Attribute attribute;
	/** 編集後値 */
	private Attribute editedValueAttribute;
	/** 編集前値 */
	private Attribute oldValueAttribute;
	/** 親モデル */
	private AbstractEntityModel entity;

	/**
	 * コンストラクタ
	 * @param attribute 編集対象モデル
	 * @param editedValueAttribute 編集後値
	 * @param entity 親モデル
	 */
	public AttributeEditCommand(Attribute attribute,
			Attribute editedValueAttribute, AbstractEntityModel entity) {
		this.attribute = attribute;
		this.editedValueAttribute = editedValueAttribute;
		this.oldValueAttribute = new Attribute();
		oldValueAttribute.setName(attribute.getName());
		oldValueAttribute.setDataType(attribute.getDataType());
		oldValueAttribute.setDerivationRule(attribute.getDerivationRule());
		oldValueAttribute.setDescription(attribute.getDescription());
		oldValueAttribute.setLock(attribute.getLock());
		oldValueAttribute.setScale(attribute.getScale());
		oldValueAttribute.setSize(attribute.getSize());
		oldValueAttribute.setValidationRule(attribute.getValidationRule());
		this.entity = entity;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		attribute.setName(editedValueAttribute.getName());
		attribute.setDataType(editedValueAttribute.getDataType());
		attribute.setDerivationRule(editedValueAttribute.getDerivationRule());
		attribute.setDescription(editedValueAttribute.getDescription());
		attribute.setLock(editedValueAttribute.getLock());
		attribute.setScale(editedValueAttribute.getScale());
		attribute.setSize(editedValueAttribute.getSize());
		attribute.setValidationRule(editedValueAttribute.getValidationRule());

		entity.setName(this.entity.getName());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		attribute.setName(oldValueAttribute.getName());
		attribute.setDataType(oldValueAttribute.getDataType());
		attribute.setDerivationRule(oldValueAttribute.getDerivationRule());
		attribute.setDescription(oldValueAttribute.getDescription());
		attribute.setLock(oldValueAttribute.getLock());
		attribute.setScale(oldValueAttribute.getScale());
		attribute.setSize(oldValueAttribute.getSize());
		attribute.setValidationRule(oldValueAttribute.getValidationRule());
		this.entity.setName(this.entity.getName());
	}

}

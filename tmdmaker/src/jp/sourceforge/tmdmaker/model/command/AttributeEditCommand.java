/*
 * Copyright 2009-2010 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
	 * 
	 * @param attribute
	 *            編集対象モデル
	 * @param editedValueAttribute
	 *            編集後値
	 */
	public AttributeEditCommand(Attribute attribute,
			Attribute editedValueAttribute) {
		this(attribute, editedValueAttribute, null);
	}
	/**
	 * コンストラクタ
	 * 
	 * @param attribute
	 *            編集対象モデル
	 * @param editedValueAttribute
	 *            編集後値
	 * @param entity
	 *            親モデル
	 */
	public AttributeEditCommand(Attribute attribute,
			Attribute editedValueAttribute, AbstractEntityModel entity) {
		this.attribute = attribute;
		this.editedValueAttribute = editedValueAttribute;
		this.oldValueAttribute = new Attribute();
		oldValueAttribute.setName(attribute.getName());
		oldValueAttribute.setDataTypeDeclaration(attribute.getDataTypeDeclaration());
		oldValueAttribute.setDerivationRule(attribute.getDerivationRule());
		oldValueAttribute.setDescription(attribute.getDescription());
		oldValueAttribute.setLock(attribute.getLock());
		oldValueAttribute.setValidationRule(attribute.getValidationRule());
		oldValueAttribute.setImplementName(attribute.getImplementName());
		oldValueAttribute.setNullable(attribute.isNullable());
		this.entity = entity;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		attribute.setDataTypeDeclaration(editedValueAttribute.getDataTypeDeclaration());
		attribute.setDerivationRule(editedValueAttribute.getDerivationRule());
		attribute.setDescription(editedValueAttribute.getDescription());
		attribute.setLock(editedValueAttribute.getLock());
		attribute.setValidationRule(editedValueAttribute.getValidationRule());
		attribute.setDataTypeDeclaration(editedValueAttribute.getDataTypeDeclaration());
		attribute.setImplementName(editedValueAttribute.getImplementName());
		attribute.setNullable(editedValueAttribute.isNullable());
		attribute.setName(editedValueAttribute.getName());
		if (entity != null) {
			entity.setName(this.entity.getName());
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		attribute.setDataTypeDeclaration(oldValueAttribute.getDataTypeDeclaration());
		attribute.setDerivationRule(oldValueAttribute.getDerivationRule());
		attribute.setDescription(oldValueAttribute.getDescription());
		attribute.setLock(oldValueAttribute.getLock());
		attribute.setValidationRule(oldValueAttribute.getValidationRule());
		attribute.setDataTypeDeclaration(oldValueAttribute.getDataTypeDeclaration());
		attribute.setImplementName(oldValueAttribute.getImplementName());
		attribute.setNullable(oldValueAttribute.isNullable());
		attribute.setName(oldValueAttribute.getName());
		if (entity != null) {
			this.entity.setName(this.entity.getName());
		}
	}
}

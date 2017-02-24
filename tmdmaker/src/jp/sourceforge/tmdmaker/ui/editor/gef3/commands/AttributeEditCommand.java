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
package jp.sourceforge.tmdmaker.ui.editor.gef3.commands;

import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.Attribute;
import jp.sourceforge.tmdmaker.model.IAttribute;

import org.eclipse.gef.commands.Command;

/**
 * アトリビュート編集コマンド
 * 
 * @author nakaG
 * 
 */
public class AttributeEditCommand extends Command {
	/** 編集対象モデル */
	private IAttribute attribute;
	/** 編集後値 */
	private IAttribute editedValueAttribute;
	/** 編集前値 */
	private IAttribute oldValueAttribute;
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
			IAttribute editedValueAttribute) {
		this(attribute, editedValueAttribute, null);
	}

	/**
	 * コンストラクタ
	 * 
	 * @param original
	 *            編集対象モデル
	 * @param editedValueAttribute
	 *            編集後値
	 * @param entity
	 *            親モデル
	 */
	public AttributeEditCommand(IAttribute original,
			IAttribute editedValueAttribute, AbstractEntityModel entity) {
		this.attribute = original;
		this.editedValueAttribute = editedValueAttribute;
		this.oldValueAttribute = original.getCopy();
		this.entity = entity;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		editedValueAttribute.copyTo(attribute);
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
		oldValueAttribute.copyTo(attribute);
		if (entity != null) {
			this.entity.setName(this.entity.getName());
		}
	}
}

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
import jp.sourceforge.tmdmaker.model.IAttribute;

import org.eclipse.gef.commands.Command;

/**
 * アトリビュート追加Command。未使用。
 * 
 * @author nakaG
 * 
 */
public class AttributeAddCommand extends Command {
	private AbstractEntityModel entity;
	private IAttribute attribute;

	/**
	 * コンストラクタ
	 * 
	 * @param entity
	 *            エンティティ系モデル
	 * @param attribute
	 *            追加アトリビュート
	 */
	public AttributeAddCommand(AbstractEntityModel entity, IAttribute attribute) {
		this.entity = entity;
		this.attribute = attribute;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		// entity.addAttribute(attribute);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {

	}

}

/*
 * Copyright 2009-2019 TMD-Maker Project <https://www.tmdmaker.org>
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
package org.tmdmaker.ui.editor.gef3.commands;

import org.eclipse.gef.commands.Command;
import org.tmdmaker.core.model.AbstractEntityModel;
import org.tmdmaker.core.model.Attribute;

/**
 * アトリビュートの所属先を別のエンティティ系モデルへ移動するCommand
 * 
 * @author nakaG
 * 
 */
public class AttributeTransferCommand extends Command {
	private Attribute attributeToMove;
	private AbstractEntityModel entityFrom;
	private AbstractEntityModel entityTo;
	private int indexFrom;
	private int indexTo;

	/**
	 * コンストラクタ
	 * 
	 * @param attributeToMove
	 * @param entityFrom
	 * @param indexFrom
	 * @param entityTo
	 * @param indexTo
	 */
	public AttributeTransferCommand(Attribute attributeToMove,
			AbstractEntityModel entityFrom, int indexFrom,
			AbstractEntityModel entityTo, int indexTo) {
		this.entityFrom = entityFrom;
		this.attributeToMove = attributeToMove;
		this.indexFrom = indexFrom;
		this.entityTo = entityTo;
		this.indexTo = indexTo;

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		entityFrom.removeAttribute(attributeToMove);
		entityTo.addAttribute(indexTo, attributeToMove);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		entityTo.removeAttribute(attributeToMove);
		entityFrom.addAttribute(indexFrom, attributeToMove);
	}

}

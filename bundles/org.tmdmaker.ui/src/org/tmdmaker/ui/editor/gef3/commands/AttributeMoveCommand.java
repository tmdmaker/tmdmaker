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
package org.tmdmaker.ui.editor.gef3.commands;

import org.eclipse.gef.commands.Command;
import org.tmdmaker.core.model.AbstractEntityModel;
import org.tmdmaker.core.model.Attribute;

/**
 * アトリビュートの同一エンティティ系モデル内での並び順を変更するCommand
 * 
 * @author nakaG
 * 
 */
public class AttributeMoveCommand extends Command {
	private AbstractEntityModel parent;
	private Attribute child;
	private int oldIndex;
	private int newIndex;
	
	public AttributeMoveCommand(Attribute child, AbstractEntityModel parent,
			int oldIndex, int newIndex) {
		super();
		this.child = child;
		this.parent = parent;
		if (newIndex > oldIndex)
			newIndex--;
		this.oldIndex = oldIndex;
		this.newIndex = newIndex;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		parent.removeAttribute(child);
		parent.addAttribute(newIndex, child);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		parent.removeAttribute(child);
		parent.addAttribute(oldIndex, child);
	}

}

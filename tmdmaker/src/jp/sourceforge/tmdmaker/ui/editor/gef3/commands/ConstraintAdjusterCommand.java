/*
 * Copyright 2009-2017 TMD-Maker Project <http://tmdmaker.osdn.jp/>
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

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.commands.Command;

import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.AbstractRelationship;
import jp.sourceforge.tmdmaker.model.ConnectableElement;
import jp.sourceforge.tmdmaker.ui.editor.draw2d.ConstraintConverter;

/**
 * モデル生成時に位置を調整するCommand
 * 
 * @author nakag
 *
 */
public class ConstraintAdjusterCommand extends Command {
	private AbstractEntityModel source;
	private ConnectableElement target;
	private Rectangle oldConstraint;
	private int x;
	private int y;

	public ConstraintAdjusterCommand(AbstractRelationship connection, int x, int y) {
		this.source = connection.getSource();
		this.target = connection.getTarget();
		this.x = x;
		this.y = y;
	}

	public ConstraintAdjusterCommand(AbstractEntityModel source, ConnectableElement target, int x,
			int y) {
		this.source = source;
		this.target = target;
		this.x = x;
		this.y = y;
	}

	/**
	 * 
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		oldConstraint = ConstraintConverter.getRectangle(target);
		ConstraintConverter.setTranslatedConstraint(target, source, x, y);
	}

	/**
	 * 
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		ConstraintConverter.setConstraint(target, oldConstraint);
	}

}

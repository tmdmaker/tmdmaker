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
package org.tmdmaker.ui.editor.gef3.tools;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.tools.SelectionTool;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.tmdmaker.ui.editor.gef3.commands.ConstraintChangeCommand;
import org.tmdmaker.ui.editor.gef3.editparts.node.AbstractModelEditPart;

/**
 * カーソルキーでモデルを移動できるSelectionTool
 * 
 * @author nakaG
 * 
 */
public class MovableSelectionTool extends SelectionTool {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.tools.SelectionTool#handleKeyDown(org.eclipse.swt.events.KeyEvent)
	 */
	@Override
	protected boolean handleKeyDown(KeyEvent e) {
		Point p = calcMovePoint(e.keyCode);
		if (hasMoved(p)) {
			// カーソルキー操作の時だけモデルを移動させる
			doMove(p);
		}
		return super.handleKeyDown(e);
	}

	private Point calcMovePoint(int keyCode) {
		Point p = new Point(0, 0);
		switch (keyCode) {
		case SWT.ARROW_DOWN:
			p.y = 1;
			break;
		case SWT.ARROW_LEFT:
			p.x = -1;
			break;
		case SWT.ARROW_RIGHT:
			p.x = 1;
			break;
		case SWT.ARROW_UP:
			p.y = -1;
			break;
		default:
			break;
		}
		return p;
	}

	private void doMove(Point p) {
		CompoundCommand command = new CompoundCommand();

		for (Object selection : getCurrentViewer().getSelectedEditParts()) {
			if (selection instanceof AbstractModelEditPart) {
				AbstractModelEditPart<?> part = (AbstractModelEditPart<?>) selection;
				command.add(new ConstraintChangeCommand(part.getModel(), p.x, p.y));
			}
		}
		if (!command.isEmpty()) {
			executeCommand(command.unwrap());
		}
	}

	private boolean hasMoved(Point p) {
		return p.x != 0 || p.y != 0;
	}
}

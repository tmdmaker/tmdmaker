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
package jp.sourceforge.tmdmaker.tool;

import java.util.List;

import jp.sourceforge.tmdmaker.editpart.AbstractEntityEditPart;
import jp.sourceforge.tmdmaker.model.ModelElement;
import jp.sourceforge.tmdmaker.model.command.ModelConstraintChangeCommand;

import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.tools.SelectionTool;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;

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
	@SuppressWarnings("unchecked")
	@Override
	protected boolean handleKeyDown(KeyEvent e) {
		int x = 0;
		int y = 0;
		boolean movable = false;
		if (e.keyCode == SWT.ARROW_DOWN) {
			y = 1;
			movable = true;
		} else if (e.keyCode == SWT.ARROW_LEFT) {
			x = -1;
			movable = true;
		} else if (e.keyCode == SWT.ARROW_RIGHT) {
			x = 1;
			movable = true;
		} else if (e.keyCode == SWT.ARROW_UP) {
			y = -1;
			movable = true;
		}
		List selections = getCurrentViewer().getSelectedEditParts();
		// カーソルキー操作の時だけモデルを移動させる
		if (!selections.isEmpty() && movable) {
			CompoundCommand command = new CompoundCommand();
			for (Object selection : selections) {
				if (selection instanceof AbstractEntityEditPart) {
					AbstractEntityEditPart part = (AbstractEntityEditPart) selection;
					command.add(new ModelConstraintChangeCommand(
							(ModelElement) part.getModel(), x, y));
				}
			}

			executeCommand(command.unwrap());
		}
		return super.handleKeyDown(e);
	}

}

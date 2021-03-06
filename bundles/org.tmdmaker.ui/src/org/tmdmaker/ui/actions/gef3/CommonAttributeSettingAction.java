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
package org.tmdmaker.ui.actions.gef3;

import java.util.List;

import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.commands.Command;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.Dialog;
import org.tmdmaker.core.model.Diagram;
import org.tmdmaker.core.model.IAttribute;
import org.tmdmaker.ui.Messages;
import org.tmdmaker.ui.dialogs.CommonAttributeDialog;

/**
 * 共通アトリビュート設定Action
 * 
 * @author nakaG
 * 
 */
public class CommonAttributeSettingAction extends Action {
	/** ビューワ */
	private GraphicalViewer viewer;
	/** ID */
	public static final String ID = "CommonAtributeSettingAction"; //$NON-NLS-1$

	/**
	 * コンストラクタ
	 * 
	 * @param viewer
	 *            ビューワ
	 */
	public CommonAttributeSettingAction(GraphicalViewer viewer) {
		super();
		this.viewer = viewer;
		setText(Messages.CommonAttributeSettings);
		setId(ID);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
		Diagram diagram = (Diagram) viewer.getContents().getModel();
		CommonAttributeDialog dialog = new CommonAttributeDialog(viewer.getControl().getShell(),
				diagram.getCommonAttributes());
		if (dialog.open() == Dialog.OK) {
			viewer.getEditDomain().getCommandStack()
					.execute(new CommonAttributeEditCommand(diagram, dialog.getEditedAttributes()));
		}
	}

	/**
	 * 共通属性編集Command
	 * 
	 * @author nakaG
	 * 
	 */
	private static class CommonAttributeEditCommand extends Command {
		private Diagram diagram;
		private List<IAttribute> newAttributes;
		private List<IAttribute> oldAttributes;

		/**
		 * コンストラクタ
		 * 
		 * @param diagram
		 *            TMダイアグラム
		 * @param attributes
		 *            共通属性
		 */
		public CommonAttributeEditCommand(Diagram diagram, List<IAttribute> attributes) {
			this.diagram = diagram;
			this.oldAttributes = diagram.getCommonAttributes();
			this.newAttributes = attributes;
		}

		/**
		 * 
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.gef.commands.Command#execute()
		 */
		@Override
		public void execute() {
			diagram.setCommonAttributes(newAttributes);
		}

		/**
		 * 
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.gef.commands.Command#undo()
		 */
		@Override
		public void undo() {
			diagram.setCommonAttributes(oldAttributes);
		}

	}
}

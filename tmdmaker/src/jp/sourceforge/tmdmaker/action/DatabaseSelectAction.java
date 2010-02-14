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
package jp.sourceforge.tmdmaker.action;

import jp.sourceforge.tmdmaker.dialog.DatabaseSelectDialog;
import jp.sourceforge.tmdmaker.model.Diagram;

import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.commands.Command;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.Dialog;

/**
 * データベース選択Action
 * 
 * @author nakaG
 * 
 */
public class DatabaseSelectAction extends Action {
	/** ビューワ */
	private GraphicalViewer viewer;
	/** ID */
	public static final String ID = "DatabaseSelectAction";

	/**
	 * コンストラクタ
	 * 
	 * @param viewer
	 *            ビューワ
	 */
	public DatabaseSelectAction(GraphicalViewer viewer) {
		super();
		this.viewer = viewer;
		setText("データベースを選択");
		setId(ID);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
		System.out.println("database select");
		try {
			Diagram diagram = (Diagram) viewer.getContents().getModel();

			DatabaseSelectDialog dialog = new DatabaseSelectDialog(viewer
					.getControl().getShell(), diagram.getDatabaseName());
			if (dialog.open() == Dialog.OK) {
				viewer.getEditDomain().getCommandStack().execute(
						new DatabaseChangeCommand(diagram, dialog
								.getSelectedDatabaseName()));
			}
		} catch (Throwable t) {
			System.out.println(t);
		}
	}

	/**
	 * データベース変更Command
	 * 
	 * @author nakaG
	 * 
	 */
	private static class DatabaseChangeCommand extends Command {
		/** 変更対象 */
		private Diagram diagram;
		/** 変更前データベース */
		private String oldDatabaseName;
		/** 変更後データベース */
		private String newDatabaseName;

		/**
		 * コンストラクタ
		 * 
		 * @param diagram
		 *            変更対象
		 * @param newDatabaseName
		 *            変更後データベース
		 */
		public DatabaseChangeCommand(Diagram diagram, String newDatabaseName) {
			this.diagram = diagram;
			this.oldDatabaseName = diagram.getDatabaseName();
			this.newDatabaseName = newDatabaseName;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.gef.commands.Command#execute()
		 */
		@Override
		public void execute() {
			diagram.setDatabaseName(newDatabaseName);
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.gef.commands.Command#undo()
		 */
		@Override
		public void undo() {
			diagram.setDatabaseName(oldDatabaseName);
		}
	}
}

/*
 * Copyright 2009-2016 TMD-Maker Project <http://tmdmaker.osdn.jp/>
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
package jp.sourceforge.tmdmaker.dialog;

import jp.sourceforge.tmdmaker.Messages;
import jp.sourceforge.tmdmaker.dialog.component.DatabaseSettingPanel;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

/**
 * データベース変更ダイアログ
 * 
 * @author nakaG
 * 
 */
public class DatabaseSelectDialog extends Dialog {
	/** データベース選択用 */
	private DatabaseSettingPanel panel;
	/** 変更前データベース名 */
	private String originalDatabaseName;
	/** 変更後データベース名 */
	private String selectedDatabaseName;

	/**
	 * コンストラクタ
	 * 
	 * @param parentShell
	 *            親
	 * @param originalDatabaseName
	 *            変更前データベース名
	 */
	public DatabaseSelectDialog(Shell parentShell, String originalDatabaseName) {
		super(parentShell);
		if (originalDatabaseName == null) {
			this.originalDatabaseName = ""; //$NON-NLS-1$
		} else {
			this.originalDatabaseName = originalDatabaseName;
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		getShell().setText(Messages.DatabaseSelectDialog_1);
		Composite composite = new Composite(parent, SWT.NULL);

		panel = new DatabaseSettingPanel(composite, SWT.NULL);
		panel.initializeValue(originalDatabaseName);

		composite.pack();
		return composite;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#okPressed()
	 */
	@Override
	protected void okPressed() {
		selectedDatabaseName = panel.getSelectedDatabaseName();
		super.okPressed();
	}

	/**
	 * @return the selectedDatabaseName
	 */
	public String getSelectedDatabaseName() {
		return selectedDatabaseName;
	}

}

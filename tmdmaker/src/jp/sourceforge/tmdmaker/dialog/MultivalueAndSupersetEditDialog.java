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
package jp.sourceforge.tmdmaker.dialog;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * 多値のANDの概念的スーパーセット編集ダイアログ
 * 
 * @author nakaG
 * 
 */
public class MultivalueAndSupersetEditDialog extends Dialog {
	/** 名称入力欄 */
	private Text inputNameText;
	private String inputName;
	private String originalName;

	/**
	 * コンストラクタ
	 * 
	 * @param parentShell
	 *            親
	 */
	public MultivalueAndSupersetEditDialog(Shell parentShell,
			String originalName) {
		super(parentShell);
		this.originalName = originalName;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		getShell().setText("みなしスーパーセット編集");

		Composite composite = new Composite(parent, SWT.NULL);
		composite.setLayout(new GridLayout(2, false));
		GridData gridData = new GridData(GridData.FILL_BOTH);
		composite.setLayoutData(gridData);
		Label label = new Label(composite, SWT.NULL);
		label.setText("名称");
		inputNameText = new Text(composite, SWT.BORDER);
		gridData = new GridData(GridData.FILL_BOTH);
		gridData.widthHint = 100;
		inputNameText.setLayoutData(gridData);
		initializeValue();
		return composite;
	}

	private void initializeValue() {
		inputNameText.setText(originalName);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#okPressed()
	 */
	@Override
	protected void okPressed() {
		inputName = inputNameText.getText();
		super.okPressed();
	}

	public String getInputName() {
		return inputName;
	}
}

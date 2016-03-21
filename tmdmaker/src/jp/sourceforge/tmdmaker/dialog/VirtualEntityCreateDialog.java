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
import jp.sourceforge.tmdmaker.model.VirtualEntityType;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * みなしエンティティ作成ダイアログ
 * 
 * @author nakaG
 * 
 */
public class VirtualEntityCreateDialog extends Dialog {
	/** VE名入力欄 */
	private Text virtualEntityName;
	private String inputVirtualEntityName;
	private VirtualEntityType inputVirtualEntityType;
	private ModifyListener listener = new ModifyListener() {

		@Override
		public void modifyText(ModifyEvent e) {
			Text t = (Text) e.getSource();
			String name = t.getText();
			Button okButton = getButton(IDialogConstants.OK_ID);
			if (okButton != null) {
				okButton.setEnabled(name.length() != 0);
			}
		}
	};
	/** 種別設定用 */
	private Combo typeCombo;
	private GridData gridData_1;

	/**
	 * コンストラクタ
	 * 
	 * @param parentShell
	 *            親
	 */
	public VirtualEntityCreateDialog(Shell parentShell) {
		super(parentShell);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		getShell().setText(Messages.CreateVirtualEntity);

		Composite composite = new Composite(parent, SWT.NULL);
		GridLayout gl_composite = new GridLayout(3, false);
		gl_composite.verticalSpacing = 3;
		gl_composite.horizontalSpacing = 3;
		composite.setLayout(gl_composite);
		GridData gridData = new GridData(GridData.FILL_BOTH);
		composite.setLayoutData(gridData);
		Label label = new Label(composite, SWT.NULL);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		label.setText(Messages.VirtualEntityName);
		gridData_1 = new GridData(GridData.FILL_BOTH);
		gridData_1.verticalAlignment = SWT.CENTER;
		gridData_1.grabExcessVerticalSpace = false;
		gridData_1.widthHint = 100;
		virtualEntityName = new Text(composite, SWT.BORDER);
		virtualEntityName.setLayoutData(gridData_1);
		virtualEntityName.addModifyListener(listener);
		
		typeCombo = new Combo(composite, SWT.READ_ONLY);
		typeCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		typeCombo.add(Messages.Normal);
		typeCombo.add(Messages.ResourceType);
		typeCombo.add(Messages.EventType);
		typeCombo.select(0);
		
		composite.pack();
		return composite;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#createButtonsForButtonBar(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		super.createButtonsForButtonBar(parent);
		Button okButton = getButton(IDialogConstants.OK_ID);
		okButton.setEnabled(false);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#okPressed()
	 */
	@Override
	protected void okPressed() {
		inputVirtualEntityName = virtualEntityName.getText();
		inputVirtualEntityType = VirtualEntityType.values()[typeCombo
		                            						.getSelectionIndex()];
		super.okPressed();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#close()
	 */
	@Override
	public boolean close() {
		virtualEntityName.removeModifyListener(listener);
		return super.close();
	}

	public String getInputVirtualEntityName() {
		return inputVirtualEntityName;
	}

	/**
	 * @return the inputVirtualEntityType
	 */
	public VirtualEntityType getInputVirtualEntityType() {
		return inputVirtualEntityType;
	}
	
}

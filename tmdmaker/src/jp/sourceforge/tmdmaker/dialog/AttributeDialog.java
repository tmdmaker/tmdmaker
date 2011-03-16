/*
 * Copyright 2009-2011 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
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

import jp.sourceforge.tmdmaker.dialog.component.AttributePanel;
import jp.sourceforge.tmdmaker.dialog.model.EditAttribute;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * アトリビュート編集ダイアログ
 * 
 * @author nakaG
 * 
 */
public class AttributeDialog extends Dialog {
	private EditAttribute original;
	private AttributePanel panel;
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

	/**
	 * コンストラクタ
	 * 
	 * @param parentShell
	 *            親
	 * @param original
	 *            編集対象アトリビュート
	 */
	public AttributeDialog(Shell parentShell, EditAttribute original) {
		super(parentShell);
		this.original = original;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		getShell().setText("アトリビュート編集");
		Composite composite = new Composite(parent, SWT.NULL);
		panel = new AttributePanel(composite, SWT.NULL);
		panel.initializeValue(original);
		panel.addNameModifyListener(listener);

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
		okButton.setEnabled(original.getName().length() != 0);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#okPressed()
	 */
	@Override
	protected void okPressed() {
		original.setDerivationRule(panel.getDerivationRule());
		original.setDerivation(panel.isDerivation());
		original.setDescription(panel.getDescription());
		original.setLock(panel.getLock());
		original.setName(panel.getInputName());
		original.setValidationRule(panel.getValidationRule());
		original.setImplementName(panel.getImplementName());
		original.setDataType(panel.getDataType());
		original.setSize(panel.getPresition());
		original.setScale(panel.getScale());
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
		panel.removeNameModifyListener(listener);
		return super.close();
	}

	/**
	 * @return the editedValue
	 */
	public EditAttribute getEditedValue() {
		return original;
	}

}

/*
 * Copyright 2009 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
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
import jp.sourceforge.tmdmaker.model.EditAttribute;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

/**
 * アトリビュート編集ダイアログ
 * 
 * @author nakaG
 * 
 */
public class AttributeDialog extends Dialog {
	private EditAttribute original;
	// private EditAttribute editedValue;
	private AttributePanel panel;

	/**
	 * コンストラクタ
	 * 
	 * @param parentShell
	 *            親
	 */
	public AttributeDialog(Shell parentShell) {
		super(parentShell);
	}

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
		return composite;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#okPressed()
	 */
	@Override
	protected void okPressed() {
		// editedValue = new EditAttribute();
		// editedValue.setOriginalAttribute(original.getOriginalAttribute());
		// // editedValue.setDataType(panel.getDataType());
		// editedValue.setDerivationRule(panel.getDerivationRule());
		// editedValue.setDescription(panel.getDescription());
		// editedValue.setLock(panel.getLock());
		// editedValue.setName(panel.getName());
		// editedValue.setValidationRule(panel.getValidationRule());

		// editedValue.setDataType(panel.getDataType());
		original.setDerivationRule(panel.getDerivationRule());
		original.setDescription(panel.getDescription());
		original.setLock(panel.getLock());
		original.setName(panel.getInputName());
		original.setValidationRule(panel.getValidationRule());

		super.okPressed();
	}

	/**
	 * @return the editedValue
	 */
	public EditAttribute getEditedValue() {
		return original;
	}

}

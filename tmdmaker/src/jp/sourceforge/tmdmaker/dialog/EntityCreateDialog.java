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

import jp.sourceforge.tmdmaker.dialog.component.EntityNameAndTypeSettingPanel;
import jp.sourceforge.tmdmaker.model.EditAttribute;
import jp.sourceforge.tmdmaker.model.EntityType;
import jp.sourceforge.tmdmaker.model.Identifier;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

/**
 * エンティティ新規作成ダイアログ
 * 
 * @author nakaG
 * 
 */
public class EntityCreateDialog extends Dialog {
	/** エンティティ名称 */
	private String inputEntityName;
	/** 類別 */
	private EntityType inputEntityType = EntityType.RESOURCE;
	/** エンティティ名称・種類設定用パネル */
	private EntityNameAndTypeSettingPanel panel;
	/** 個体指定子 */
	private Identifier inputIdentifier;

	/**
	 * コンストラクタ
	 * 
	 * @param parentShell
	 *            親
	 */
	public EntityCreateDialog(Shell parentShell) {
		super(parentShell);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		getShell().setText("エンティティ新規作成");

		Composite composite = new Composite(parent, SWT.NULL);

		panel = new EntityNameAndTypeSettingPanel(composite, SWT.NULL);
		panel.setEditIdentifier(new EditAttribute());
		// panel.setInitialFocus();

		return composite;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#okPressed()
	 */
	@Override
	protected void okPressed() {
		this.inputEntityType = this.panel.getSelectedType();
		this.inputEntityName = this.panel.getEntityName();
		this.panel.getEditIdentifier();
		this.inputIdentifier = new Identifier(this.panel.getIdentifierName());
		EditAttribute editIdentifier = this.panel.getEditIdentifier();
		editIdentifier.copyTo(this.inputIdentifier);

		if (validate()) {
			super.okPressed();
		} else {

			return;
		}
	}

	/**
	 * ダイアログ検証
	 * 
	 * @return 必須事項が全て入力されている場合にtrueを返す
	 */
	private boolean validate() {
		String inputIdentifierName = this.inputIdentifier.getName();
		return inputIdentifierName != null && inputIdentifierName.length() > 0
				&& this.inputEntityName != null
				&& this.inputEntityName.length() > 0;
	}

	/**
	 * @return the inputEntityType
	 */
	public EntityType getInputEntityType() {
		return inputEntityType;
	}

	/**
	 * @return the inputEntityName
	 */
	public String getInputEntityName() {
		return inputEntityName;
	}

	/**
	 * @return the inputIdentifier
	 */
	public Identifier getInputIdentifier() {
		return inputIdentifier;
	}

}
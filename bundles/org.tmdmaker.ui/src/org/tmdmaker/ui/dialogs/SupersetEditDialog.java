/*
 * Copyright 2009-2019 TMD-Maker Project <https://tmdmaker.osdn.jp/>
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
package org.tmdmaker.ui.dialogs;

import java.beans.PropertyChangeEvent;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.tmdmaker.core.model.AbstractEntityModel;
import org.tmdmaker.core.model.VirtualSuperset;
import org.tmdmaker.ui.Messages;
import org.tmdmaker.ui.dialogs.components.ImplementInfoSettingPanel;
import org.tmdmaker.ui.dialogs.model.EditTable;

/**
 * (多値のANDとみなし)スーパーセット編集ダイアログ
 * 
 * @author nakaG
 * 
 */
public class SupersetEditDialog extends ModelEditDialog<VirtualSuperset> {
	/** 名称入力欄 */
	private Text inputNameText;
	/** 実装可否設定用 */
	private ImplementInfoSettingPanel panel;
	private GridData gridData_1;

	/**
	 * コンストラクタ
	 * 
	 * @param parentShell
	 *            親
	 * @param original
	 *            編集元モデル
	 */
	public SupersetEditDialog(Shell parentShell, AbstractEntityModel original) {
		super(parentShell);
		entity = new EditTable(original);
		entity.addPropertyChangeListener(this);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
	 */
	@Override
	public void propertyChange(PropertyChangeEvent arg0) {
		// do nothing
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		getShell().setText(Messages.EditVirtualSuperset);

		Composite composite = new Composite(parent, SWT.NULL);
		composite.setLayout(new GridLayout(2, false));
		GridData gridData = new GridData(GridData.FILL_BOTH);
		composite.setLayoutData(gridData);
		Label label = new Label(composite, SWT.NULL);
		label.setText(Messages.ModelName);
		inputNameText = new Text(composite, SWT.BORDER);
		inputNameText.setText(entity.getName());
		inputNameText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				entity.setName(inputNameText.getText());
			}
		});
		gridData_1 = new GridData(GridData.FILL_BOTH);
		gridData_1.grabExcessVerticalSpace = false;
		gridData_1.widthHint = 100;
		inputNameText.setLayoutData(gridData_1);

		gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		panel = new ImplementInfoSettingPanel(composite, SWT.NULL, entity);
		panel.setLayoutData(gridData);

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
		editedValue = entity.createEditedModel();

		super.okPressed();
	}
}

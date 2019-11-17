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
package org.tmdmaker.ui.dialogs.components;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.tmdmaker.ui.Messages;
import org.tmdmaker.ui.dialogs.AttributeDialog;
import org.tmdmaker.ui.dialogs.model.EditAttribute;
import org.tmdmaker.ui.dialogs.model.EditDetail;

/**
 * Detail identifier setting panel.
 * 
 * @author nakag
 *
 */
public class DetailIdentifierSettingPanel extends Composite {
	private EditDetail detail;
	private Label identifierLabel = null;
	private Text identifierText = null;
	private Button descButton = null;
	private Button isDetailIdentifierEnabledCheckBox = null;

	public DetailIdentifierSettingPanel(Composite parent, int style, EditDetail entity) {
		super(parent, style);
		this.detail = entity;
		initialize();
	}

	private void initialize() {
		GridData gridData1 = new GridData();
		gridData1.grabExcessHorizontalSpace = true;
		gridData1.horizontalAlignment = GridData.FILL;
		gridData1.widthHint = 60;
		gridData1.verticalAlignment = GridData.CENTER;
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.widthHint = -1;
		gridData.verticalAlignment = GridData.CENTER;
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 3;
		identifierLabel = new Label(this, SWT.NONE);
		identifierLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		identifierLabel.setText(Messages.Identifier);
		identifierText = new Text(this, SWT.BORDER);
		identifierText.setLayoutData(gridData);
		identifierText.addModifyListener(new org.eclipse.swt.events.ModifyListener() {
			public void modifyText(org.eclipse.swt.events.ModifyEvent e) {
				detail.setIdentifierName(((Text) e.widget).getText());
			}
		});
		descButton = new Button(this, SWT.NONE);
		descButton.setText(Messages.DescriptionButton);
		descButton.setLayoutData(gridData1);
		descButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			@Override
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				AttributeDialog dialog = new AttributeDialog(getShell(),
						detail.getEditIdentifier());
				if (dialog.open() == Dialog.OK) {
					EditAttribute edited = dialog.getEditedValue();
					if (edited.isEdited()) {
						detail.updateEditIdentifier(edited);
						identifierText.setText(edited.getName());
					}
				}
			}
		});
		this.setLayout(gridLayout);
		updateValue();
		setSize(new Point(355, 66));
		
		isDetailIdentifierEnabledCheckBox = new Button(this, SWT.CHECK);
		isDetailIdentifierEnabledCheckBox.setSelection(detail.isDetailIdentifierEnabled());
		isDetailIdentifierEnabledCheckBox.setEnabled(detail.canDisableDetailIdentifierEnabled());
		isDetailIdentifierEnabledCheckBox.setText(Messages.IsDetailIdentifierEnabled);
		isDetailIdentifierEnabledCheckBox.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			@Override
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				boolean enabled = isDetailIdentifierEnabledCheckBox.getSelection();
				detail.setDetailIdentifierEnabled(enabled);
				identifierText.setEnabled(enabled);
				descButton.setEnabled(enabled);
			}
		});
		
		identifierText.setEnabled(detail.isDetailIdentifierEnabled());
		descButton.setEnabled(detail.isDetailIdentifierEnabled());
	}

	public void updateValue() {
		identifierText.setText(detail.getEditIdentifier().getName());
	}

}

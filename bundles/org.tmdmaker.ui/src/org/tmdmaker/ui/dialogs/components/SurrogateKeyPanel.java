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

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.tmdmaker.ui.Messages;
import org.tmdmaker.ui.dialogs.model.EditImplementEntity;

/**
 * Surrogate key panel.
 * 
 * @author nakag
 *
 */
public class SurrogateKeyPanel extends Composite {
	private EditImplementEntity entity;
	private Button useSurrogateKeyCheckBox = null;
	private Label nameLabel = null;
	private Text inputNameText = null;

	public SurrogateKeyPanel(Composite parent, int style, EditImplementEntity entity) {
		super(parent, style);
		this.entity = entity;
		initialize();
	}

	private void initialize() {
		GridData gridData1 = new GridData();
		gridData1.grabExcessHorizontalSpace = true;
		gridData1.horizontalAlignment = GridData.FILL;
		gridData1.widthHint = 150;
		gridData1.verticalAlignment = GridData.CENTER;
		GridData gridData = new GridData();
		gridData.horizontalSpan = 2;
		gridData.verticalAlignment = GridData.CENTER;
		gridData.horizontalAlignment = SWT.LEFT;
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		useSurrogateKeyCheckBox = new Button(this, SWT.CHECK);
		useSurrogateKeyCheckBox.setText(Messages.AddSurrogateKey);
		useSurrogateKeyCheckBox.setLayoutData(gridData);
		useSurrogateKeyCheckBox.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			@Override
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				entity.setSurrogateKeyEnabled(useSurrogateKeyCheckBox.getSelection());
			}
		});
		nameLabel = new Label(this, SWT.NONE);
		nameLabel.setText(Messages.ModelName);
		inputNameText = new Text(this, SWT.BORDER);
		inputNameText.setLayoutData(gridData1);
		inputNameText.addModifyListener(new org.eclipse.swt.events.ModifyListener() {
			public void modifyText(org.eclipse.swt.events.ModifyEvent e) {
				entity.setSurrogateKeyName(inputNameText.getText());
			}
		});
		this.setLayout(gridLayout);
		setSize(new Point(195, 52));
	}

	public void refreshVisual() {
		if (entity.isSurrogateKeyEnabled()) {
			useSurrogateKeyCheckBox.setSelection(true);
			inputNameText.setEnabled(true);
			inputNameText.setText(entity.getSurrogateKeyName());
		} else {
			useSurrogateKeyCheckBox.setSelection(false);
			inputNameText.setText(entity.getSurrogateKeyName());
			inputNameText.setEnabled(false);
		}
	}
}

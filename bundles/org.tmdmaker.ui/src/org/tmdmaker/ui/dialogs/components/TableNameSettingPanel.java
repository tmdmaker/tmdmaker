/*
 * Copyright 2009-2019 TMD-Maker Project <https://www.tmdmaker.org>
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
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.tmdmaker.ui.Messages;
import org.tmdmaker.ui.dialogs.model.EditTable;

/**
 * Table name setting panel.
 * 
 * @author nakag
 *
 */
public class TableNameSettingPanel extends Composite {
	private EditTable entity;
	private Label nameLabel = null;
	private Text nameText = null;

	public TableNameSettingPanel(Composite parent, int style, EditTable entity) {
		super(parent, style);
		this.entity = entity;
		initialize();
	}

	private void initialize() {
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.verticalAlignment = GridData.CENTER;
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		nameLabel = new Label(this, SWT.NONE);
		nameLabel.setText(Messages.ModelName);
		nameText = new Text(this, SWT.BORDER);
		nameText.setLayoutData(gridData);
		nameText.addModifyListener(new org.eclipse.swt.events.ModifyListener() {
			public void modifyText(org.eclipse.swt.events.ModifyEvent e) {
				Text t = (Text) e.widget;
				entity.setName(t.getText());
			}
		});
		this.setLayout(gridLayout);
		this.nameText.setText(entity.getName());
		setSize(new Point(315, 30));
	}
}

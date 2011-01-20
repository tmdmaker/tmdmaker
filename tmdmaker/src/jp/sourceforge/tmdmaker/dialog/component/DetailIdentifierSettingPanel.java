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
package jp.sourceforge.tmdmaker.dialog.component;

import jp.sourceforge.tmdmaker.dialog.AttributeDialog;
import jp.sourceforge.tmdmaker.dialog.model.EditAttribute;
import jp.sourceforge.tmdmaker.dialog.model.EditEntity;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class DetailIdentifierSettingPanel extends Composite {
	private EditEntity entity;
	private Label identifierLabel = null;
	private Text identifierText = null;
	private Button descButton = null;

	public DetailIdentifierSettingPanel(Composite parent, int style,
			EditEntity entity) {
		super(parent, style);
		this.entity = entity;
		initialize();
	}

	private void initialize() {
		GridData gridData1 = new GridData();
		gridData1.horizontalAlignment = GridData.FILL;
		gridData1.widthHint = 60;
		gridData1.grabExcessHorizontalSpace = false;
		gridData1.verticalAlignment = GridData.CENTER;
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.widthHint = -1;
		gridData.verticalAlignment = GridData.CENTER;
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 3;
		identifierLabel = new Label(this, SWT.NONE);
		identifierLabel.setText("個体指定子");
		identifierText = new Text(this, SWT.BORDER);
		identifierText.setLayoutData(gridData);
		identifierText
				.addModifyListener(new org.eclipse.swt.events.ModifyListener() {
					public void modifyText(org.eclipse.swt.events.ModifyEvent e) {
						entity.setIdentifierName(((Text) e.widget).getText());
					}
				});
		descButton = new Button(this, SWT.NONE);
		descButton.setText("詳細");
		descButton.setLayoutData(gridData1);
		descButton
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(
							org.eclipse.swt.events.SelectionEvent e) {
						AttributeDialog dialog = new AttributeDialog(
								getShell(), entity.getEditIdentifier());
						if (dialog.open() == Dialog.OK) {
							EditAttribute edited = dialog.getEditedValue();
							if (edited.isEdited()) {
								entity.updateEditIdentifier(edited);
								identifierText.setText(edited.getName());
							}
						}
					}
				});
		this.setLayout(gridLayout);
		updateValue();
		setSize(new Point(321, 32));
	}

	public void updateValue() {
		identifierText.setText(entity.getEditIdentifier().getName());
	}

} // @jve:decl-index=0:visual-constraint="0,0"

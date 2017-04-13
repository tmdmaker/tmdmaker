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
package jp.sourceforge.tmdmaker.ui.dialogs.components;

import jp.sourceforge.tmdmaker.Messages;
import jp.sourceforge.tmdmaker.model.EntityType;
import jp.sourceforge.tmdmaker.model.rule.EntityRecognitionRule;
import jp.sourceforge.tmdmaker.ui.dialogs.AttributeDialog;
import jp.sourceforge.tmdmaker.ui.dialogs.models.EditAttribute;
import jp.sourceforge.tmdmaker.ui.dialogs.models.EditEntity;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class EntityNameAndIdentifierNameAndTypeSettingPanel extends Composite {
	private EditEntity entity;
	private Label identifierLabel = null;
	private Text identifierText = null;
	private Label typeLabel = null;
	private Combo typeCombo = null;
	private Button nameAutoCreateCheckBox = null;
	private Label nameLabel = null;
	private Text nameText = null;
	private Button descButton = null;

	public EntityNameAndIdentifierNameAndTypeSettingPanel(Composite parent, int style) {
		super(parent, SWT.NONE);
		initialize();
		this.nameAutoCreateCheckBox.setSelection(true);
		this.nameText.setEnabled(false);
		this.typeCombo.select(0);
	}

	public EntityNameAndIdentifierNameAndTypeSettingPanel(Composite parent, int style,
			EditEntity entity) {
		super(parent, style);
		this.entity = entity;
		initialize();
	}

	public void updateValue() {
		String identifierName = entity.getEditIdentifier().getName();
		String entityName = entity.getName();
		selectAutoCreateCheckBox(identifierName, entityName);
		this.identifierText.setText(identifierName);
		if (!nameAutoCreateCheckBox.getSelection()) {
			this.nameText.setText(entityName);
		}
		selectEntityTypeCombo(entity.getType());
		typeCombo.setEnabled(entity.isEntityTypeEditable());
	}

	private void selectAutoCreateCheckBox(String identifierName, String entityName) {
		String autoCreateEntityName = createEntityName(identifierName);
		if (autoCreateEntityName.equals(entityName)) {
			this.nameAutoCreateCheckBox.setSelection(true);
			this.nameText.setEnabled(false);
		} else {
			this.nameAutoCreateCheckBox.setSelection(false);
			this.nameText.setEnabled(true);
		}
	}

	private void selectEntityTypeCombo(EntityType type) {
		if (EntityType.RESOURCE.equals(type)) {
			this.typeCombo.select(0);
		} else if (EntityType.EVENT.equals(type)) {
			this.typeCombo.select(1);
		} else {
			this.typeCombo.select(2);
		}
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		GridData gridData11 = new GridData();
		gridData11.horizontalAlignment = GridData.FILL;
		gridData11.verticalAlignment = GridData.CENTER;
		GridData gridData10 = new GridData();
		gridData10.horizontalAlignment = SWT.CENTER;
		gridData10.verticalAlignment = GridData.CENTER;
		GridData gridData9 = new GridData();
		gridData9.horizontalSpan = 2;
		GridData gridData1 = new GridData();
		gridData1.verticalAlignment = GridData.CENTER;
		gridData1.horizontalAlignment = GridData.FILL;
		GridData gridData = new GridData();
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalAlignment = GridData.FILL;
		gridData.widthHint = -1;
		gridData.verticalAlignment = GridData.CENTER;
		nameAutoCreateCheckBox = new Button(this, SWT.CHECK);
		nameAutoCreateCheckBox.setText(Messages.AutomaticGenerateEntityName);
		nameAutoCreateCheckBox.setLayoutData(gridData9);
		typeLabel = new Label(this, SWT.NONE);
		typeLabel.setText(Messages.EntityType);
		typeLabel.setLayoutData(gridData10);
		nameLabel = new Label(this, SWT.NONE);
		nameLabel.setText(Messages.EntityName);
		nameText = new Text(this, SWT.BORDER);
		nameText.setLayoutData(gridData1);
		nameText.addModifyListener(new org.eclipse.swt.events.ModifyListener() {
			public void modifyText(org.eclipse.swt.events.ModifyEvent e) {
				Text t = (Text) e.widget;
				entity.setName(t.getText());
			}
		});
		createTypeCombo();
		nameAutoCreateCheckBox.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				Button b = (Button) e.widget;
				if (b.getSelection()) {
					nameText.setEnabled(false);
					nameText.setText(createEntityName(identifierText.getText()));
				} else {
					nameText.setEnabled(true);
				}
			}
		});
		identifierLabel = new Label(this, SWT.NONE);
		identifierLabel.setText(Messages.Identifier);
		identifierText = new Text(this, SWT.BORDER);
		identifierText.setLayoutData(gridData);
		identifierText.addModifyListener(new org.eclipse.swt.events.ModifyListener() {
			public void modifyText(org.eclipse.swt.events.ModifyEvent e) {
				Text t = (Text) e.widget;
				entity.setIdentifierName(t.getText());
				if (nameAutoCreateCheckBox.getSelection()) {
					String oldName = nameText.getText();
					String newName = createEntityName(t.getText());
					nameText.setText(newName);
					entity.updateTypeAttribute(oldName, newName);
				}
			}
		});
		descButton = new Button(this, SWT.NONE);
		descButton.setText(Messages.DescriptionButton);
		descButton.setLayoutData(gridData11);
		descButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				AttributeDialog dialog = new AttributeDialog(getShell(), entity.getEditIdentifier());
				if (dialog.open() == Dialog.OK) {
					EditAttribute edited = dialog.getEditedValue();
					if (edited.isEdited()) {
						entity.updateEditIdentifier(edited);
						identifierText.setText(edited.getName());
					}
				}
			}
		});
		GridLayout gridLayout = new GridLayout();
		gridLayout.marginLeft = 5;
		gridLayout.marginRight = 5;
		gridLayout.numColumns = 3;
		gridLayout.horizontalSpacing = 5;

		updateValue();
		this.setLayout(gridLayout);
		this.setSize(new Point(372, 88));
	}

	/**
	 * This method initializes typeCombo
	 *
	 */
	private void createTypeCombo() {
		typeCombo = new Combo(this, SWT.READ_ONLY);
		typeCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		typeCombo.addSelectionListener(new org.eclipse.swt.events.SelectionListener() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				entity.setType(getSelectedType());
			}

			public void widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent e) {
			}
		});
		typeCombo.add(Messages.Resource);
		typeCombo.add(Messages.Event);
		if (entity.isLaputa()) {
			typeCombo.add(Messages.Laputa);
		}
	}

	/**
	 * 
	 * @param identifierName
	 * @return
	 */
	private String createEntityName(String identifierName) {
		return EntityRecognitionRule.getInstance().generateEntityNameFromIdentifier(identifierName);
	}

	/**
	 * 
	 * @return the entityType
	 */
	private EntityType getSelectedType() {
		int selection = typeCombo.getSelectionIndex();
		if (selection == 0) {
			return EntityType.RESOURCE;
		} else if (selection == 1) {
			return EntityType.EVENT;
		} else {
			return EntityType.LAPUTA;
		}
	}

}

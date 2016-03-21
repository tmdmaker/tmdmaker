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
package jp.sourceforge.tmdmaker.dialog.component;

import jp.sourceforge.tmdmaker.Messages;
import jp.sourceforge.tmdmaker.dialog.AttributeDialog;
import jp.sourceforge.tmdmaker.dialog.model.EditAttribute;
import jp.sourceforge.tmdmaker.model.EntityType;
import jp.sourceforge.tmdmaker.model.rule.EntityRecognitionRule;

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

/**
 * Entity name and type setting panel.
 * 
 * @author nakag
 *
 */
public class EntityNameAndTypeSettingPanel extends Composite {
	private EditAttribute editIdentifier = null;
	private Label identifierLabel = null;
	private Text identifierText = null;
	private Label typeLabel = null;
	private Combo typeCombo = null;
	private Button nameAutoCreateCheckBox = null;
	private Label nameLabel = null;
	private Text nameText = null;
	private Button descButton = null;

	public EntityNameAndTypeSettingPanel(Composite parent, int style) {
		super(parent, style);
		initialize();
		this.nameAutoCreateCheckBox.setSelection(true);
		this.nameText.setEnabled(false);
		this.typeCombo.select(0);
	}

	public void initializeValue(String identifierName, String entityName, EntityType type) {
		setIdentifierNameText(identifierName);
		setEntityNameText(entityName);
		selectEntityTypeCombo(type);
		selectAutoCreateCheckBox(identifierName, entityName);
	}

	public void setEntityNameText(String entityName) {
		this.nameText.setText(entityName);
	}

	public void setIdentifierNameText(String identifierName) {
		this.identifierText.setText(identifierName);
	}

	public void selectAutoCreateCheckBox(String identifierName, String entityName) {
		String autoCreateEntityName = createEntityName(identifierName);
		if (autoCreateEntityName.equals(entityName)) {
			this.nameAutoCreateCheckBox.setSelection(true);
			this.nameText.setEnabled(false);
		} else {
			this.nameAutoCreateCheckBox.setSelection(false);
			this.nameText.setEnabled(true);
		}
	}

	public void selectEntityTypeCombo(EntityType type) {
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
		identifierLabel = new Label(this, SWT.NONE);
		identifierLabel.setText(Messages.Identifier);
		GridData gridData11 = new GridData();
		gridData11.horizontalAlignment = GridData.FILL;
		gridData11.verticalAlignment = GridData.CENTER;
		GridData gridData10 = new GridData();
		gridData10.horizontalAlignment = GridData.CENTER;
		gridData10.verticalAlignment = GridData.CENTER;
		GridData gridData9 = new GridData();
		gridData9.horizontalSpan = 2;
		GridData gridData1 = new GridData();
		gridData1.grabExcessHorizontalSpace = false;
		gridData1.verticalAlignment = GridData.CENTER;
		gridData1.horizontalAlignment = GridData.FILL;
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.widthHint = -1;
		gridData.verticalAlignment = GridData.CENTER;
		identifierText = new Text(this, SWT.BORDER);
		identifierText.setLayoutData(gridData);
		descButton = new Button(this, SWT.NONE);
		descButton.setText(Messages.DescriptionButton);
		descButton.setLayoutData(gridData11);
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
		identifierText.addModifyListener(new org.eclipse.swt.events.ModifyListener() {
			public void modifyText(org.eclipse.swt.events.ModifyEvent e) {
				Text t = (Text) e.widget;
				editIdentifier.setName(t.getText());
				if (nameAutoCreateCheckBox.getSelection()) {
					nameText.setText(createEntityName(t.getText()));
				}
			}
		});
		identifierText.addFocusListener(new org.eclipse.swt.events.FocusAdapter() {
			public void focusLost(org.eclipse.swt.events.FocusEvent e) {
				// typeCombo.setFocus();
			}
		});
		descButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				AttributeDialog dialog = new AttributeDialog(getShell(), editIdentifier);
				if (dialog.open() == Dialog.OK) {
					EditAttribute edited = dialog.getEditedValue();
					if (edited.isEdited()) {
						identifierText.setText(edited.getName());
					}
				}
			}
		});
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 3;
		gridLayout.horizontalSpacing = 5;
		this.setLayout(gridLayout);
		this.setSize(new Point(358, 97));
	}

	/**
	 * This method initializes typeCombo
	 *
	 */
	private void createTypeCombo() {
		typeCombo = new Combo(this, SWT.READ_ONLY);
		typeCombo.add(Messages.Resource);
		typeCombo.add(Messages.Event);
		typeCombo.add(Messages.Laputa);
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
	 * @return the entityName
	 */
	public String getEntityName() {
		return this.nameText.getText();
	}

	/**
	 * 
	 * @return the identifierName
	 */
	public String getIdentifierName() {
		return this.identifierText.getText();
	}

	/**
	 * 
	 * @return the entityType
	 */
	public EntityType getSelectedType() {
		int selection = typeCombo.getSelectionIndex();
		if (selection == 0) {
			return EntityType.RESOURCE;
		} else if (selection == 1) {
			return EntityType.EVENT;
		} else {
			return EntityType.LAPUTA;
		}
	}

	public void setEntityTypeComboEnabled(boolean enabled) {
		this.typeCombo.setEnabled(enabled);
	}

	/**
	 * @return the editIdentifier
	 */
	public EditAttribute getEditIdentifier() {
		return editIdentifier;
	}

	/**
	 * @param editIdentifier
	 *            the editIdentifier to set
	 */
	public void setEditIdentifier(EditAttribute editIdentifier) {
		this.editIdentifier = editIdentifier;
	}

}
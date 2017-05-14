/*
 * Copyright 2009-2017 TMD-Maker Project <http://tmdmaker.osdn.jp/>
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

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import jp.sourceforge.tmdmaker.Messages;
import jp.sourceforge.tmdmaker.model.EntityType;
import jp.sourceforge.tmdmaker.ui.dialogs.AttributeDialog;
import jp.sourceforge.tmdmaker.ui.dialogs.models.EditAttribute;
import jp.sourceforge.tmdmaker.ui.dialogs.models.EditEntity;

public class EntityEditSettingPanel extends Composite {
	private Label identifierLabel = null;
	private Text identifierText = null;
	private Label typeLabel = null;
	private Button nameAutoCreateCheckBox = null;
	private Label nameLabel = null;
	private Text nameText = null;
	private Button descButton = null;
	private Button btnRadioButton_0;
	private Button btnRadioButton_1;
	private Button btnRadioButton_2;
	private EditEntity entity;
	private Composite entityTypeComposite;

	public EntityEditSettingPanel(Composite parent, int style) {
		super(parent, SWT.NONE);
		initialize();
	}

	private void initializeEntityTypeRadio() {
		EntityType entityType = entity.getType();
		btnRadioButton_0.setSelection(EntityType.RESOURCE.equals(entityType));
		btnRadioButton_1.setSelection(EntityType.EVENT.equals(entityType));
		btnRadioButton_2.setSelection(EntityType.LAPUTA.equals(entityType));

		boolean editable = entity.isEntityTypeEditable();
		btnRadioButton_0.setEnabled(editable);
		btnRadioButton_1.setEnabled(editable);
		btnRadioButton_2.setEnabled(editable);
	}

	public EntityEditSettingPanel(Composite parent, int style, EditEntity entity) {
		super(parent, style);
		this.entity = entity;
		initialize();
	}

	public void updateValue() {
		String identifierName = entity.getEditIdentifier().getName();
		String entityName = entity.getName();
		selectAutoCreateCheckBox();
		this.identifierText.setText(identifierName);
		if (!nameAutoCreateCheckBox.getSelection()) {
			this.nameText.setText(entityName);
		}
		initializeEntityTypeRadio();
	}

	private void selectAutoCreateCheckBox() {
		if (entity.hasAutoCreateEntityName()) {
			this.nameAutoCreateCheckBox.setSelection(true);
			this.nameText.setEnabled(false);
		} else {
			this.nameAutoCreateCheckBox.setSelection(false);
			this.nameText.setEnabled(true);
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
		GridData gridData9 = new GridData();
		gridData9.horizontalSpan = 2;
		GridData gridData1 = new GridData();
		gridData1.grabExcessHorizontalSpace = true;
		gridData1.verticalAlignment = GridData.CENTER;
		gridData1.horizontalAlignment = GridData.FILL;
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.widthHint = -1;
		gridData.verticalAlignment = GridData.CENTER;
		nameAutoCreateCheckBox = new Button(this, SWT.CHECK);
		nameAutoCreateCheckBox.setText(Messages.AutomaticGenerateEntityName);
		nameAutoCreateCheckBox.setLayoutData(gridData9);
		new Label(this, SWT.NONE);
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
		nameAutoCreateCheckBox.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				Button b = (Button) e.widget;
				if (b.getSelection()) {
					nameText.setEnabled(false);
					nameText.setText(entity.generateEntityNameFromIdentifier());
				} else {
					nameText.setEnabled(true);
				}
			}
		});
		new Label(this, SWT.NONE);
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
					String newName = entity.generateEntityNameFromIdentifier();
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
				AttributeDialog dialog = new AttributeDialog(getShell(),
						entity.getEditIdentifier());
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

		typeLabel = new Label(this, SWT.NONE);
		typeLabel.setText(Messages.EntityType);

		entityTypeComposite = new Composite(this, SWT.NONE);
		RowLayout rl_composite = new RowLayout(SWT.HORIZONTAL);
		rl_composite.justify = true;
		entityTypeComposite.setLayout(rl_composite);
		entityTypeComposite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));

		btnRadioButton_0 = new Button(entityTypeComposite, SWT.RADIO);
		btnRadioButton_0.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				entity.setType(EntityType.RESOURCE);
			}
		});
		btnRadioButton_0.setText(Messages.Resource);

		btnRadioButton_1 = new Button(entityTypeComposite, SWT.RADIO);
		btnRadioButton_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				entity.setType(EntityType.EVENT);
			}
		});
		btnRadioButton_1.setText(Messages.Event);

		btnRadioButton_2 = new Button(entityTypeComposite, SWT.RADIO);
		btnRadioButton_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				entity.setType(EntityType.LAPUTA);
			}
		});
		btnRadioButton_2.setText(Messages.Laputa);

		updateValue();
		this.setLayout(gridLayout);
		this.setSize(new Point(382, 114));
	}
}

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
import jp.sourceforge.tmdmaker.model.parts.ModelName;
import jp.sourceforge.tmdmaker.ui.dialogs.AttributeDialog;
import jp.sourceforge.tmdmaker.ui.dialogs.models.EditAttribute;
import jp.sourceforge.tmdmaker.ui.dialogs.models.EntityCreationModel;

/**
 * Entity name and type setting panel.
 * 
 * @author nakag
 *
 */
public class EntityNameAndTypeSettingPanel extends Composite {
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
	private EntityCreationModel entity;

	public EntityNameAndTypeSettingPanel(Composite parent, int style) {
		this(parent, style, new EntityCreationModel());
	}

	public EntityNameAndTypeSettingPanel(Composite parent, int style, EntityCreationModel _entity) {
		super(parent, style);
		this.entity = _entity;
		initialize();

		initializeEntityTypeRadio();
	}

	private void initializeEntityTypeRadio() {
		btnRadioButton_0.setSelection(EntityType.RESOURCE.equals(entity.getEntityType()));
		btnRadioButton_1.setSelection(EntityType.EVENT.equals(entity.getEntityType()));
		btnRadioButton_2.setSelection(EntityType.LAPUTA.equals(entity.getEntityType()));
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		identifierLabel = new Label(this, SWT.NONE);
		identifierLabel.setText(Messages.Identifier);
		GridData gridData11 = new GridData();
		gridData11.grabExcessHorizontalSpace = true;
		gridData11.horizontalAlignment = GridData.FILL;
		gridData11.verticalAlignment = GridData.CENTER;
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
		new Label(this, SWT.NONE);
		nameLabel = new Label(this, SWT.NONE);
		nameLabel.setText(Messages.EntityName);
		nameText = new Text(this, SWT.BORDER);
		nameText.setLayoutData(gridData1);
		nameText.addModifyListener(new org.eclipse.swt.events.ModifyListener() {
			public void modifyText(org.eclipse.swt.events.ModifyEvent e) {
				Text t = (Text) e.widget;
				entity.setEntityName(new ModelName(t.getText()));
			}
		});
		nameAutoCreateCheckBox.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			@Override
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				Button b = (Button) e.widget;
				entity.setEntityNameAutoGeneration(b.getSelection());
				if (b.getSelection()) {
					nameText.setEnabled(false);
					nameText.setText(entity.getEntityName().getValue());
				} else {
					nameText.setEnabled(true);
				}
			}
		});
		identifierText.addModifyListener(new org.eclipse.swt.events.ModifyListener() {
			public void modifyText(org.eclipse.swt.events.ModifyEvent e) {
				Text t = (Text) e.widget;
				entity.setIdentifierName(t.getText());
				if (nameAutoCreateCheckBox.getSelection()) {
					nameText.setText(entity.getEntityName().getValue());
				}
			}
		});

		descButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			@Override
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				AttributeDialog dialog = new AttributeDialog(getShell(), entity.getIdentifier());
				if (dialog.open() == Dialog.OK) {
					EditAttribute edited = dialog.getEditedValue();
					if (edited.isEdited()) {
						identifierText.setText(edited.getName());
					}
				}
			}
		});
		this.nameAutoCreateCheckBox.setSelection(true);
		this.nameText.setEnabled(false);
		new Label(this, SWT.NONE);
		GridData gridData10 = new GridData();
		gridData10.horizontalAlignment = GridData.CENTER;
		gridData10.verticalAlignment = GridData.CENTER;
		typeLabel = new Label(this, SWT.NONE);
		typeLabel.setText(Messages.EntityType);
		typeLabel.setLayoutData(gridData10);

		Composite composite = new Composite(this, SWT.NONE);
		RowLayout rl_composite = new RowLayout(SWT.HORIZONTAL);
		rl_composite.justify = true;
		composite.setLayout(rl_composite);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 2, 1));

		btnRadioButton_0 = new Button(composite, SWT.RADIO);
		btnRadioButton_0.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				entity.setEntityType(EntityType.RESOURCE);
			}
		});
		btnRadioButton_0.setText(Messages.Resource);

		btnRadioButton_1 = new Button(composite, SWT.RADIO);
		btnRadioButton_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				entity.setEntityType(EntityType.EVENT);
			}
		});
		btnRadioButton_1.setText(Messages.Event);

		btnRadioButton_2 = new Button(composite, SWT.RADIO);
		btnRadioButton_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				entity.setEntityType(EntityType.LAPUTA);
			}
		});
		btnRadioButton_2.setText(Messages.Laputa);

		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 3;
		gridLayout.horizontalSpacing = 5;
		this.setLayout(gridLayout);
		this.setSize(new Point(350, 115));
	}
}
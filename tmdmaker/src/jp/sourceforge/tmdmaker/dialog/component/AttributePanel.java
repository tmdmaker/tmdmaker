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
import jp.sourceforge.tmdmaker.dialog.model.EditAttribute;
import jp.sourceforge.tmdmaker.model.StandardSQLDataType;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;

/**
 * Attribute panel.
 * 
 * @author nakag
 *
 */
public class AttributePanel extends Composite {

	private Label inputNameLabel = null;
	private Text inputNameText = null;
	private Label descriptionLabel = null;
	private Text validationRuleTextArea = null;
	private Label validationRuleLabel = null;
	private Label lockLabel = null;
	private Text lockTextArea = null;
	private Label dataLabel = null;
	private Combo dataCombo = null;
	private Label derivationRuleLabel = null;
	private Text derivationRuleTextArea = null;
	private Text descriptionTextArea = null;
	private Composite dataTypeComposite = null;
	private Text precisionText = null;
	private Text scaleText = null;
	private Label pLabel = null;
	private Label sLabel = null;
	private Label implementNameLabel = null;
	private Text implementNameText = null;
	private Button derivationCheckBox = null;
	private Button btnAutoIncrementCheckButton = null;
	private Text textDefaultValue;

	public AttributePanel(Composite parent, int style) {
		super(parent, style);
		initialize();
		pack();
	}

	private void initialize() {
		GridData gridData10 = new GridData();
		gridData10.horizontalAlignment = GridData.FILL;
		gridData10.verticalAlignment = SWT.FILL;
		GridData gridData11 = new GridData();
		gridData11.horizontalAlignment = GridData.FILL;
		gridData11.heightHint = 60;
		gridData11.verticalAlignment = SWT.FILL;
		GridData gridData8 = new GridData();
		gridData8.widthHint = -1;
		gridData8.verticalAlignment = SWT.FILL;
		gridData8.horizontalAlignment = GridData.FILL;
		GridData gridData7 = new GridData();
		gridData7.heightHint = 60;
		gridData7.verticalAlignment = GridData.FILL;
		gridData7.horizontalAlignment = GridData.FILL;
		GridData gridData5 = new GridData();
		gridData5.horizontalAlignment = GridData.FILL;
		gridData5.heightHint = 60;
		gridData5.verticalAlignment = GridData.FILL;
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = false;
		gridData.heightHint = 60;
		gridData.widthHint = 200;
		gridData.verticalAlignment = GridData.FILL;
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		inputNameLabel = new Label(this, SWT.NONE);
		inputNameLabel.setText(Messages.AttributePanel_0);
		inputNameLabel.setBounds(new Rectangle(0, -8, 41, 8));
		inputNameText = new Text(this, SWT.BORDER);
		inputNameText.setLayoutData(gridData8);
		implementNameLabel = new Label(this, SWT.NONE);
		implementNameLabel.setText(Messages.AttributePanel_1);
		implementNameText = new Text(this, SWT.BORDER);
		implementNameText.setLayoutData(gridData10);
		descriptionLabel = new Label(this, SWT.NONE);
		descriptionLabel.setText(Messages.AttributePanel_2);
		descriptionTextArea = new Text(this, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.BORDER);
		descriptionTextArea.setLayoutData(gridData11);
		dataLabel = new Label(this, SWT.NONE);
		dataLabel.setText(Messages.AttributePanel_3);
		createDataTypeComposite();
		validationRuleLabel = new Label(this, SWT.NONE);
		validationRuleLabel.setText(Messages.AttributePanel_4);
		validationRuleTextArea = new Text(this, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.BORDER);
		validationRuleTextArea.setLayoutData(gridData);
		lockLabel = new Label(this, SWT.NONE);
		lockLabel.setText(Messages.AttributePanel_5);
		lockTextArea = new Text(this, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.BORDER);
		lockTextArea.setLayoutData(gridData5);
		derivationRuleLabel = new Label(this, SWT.NONE);
		derivationRuleLabel.setText(Messages.AttributePanel_6);
		derivationRuleTextArea = new Text(this, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.BORDER);
		derivationRuleTextArea.setLayoutData(gridData7);
		new Label(this, SWT.NONE);
		derivationCheckBox = new Button(this, SWT.CHECK);
		derivationCheckBox.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		derivationCheckBox.setText(Messages.AttributePanel_7);
		this.setLayout(gridLayout);
		this.setSize(new Point(346, 489));
	}

	/**
	 * This method initializes dataCombo
	 *
	 */
	private void createDataCombo() {
		GridData gridData1 = new GridData();
		gridData1.horizontalAlignment = SWT.FILL;
		gridData1.widthHint = 180;
		gridData1.horizontalSpan = 4;
		gridData1.verticalAlignment = SWT.FILL;
		dataCombo = new Combo(dataTypeComposite, SWT.READ_ONLY);
		dataCombo.setLayoutData(gridData1);
		dataCombo.addSelectionListener(new org.eclipse.swt.events.SelectionListener() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				int index = ((Combo) e.getSource()).getSelectionIndex();
				fireDataTypeChanged(index);
			}

			public void widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent e) {
			}
		});
		dataCombo.add(""); //$NON-NLS-1$
		for (StandardSQLDataType dataType : StandardSQLDataType.values()) {
			dataCombo.add(dataType.getName());
		}
	}

	public void initializeValue(EditAttribute ea) {
		inputNameText.setText(ea.getName());
		descriptionTextArea.setText(ea.getDescription());
		validationRuleTextArea.setText(ea.getValidationRule());
		lockTextArea.setText(ea.getLock());
		derivationRuleTextArea.setText(ea.getDerivationRule());
		derivationCheckBox.setSelection(ea.isDerivation());
		implementNameText.setText(ea.getImplementName());
		StandardSQLDataType dt = ea.getDataType();
		if (dt != null) {
			if (dt.isSupportSize()) {
				precisionText.setText(ea.getSize());
			}
			if (dt.isSupportScale()) {
				scaleText.setText(ea.getScale());
			}
			dataCombo.select(dt.ordinal() + 1);
			if (ea.getAutoIncrement() != null) {
				btnAutoIncrementCheckButton.setSelection(ea.getAutoIncrement());
			}
			if (ea.getDefaultValue() != null) {
				textDefaultValue.setText(ea.getDefaultValue());
			}
			fireDataTypeChanged(dataCombo.getSelectionIndex());
		}
	}

	private void fireDataTypeChanged(int index) {
		if (index > 0) {
			StandardSQLDataType dataType = StandardSQLDataType.values()[index - 1];
			precisionText.setEnabled(dataType.isSupportSize());
			scaleText.setEnabled(dataType.isSupportScale());
		} else {
			precisionText.setEnabled(true);
			scaleText.setEnabled(true);
		}
	}

	public String getDescription() {
		return descriptionTextArea.getText();
	}

	public StandardSQLDataType getDataType() {
		int selectionIndex = dataCombo.getSelectionIndex();
		if (selectionIndex > 0) {
			return StandardSQLDataType.values()[selectionIndex - 1];
		} else {
			return null;
		}
	}

	public String getPresition() {
		if (precisionText.isEnabled()) {
			return precisionText.getText();
		} else {
			return ""; //$NON-NLS-1$
		}
	}

	public String getScale() {
		if (scaleText.isEnabled()) {
			return scaleText.getText();
		} else {
			return ""; //$NON-NLS-1$
		}
	}

	public Boolean getAutoIncrement() {
		if (btnAutoIncrementCheckButton.getSelection()) {
			return true;
		}
		return null;
	}

	public String getValidationRule() {
		return validationRuleTextArea.getText();
	}

	public String getLock() {
		return lockTextArea.getText();
	}

	public String getDerivationRule() {
		return derivationRuleTextArea.getText();
	}

	public boolean isDerivation() {
		return derivationCheckBox.getSelection();
	}

	public String getInputName() {
		return inputNameText.getText();
	}

	public String getImplementName() {
		return implementNameText.getText();
	}

	public String getDefaultValue() {
		return textDefaultValue.getText();
	}

	/**
	 * This method initializes dataTypeComposite
	 *
	 */
	private void createDataTypeComposite() {
		GridData gridData4 = new GridData();
		gridData4.verticalAlignment = SWT.FILL;
		gridData4.horizontalAlignment = SWT.FILL;
		gridData4.widthHint = 30;
		GridData gridData3 = new GridData();
		gridData3.verticalAlignment = SWT.FILL;
		gridData3.horizontalAlignment = SWT.FILL;
		gridData3.widthHint = 50;
		GridLayout gridLayout1 = new GridLayout();
		gridLayout1.numColumns = 4;
		gridLayout1.marginWidth = 5;
		gridLayout1.verticalSpacing = 5;
		gridLayout1.horizontalSpacing = 5;
		GridData gridData2 = new GridData();
		gridData2.widthHint = 272;
		gridData2.grabExcessVerticalSpace = true;
		gridData2.heightHint = 140;
		gridData2.horizontalAlignment = SWT.FILL;
		gridData2.grabExcessHorizontalSpace = true;
		gridData2.verticalAlignment = SWT.FILL;
		dataTypeComposite = new Composite(this, SWT.NONE);
		dataTypeComposite.setLayoutData(gridData2);
		dataTypeComposite.setLayout(gridLayout1);
		createDataCombo();
		pLabel = new Label(dataTypeComposite, SWT.NONE);
		pLabel.setText(Messages.AttributePanel_8);
		precisionText = new Text(dataTypeComposite, SWT.BORDER);
		precisionText.setLayoutData(gridData3);
		sLabel = new Label(dataTypeComposite, SWT.NONE);
		sLabel.setText(Messages.AttributePanel_9);
		scaleText = new Text(dataTypeComposite, SWT.BORDER);
		scaleText.setLayoutData(gridData4);

		btnAutoIncrementCheckButton = new Button(dataTypeComposite, SWT.CHECK);
		btnAutoIncrementCheckButton
				.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 4, 1));
		btnAutoIncrementCheckButton.setText(Messages.AttributePanel_10);

		Label lblNewLabel = new Label(dataTypeComposite, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		lblNewLabel.setText(Messages.AttributePanel_11);

		textDefaultValue = new Text(dataTypeComposite, SWT.BORDER);
		textDefaultValue.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 3, 1));
	}

	public void addNameModifyListener(ModifyListener listener) {
		inputNameText.addModifyListener(listener);
	}

	public void removeNameModifyListener(ModifyListener listener) {
		inputNameText.removeModifyListener(listener);
	}
}

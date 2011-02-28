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
	public AttributePanel(Composite parent, int style) {
		super(parent, style);
		initialize();
	}

	private void initialize() {
		GridData gridData10 = new GridData();
		gridData10.horizontalAlignment = GridData.FILL;
		gridData10.verticalAlignment = GridData.CENTER;
		GridData gridData11 = new GridData();
		gridData11.horizontalAlignment = GridData.FILL;
		gridData11.heightHint = 60;
		gridData11.verticalAlignment = GridData.CENTER;
		GridData gridData8 = new GridData();
		gridData8.widthHint = -1;
		gridData8.verticalAlignment = GridData.CENTER;
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
		inputNameLabel.setText("名称");
		inputNameLabel.setBounds(new Rectangle(0, -8, 41, 8));
		inputNameText = new Text(this, SWT.BORDER);
		inputNameText.setLayoutData(gridData8);
		implementNameLabel = new Label(this, SWT.NONE);
		implementNameLabel.setText("実装名");
		implementNameText = new Text(this, SWT.BORDER);
		implementNameText.setLayoutData(gridData10);
		descriptionLabel = new Label(this, SWT.NONE);
		descriptionLabel.setText("摘要");
		descriptionTextArea = new Text(this, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.BORDER);
		descriptionTextArea.setLayoutData(gridData11);
		dataLabel = new Label(this, SWT.NONE);
		dataLabel.setText("データ属性");
		createDataTypeComposite();
		validationRuleLabel = new Label(this, SWT.NONE);
		validationRuleLabel.setText("前提");
		validationRuleTextArea = new Text(this, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.BORDER);
		validationRuleTextArea.setLayoutData(gridData);
		lockLabel = new Label(this, SWT.NONE);
		lockLabel.setText("機密性");
		lockTextArea = new Text(this, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.BORDER);
		lockTextArea.setLayoutData(gridData5);
		derivationRuleLabel = new Label(this, SWT.NONE);
		derivationRuleLabel.setText("計算式");
		derivationRuleTextArea = new Text(this, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.BORDER);
		derivationRuleTextArea.setLayoutData(gridData7);
		Label filler6 = new Label(this, SWT.NONE);
		derivationCheckBox = new Button(this, SWT.CHECK);
		derivationCheckBox.setText("ダイアグラムに(D)を表示する");
		this.setLayout(gridLayout);
		this.setSize(new Point(285, 423));
	}

	/**
	 * This method initializes dataCombo	
	 *
	 */
	private void createDataCombo() {
		GridData gridData1 = new GridData();
		gridData1.horizontalAlignment = GridData.CENTER;
		gridData1.widthHint = 180;
		gridData1.horizontalSpan = 4;
		gridData1.verticalAlignment = GridData.CENTER;
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
		dataCombo.add("");
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
			return "";
		}
	}
	public String getScale() {
		if (scaleText.isEnabled()) {
			return scaleText.getText();
		} else {
			return "";
		}
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
	/**
	 * This method initializes dataTypeComposite	
	 *
	 */
	private void createDataTypeComposite() {
		GridData gridData4 = new GridData();
		gridData4.widthHint = 30;
		GridData gridData3 = new GridData();
		gridData3.widthHint = 50;
		GridLayout gridLayout1 = new GridLayout();
		gridLayout1.numColumns = 4;
		gridLayout1.marginWidth = 5;
		gridLayout1.verticalSpacing = 5;
		gridLayout1.horizontalSpacing = 5;
		GridData gridData2 = new GridData();
		gridData2.horizontalAlignment = GridData.CENTER;
		gridData2.grabExcessHorizontalSpace = false;
		gridData2.verticalAlignment = GridData.CENTER;
		dataTypeComposite = new Composite(this, SWT.NONE);
		dataTypeComposite.setLayoutData(gridData2);
		dataTypeComposite.setLayout(gridLayout1);
		createDataCombo();
		pLabel = new Label(dataTypeComposite, SWT.NONE);
		pLabel.setText("桁数");
		precisionText = new Text(dataTypeComposite, SWT.BORDER);
		precisionText.setLayoutData(gridData3);
		sLabel = new Label(dataTypeComposite, SWT.NONE);
		sLabel.setText("位取り");
		scaleText = new Text(dataTypeComposite, SWT.BORDER);
		scaleText.setLayoutData(gridData4);
	}
	public void addNameModifyListener(ModifyListener listener) {
		inputNameText.addModifyListener(listener);
	}
	public void removeNameModifyListener(ModifyListener listener) {
		inputNameText.removeModifyListener(listener);
	}
}  //  @jve:decl-index=0:visual-constraint="-60,-10"

/*
 * Copyright 2009-2010 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
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

import jp.sourceforge.tmdmaker.model.EditAttribute;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.layout.GridData;

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
	public AttributePanel(Composite parent, int style) {
		super(parent, style);
		initialize();
	}

	private void initialize() {
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
		inputNameLabel.setText("論理名");
		inputNameText = new Text(this, SWT.BORDER);
		inputNameText.setLayoutData(gridData8);
		descriptionLabel = new Label(this, SWT.NONE);
		descriptionLabel.setText("摘要");
		descriptionTextArea = new Text(this, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.BORDER);
		descriptionTextArea.setLayoutData(gridData11);
		dataLabel = new Label(this, SWT.NONE);
		dataLabel.setText("データ属性");
		createDataCombo();
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
		this.setLayout(gridLayout);
		this.setSize(new Point(289, 337));
	}

	/**
	 * This method initializes dataCombo	
	 *
	 */
	private void createDataCombo() {
		GridData gridData1 = new GridData();
		gridData1.widthHint = -1;
		gridData1.verticalAlignment = GridData.CENTER;
		gridData1.horizontalAlignment = GridData.FILL;
		dataCombo = new Combo(this, SWT.NONE);
		dataCombo.setLayoutData(gridData1);
	}
	public void initializeValue(EditAttribute ea) {
		System.out.println("name = " + ea.getName());
		inputNameText.setText(ea.getName());
//		readOnlyNameText.setEnabled(false);
		descriptionTextArea.setText(ea.getDescription());
//		dataCombo
		validationRuleTextArea.setText(ea.getValidationRule());
		lockTextArea.setText(ea.getLock());
		derivationRuleTextArea.setText(ea.getDerivationRule());
	}
	public String getDescription() {
		return descriptionTextArea.getText();
	}
	public String getDataType() {
		return dataCombo.getItem(dataCombo.getSelectionIndex());
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
	public String getInputName() {
		return inputNameText.getText();
	}
}  //  @jve:decl-index=0:visual-constraint="0,0"

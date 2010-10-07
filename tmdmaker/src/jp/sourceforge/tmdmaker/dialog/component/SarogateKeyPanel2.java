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

import jp.sourceforge.tmdmaker.dialog.model.EditAttribute;
import jp.sourceforge.tmdmaker.model.StandardSQLDataType;

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
 * @author nakaG
 *
 */
public class SarogateKeyPanel2 extends Composite {
	private EditAttribute editAttribute;  //  @jve:decl-index=0:
	private Label implementNameLabel = null;
	private Text implementNameText = null;
	private Label dataLabel = null;
	private Composite dataTypeComposite = null;
	private Combo dataTypeCombo = null;
	private Label pLabel = null;
	private Text precisionText = null;
	private Label sLabel = null;
	private Text scaleText = null;
	private Button checkBox = null;
	public SarogateKeyPanel2(Composite parent, int style) {
		super(parent, style);
		initialize();
	}

	private void initialize() {
		GridData gridData5 = new GridData();
		gridData5.horizontalSpan = 2;
		gridData5.verticalAlignment = GridData.CENTER;
		gridData5.horizontalAlignment = GridData.FILL;
		GridData gridData2 = new GridData();
		gridData2.horizontalAlignment = GridData.FILL;
		gridData2.verticalAlignment = GridData.CENTER;
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		this.setLayout(gridLayout);
		setSize(new Point(271, 107));
		checkBox = new Button(this, SWT.CHECK);
		checkBox.setText("サロゲートキーを追加する");
		checkBox.setLayoutData(gridData5);
		checkBox.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				System.out.println("widgetSelected()"); // TODO Auto-generated Event stub widgetSelected()
				setEnableInputArea(checkBox.getSelection());
			}
		});
		implementNameLabel = new Label(this, SWT.NONE);
		implementNameLabel.setText("実装名");
		implementNameText = new Text(this, SWT.BORDER);
		implementNameText.setLayoutData(gridData2);
		dataLabel = new Label(this, SWT.NONE);
		dataLabel.setText("データ属性");
		createDataTypeComposite();
	}
	public void initializeValue(EditAttribute ea) {
		editAttribute = ea;
		if (ea.getImplementName() != null && !ea.getImplementName().equals("")) {
			checkBox.setSelection(true);
			setEnableInputArea(true);
			implementNameText.setText(ea.getImplementName());
			StandardSQLDataType dt = ea.getDataType();
			if (dt != null) {
				if (dt.isSupportSize()) {
					precisionText.setText(ea.getSize());
				}
				if (dt.isSupportScale()) {
					scaleText.setText(ea.getScale());
				}
				dataTypeCombo.select(dt.ordinal()+1);
				fireDataTypeChanged(dataTypeCombo.getSelectionIndex());
			}

		} else {
			checkBox.setSelection(false);
			setEnableInputArea(false);
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

	private void setEnableInputArea(boolean enabled) {
		implementNameText.setEnabled(enabled);
		dataTypeCombo.setEnabled(enabled);
		precisionText.setEnabled(enabled);
		scaleText.setEnabled(enabled);
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
		gridLayout1.marginWidth = 0;
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = false;
		gridData.verticalAlignment = GridData.FILL;
		dataTypeComposite = new Composite(this, SWT.NONE);
		createDataTypeCombo();
		dataTypeComposite.setLayout(gridLayout1);
		dataTypeComposite.setLayoutData(gridData);
		pLabel = new Label(dataTypeComposite, SWT.NONE);
		pLabel.setText("桁数");
		precisionText = new Text(dataTypeComposite, SWT.BORDER);
		precisionText.setLayoutData(gridData3);
		sLabel = new Label(dataTypeComposite, SWT.NONE);
		sLabel.setText("位取り");
		scaleText = new Text(dataTypeComposite, SWT.BORDER);
		scaleText.setLayoutData(gridData4);
	}

	/**
	 * This method initializes dataTypeCombo	
	 *
	 */
	private void createDataTypeCombo() {
		GridData gridData1 = new GridData();
		gridData1.horizontalSpan = 4;
		gridData1.verticalAlignment = GridData.CENTER;
		gridData1.widthHint = 180;
		gridData1.grabExcessHorizontalSpace = false;
		gridData1.horizontalAlignment = GridData.FILL;
		dataTypeCombo = new Combo(dataTypeComposite, SWT.NONE);
		dataTypeCombo.setLayoutData(gridData1);
		dataTypeCombo
				.addSelectionListener(new org.eclipse.swt.events.SelectionListener() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						System.out.println("widgetSelected()"); // TODO Auto-generated Event stub widgetSelected()
						int index = ((Combo)e.getSource()).getSelectionIndex();
						fireDataTypeChanged(index);
					}
					public void widgetDefaultSelected(
							org.eclipse.swt.events.SelectionEvent e) {
					}
				});
		
		dataTypeCombo.add("");
		for (StandardSQLDataType dataType : StandardSQLDataType.values()) {
			dataTypeCombo.add(dataType.getName());
		}

	}

	/**
	 * @return the editAttribute
	 */
	public EditAttribute getEditAttribute() {
		return editAttribute;
	}
	
}  //  @jve:decl-index=0:visual-constraint="0,0"

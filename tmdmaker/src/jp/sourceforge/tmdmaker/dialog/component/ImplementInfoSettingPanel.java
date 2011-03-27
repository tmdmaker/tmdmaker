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

import jp.sourceforge.tmdmaker.dialog.model.EditTable;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;

/**
 * @author nakaG
 *
 */
public class ImplementInfoSettingPanel extends Composite {
	private EditTable entity;

	private Button notImplementCheckBox = null;
	private Label implementNameLabel = null;
	private Text implementNameText = null;

	public ImplementInfoSettingPanel(Composite parent, int style, EditTable entity) {
		super(parent, style);
		this.entity = entity;
		initialize();
	}

	private void initialize() {
		GridData gridData1 = new GridData();
		gridData1.grabExcessHorizontalSpace = true;
		gridData1.verticalAlignment = GridData.CENTER;
		gridData1.horizontalAlignment = GridData.FILL;
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.horizontalSpan = 2;
		gridData.verticalAlignment = GridData.CENTER;
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		notImplementCheckBox = new Button(this, SWT.CHECK);
		notImplementCheckBox.setText("実装しない");
		notImplementCheckBox.setLayoutData(gridData);
		notImplementCheckBox
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						implementNameText.setEnabled(!implementNameText.getEnabled());
						entity.setNotImplement(notImplementCheckBox.getSelection());
					}
				});
		implementNameLabel = new Label(this, SWT.NONE);
		implementNameLabel.setText("実装名");
		implementNameText = new Text(this, SWT.BORDER);
		implementNameText.setLayoutData(gridData1);
		implementNameText
				.addModifyListener(new org.eclipse.swt.events.ModifyListener() {
					public void modifyText(org.eclipse.swt.events.ModifyEvent e) {
						entity.setImplementName(implementNameText.getText());
					}
				});
		this.setLayout(gridLayout);
		this.setSize(new Point(134, 49));
		updateValue();
	}
	private void updateValue() {
		boolean notImplement = entity.isNotImplement();
		notImplementCheckBox.setSelection(notImplement);
		implementNameText.setText(entity.getImplementName());
		implementNameText.setEnabled(!notImplement);
	}
}  //  @jve:decl-index=0:visual-constraint="0,0"

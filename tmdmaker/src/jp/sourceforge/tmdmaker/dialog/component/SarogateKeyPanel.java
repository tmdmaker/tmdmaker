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

import jp.sourceforge.tmdmaker.dialog.model.EditImplementEntity;

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
public class SarogateKeyPanel extends Composite {
	private EditImplementEntity entity;
	private Button useSarogateKeyCheckBox = null;
	private Label nameLabel = null;
	private Text inputNameText = null;

	public SarogateKeyPanel(Composite parent, int style, EditImplementEntity entity) {
		super(parent, style);
		this.entity = entity;
		initialize();
//		refreshVisual();
	}

	private void initialize() {
		GridData gridData1 = new GridData();
		gridData1.horizontalAlignment = GridData.FILL;
		gridData1.widthHint = 150;
		gridData1.verticalAlignment = GridData.CENTER;
		GridData gridData = new GridData();
		gridData.horizontalSpan = 2;
		gridData.verticalAlignment = GridData.CENTER;
		gridData.horizontalAlignment = GridData.BEGINNING;
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		useSarogateKeyCheckBox = new Button(this, SWT.CHECK);
		useSarogateKeyCheckBox.setText("サロゲートキーを追加する");
		useSarogateKeyCheckBox.setLayoutData(gridData);
		useSarogateKeyCheckBox
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						System.out.println("widgetSelected()"); // TODO Auto-generated Event stub widgetSelected()
						entity.setSarogateKeyEnabled(useSarogateKeyCheckBox.getSelection());
					}
				});
		nameLabel = new Label(this, SWT.NONE);
		nameLabel.setText("名称");
		inputNameText = new Text(this, SWT.BORDER);
		inputNameText.setLayoutData(gridData1);
		inputNameText.addModifyListener(new org.eclipse.swt.events.ModifyListener() {
			public void modifyText(org.eclipse.swt.events.ModifyEvent e) {
				System.out.println("SarogateKeyPanel#modifyText()"); // TODO Auto-generated Event stub modifyText()
				entity.setSarogateKeyName(inputNameText.getText());
			}
		});
		this.setLayout(gridLayout);
		setSize(new Point(197, 49));
	}

	public void refreshVisual() {
		if (entity.isSarogateKeyEnabled()) {
			useSarogateKeyCheckBox.setSelection(true);
			inputNameText.setEnabled(true);
			inputNameText.setText(entity.getSarogateKeyName());
		} else {
			useSarogateKeyCheckBox.setSelection(false);
			inputNameText.setText(entity.getSarogateKeyName());
			inputNameText.setEnabled(false);
		}
	}
}  //  @jve:decl-index=0:visual-constraint="0,0"

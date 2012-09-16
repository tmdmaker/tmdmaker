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

import jp.sourceforge.tmdmaker.model.VirtualSuperset;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class VirtualSupersetSettingPanel extends Composite {
	private boolean applyAttribute = false;
	private Label virtualSupersetNameLabel = null;
	private Text virtualSupersetNameText = null;
	private Composite typeComposite = null;
	private Button sameRadioButton = null;
	private Button differentRadioButton = null;
	public VirtualSupersetSettingPanel(Composite parent, int style) {
		super(parent, style);
		initialize();
	}

	private void initialize() {
		GridData gridData = new GridData();
		gridData.horizontalSpan = 2;
		gridData.horizontalAlignment = GridData.FILL;
		gridData.verticalAlignment = GridData.CENTER;
		gridData.widthHint = 80;
		gridData.grabExcessHorizontalSpace = true;
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 3;
		virtualSupersetNameLabel = new Label(this, SWT.NONE);
		virtualSupersetNameLabel.setText("みなしスーパーセット名");
		virtualSupersetNameText = new Text(this, SWT.BORDER);
		virtualSupersetNameText.setLayoutData(gridData);
		createTypeComposite();
		this.setLayout(gridLayout);
		this.setSize(new Point(264, 66));
	}

	/**
	 * This method initializes typeComposite	
	 *
	 */
	private void createTypeComposite() {
		GridData gridData5 = new GridData();
		gridData5.grabExcessHorizontalSpace = true;
		gridData5.verticalAlignment = GridData.CENTER;
		gridData5.horizontalAlignment = GridData.FILL;
		GridData gridData2 = new GridData();
		gridData2.horizontalAlignment = GridData.FILL;
		gridData2.grabExcessHorizontalSpace = true;
		gridData2.verticalAlignment = GridData.CENTER;
		GridLayout gridLayout1 = new GridLayout();
		gridLayout1.numColumns = 2;
		gridLayout1.verticalSpacing = 5;
		gridLayout1.marginWidth = 10;
		GridData gridData1 = new GridData();
		gridData1.horizontalAlignment = GridData.FILL;
		gridData1.horizontalSpan = 3;
		gridData1.grabExcessHorizontalSpace = false;
		gridData1.verticalAlignment = GridData.CENTER;
		typeComposite = new Composite(this, SWT.NONE);
		typeComposite.setLayoutData(gridData1);
		typeComposite.setLayout(gridLayout1);
		sameRadioButton = new Button(typeComposite, SWT.RADIO);
		sameRadioButton.setText("アトリビュートに適用");
		sameRadioButton.setLayoutData(gridData2);
		sameRadioButton
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						applyAttribute = true;
					}
				});
		differentRadioButton = new Button(typeComposite, SWT.RADIO);
		differentRadioButton.setText("エンティティに適用");
		differentRadioButton.setLayoutData(gridData5);
		differentRadioButton
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						applyAttribute = false;
					}
				});
	}

	public void initializeValue(VirtualSuperset superset) {
		if (superset != null) {
			if (superset.getName() != null) {
				virtualSupersetNameText.setText(superset.getName());
			}
			applyAttribute = superset.getVirtualSupersetType().isApplyAttribute();
			sameRadioButton.setSelection(applyAttribute);
			differentRadioButton.setSelection(!applyAttribute);
		} else {
			applyAttribute = true;
			sameRadioButton.setSelection(applyAttribute);
		}
	}
	public String getVirtualSupersetName() {
		return virtualSupersetNameText.getText();
	}

	/**
	 * @return the sameType
	 */
	public boolean isApplyAttributeSelected() {
		return applyAttribute;
	}
	public void addNameModifyListener(ModifyListener listener) {
		virtualSupersetNameText.addModifyListener(listener);
	}
	public void removeNameModifyListener(ModifyListener listener) {
		virtualSupersetNameText.removeModifyListener(listener);
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"

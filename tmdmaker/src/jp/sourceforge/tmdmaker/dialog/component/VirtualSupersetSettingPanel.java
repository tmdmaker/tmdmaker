/*
 * Copyright 2009 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
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

import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.VirtualSuperset;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Text;

public class VirtualSupersetSettingPanel extends Composite {
	private java.util.List<AbstractEntityModel> notSelectedModelList = null;  //  @jve:decl-index=0:
	private java.util.List<AbstractEntityModel> selectedModelList = null;  //  @jve:decl-index=0:
	private Label virtualSupersetNameLabel = null;
	private Text virtualSupersetNameText = null;
	private List selectedEntityNameList = null;
	private List notSelectedEntityNameList = null;
	private Button selectButton = null;
	private Button removeButton = null;
	private Composite typeComposite = null;
	private Button sameRadioButton = null;
	private Button differentRadioButton = null;
	private Label selectedLabel = null;
	private Label notSelectedLabel = null;
	private Button selectAllButton = null;
	private Button removeAllButton = null;
	
	public VirtualSupersetSettingPanel(Composite parent, int style) {
		super(parent, style);
		initialize();
	}

	private void initialize() {
		GridData gridData111 = new GridData();
		gridData111.widthHint = 30;
		GridData gridData4 = new GridData();
		gridData4.widthHint = 30;
		GridData gridData3 = new GridData();
		gridData3.widthHint = 30;
		GridData gridData21 = new GridData();
		gridData21.widthHint = 30;
		GridData gridData12 = new GridData();
		gridData12.verticalSpan = 4;
		gridData12.verticalAlignment = GridData.FILL;
		gridData12.widthHint = 100;
		gridData12.grabExcessHorizontalSpace = true;
		gridData12.horizontalAlignment = GridData.FILL;
		GridData gridData11 = new GridData();
		gridData11.verticalSpan = 4;
		gridData11.verticalAlignment = GridData.FILL;
		gridData11.widthHint = 100;
		gridData11.grabExcessHorizontalSpace = false;
		gridData11.horizontalAlignment = GridData.FILL;
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
		selectedLabel = new Label(this, SWT.NONE);
		selectedLabel.setText("選択");
		Label filler = new Label(this, SWT.NONE);
		notSelectedLabel = new Label(this, SWT.NONE);
		notSelectedLabel.setText("未選択");
		selectedEntityNameList = new List(this, SWT.MULTI|SWT.BORDER|SWT.V_SCROLL);
		selectedEntityNameList.setLayoutData(gridData11);
		selectedEntityNameList.addFocusListener(new org.eclipse.swt.events.FocusAdapter() {
			public void focusGained(org.eclipse.swt.events.FocusEvent e) {
				System.out.println("focusGained()"); // TODO Auto-generated Event stub focusGained()
				selectButton.setEnabled(false);
				selectAllButton.setEnabled(false);
				removeButton.setEnabled(true);
				removeAllButton.setEnabled(true);
				notSelectedEntityNameList.setSelection(new int[0]);
			}
		});
		selectedEntityNameList
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						System.out.println("widgetSelected()"); // TODO Auto-generated Event stub widgetSelected()
						
					}
				});
		selectButton = new Button(this, SWT.NONE);
		selectButton.setText("<");
		selectButton.setLayoutData(gridData4);
		selectButton
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						System.out.println("widgetSelected()"); // TODO Auto-generated Event stub widgetSelected()
						String[] selectedEntities = notSelectedEntityNameList.getSelection();
						int[] selectedIndeces = notSelectedEntityNameList.getSelectionIndices();
						if (selectedIndeces.length == 0) {
							return;
						}
						int selectedIndex = selectedIndeces[0];
						for (String s : selectedEntities) {
							int index = notSelectedEntityNameList.indexOf(s);
							AbstractEntityModel model = notSelectedModelList.get(index);
							selectedEntityNameList.add(s);
							selectedModelList.add(model);
							
							notSelectedEntityNameList.remove(s);
							notSelectedModelList.remove(index);
						}
						if (notSelectedEntityNameList.getItemCount() <= selectedIndex) {
							System.out.println(selectedIndex);
							selectedIndex = notSelectedEntityNameList.getItemCount() -1;
						}
						notSelectedEntityNameList.setSelection(selectedIndex);
					}
				});
		notSelectedEntityNameList = new List(this, SWT.MULTI|SWT.BORDER|SWT.V_SCROLL);
		notSelectedEntityNameList.setLayoutData(gridData12);
		notSelectedEntityNameList
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						System.out.println("widgetSelected()"); // TODO Auto-generated Event stub widgetSelected()
						for (String i : ((List) e.getSource()).getSelection()) System.out.println(i);
						selectButton.setEnabled(true);
					}
				});
		notSelectedEntityNameList
				.addFocusListener(new org.eclipse.swt.events.FocusAdapter() {
					public void focusGained(org.eclipse.swt.events.FocusEvent e) {
						System.out.println("focusGained()"); // TODO Auto-generated Event stub focusGained()
						selectButton.setEnabled(true);
						selectAllButton.setEnabled(true);
						removeButton.setEnabled(false);
						removeAllButton.setEnabled(false);
						selectedEntityNameList.setSelection(new int[0]);
					}
				});
		removeButton = new Button(this, SWT.NONE);
		removeButton.setText(">");
		removeButton.setLayoutData(gridData3);
		selectAllButton = new Button(this, SWT.NONE);
		selectAllButton.setText("<<");
		selectAllButton.setLayoutData(gridData21);
		selectAllButton
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						System.out.println("widgetSelected()"); // TODO Auto-generated Event stub widgetSelected()
						for (String s : notSelectedEntityNameList.getItems()) {
							int index = notSelectedEntityNameList.indexOf(s);					
							selectedEntityNameList.add(s);
							AbstractEntityModel model = notSelectedModelList.get(index);
							selectedModelList.add(model);
						}
						notSelectedEntityNameList.removeAll();
						notSelectedModelList.clear();
					}
				});
		removeAllButton = new Button(this, SWT.NONE);
		removeAllButton.setText(">>");
		removeAllButton.setLayoutData(gridData111);
		removeAllButton
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						System.out.println("widgetSelected()"); // TODO Auto-generated Event stub widgetSelected()
						for (String s : selectedEntityNameList.getItems()) {
							int index = selectedEntityNameList.indexOf(s);
							notSelectedEntityNameList.add(s);
							AbstractEntityModel model = selectedModelList.get(index);
							notSelectedModelList.add(model);
						}
						selectedEntityNameList.removeAll();
						selectedModelList.clear();
					}
				});
		removeButton
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						System.out.println("widgetSelected()"); // TODO Auto-generated Event stub widgetSelected()

						String[] selectedEntities = selectedEntityNameList.getSelection();
						int[] selectedIndeces = selectedEntityNameList.getSelectionIndices();
						if (selectedIndeces.length == 0) {
							return;
						}
						int selectedIndex = selectedIndeces[0];
						for (String s : selectedEntities) {
							int index = selectedEntityNameList.indexOf(s);
							AbstractEntityModel model = selectedModelList.get(index);

							notSelectedEntityNameList.add(s);
							notSelectedModelList.add(model);
							selectedEntityNameList.remove(s);
							selectedModelList.remove(index);
						}
						if (selectedEntityNameList.getItemCount() <= selectedIndex) {
							System.out.println(selectedIndex);
							selectedIndex = selectedEntityNameList.getItemCount() -1;
						}
						selectedEntityNameList.setSelection(selectedIndex);
					}
				});
		this.setLayout(gridLayout);
		this.setSize(new Point(300, 198));
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
		differentRadioButton = new Button(typeComposite, SWT.RADIO);
		differentRadioButton.setText("エンティティに適用");
		differentRadioButton.setLayoutData(gridData5);
	}

	public void initializeValue(java.util.List<AbstractEntityModel> notSelection, java.util.List<AbstractEntityModel> selection, VirtualSuperset superset) {
		notSelectedModelList = notSelection;
		selectedModelList = selection;
		for (AbstractEntityModel m : notSelection) {
			notSelectedEntityNameList.add(m.getName());
		}
		for (AbstractEntityModel m : selection) {
			selectedEntityNameList.add(m.getName());
		}
		if (superset != null && superset.getName() != null) {
			virtualSupersetNameText.setText(superset.getName());
		}
	}
	public String getVirtualSupersetName() {
		return virtualSupersetNameText.getText();
	}
}  //  @jve:decl-index=0:visual-constraint="10,10"

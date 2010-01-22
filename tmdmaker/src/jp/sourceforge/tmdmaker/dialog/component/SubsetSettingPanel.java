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

import java.util.ArrayList;
import java.util.List;

import jp.sourceforge.tmdmaker.model.Attribute;
import jp.sourceforge.tmdmaker.model.EditSubsetEntity;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

public class SubsetSettingPanel extends Composite {
	private static int EDIT_COLUMN = 0;
	private int selectedIndex = -1;
	private TableEditor tableEditor = null;
	private boolean exceptNull = false;
	private boolean sameType = false;
	private List<EditSubsetEntity> deletedSubsetEntityList = new ArrayList<EditSubsetEntity>();  //  @jve:decl-index=0:
	private List<EditSubsetEntity> subsetEntityList = null;  //  @jve:decl-index=0:
	private Label partitionCodeLabel = null;
	private Combo partitionSelectCombo = null;
	private Group typeGroup = null;
	private Button sameRadioButton = null;
	private Button differenceRadioButton = null;
	private Button nullCheckBox = null;
	private Composite subsetContainerComposite = null;
	private Table subsetTable = null;
	private Composite subsetControlComposite = null;
	private Button newButton = null;
	private Button deleteButton = null;
	public SubsetSettingPanel(Composite parent, int style) {
		super(parent, style);
		initialize();
	}

	private void initialize() {
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		partitionCodeLabel = new Label(this, SWT.NONE);
		partitionCodeLabel.setText("区分コード");
		this.setLayout(gridLayout);
		createPartitionSelectCombo();
		createTypeGroup();
		createSubsetContainerComposite();
		this.setSize(new Point(208, 150));
	}

	/**
	 * This method initializes partitionSelectCombo	
	 *
	 */
	private void createPartitionSelectCombo() {
		GridData gridData3 = new GridData();
		gridData3.horizontalAlignment = GridData.FILL;
		gridData3.verticalAlignment = GridData.CENTER;
		partitionSelectCombo = new Combo(this, SWT.NONE);
		partitionSelectCombo.setLayoutData(gridData3);
	}

	/**
	 * This method initializes typeGroup	
	 *
	 */
	private void createTypeGroup() {
		GridLayout gridLayout1 = new GridLayout();
		gridLayout1.numColumns = 3;
		GridData gridData = new GridData();
		gridData.horizontalSpan = 2;
		gridData.horizontalAlignment = GridData.FILL;
		gridData.verticalAlignment = GridData.CENTER;
		gridData.grabExcessHorizontalSpace = false;
		typeGroup = new Group(this, SWT.NONE);
		typeGroup.setText("サブセット種類");
		typeGroup.setLayout(gridLayout1);
		typeGroup.setLayoutData(gridData);
		sameRadioButton = new Button(typeGroup, SWT.RADIO);
		sameRadioButton.setText("同一");
		sameRadioButton
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						sameType = true;
						exceptNull = false;
						nullCheckBox.setSelection(false);
						nullCheckBox.setEnabled(false);
					}
				});
		differenceRadioButton = new Button(typeGroup, SWT.RADIO);
		differenceRadioButton.setText("相違");
		differenceRadioButton
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						sameType = false;
						nullCheckBox.setEnabled(true);
					}
				});
		nullCheckBox = new Button(typeGroup, SWT.CHECK);
		nullCheckBox.setText("NULLを排除");
		nullCheckBox
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						exceptNull = !exceptNull;
					}
				});
	}

	/**
	 * This method initializes subsetContainerComposite	
	 *
	 */
	private void createSubsetContainerComposite() {
		GridData gridData2 = new GridData();
		gridData2.horizontalAlignment = GridData.FILL;
		gridData2.verticalAlignment = GridData.FILL;
		GridLayout gridLayout2 = new GridLayout();
		gridLayout2.numColumns = 2;
		GridData gridData1 = new GridData();
		gridData1.horizontalSpan = 2;
		gridData1.verticalAlignment = GridData.CENTER;
		gridData1.horizontalAlignment = GridData.FILL;
		subsetContainerComposite = new Composite(this, SWT.NONE);
		subsetContainerComposite.setLayoutData(gridData1);
		subsetContainerComposite.setLayout(gridLayout2);
		subsetTable = new Table(subsetContainerComposite, SWT.NONE);
		subsetTable.setHeaderVisible(true);
		subsetTable.setLayoutData(gridData2);
		subsetTable.setLinesVisible(true);
		subsetTable.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				System.out.println("widgetSelected()"); // TODO Auto-generated Event stub widgetSelected()
			}
		});
		subsetTable.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				selectedIndex = subsetTable.getSelectionIndex();
				if (selectedIndex == -1) {
					return;
				}
				Control oldEditor = tableEditor.getEditor();
				if (oldEditor != null) {
					oldEditor.dispose();
				}
				TableItem item = (TableItem) e.item;
				final Text text = new Text(subsetTable, SWT.NONE);
				text.setText(item.getText(EDIT_COLUMN));
				text.addFocusListener(new FocusAdapter() {

					/**
					 * {@inheritDoc}
					 *
					 * @see org.eclipse.swt.events.FocusAdapter#focusLost(org.eclipse.swt.events.FocusEvent)
					 */
					@Override
					public void focusLost(FocusEvent e) {
						TableItem item = tableEditor.getItem();
						String editValue = text.getText();
						if (editValue == null) {
							editValue = "";
						}
						item.setText(EDIT_COLUMN, editValue);
						EditSubsetEntity ese = subsetEntityList.get(selectedIndex);
						ese.setName(editValue);
						text.dispose();
					}
					
				});
				text.addModifyListener(new ModifyListener() {

					/**
					 * {@inheritDoc}
					 *
					 * @see org.eclipse.swt.events.ModifyListener#modifyText(org.eclipse.swt.events.ModifyEvent)
					 */
					@Override
					public void modifyText(ModifyEvent e) {
						TableItem item = tableEditor.getItem();
						String editValue = text.getText();
						if (editValue == null) {
							editValue = "";
						}
						item.setText(EDIT_COLUMN, editValue);
						EditSubsetEntity ese = subsetEntityList.get(selectedIndex);
						ese.setName(editValue);
						
					}
					
				});
				text.selectAll();
				text.setFocus();
				
				tableEditor.setEditor(text, item, EDIT_COLUMN);

			}
		});
		createSubsetControlComposite();
		TableColumn tableColumn = new TableColumn(subsetTable, SWT.NONE);
		tableColumn.setWidth(120);
		tableColumn.setText("サブセット名");
		tableEditor = new TableEditor(subsetTable);
		tableEditor.grabHorizontal = true;
		tableEditor.horizontalAlignment = SWT.LEFT;
		tableEditor.minimumWidth = 50;

	}

	/**
	 * This method initializes subsetControlComposite	
	 *
	 */
	private void createSubsetControlComposite() {
		subsetControlComposite = new Composite(subsetContainerComposite, SWT.NONE);
		subsetControlComposite.setLayout(new GridLayout());
		newButton = new Button(subsetControlComposite, SWT.NONE);
		newButton.setText("新規");
		newButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				EditSubsetEntity ese = new EditSubsetEntity();
				ese.setName("サブセット" + String.valueOf(subsetEntityList.size() + 1));
				subsetEntityList.add(ese);
				selectedIndex = subsetEntityList.size() - 1;
				updateTable();
				updateSelection();
			}
		});
		deleteButton = new Button(subsetControlComposite, SWT.NONE);
		deleteButton.setText("削除");
		deleteButton
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						if (selectedIndex == -1) {
							return;
						}
						EditSubsetEntity deleted = subsetEntityList.remove(selectedIndex);
						deletedSubsetEntityList.add(deleted);
						if (subsetEntityList.size() <= selectedIndex) {
							selectedIndex--;
						}
						updateTable();
						updateSelection();
					}
				});
	}

	public void initializeValue(boolean sameType, boolean exceptNull, List<Attribute> attributes, List<EditSubsetEntity> subsets, Attribute selectedAttribute) {
		this.sameType = sameType;
		this.exceptNull = exceptNull;
		if (sameType) {
			this.sameRadioButton.setSelection(true);
			this.nullCheckBox.setEnabled(false);
		} else {
			this.differenceRadioButton.setSelection(true);
			this.nullCheckBox.setSelection(exceptNull);
		}
		for (Attribute a : attributes) {
			this.partitionSelectCombo.add(a.getName());
			if (a.equals(selectedAttribute)) {
				this.partitionSelectCombo.select(this.partitionSelectCombo.getItemCount() - 1);
			}
		}
		this.subsetEntityList = subsets;
		for (EditSubsetEntity se : subsets) {
			TableItem item = new TableItem(this.subsetTable, SWT.NULL);
			item.setText(se.getName());
		}
	}
	private void updateTable() {
		this.subsetTable.removeAll();
		for (EditSubsetEntity ese : this.subsetEntityList) {
			TableItem item = new TableItem(this.subsetTable, SWT.NULL);
			item.setText(ese.getName());
		}
	}
	private void updateSelection() {
		this.subsetTable.select(selectedIndex);
	
	}
	public boolean isSameTypeSelected() {
		return sameType;
	}
	public int getSelectedPartitionAttributeIndex() {
		return this.partitionSelectCombo.getSelectionIndex();
	}
	public List<EditSubsetEntity> getSubsetEntityList() {
		return this.subsetEntityList;
	}
	public boolean isExceptNull() {
		return exceptNull;
	}
	/**
	 * @return the deletedSubsetEntityList
	 */
	public List<EditSubsetEntity> getDeletedSubsetEntityList() {
		return deletedSubsetEntityList;
	}

}  //  @jve:decl-index=0:visual-constraint="0,0"

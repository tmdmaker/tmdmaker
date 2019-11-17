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
package org.tmdmaker.ui.dialogs.components;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Text;
import org.tmdmaker.core.model.IAttribute;
import org.tmdmaker.core.model.KeyModel;
import org.tmdmaker.ui.Messages;

/**
 * Index panel.
 * 
 * @author nakag
 *
 */
public class IndexPanel extends Composite {
	private java.util.List<IAttribute> selectModels = null;
	private java.util.List<IAttribute> notSelectModels = null;

	private Label attributeSelectedLabel = null;
	private Label attributeNotSelectedLabel = null;
	private List attributeSelectedList = null;
	private List attributeNotSelectedList = null;
	private Button selectButton = null;
	private Button removeButton = null;
	private Composite indexNameComposite = null;
	private Label indexNameLabel = null;
	private Text indexNameText = null;
	private Button selectAllButton = null;
	private Button removeAllButton = null;
	private Button upButton = null;
	private Button downButton = null;
	private Button uniqueCheckBox = null;
	private Button masterCheckBox = null;

	public IndexPanel(Composite parent, int style) {
		super(parent, style);
		initialize();
	}

	private void initialize() {
		GridData gridData17 = new GridData();
		gridData17.horizontalSpan = 2;
		GridData gridData16 = new GridData();
		gridData16.horizontalSpan = 2;
		gridData16.verticalAlignment = GridData.CENTER;
		gridData16.grabExcessHorizontalSpace = true;
		gridData16.horizontalAlignment = GridData.FILL;
		GridData gridData11 = new GridData();
		gridData11.grabExcessHorizontalSpace = true;
		gridData11.widthHint = 30;
		gridData11.verticalAlignment = GridData.CENTER;
		gridData11.horizontalAlignment = SWT.FILL;
		GridData gridData10 = new GridData();
		gridData10.grabExcessHorizontalSpace = true;
		gridData10.widthHint = 30;
		gridData10.verticalAlignment = GridData.CENTER;
		gridData10.horizontalAlignment = SWT.FILL;
		GridData gridData9 = new GridData();
		gridData9.widthHint = 30;
		gridData9.verticalAlignment = GridData.CENTER;
		gridData9.horizontalAlignment = SWT.FILL;
		GridData gridData8 = new GridData();
		gridData8.widthHint = 30;
		gridData8.verticalAlignment = GridData.CENTER;
		gridData8.horizontalAlignment = SWT.FILL;
		GridData gridData5 = new GridData();
		gridData5.grabExcessHorizontalSpace = true;
		gridData5.verticalSpan = 4;
		gridData5.verticalAlignment = GridData.FILL;
		gridData5.widthHint = 150;
		gridData5.horizontalAlignment = GridData.CENTER;
		GridData gridData4 = new GridData();
		gridData4.verticalSpan = 4;
		gridData4.verticalAlignment = GridData.FILL;
		gridData4.widthHint = 150;
		gridData4.horizontalAlignment = GridData.CENTER;
		GridLayout gridLayout11 = new GridLayout();
		gridLayout11.numColumns = 4;
		GridData gridData15 = new GridData();
		gridData15.horizontalAlignment = GridData.CENTER;
		gridData15.widthHint = 30;
		gridData15.verticalAlignment = GridData.CENTER;
		GridData gridData14 = new GridData();
		gridData14.horizontalAlignment = GridData.CENTER;
		gridData14.widthHint = 30;
		gridData14.verticalAlignment = GridData.CENTER;
		GridData gridData13 = new GridData();
		gridData13.horizontalAlignment = GridData.CENTER;
		gridData13.widthHint = 30;
		gridData13.verticalAlignment = GridData.CENTER;
		GridData gridData12 = new GridData();
		gridData12.horizontalAlignment = GridData.CENTER;
		gridData12.widthHint = 30;
		gridData12.verticalAlignment = GridData.CENTER;
		GridData gridData7 = new GridData();
		gridData7.widthHint = 150;
		gridData7.verticalAlignment = GridData.FILL;
		gridData7.verticalSpan = 4;
		gridData7.horizontalAlignment = GridData.BEGINNING;
		GridData gridData6 = new GridData();
		gridData6.widthHint = 150;
		gridData6.horizontalAlignment = GridData.BEGINNING;
		gridData6.verticalAlignment = GridData.FILL;
		gridData6.verticalSpan = 4;
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 3;
		this.setLayout(gridLayout11);
		createIndexNameComposite();
		uniqueCheckBox = new Button(this, SWT.CHECK);
		uniqueCheckBox.setText(Messages.UniqueConstraint);
		uniqueCheckBox.setLayoutData(gridData16);
		uniqueCheckBox.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			@Override
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				masterCheckBox.setEnabled(uniqueCheckBox.getSelection());
			}
		});
		masterCheckBox = new Button(this, SWT.CHECK);
		masterCheckBox.setText(Messages.SpecifyMasterKey);
		masterCheckBox.setLayoutData(gridData17);
		Label filler31 = new Label(this, SWT.NONE);
		filler31.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		attributeSelectedLabel = new Label(this, SWT.NONE);
		attributeSelectedLabel.setText(Messages.Select);
		new Label(this, SWT.NONE);
		attributeNotSelectedLabel = new Label(this, SWT.NONE);
		attributeNotSelectedLabel
				.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		attributeNotSelectedLabel.setText(Messages.Unselect);
		upButton = new Button(this, SWT.NONE);
		upButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		upButton.setText(Messages.UpButton);
		upButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			@Override
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				int selectionIndex = attributeSelectedList.getSelectionIndex();
				if (selectionIndex <= 0) {
					return;
				}
				IAttribute moved = selectModels.remove(selectionIndex);
				selectModels.add(selectionIndex - 1, moved);
				updateList();
				attributeSelectedList.setSelection(selectionIndex - 1);
			}
		});
		attributeSelectedList = new List(this, SWT.MULTI | SWT.BORDER | SWT.V_SCROLL);
		attributeSelectedList.setLayoutData(gridData4);
		attributeSelectedList.addFocusListener(new org.eclipse.swt.events.FocusAdapter() {
			@Override
			public void focusGained(org.eclipse.swt.events.FocusEvent e) {
				selectButton.setEnabled(false);
				removeButton.setEnabled(true);
				selectAllButton.setEnabled(false);
				removeAllButton.setEnabled(true);
				upButton.setEnabled(true);
				downButton.setEnabled(true);
			}
		});
		selectButton = new Button(this, SWT.NONE);
		selectButton.setText("<"); //$NON-NLS-1$
		selectButton.setLayoutData(gridData8);
		selectButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			@Override
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				int selectionIndex = attributeNotSelectedList.getSelectionIndex();
				if (selectionIndex == -1) {
					return;
				}
				selectModels.add(notSelectModels.remove(selectionIndex));
				updateList();
			}
		});
		attributeNotSelectedList = new List(this, SWT.MULTI | SWT.BORDER | SWT.V_SCROLL);
		attributeNotSelectedList.setLayoutData(gridData5);
		attributeNotSelectedList.addFocusListener(new org.eclipse.swt.events.FocusAdapter() {
			@Override
			public void focusGained(org.eclipse.swt.events.FocusEvent e) {
				selectButton.setEnabled(true);
				removeButton.setEnabled(false);
				selectAllButton.setEnabled(true);
				removeAllButton.setEnabled(false);
				upButton.setEnabled(false);
				downButton.setEnabled(false);
			}
		});
		downButton = new Button(this, SWT.NONE);
		downButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		downButton.setText(Messages.DownButton);
		downButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			@Override
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				int selectionIndex = attributeSelectedList.getSelectionIndex();
				if (selectionIndex == -1 || selectionIndex == selectModels.size() - 1) {
					return;
				}
				IAttribute moved = selectModels.remove(selectionIndex);
				selectModels.add(selectionIndex + 1, moved);
				updateList();
				attributeSelectedList.setSelection(selectionIndex + 1);

			}
		});
		removeButton = new Button(this, SWT.NONE);
		removeButton.setText(">"); //$NON-NLS-1$
		removeButton.setLayoutData(gridData9);
		removeButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			@Override
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				int selectionIndex = attributeSelectedList.getSelectionIndex();
				if (selectionIndex == -1) {
					return;
				}
				notSelectModels.add(selectModels.remove(selectionIndex));
				updateList();
			}
		});
		Label filler1 = new Label(this, SWT.NONE);
		filler1.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		selectAllButton = new Button(this, SWT.NONE);
		selectAllButton.setText("<<"); //$NON-NLS-1$
		selectAllButton.setLayoutData(gridData10);
		selectAllButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			@Override
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				selectModels.addAll(notSelectModels);
				notSelectModels.clear();
				updateList();
			}
		});
		Label filler = new Label(this, SWT.NONE);
		filler.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		removeAllButton = new Button(this, SWT.NONE);
		removeAllButton.setText(">>"); //$NON-NLS-1$
		removeAllButton.setLayoutData(gridData11);
		removeAllButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			@Override
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				notSelectModels.addAll(selectModels);
				selectModels.clear();
				updateList();
			}
		});
		Label filler2 = new Label(this, SWT.NONE);
		filler2.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		this.setSize(new Point(464, 225));
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
	}

	/**
	 * This method initializes indexNameComposite
	 *
	 */
	private void createIndexNameComposite() {
		GridData gridData3 = new GridData();
		gridData3.horizontalAlignment = GridData.FILL;
		gridData3.widthHint = 150;
		gridData3.grabExcessHorizontalSpace = true;
		gridData3.verticalAlignment = GridData.CENTER;
		GridData gridData2 = new GridData();
		gridData2.horizontalAlignment = GridData.FILL;
		gridData2.grabExcessHorizontalSpace = true;
		gridData2.horizontalSpan = 4;
		gridData2.verticalAlignment = GridData.CENTER;
		GridLayout gridLayout2 = new GridLayout();
		gridLayout2.numColumns = 2;
		gridLayout2.verticalSpacing = 5;
		GridData gridData = new GridData();
		gridData.widthHint = -1;
		gridData.horizontalAlignment = GridData.FILL;
		gridData.verticalAlignment = GridData.CENTER;
		gridData.grabExcessHorizontalSpace = true;
		GridLayout gridLayout1 = new GridLayout();
		gridLayout1.numColumns = 2;
		GridData gridData1 = new GridData();
		gridData1.horizontalAlignment = GridData.FILL;
		gridData1.horizontalSpan = 3;
		gridData1.grabExcessHorizontalSpace = false;
		gridData1.verticalAlignment = GridData.CENTER;
		indexNameComposite = new Composite(this, SWT.NONE);
		indexNameComposite.setLayout(gridLayout2);
		indexNameComposite.setLayoutData(gridData2);
		indexNameLabel = new Label(indexNameComposite, SWT.NONE);
		indexNameLabel.setText(Messages.IndexName);
		indexNameText = new Text(indexNameComposite, SWT.BORDER);
		indexNameText.setLayoutData(gridData3);
	}

	/**
	 * @return the attributeSelectedList
	 */
	public List getAttributeSelectedList() {
		return attributeSelectedList;
	}

	/**
	 * @param attributeSelectedList
	 *            the attributeSelectedList to set
	 */
	public void setAttributeSelectedList(List attributeSelectedList) {
		this.attributeSelectedList = attributeSelectedList;
	}

	public void initializeValue(KeyModel keyModel, java.util.List<IAttribute> notSelectAttributes) {
		indexNameText.setText(keyModel.getName());
		selectModels = keyModel.getAttributes();
		notSelectModels = notSelectAttributes;
		uniqueCheckBox.setSelection(keyModel.isUnique());
		masterCheckBox.setEnabled(uniqueCheckBox.getSelection());
		masterCheckBox.setSelection(keyModel.isMasterKey());
		updateList();
	}

	public void updateList() {
		attributeSelectedList.removeAll();
		attributeNotSelectedList.removeAll();
		for (IAttribute a : selectModels) {
			attributeSelectedList.add(a.getName());
		}
		for (IAttribute a : notSelectModels) {
			attributeNotSelectedList.add(a.getName());
		}
	}

	/**
	 * @return the selectModels
	 */
	public java.util.List<IAttribute> getSelectModels() {
		return selectModels;
	}

	public String getIndexName() {
		return indexNameText.getText();
	}

	public boolean isUnique() {
		return uniqueCheckBox.getSelection();
	}

	public boolean isMasterKey() {
		return masterCheckBox.getSelection();
	}
}
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

import java.util.ArrayList;

import jp.sourceforge.tmdmaker.Messages;
import jp.sourceforge.tmdmaker.model.AbstractEntityModel;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;

/**
 * Model select panel.
 * 
 * @author nakag
 *
 */
public class ModelSelectPanel extends Composite {
	private java.util.List<AbstractEntityModel> selectModels = null;
	private java.util.List<AbstractEntityModel> notSelectModels = null;
	private List selectedList = null;
	private List candidateList = null;
	private Label selectedLabel = null;
	private Label candidateLabel = null;
	private Composite buttonsComposite = null;
	private Button selectButton = null;
	private Button removeButton = null;
	private Button selectAllButton = null;
	private Button removeAllButton = null;

	public ModelSelectPanel(Composite parent, int style) {
		super(parent, style);
		initialize();
	}

	private void initialize() {
		GridData gridData1 = new GridData();
		gridData1.verticalSpan = 4;
		gridData1.verticalAlignment = GridData.FILL;
		gridData1.widthHint = 150;
		gridData1.grabExcessVerticalSpace = false;
		gridData1.heightHint = 100;
		gridData1.horizontalAlignment = GridData.BEGINNING;
		GridData gridData = new GridData();
		gridData.grabExcessVerticalSpace = false;
		gridData.horizontalAlignment = GridData.BEGINNING;
		gridData.verticalAlignment = GridData.FILL;
		gridData.widthHint = 150;
		gridData.heightHint = 100;
		gridData.verticalSpan = 4;
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 3;
		selectedLabel = new Label(this, SWT.NONE);
		selectedLabel.setText(Messages.Select);
		Label filler = new Label(this, SWT.NONE);
		filler.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		candidateLabel = new Label(this, SWT.NONE);
		candidateLabel.setText(Messages.Unselect);
		selectedList = new List(this, SWT.MULTI | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		selectedList.setLayoutData(gridData);
		selectedList.addFocusListener(new org.eclipse.swt.events.FocusAdapter() {
			public void focusGained(org.eclipse.swt.events.FocusEvent e) {
				selectButton.setEnabled(false);
				removeButton.setEnabled(true);
				selectAllButton.setEnabled(false);
				removeAllButton.setEnabled(true);
				candidateList.setSelection(new int[0]);
			}
		});
		createButtonsComposite();
		candidateList = new List(this, SWT.MULTI | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		candidateList.setLayoutData(gridData1);
		candidateList.addFocusListener(new org.eclipse.swt.events.FocusAdapter() {
			public void focusGained(org.eclipse.swt.events.FocusEvent e) {
				selectButton.setEnabled(true);
				removeButton.setEnabled(false);
				selectAllButton.setEnabled(true);
				removeAllButton.setEnabled(false);
				selectedList.setSelection(new int[0]);
			}
		});
		this.setLayout(gridLayout);
		this.setSize(new Point(409, 159));
	}

	public void initializeValue(java.util.List<AbstractEntityModel> selectModels,
			java.util.List<AbstractEntityModel> notSelectModels) {
		this.selectModels = selectModels;
		this.notSelectModels = notSelectModels;
		updateList();
	}

	private void updateList() {
		selectedList.removeAll();
		candidateList.removeAll();
		for (AbstractEntityModel m : this.selectModels) {
			selectedList.add(m.getName());
		}
		for (AbstractEntityModel m : this.notSelectModels) {
			candidateList.add(m.getName());
		}
	}

	/**
	 * @return the selectModels
	 */
	public java.util.List<AbstractEntityModel> getSelectModels() {
		return selectModels;
	}

	/**
	 * @return the notSelectModels
	 */
	public java.util.List<AbstractEntityModel> getNotSelectModels() {
		return notSelectModels;
	}

	/**
	 * This method initializes buttonsComposite
	 *
	 */
	private void createButtonsComposite() {
		RowLayout rowLayout = new RowLayout();
		rowLayout.type = org.eclipse.swt.SWT.VERTICAL;
		rowLayout.justify = false;
		rowLayout.spacing = 3;
		rowLayout.fill = true;
		GridData gridData6 = new GridData();
		gridData6.horizontalAlignment = GridData.FILL;
		gridData6.verticalSpan = 4;
		gridData6.verticalAlignment = GridData.FILL;
		buttonsComposite = new Composite(this, SWT.NONE);
		buttonsComposite.setLayoutData(gridData6);
		buttonsComposite.setLayout(rowLayout);
		selectButton = new Button(buttonsComposite, SWT.NONE);
		selectButton.setText("<"); //$NON-NLS-1$
		selectButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				int[] selectedIndices = candidateList.getSelectionIndices();
				if (selectedIndices.length == 0) {
					return;
				}
				java.util.List<AbstractEntityModel> selectedModels = new ArrayList<AbstractEntityModel>();
				for (int i : selectedIndices) {
					selectedModels.add(notSelectModels.get(i));
				}
				notSelectModels.removeAll(selectedModels);
				selectModels.addAll(selectedModels);
				updateList();
			}
		});

		removeButton = new Button(buttonsComposite, SWT.NONE);
		removeButton.setText(">"); //$NON-NLS-1$
		removeButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				int[] selectedIndices = selectedList.getSelectionIndices();
				if (selectedIndices.length == 0) {
					return;
				}
				java.util.List<AbstractEntityModel> selectedModels = new ArrayList<AbstractEntityModel>();
				for (int i : selectedIndices) {
					selectedModels.add(selectModels.get(i));
				}
				selectModels.removeAll(selectedModels);
				notSelectModels.addAll(selectedModels);
				updateList();
			}
		});

		selectAllButton = new Button(buttonsComposite, SWT.NONE);
		selectAllButton.setText("<<"); //$NON-NLS-1$
		selectAllButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				selectModels.addAll(notSelectModels);
				notSelectModels.clear();
				updateList();
			}
		});

		removeAllButton = new Button(buttonsComposite, SWT.NONE);
		removeAllButton.setText(">>"); //$NON-NLS-1$
		removeAllButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				notSelectModels.addAll(selectModels);
				selectModels.clear();
				updateList();
			}
		});

	}

}

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
import java.util.List;

import jp.sourceforge.tmdmaker.Messages;
import jp.sourceforge.tmdmaker.dialog.AttributeDialog;
import jp.sourceforge.tmdmaker.dialog.model.EditAttribute;
import jp.sourceforge.tmdmaker.dialog.model.EditTable;
import jp.sourceforge.tmdmaker.model.IAttribute;

import org.eclipse.jface.dialogs.Dialog;
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
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

/**
 * Attribute setting panel.
 * 
 * @author nakag
 *
 */
public class AttributeSettingPanel extends Composite {
	private EditTable entity;
	private static final int EDIT_COLUMN = 0;
	private List<IAttribute> deletedAttributes = new ArrayList<IAttribute>();
	private int selectedIndex = -1;
	private TableEditor tableEditor = null;
	private Table attributeTable = null;
	private Composite controlComposite = null;
	private Button newButton = null;
	private Button deleteButton = null;
	private Button upButton = null;
	private Button downButton = null;
	private Button descButton = null;
	private Button identifierChangeButton = null;

	public AttributeSettingPanel(Composite parent, int style, EditTable entity) {
		super(parent, style);
		this.entity = entity;
		initialize();
	}

	private void initialize() {
		GridData gridData12 = new GridData();
		gridData12.grabExcessVerticalSpace = false;
		gridData12.verticalAlignment = GridData.FILL;
		gridData12.horizontalAlignment = GridData.BEGINNING;
		GridData gridData = new GridData();
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalAlignment = GridData.FILL;
		gridData.verticalAlignment = GridData.FILL;
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		attributeTable = new Table(this, SWT.FULL_SELECTION);
		tableEditor = new TableEditor(attributeTable);
		tableEditor.grabHorizontal = true;
		tableEditor.horizontalAlignment = SWT.LEFT;
		tableEditor.minimumWidth = 50;
		attributeTable.setHeaderVisible(true);
		attributeTable.setLayoutData(gridData12);
		attributeTable.setLayoutData(gridData);
		attributeTable.setLinesVisible(true);
		attributeTable.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				selectedIndex = attributeTable.getSelectionIndex();
				if (selectedIndex == -1) {
					return;
				}
				Control oldEditor = tableEditor.getEditor();
				if (oldEditor != null) {
					oldEditor.dispose();
				}

				TableItem item = (TableItem) e.item;
				final Text text = new Text(attributeTable, SWT.NONE);
				text.setText(item.getText(EDIT_COLUMN));
				text.addFocusListener(new FocusAdapter() {
					private String beforeName;

					/**
					 * 
					 * {@inheritDoc}
					 * 
					 * @see org.eclipse.swt.events.FocusAdapter#focusGained(org.eclipse.swt.events.FocusEvent)
					 */
					@Override
					public void focusGained(FocusEvent e) {
						beforeName = text.getText();
						if (beforeName == null) {
							beforeName = ""; //$NON-NLS-1$
						}
						super.focusGained(e);
					}

					/**
					 * {@inheritDoc}
					 *
					 * @see org.eclipse.swt.events.FocusAdapter#focusLost(org.eclipse.swt.events.FocusEvent)
					 */
					@Override
					public void focusLost(FocusEvent e) {
						TableItem item = tableEditor.getItem();
						if (item != null) {
							if (item.isDisposed())
								return;
							String editValue = text.getText();
							if (editValue == null) {
								editValue = ""; //$NON-NLS-1$
							}
							if (editValue.length() == 0) {
								editValue = beforeName;
							}
							item.setText(EDIT_COLUMN, editValue);
							EditAttribute ea = entity.getEditAttribute(selectedIndex);
							ea.setName(editValue);
						}
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
							editValue = ""; //$NON-NLS-1$
						}
						item.setText(EDIT_COLUMN, editValue);
						EditAttribute ea = entity.getEditAttribute(selectedIndex);
						ea.setName(editValue);
					}

				});
				text.selectAll();
				text.setFocus();

				tableEditor.setEditor(text, item, EDIT_COLUMN);
			}
		});
		TableColumn tableColumn = new TableColumn(attributeTable, SWT.NONE);
		tableColumn.setWidth(200);
		tableColumn.setText(Messages.Attribute);
		this.setLayout(gridLayout);
		createControlComposite();
		this.setSize(new Point(338, 213));
	}

	/**
	 * This method initializes controlComposite
	 *
	 */
	private void createControlComposite() {
		GridData gridData6 = new GridData();
		gridData6.horizontalAlignment = GridData.FILL;
		gridData6.verticalAlignment = GridData.CENTER;
		GridData gridData5 = new GridData();
		gridData5.horizontalAlignment = GridData.FILL;
		gridData5.verticalAlignment = GridData.CENTER;
		GridData gridData4 = new GridData();
		gridData4.widthHint = -1;
		gridData4.verticalAlignment = GridData.CENTER;
		gridData4.horizontalAlignment = GridData.FILL;
		GridData gridData3 = new GridData();
		gridData3.widthHint = 60;
		gridData3.verticalAlignment = GridData.CENTER;
		gridData3.horizontalAlignment = GridData.FILL;
		GridData gridData2 = new GridData();
		gridData2.widthHint = 60;
		gridData2.verticalAlignment = GridData.CENTER;
		gridData2.horizontalAlignment = GridData.FILL;
		GridData gridData1 = new GridData();
		gridData1.widthHint = 60;
		gridData1.verticalAlignment = GridData.CENTER;
		gridData1.horizontalAlignment = GridData.FILL;
		controlComposite = new Composite(this, SWT.NONE);
		controlComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 1, 1));
		controlComposite.setLayout(new GridLayout());
		newButton = new Button(controlComposite, SWT.NONE);
		newButton.setText(Messages.AddButton);
		newButton.setLayoutData(gridData1);
		newButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				entity.addAttribute();
				selectedIndex = entity.getMaxAttributeIndex();
				updateSelection();
			}
		});
		upButton = new Button(controlComposite, SWT.NONE);
		upButton.setText(Messages.UpButton);
		upButton.setLayoutData(gridData2);
		upButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				if (selectedIndex == -1 || selectedIndex == 0) {
					return;
				}
				entity.upAttribute(selectedIndex);
				selectedIndex--;
				updateSelection();
			}
		});
		downButton = new Button(controlComposite, SWT.NONE);
		downButton.setText(Messages.DownButton);
		downButton.setLayoutData(gridData3);
		downButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				if (selectedIndex == -1 || selectedIndex == entity.getMaxAttributeIndex()) {
					return;
				}
				entity.downAttribute(selectedIndex);
				selectedIndex++;
				updateSelection();
			}
		});
		descButton = new Button(controlComposite, SWT.NONE);
		descButton.setText(Messages.DescriptionButton);
		descButton.setLayoutData(gridData5);
		descButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				if (selectedIndex == -1) {
					return;
				}
				EditAttribute edit = entity.getEditAttribute(selectedIndex);

				AttributeDialog dialog = new AttributeDialog(getShell(), edit);
				if (dialog.open() == Dialog.OK) {
					EditAttribute edited = dialog.getEditedValue();
					entity.editAttribute(selectedIndex, edited);
					updateSelection();
				}

			}
		});
		deleteButton = new Button(controlComposite, SWT.NONE);
		deleteButton.setText(Messages.RemoveButton);
		deleteButton.setLayoutData(gridData4);
		deleteButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				if (selectedIndex == -1) {
					return;
				}
				Control oldEditor = tableEditor.getEditor();
				if (oldEditor != null) {
					oldEditor.dispose();
				}
				entity.deleteAttribute(selectedIndex);
				if (entity.getMaxAttributeIndex() <= selectedIndex) {
					selectedIndex--;
				}
				updateSelection();
			}
		});
		identifierChangeButton = new Button(controlComposite, SWT.NONE);
		identifierChangeButton.setText(Messages.ToIdentifier);
		identifierChangeButton.setLayoutData(gridData6);
		identifierChangeButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				if (selectedIndex == -1) {
					return;
				}
				entity.uptoIdentifier(selectedIndex);
				updateSelection();
			}
		});
		identifierChangeButton.setVisible(entity.canUpToIdentifier());
		updateAttributeTable();
	}

	public void updateAttributeTable() {
		attributeTable.removeAll();
		for (EditAttribute ea : entity.getAttributes()) {
			TableItem item = new TableItem(attributeTable, SWT.NULL);
			item.setText(0, ea.getName());
		}
	}

	private void updateSelection() {
		attributeTable.select(selectedIndex);
	}

	/**
	 * @return the deletedAttributes
	 */
	public List<IAttribute> getDeletedAttributeList() {
		return deletedAttributes;
	}

}
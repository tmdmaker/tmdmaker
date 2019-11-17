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

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.tmdmaker.core.model.StandardSQLDataType;
import org.tmdmaker.ui.Messages;
import org.tmdmaker.ui.dialogs.model.EditImplementAttribute;
import org.tmdmaker.ui.dialogs.model.EditImplementEntity;

/**
 * Implement info edit panel.
 * 
 * @author nakag
 *
 */
public class ImplementInfoEditPanel extends Composite {
	private static final String[] NULLABLES = { Messages.Restriction, Messages.Permit };
	private static final int COLUMN_NO_MODEL_NAME = 0;
	private static final int COLUMN_NO_ATTRIBUTE_NAME = 1;
	private static final int COLUMN_NO_IMPLEMENT_NAME = 2;
	private static final int COLUMN_NO_DATATYPE = 3;
	private static final int COLUMN_NO_SIZE = 4;
	private static final int COLUMN_NO_SCALE = 5;
	private static final int COLUMN_NO_NULLABLE = 6;
	private EditImplementEntity implementModel;
	private int tableSelectedIndex = -1;
	private List<EditImplementAttribute> attributes;
	private TableEditor tableEditor;
	private Label implementNameLabel = null;
	private Text implementNameText = null;
	private Table columnTable = null;

	public ImplementInfoEditPanel(Composite parent, int style, EditImplementEntity implementModel) {
		super(parent, style);
		initialize();
		this.implementModel = implementModel;
		this.attributes = implementModel.getAttributes();
		pack();
	}

	private void initialize() {
		GridData gridData11 = new GridData();
		gridData11.horizontalAlignment = GridData.FILL;
		gridData11.horizontalSpan = 2;
		gridData11.heightHint = 150;
		gridData11.grabExcessHorizontalSpace = false;
		gridData11.verticalAlignment = GridData.CENTER;
		GridData gridData10 = new GridData();
		gridData10.horizontalAlignment = GridData.FILL;
		gridData10.heightHint = 150;
		gridData10.horizontalSpan = 2;
		gridData10.verticalAlignment = GridData.CENTER;
		GridData gridData9 = new GridData();
		gridData9.horizontalAlignment = GridData.FILL;
		gridData9.heightHint = 150;
		gridData9.horizontalSpan = 2;
		gridData9.verticalAlignment = GridData.CENTER;
		GridData gridData8 = new GridData();
		gridData8.horizontalSpan = 2;
		gridData8.verticalAlignment = GridData.CENTER;
		gridData8.heightHint = 150;
		gridData8.horizontalAlignment = GridData.FILL;
		GridData gridData7 = new GridData();
		gridData7.horizontalAlignment = GridData.FILL;
		gridData7.horizontalSpan = 2;
		gridData7.heightHint = 150;
		gridData7.verticalAlignment = GridData.CENTER;
		GridData gridData6 = new GridData();
		gridData6.heightHint = 150;
		gridData6.horizontalAlignment = GridData.FILL;
		gridData6.verticalAlignment = GridData.CENTER;
		gridData6.horizontalSpan = 2;
		GridData gridData5 = new GridData();
		gridData5.heightHint = 150;
		gridData5.verticalAlignment = GridData.CENTER;
		gridData5.horizontalSpan = 2;
		gridData5.grabExcessHorizontalSpace = true;
		gridData5.horizontalAlignment = GridData.FILL;
		GridData gridData3 = new GridData();
		gridData3.horizontalAlignment = GridData.FILL;
		gridData3.horizontalSpan = 2;
		gridData3.grabExcessHorizontalSpace = true;
		gridData3.grabExcessVerticalSpace = false;
		gridData3.heightHint = 100;
		gridData3.verticalAlignment = GridData.CENTER;
		GridData gridData1 = new GridData();
		gridData1.grabExcessHorizontalSpace = false;
		gridData1.verticalAlignment = GridData.CENTER;
		gridData1.horizontalAlignment = GridData.FILL;
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalSpan = 2;
		gridData.horizontalIndent = 0;
		gridData.heightHint = 200;
		gridData.verticalAlignment = GridData.CENTER;
		implementNameLabel = new Label(this, SWT.NONE);
		implementNameLabel.setText(Messages.ImplementationName);
		implementNameText = new Text(this, SWT.BORDER);
		implementNameText.setLayoutData(gridData1);
		implementNameText.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {
				implementModel.setImplementName(((Text) e.widget).getText());
			}
		});
		columnTable = new Table(this, SWT.SINGLE | SWT.FULL_SELECTION);
		tableEditor = new TableEditor(columnTable);
		tableEditor.grabHorizontal = true;
		tableEditor.horizontalAlignment = SWT.LEFT;
		tableEditor.minimumWidth = 25;
		columnTable.setHeaderVisible(true);
		columnTable.setLayoutData(gridData11);
		columnTable.setLayoutData(gridData10);
		columnTable.setLayoutData(gridData9);
		columnTable.setLayoutData(gridData8);
		columnTable.setLayoutData(gridData7);
		columnTable.setLayoutData(gridData6);
		columnTable.setLayoutData(gridData5);
		columnTable.setLayoutData(gridData3);
		columnTable.setLayoutData(gridData);
		columnTable.setLinesVisible(true);
		TableColumn tableColumn11 = new TableColumn(columnTable, SWT.NONE);
		tableColumn11.setWidth(150);
		tableColumn11.setText(Messages.ModelName);
		columnTable.addMouseListener(new org.eclipse.swt.events.MouseAdapter() {
			@Override
			public void mouseDown(org.eclipse.swt.events.MouseEvent e) {
				tableSelectedIndex = columnTable.getSelectionIndex();

				if (tableSelectedIndex == -1) {
					return;
				}
				Control oldEditor = tableEditor.getEditor();
				if (oldEditor != null) {
					oldEditor.dispose();
				}
				TableItem item = columnTable.getItem(tableSelectedIndex);
				Point point = new Point(e.x, e.y);
				int selectedColumnIndex = getSelectedColumnIndex(item, point);
				// テーブルの特定の列をクリックした場合
				if (selectedColumnIndex != -1) {
					Control control = createEditorControl(selectedColumnIndex, columnTable, item);
					// 編集対象の列が選択された場合
					if (control != null) {
						addEventListeners(control);
						columnTable.setSelection(new int[0]);
						tableEditor.setEditor(control, item, selectedColumnIndex);
						if (control instanceof Text) {
							((Text) control).selectAll();
						}
						control.setFocus();
					}
				}
			}
		});
		TableColumn tableColumn = new TableColumn(columnTable, SWT.NONE);
		tableColumn.setWidth(150);
		tableColumn.setText(Messages.AttributeName);
		TableColumn tableColumn1 = new TableColumn(columnTable, SWT.NONE);
		tableColumn1.setWidth(150);
		tableColumn1.setText(Messages.ImplementationName);
		TableColumn tableColumn2 = new TableColumn(columnTable, SWT.NONE);
		tableColumn2.setWidth(150);
		tableColumn2.setText(Messages.DataType);
		TableColumn tableColumn21 = new TableColumn(columnTable, SWT.NONE);
		tableColumn21.setWidth(50);
		tableColumn21.setText(Messages.Size);
		TableColumn tableColumn31 = new TableColumn(columnTable, SWT.NONE);
		tableColumn31.setWidth(50);
		tableColumn31.setText(Messages.Scale);
		TableColumn tableColumn3 = new TableColumn(columnTable, SWT.NONE);
		tableColumn3.setWidth(60);
		tableColumn3.setText(Messages.Null);
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		this.setLayout(gridLayout);
		this.setSize(new Point(765, 249));
	}

	private int getSelectedColumnIndex(TableItem item, Point point) {
		for (int i = 0; i < columnTable.getColumnCount(); i++) {
			if (item.getBounds(i).contains(point)) {
				return i;
			}
		}
		return -1;
	}

	private void addEventListeners(Control control) {
		if (control instanceof Text) {
			final Text text = (Text) control;
			text.addFocusListener(new FocusAdapter() {
				int forcusGainIndex = -1;

				@Override
				public void focusGained(FocusEvent e) {
					forcusGainIndex = tableSelectedIndex;
					super.focusGained(e);
				}

				@Override
				public void focusLost(FocusEvent e) {
					setData(tableEditor.getColumn(), text.getText(), forcusGainIndex);
					updateTable();
					text.dispose();
				}
			});
			columnTable.setSelection(new int[0]);
			text.selectAll();
			text.setFocus();
		} else if (control instanceof Combo) {
			final Combo combo = (Combo) control;

			combo.addFocusListener(new FocusAdapter() {
				int forcusGainedIndex = -1;

				/**
				 * {@inheritDoc}
				 * 
				 * @see org.eclipse.swt.events.FocusAdapter#focusGained(org.eclipse.swt.events.FocusEvent)
				 */
				@Override
				public void focusGained(FocusEvent e) {
					forcusGainedIndex = tableSelectedIndex;
					super.focusGained(e);
				}

				/**
				 * {@inheritDoc}
				 * 
				 * @see org.eclipse.swt.events.FocusAdapter#focusLost(org.eclipse.swt.events.FocusEvent)
				 */
				@Override
				public void focusLost(FocusEvent e) {
					int index = combo.getSelectionIndex();
					if (index != -1) {
						setData(tableEditor.getColumn(), String.valueOf(index), forcusGainedIndex);
						updateTable();
					}
					combo.dispose();
				}

			});
			combo.addSelectionListener(new SelectionAdapter() {

				/**
				 * {@inheritDoc}
				 * 
				 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
				 */
				@Override
				public void widgetSelected(SelectionEvent e) {
					int index = combo.getSelectionIndex();
					if (index != -1) {
						setData(tableEditor.getColumn(), String.valueOf(index), tableSelectedIndex);
						updateTable();
					}
					combo.dispose();

				}

			});
		}
	}

	public void updateTable() {
		implementNameText.setText(implementModel.getImplementName());
		columnTable.removeAll();
		for (EditImplementAttribute a : attributes) {
			TableItem item = new TableItem(columnTable, SWT.NULL);
			item.setText(COLUMN_NO_MODEL_NAME, a.getContainerModel().getName());
			item.setText(COLUMN_NO_ATTRIBUTE_NAME, a.getName());
			item.setText(COLUMN_NO_IMPLEMENT_NAME, a.getImplementName());
			StandardSQLDataType type = a.getDataType();
			if (type != null) {
				item.setText(COLUMN_NO_DATATYPE, type.getName());
				if (type.isSupportSize()) {
					item.setText(COLUMN_NO_SIZE, a.getSize());
				} else {
					item.setText(COLUMN_NO_SIZE, ""); //$NON-NLS-1$
				}
				if (type.isSupportScale()) {
					item.setText(COLUMN_NO_SCALE, a.getScale());
				} else {
					item.setText(COLUMN_NO_SCALE, ""); //$NON-NLS-1$
				}
			} else {
				item.setText(COLUMN_NO_DATATYPE, ""); //$NON-NLS-1$
				item.setText(COLUMN_NO_SIZE, ""); //$NON-NLS-1$
				item.setText(COLUMN_NO_SCALE, ""); //$NON-NLS-1$
			}
			if (a.isNullable()) {
				item.setText(COLUMN_NO_NULLABLE, NULLABLES[1]);
			} else {
				item.setText(COLUMN_NO_NULLABLE, NULLABLES[0]);
			}
		}

	}

	private void setData(int columnIndex, String value, int attributeIndex) {
		EditImplementAttribute a = attributes.get(attributeIndex);

		switch (columnIndex) {
		case COLUMN_NO_ATTRIBUTE_NAME:
			a.setName(value);
			break;
		case COLUMN_NO_IMPLEMENT_NAME:
			a.setImplementName(value);
			break;
		case COLUMN_NO_DATATYPE:
			int index = Integer.parseInt(value);
			if (index > 0) {
				a.setDataType(StandardSQLDataType.values()[index - 1]);
				if (!a.getDataType().isSupportSize()) {
					a.setSize(""); //$NON-NLS-1$
				}
				if (!a.getDataType().isSupportScale()) {
					a.setScale(""); //$NON-NLS-1$
				}
			} else {
				a.setDataType(null);
				a.setSize(""); //$NON-NLS-1$
				a.setScale(""); //$NON-NLS-1$
			}
			break;
		case COLUMN_NO_SIZE:
			a.setSize(value);
			break;
		case COLUMN_NO_SCALE:
			a.setScale(value);
			break;
		case COLUMN_NO_NULLABLE:
			int selectedIndex = Integer.parseInt(value);
			a.setNullable(selectedIndex == 1);
			break;
		default:
			break;
		}
	}

	private Control createEditorControl(int columnIndex, Table columnTable, TableItem item) {
		Control control = null;
		switch (columnIndex) {
		case COLUMN_NO_MODEL_NAME:
		case COLUMN_NO_ATTRIBUTE_NAME:
			// モデル名,属性名は変更不可
			break;
		case COLUMN_NO_IMPLEMENT_NAME:
			control = createImplementNameColumn(columnIndex, columnTable, item);
			break;
		case COLUMN_NO_DATATYPE:
			control = createDatatypeColumn(columnTable);
			break;
		case COLUMN_NO_SIZE:
			control = createSizeColumn(columnIndex, columnTable, item, control);
			break;
		case COLUMN_NO_SCALE:
			control = createScaleColumn(columnIndex, columnTable, item, control);
			break;
		case COLUMN_NO_NULLABLE:
			control = createNullableColumn(columnIndex, columnTable, item);
			break;
		default:
			break;
		}
		return control;
	}

	private Combo createNullableColumn(int columnIndex, Table columnTable, TableItem item) {
		Combo nullableCombo = new Combo(columnTable, SWT.READ_ONLY);

		String value = item.getText(columnIndex);

		int index = 0;
		for (int i = 0; i < NULLABLES.length; i++) {
			String s = NULLABLES[i];
			if (s.equals(value)) {
				index = i;
			}
			nullableCombo.add(s);
		}
		nullableCombo.select(index);

		return nullableCombo;
	}

	private Control createScaleColumn(int columnIndex, Table columnTable, TableItem item,
			Control control) {

		StandardSQLDataType type = attributes.get(tableSelectedIndex).getDataType();
		if (type != null && type.isSupportScale()) {
			control = createImplementNameColumn(columnIndex, columnTable, item);
		}
		return control;
	}

	private Control createSizeColumn(int columnIndex, Table columnTable, TableItem item,
			Control control) {

		StandardSQLDataType type = attributes.get(tableSelectedIndex).getDataType();
		if (type != null && type.isSupportSize()) {
			control = createImplementNameColumn(columnIndex, columnTable, item);
		}
		return control;
	}

	private Combo createDatatypeColumn(Table columnTable) {
		Combo dataTypeCombo = new Combo(columnTable, SWT.READ_ONLY);

		int index = 0;
		StandardSQLDataType type = attributes.get(tableSelectedIndex).getDataType();
		if (type != null) {
			index = type.ordinal() + 1;
		}
		dataTypeCombo.add(""); //$NON-NLS-1$
		for (StandardSQLDataType dataType : StandardSQLDataType.values()) {
			dataTypeCombo.add(dataType.getName());
		}
		dataTypeCombo.select(index);

		return dataTypeCombo;
	}

	private Control createImplementNameColumn(int columnIndex, Table columnTable, TableItem item) {
		Control control;

		Text text = new Text(columnTable, SWT.NONE);
		text.setText(item.getText(columnIndex));
		control = text;

		return control;
	}

	public String getImplementName() {
		return implementNameText.getText();
	}

	/**
	 * @return the attributes
	 */
	public List<EditImplementAttribute> getAttributes() {
		return attributes;
	}

}

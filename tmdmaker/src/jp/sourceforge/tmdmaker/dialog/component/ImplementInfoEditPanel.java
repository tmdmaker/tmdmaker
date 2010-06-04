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

import java.util.List;

import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.EditAttribute;
import jp.sourceforge.tmdmaker.model.StandardSQLDataType;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
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

public class ImplementInfoEditPanel extends Composite {
	private static final String[] NULLABLES = {"禁止", "許可"};
	private static final int COLUMN_NO_MODEL_NAME = 0;
	private static final int COLUMN_NO_ATTRIBUTE_NAME = 1;
	private static final int COLUMN_NO_IMPLEMENT_NAME = 2;
	private static final int COLUMN_NO_DATATYPE = 3;
	private static final int COLUMN_NO_SIZE = 4;
	private static final int COLUMN_NO_SCALE = 5;
	private static final int COLUMN_NO_NULLABLE = 6;
	private AbstractEntityModel model;  //  @jve:decl-index=0:
	private int tableSelectedIndex = -1;
	private List<EditAttribute> attributes;  //  @jve:decl-index=0:
	private TableEditor tableEditor;
	private Label implementNameLabel = null;
	private Text implementNameText = null;
	private Table columnTable = null;

	public ImplementInfoEditPanel(Composite parent, int style) {
		super(parent, style);
		initialize();
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
		implementNameLabel.setText("実装名");
		implementNameText = new Text(this, SWT.BORDER);
		implementNameText.setLayoutData(gridData1);
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
		tableColumn11.setText("モデル名");
		columnTable.addMouseListener(new org.eclipse.swt.events.MouseAdapter() {
			public void mouseDown(org.eclipse.swt.events.MouseEvent e) {
				System.out.println("mouseDown()");
				tableSelectedIndex = columnTable.getSelectionIndex();
				
				if (tableSelectedIndex == -1) {
					System.out.println("Not Select");
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
//						TableColumn column = columnTable.getColumn(selectedColumnIndex);
//						System.out.println(column.getText());
					Control control = createEditorControl(selectedColumnIndex, columnTable, item);
					// 編集対象の列が選択された場合
					if (control != null) {
						addEventListeners(control);
						columnTable.setSelection(new int[0]);
						tableEditor.setEditor (control, item, selectedColumnIndex);
						if (control instanceof Text) {
							((Text) control).selectAll();
						}
						control.setFocus();
					}
				}
			}
//				for (int i = 0;i < columnTable.getColumnCount(); i++) {
//					System.out.println("column" + i);
//					if (item.getBounds(i).contains(point)) {
//						System.out.println("contains");
//						final int columnIndex = i;
//						TableColumn column = columnTable.getColumn(i);
//						System.out.println(column.getText());
//						Control control = createEditorControl(columnIndex, columnTable, item);
//						if (control instanceof Text) {
//	//						final Text text = new Text(columnTable, SWT.NONE);
//							final Text text = (Text) control;
//							text.setText(item.getText(i));
//							text.addFocusListener(new FocusAdapter(){
//								public void focusLost(FocusEvent e){
//									System.out.println("focusLost " + columnIndex);
//									TableItem item = tableEditor.getItem();
//									System.out.println("tableEditor.getColumn() = " + tableEditor.getColumn());
//									item.setText(tableEditor.getColumn(),text.getText());
//									text.dispose();
//								}
//							});
//							text.addModifyListener(new ModifyListener(){
//	
//								@Override
//								public void modifyText(ModifyEvent e) {
//									System.out.println("modifyText " + columnIndex);
//									TableItem item = tableEditor.getItem();
//									String editValue = text.getText();
//									if (editValue == null) {
//										editValue = "";
//									}
//									item.setText(tableEditor.getColumn(), editValue);
//								}	
//							});
//							columnTable.setSelection(new int[0]);
//							tableEditor.setEditor (text, item, i);
//							text.selectAll();
//							text.setFocus();
//						}
//					}
//				}
//			}
		});
		TableColumn tableColumn = new TableColumn(columnTable, SWT.NONE);
		tableColumn.setWidth(150);
		tableColumn.setText("属性名");
		TableColumn tableColumn1 = new TableColumn(columnTable, SWT.NONE);
		tableColumn1.setWidth(150);
		tableColumn1.setText("実装名");
		TableColumn tableColumn2 = new TableColumn(columnTable, SWT.NONE);
		tableColumn2.setWidth(150);
		tableColumn2.setText("型");
		TableColumn tableColumn21 = new TableColumn(columnTable, SWT.NONE);
		tableColumn21.setWidth(40);
		tableColumn21.setText("長さ");
		TableColumn tableColumn31 = new TableColumn(columnTable, SWT.NONE);
		tableColumn31.setWidth(40);
		tableColumn31.setText("少数");
		TableColumn tableColumn3 = new TableColumn(columnTable, SWT.NONE);
		tableColumn3.setWidth(60);
		tableColumn3.setText("Null");
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		this.setLayout(gridLayout);
		this.setSize(new Point(746, 199));
	}
	private int getSelectedColumnIndex(TableItem item, Point point) {
		for (int i = 0;i < columnTable.getColumnCount(); i++) {
			System.out.println("column" + i);
			if (item.getBounds(i).contains(point)) {
				return i;
			}
		}
		return -1;
	}
//	text.setText(item.getText(i));
	private void addEventListeners(Control control) {
		if (control instanceof Text) {
			final Text text = (Text) control;
			text.addFocusListener(new FocusAdapter(){
				public void focusLost(FocusEvent e){
					setData(tableEditor.getColumn(), text.getText());
					updateTable();
//					TableItem item = tableEditor.getItem();
//					item.setText(tableEditor.getColumn(), text.getText());
					text.dispose();
				}
			});
//			text.addModifyListener(new ModifyListener(){
//				@Override
//				public void modifyText(ModifyEvent e) {
//					TableItem item = tableEditor.getItem();
//					String editValue = text.getText();
//					if (editValue == null) {
//						editValue = "";
//					}
//					item.setText(tableEditor.getColumn(), editValue);
//				}	
//			});
			columnTable.setSelection(new int[0]);
			text.selectAll();
			text.setFocus();
		} else if (control instanceof Combo) {
			final Combo combo = (Combo) control;
			combo.addFocusListener(new FocusAdapter() {

				/**
				 * {@inheritDoc}
				 * 
				 * @see org.eclipse.swt.events.FocusAdapter#focusLost(org.eclipse.swt.events.FocusEvent)
				 */
				@Override
				public void focusLost(FocusEvent e) {
//					TableItem item = tableEditor.getItem();
					int index = combo.getSelectionIndex();
					if (index != -1) {
						setData(tableEditor.getColumn(), String.valueOf(index));
						updateTable();
//						item.setText(tableEditor.getColumn(), combo.getItem(index));
					}
					combo.dispose();
				}
				
			});
		}
	}
	public void initializeValue(AbstractEntityModel model, List<EditAttribute> attributes) {
//		TableItem item = null;
		this.attributes = attributes;
		this.model = model;
		implementNameText.setText(model.getImplementName());
		updateTable();
//		if (model instanceof Entity) {
//			attributes.add(((Entity)model).getIdentifier());
//		} else if (model instanceof Detail) {
//			attributes.add(((Detail)model).getDetailIdentifier());
//		}
//		// Re-usedをカラムとして追加
//		Map<AbstractEntityModel, ReusedIdentifier> reused = model
//				.getReusedIdentifieres();
//		for (Entry<AbstractEntityModel, ReusedIdentifier> entry : reused
//				.entrySet()) {
//			for (IdentifierRef ref : entry.getValue().getIdentifires()) {
//				attributes.add(ref);
//			}
//		}
//		attributes.addAll(model.getAttributes());
//		for (EditAttribute a : attributes) {
//			item = new TableItem(columnTable, SWT.NULL);
//			item.setText(0, model.getName());
//			item.setText(1, a.getName());
//			item.setText(2, a.getImplementName());
//			StandardSQLDataType type = a.getDataType();
//			if (type != null) {
//				item.setText(3, type.getName());
//				item.setText(4, a.getSize());
//				item.setText(5, a.getScale());
//			} else {
//				item.setText(3, "");
//				item.setText(4, "");
//				item.setText(5, "");
//			}
//			item.setText(6, "");
//		}
	}
	private void updateTable() {
		columnTable.removeAll();
		for (EditAttribute a : attributes) {
			TableItem item = new TableItem(columnTable, SWT.NULL);
			item.setText(COLUMN_NO_MODEL_NAME, model.getName());
			item.setText(COLUMN_NO_ATTRIBUTE_NAME, a.getName());
			item.setText(COLUMN_NO_IMPLEMENT_NAME, a.getImplementName());
			StandardSQLDataType type = a.getDataType();
			if (type != null) {
				item.setText(COLUMN_NO_DATATYPE, type.getName());
				if (type.isSupportSize()) {
					item.setText(COLUMN_NO_SIZE, a.getSize());
				} else {
					item.setText(COLUMN_NO_SIZE, "");					
				}
				if (type.isSupportScale()) {
					item.setText(COLUMN_NO_SCALE, a.getScale());
				} else {
					item.setText(COLUMN_NO_SCALE, "");					
				}
			} else {
				item.setText(COLUMN_NO_DATATYPE, "");
				item.setText(COLUMN_NO_SIZE, "");
				item.setText(COLUMN_NO_SCALE, "");
			}
			if (a.isNullable()) {
				item.setText(COLUMN_NO_NULLABLE, NULLABLES[1]);
			} else {
				item.setText(COLUMN_NO_NULLABLE, NULLABLES[0]);				
			}
		}
		
	}
	private void setData(int columnIndex, String value) {
		EditAttribute a = attributes.get(tableSelectedIndex);
		
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
						a.setSize("");
					}
					if (!a.getDataType().isSupportScale()) {
						a.setScale("");
					}
				} else {
					a.setDataType(null);
					a.setSize("");
					a.setScale("");					
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
				if (selectedIndex == 1) {
					a.setNullable(true);
				} else {
					a.setNullable(false);					
				}
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
				{
					Text text = new Text(columnTable, SWT.NONE);
					text.setText(item.getText(columnIndex));
					control = text;
				}
				break;
			case COLUMN_NO_DATATYPE:
				Combo dataTypeCombo = new Combo(columnTable, SWT.READ_ONLY);
				{
					int index = 0;
					StandardSQLDataType type = attributes.get(tableSelectedIndex).getDataType();
					if (type != null) {
						index = type.ordinal() + 1;
					}
					dataTypeCombo.add("");
					for (StandardSQLDataType dataType : StandardSQLDataType.values()) {
						dataTypeCombo.add(dataType.getName());
					}
					dataTypeCombo.select(index);
				}
				control = dataTypeCombo;
				break;
			case COLUMN_NO_SIZE:
				{
					StandardSQLDataType type = attributes.get(tableSelectedIndex).getDataType();
					if (type != null && type.isSupportSize()) {
						Text text = new Text(columnTable, SWT.NONE);
						text.setText(item.getText(columnIndex));
						control = text;					
					}
				}
				break;
			case COLUMN_NO_SCALE:
				{
					StandardSQLDataType type = attributes.get(tableSelectedIndex).getDataType();
					if (type != null && type.isSupportScale()) {
						Text text = new Text(columnTable, SWT.NONE);
						text.setText(item.getText(columnIndex));
						control = text;
					}				
				}
				break;
			case COLUMN_NO_NULLABLE:
				Combo nullableCombo = new Combo(columnTable, SWT.READ_ONLY);

				String value = item.getText(columnIndex);
				{
					int index = 0;
					for (int i = 0;i < NULLABLES.length; i++) {
						String s = NULLABLES[i];
						if (s.equals(value)) {
							index = i;
						}
						nullableCombo.add(s);
					}
					nullableCombo.select(index);
				}
				control = nullableCombo;
				break;
			default:
				break;
		}
		return control;
	}
	public String getImplementName() {
		return implementNameText.getText();
	}

	/**
	 * @return the attributes
	 */
	public List<EditAttribute> getAttributes() {
		return attributes;
	}
	
}  //  @jve:decl-index=0:visual-constraint="-9,-23"
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

import jp.sourceforge.tmdmaker.model.Attribute;
import jp.sourceforge.tmdmaker.model.DataTypeDeclaration;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

public class PhysicalDesignEditPanel extends Composite {
	private TableEditor tableEditor;
	private Label physicalTableNameLabel = null;
	private Text physicalTableNametText = null;
	private Table columnTable = null;

	public PhysicalDesignEditPanel(Composite parent, int style) {
		super(parent, style);
		initialize();
	}

	private void initialize() {
		GridData gridData1 = new GridData();
		gridData1.grabExcessHorizontalSpace = false;
		gridData1.verticalAlignment = GridData.CENTER;
		gridData1.horizontalAlignment = GridData.FILL;
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = false;
		gridData.horizontalSpan = 2;
		gridData.horizontalIndent = 0;
		gridData.heightHint = 200;
		gridData.verticalAlignment = GridData.CENTER;
		physicalTableNameLabel = new Label(this, SWT.NONE);
		physicalTableNameLabel.setText("物理名");
		physicalTableNametText = new Text(this, SWT.BORDER);
		physicalTableNametText.setLayoutData(gridData1);
		columnTable = new Table(this, SWT.SINGLE | SWT.FULL_SELECTION);
		tableEditor = new TableEditor(columnTable);
		tableEditor.grabHorizontal = true;
		tableEditor.horizontalAlignment = SWT.LEFT;
		tableEditor.minimumWidth = 25;
		columnTable.setHeaderVisible(true);
		columnTable.setLayoutData(gridData);
		columnTable.setLinesVisible(true);
		columnTable.addMouseListener(new org.eclipse.swt.events.MouseAdapter() {
			public void mouseDown(org.eclipse.swt.events.MouseEvent e) {
				System.out.println("mouseDown()");
				int index = columnTable.getSelectionIndex();
				if (index == -1) {
					System.out.println("Not Select");
					return;
				}
				Control oldEditor = tableEditor.getEditor();
				if (oldEditor != null) {
					oldEditor.dispose();
				}
				TableItem item = columnTable.getItem(index);
				Point point = new Point(e.x, e.y);
				
				for (int i = 0;i < columnTable.getColumnCount(); i++) {
					System.out.println("column" + i);
					if (item.getBounds(i).contains(point)) {
						System.out.println("contains");
						final int columnIndex = i;
						final Text text = new Text(columnTable, SWT.NONE);
						text.setText(item.getText(i));
						text.addFocusListener(new FocusAdapter(){
							public void focusLost(FocusEvent e){
								System.out.println("focusLost " + columnIndex);
								TableItem item = tableEditor.getItem();
								item.setText(columnIndex,text.getText());
								text.dispose();
							}
						});
						text.addModifyListener(new ModifyListener(){

							@Override
							public void modifyText(ModifyEvent e) {
								System.out.println("modifyText " + columnIndex);
								TableItem item = tableEditor.getItem();
								String editValue = text.getText();
								if (editValue == null) {
									editValue = "";
								}
								item.setText(tableEditor.getColumn(), editValue);
							}	
						});
						columnTable.setSelection(new int[0]);
						tableEditor.setEditor (text, item, i);
						text.selectAll();
						text.setFocus();
					}
				}
			}
		});
		TableColumn tableColumn = new TableColumn(columnTable, SWT.NONE);
		tableColumn.setWidth(150);
		tableColumn.setText("アトリビュート");
		TableColumn tableColumn1 = new TableColumn(columnTable, SWT.NONE);
		tableColumn1.setWidth(150);
		tableColumn1.setText("物理名");
		TableColumn tableColumn2 = new TableColumn(columnTable, SWT.NONE);
		tableColumn2.setWidth(50);
		tableColumn2.setText("型");
		TableColumn tableColumn21 = new TableColumn(columnTable, SWT.NONE);
		tableColumn21.setWidth(40);
		tableColumn21.setText("長さ");
		TableColumn tableColumn31 = new TableColumn(columnTable, SWT.NONE);
		tableColumn31.setWidth(40);
		tableColumn31.setText("少数");
		TableColumn tableColumn3 = new TableColumn(columnTable, SWT.NONE);
		tableColumn3.setWidth(40);
		tableColumn3.setText("Null");
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 3;
		this.setLayout(gridLayout);
		this.setSize(new Point(501, 249));
	}
	public void initializeData(List<Attribute> attributes) {
		TableItem item = null;
		for (Attribute a : attributes) {
			item = new TableItem(columnTable, SWT.NULL);
			item.setText(0, a.getName());
			item.setText(1, "a");
//			item.setText(1, a.getPhysicalName());
			DataTypeDeclaration type = a.getDataTypeDeclaration();
			if (type != null) {
				item.setText(2, type.getLogicalType().getName());
				item.setText(3, String.valueOf(type.getSize()));
				item.setText(4, String.valueOf(type.getScale()));
			}
			item.setText(5, "");
		}
	}
}  //  @jve:decl-index=0:visual-constraint="-18,-16"

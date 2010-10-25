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

import jp.sourceforge.tmdmaker.TMDPlugin;
import jp.sourceforge.tmdmaker.dialog.IndexEditDialog;
import jp.sourceforge.tmdmaker.dialog.model.EditImplementAttribute;
import jp.sourceforge.tmdmaker.dialog.model.EditImplementEntity;
import jp.sourceforge.tmdmaker.model.IAttribute;
import jp.sourceforge.tmdmaker.model.KeyModel;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

/**
 * @author nakaG
 *
 */
public class IndexSettingPanel extends Composite {
	private int selectColumnIndex = -1;
	private EditImplementEntity implementModel;  //  @jve:decl-index=0:
	private Table indexTable = null;
	private Button addButton = null;
	private Button deleteButton = null;
	private Button updateButton = null;
	
	public IndexSettingPanel(Composite parent, int style, EditImplementEntity model) {
		super(parent, style);
		initialize();
		this.implementModel = model;
	}

	private void initialize() {
		GridData gridData1 = new GridData();
		gridData1.horizontalAlignment = GridData.CENTER;
		gridData1.verticalAlignment = GridData.BEGINNING;
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.verticalSpan = 3;
		gridData.grabExcessVerticalSpace = false;
		gridData.heightHint = 150;
		gridData.grabExcessHorizontalSpace = true;
		gridData.verticalAlignment = GridData.FILL;
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		indexTable = new Table(this, SWT.MULTI);
		indexTable.setHeaderVisible(true);
		indexTable.setLayoutData(gridData);
		indexTable.setLinesVisible(true);
		addButton = new Button(this, SWT.NONE);
		addButton.setText("追加");
		updateButton = new Button(this, SWT.NONE);
		updateButton.setText("更新");
		updateButton
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						System.out.println("widgetSelected()"); // TODO Auto-generated Event stub widgetSelected()
						if (selectColumnIndex == -1) {
							return;
						}
						KeyModel model = implementModel.getKeyModel(selectColumnIndex);
						java.util.List<IAttribute> notSelectedAttributes = new ArrayList<IAttribute>();
						for (EditImplementAttribute ea : implementModel.getAttributes()) {
							notSelectedAttributes.add(ea.getOriginalAttribute());
						}
						IndexEditDialog dialog = new IndexEditDialog(getShell(), model, implementModel.getAttributes());
						if (dialog.open() == Dialog.OK) {
							KeyModel keyModel = dialog.getKeyModel();
							implementModel.replaceKeyModel(selectColumnIndex, keyModel);
						}
					}
				});
		deleteButton = new Button(this, SWT.NONE);
		deleteButton.setText("削除");
		deleteButton.setLayoutData(gridData1);
		deleteButton
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						System.out.println("widgetSelected()"); // TODO Auto-generated Event stub widgetSelected()
						if (selectColumnIndex == -1) {
							return;
						}
						implementModel.removeKeyModel(selectColumnIndex);
					}
				});
		addButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				System.out.println("widgetSelected()"); // TODO Auto-generated Event stub widgetSelected()
				java.util.List<IAttribute> notSelectedAttributes = new ArrayList<IAttribute>();
				for (EditImplementAttribute ea : implementModel.getAttributes()) {
					notSelectedAttributes.add(ea.getOriginalAttribute());
				}
				IndexEditDialog dialog = new IndexEditDialog(getShell(), implementModel.getImplementName(), implementModel.getAttributes());
				if (dialog.open() == Dialog.OK) {
					KeyModel keyModel = dialog.getKeyModel();
					implementModel.addKeyModel(keyModel);
				}
			}
		});
		
		TableColumn tableColumn = new TableColumn(indexTable, SWT.NONE);
		tableColumn.setWidth(150);
		tableColumn.setText("属性名");
		this.setLayout(gridLayout);
		this.setSize(new Point(318, 176));
	}

	private void setupTableColumns() {
		int columnSize = indexTable.getColumnCount() -1;
		int keyCount = implementModel.getKeyModels().size();
		TableColumn[] columns = indexTable.getColumns();
		for (int i = 0;i < columnSize - keyCount;i++ ){
			columns[i + 1].dispose();
		}
		
		for (int i = columnSize; i < keyCount; i++) {
			TableColumn tableColumn = new TableColumn(indexTable, SWT.NONE);
			tableColumn.setWidth(60);
			tableColumn.setAlignment(SWT.CENTER);
			tableColumn.setText(String.valueOf(i + 1));
			tableColumn.addSelectionListener(new SelectionAdapter() {

				/**
				 * {@inheritDoc}
				 * 
				 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
				 */
				@Override
				public void widgetSelected(SelectionEvent e) {
					// TODO Auto-generated method stub
//					super.widgetSelected(e);
					TableColumn selectColumn = (TableColumn)e.widget;
					
					System.out.println("select column is " + selectColumn.getText());
					TableColumn[] columns = indexTable.getColumns();
					selectColumnIndex = -1;
					for (int i = 0;i < columns.length; i++) {
						columns[i].setImage(null);
						if (columns[i].equals(selectColumn)) {
							// 1列目（i=0)は実装名カラムのため2列目からがキーモデルのindexとなる
							selectColumnIndex = i - 1;
						}
					}
					selectColumn.setImage(TMDPlugin.getImage("icons/column_select.gif"));
				}
			});
		}
	}
	public void updateTable() {
		setupTableColumns();
		indexTable.removeAll();
		int size = implementModel.getKeyModels().size();
		if (size < selectColumnIndex) {
			selectColumnIndex = size;
		}
		for (int i = 0; i < size; i++) {
			TableColumn column = indexTable.getColumn(i + 1);
			if (implementModel.getKeyModel(i).isMasterKey()) {
				column.setText("M");
			} else {
				column.setText(String.valueOf(i + 1));
			}
		}
		for (EditImplementAttribute attribute : implementModel.getAttributes()) {
			TableItem item = new TableItem(indexTable, SWT.NONE);
			item.setText(0, attribute.getName());
			java.util.List<String> keyOrders = attribute.getKeyOrders();
			int orderSize = keyOrders.size();
			for (int i = 0;i < orderSize; i++) {
				item.setText(i + 1, keyOrders.get(i));
			}
		}
	}
 }  //  @jve:decl-index=0:visual-constraint="0,0"

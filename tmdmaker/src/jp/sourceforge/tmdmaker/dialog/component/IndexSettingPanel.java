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

import jp.sourceforge.tmdmaker.dialog.IndexEditDialog;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

/**
 * @author nakaG
 *
 */
public class IndexSettingPanel extends Composite {

	private Table indexTable = null;
	private Button addButton = null;
	private Button deleteButton = null;
	public IndexSettingPanel(Composite parent, int style) {
		super(parent, style);
		initialize();
	}

	private void initialize() {
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.BEGINNING;
		gridData.verticalSpan = 2;
		gridData.verticalAlignment = GridData.FILL;
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		indexTable = new Table(this, SWT.MULTI);
		indexTable.setHeaderVisible(true);
		indexTable.setLayoutData(gridData);
		indexTable.setLinesVisible(true);
		addButton = new Button(this, SWT.NONE);
		addButton.setText("追加");
		addButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				System.out.println("widgetSelected()"); // TODO Auto-generated Event stub widgetSelected()
				IndexEditDialog dialog = new IndexEditDialog(getShell());
				if (dialog.open() == Dialog.OK) {
					
				}
			}
		});
		deleteButton = new Button(this, SWT.NONE);
		deleteButton.setText("削除");
		
		TableColumn tableColumn = new TableColumn(indexTable, SWT.NONE);
		tableColumn.setWidth(150);
		tableColumn.setText("実装名");
		TableColumn tableColumn1 = new TableColumn(indexTable, SWT.NONE);
		tableColumn1.setWidth(60);
		tableColumn1.setText("M/N");
		this.setLayout(gridLayout);
		setSize(new Point(300, 200));
	}

}

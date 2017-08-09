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
package jp.sourceforge.tmdmaker.ui.dialogs.components;

import jp.sourceforge.tmdmaker.Messages;
import jp.sourceforge.tmdmaker.extension.DialectProviderFactory;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.layout.GridData;

/**
 * Database setting panel.
 * 
 * @author nakag
 *
 */
public class DatabaseSettingPanel extends Composite {

	private Label databaseNameLabel = null;
	private Combo databaseCombo = null;

	public DatabaseSettingPanel(Composite parent, int style) {
		super(parent, style);
		initialize();
	}

	private void initialize() {
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		databaseNameLabel = new Label(this, SWT.NONE);
		databaseNameLabel.setText(Messages.DatabaseName);
		this.setLayout(gridLayout);
		createDatabaseCombo();
		setSize(new Point(217, 40));
	}

	/**
	 * This method initializes databaseCombo
	 *
	 */
	private void createDatabaseCombo() {
		GridData gridData = new GridData();
		gridData.grabExcessHorizontalSpace = true;
		gridData.verticalAlignment = GridData.CENTER;
		gridData.horizontalAlignment = GridData.FILL;
		databaseCombo = new Combo(this, SWT.READ_ONLY);
		databaseCombo.setLayoutData(gridData);
		databaseCombo.add(""); //$NON-NLS-1$
		for (String database : DialectProviderFactory.getDialectProvider().getDatabaseList()) {
			databaseCombo.add(database);
		}
	}

	public void initializeValue(String databaseName) {
		int index = databaseCombo.indexOf(databaseName);
		if (index == -1) {
			index = 0;
		}
		databaseCombo.select(index);
	}

	public String getSelectedDatabaseName() {
		int index = databaseCombo.getSelectionIndex();
		return databaseCombo.getItem(index);
	}
}

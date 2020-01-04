/*
 * Copyright 2009-2019 TMD-Maker Project <https://www.tmdmaker.org>
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
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.tmdmaker.ui.Messages;

/**
 * Combination Table Type setting panel.
 * 
 * @author nakag
 *
 */
public class CombinationTableTypePanel extends Composite {
	private Button btnRadioButton_1;
	private Button btnRadioButton_0;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public CombinationTableTypePanel(Composite parent, int style) {
		super(parent, style);
		RowLayout rowLayout = new RowLayout(SWT.HORIZONTAL);
		rowLayout.justify = true;
		setLayout(rowLayout);
		
		btnRadioButton_0 = new Button(this, SWT.RADIO);
		btnRadioButton_0.setText(Messages.CombinationTable_LTruth);
		
		btnRadioButton_1 = new Button(this, SWT.RADIO);
		btnRadioButton_1.setText(Messages.CombinationTable_FTruth);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	public Button getBtnRadioButton_1() {
		return btnRadioButton_1;
	}
	public Button getBtnRadioButton_0() {
		return btnRadioButton_0;
	}
}

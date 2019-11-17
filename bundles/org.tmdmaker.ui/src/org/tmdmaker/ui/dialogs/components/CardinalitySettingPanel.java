/*
 * Copyright 2009-2017 TMD-Maker Project <http://tmdmaker.osdn.jp/>
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
import org.tmdmaker.core.model.Cardinality;

/**
 * Cardinality setting panel.
 * 
 * @author nakag
 *
 */
public class CardinalitySettingPanel extends Composite {
	private Button btnOneRadio;
	private Button btnManyRadio;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public CardinalitySettingPanel(Composite parent, int style) {
		super(parent, style);
		setLayout(new RowLayout(SWT.VERTICAL));

		btnOneRadio = new Button(this, SWT.RADIO);
		btnOneRadio.setText(Cardinality.ONE.getLabel());

		btnManyRadio = new Button(this, SWT.RADIO);
		btnManyRadio.setText(Cardinality.MANY.getLabel());

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	protected Button getBtnOneRadio() {
		return btnOneRadio;
	}

	protected Button getBtnManyRadio() {
		return btnManyRadio;
	}

	public void setCardinaliry(Cardinality cardinality) {
		btnOneRadio.setSelection(Cardinality.ONE.equals(cardinality));
		btnManyRadio.setSelection(Cardinality.MANY.equals(cardinality));
	}

	public Cardinality getSelectedCardinality() {
		if (btnOneRadio.getSelection()) {
			return Cardinality.ONE;
		}
		return Cardinality.MANY;
	}
}

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
package org.tmdmaker.ui.dialogs;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.tmdmaker.core.model.Cardinality;
import org.tmdmaker.ui.Messages;
import org.tmdmaker.ui.dialogs.components.CardinalitySettingPanel;

/**
 * リレーションシップ編集ダイアログ
 * 
 * @author nakaG
 * 
 */
public class RelationshipEditDialog extends Dialog {
	private static final String NOT_MATCH = Messages.NoRelationship;
	private Cardinality sourceCardinality = Cardinality.ONE;
	private Cardinality targetCardinality = Cardinality.ONE;
	private boolean sourceNoInstance;
	private boolean targetNoInstance;
	private String sourceName;
	private String targetName;
	private Button sourceCardinalityCheck;
	private Button targetCardinalityCheck;
	private CardinalitySettingPanel sourceCardinalityPanel;
	private CardinalitySettingPanel targetCardinalityPanel;
	private Label label_1;
	private Label label_2;

	/**
	 * コンストラクタ
	 * 
	 * @param parentShell
	 *            親
	 * @param sourceName
	 *            Fromのエンティティ名
	 * @param targetName
	 *            Toのエンティティ名
	 * @wbp.parser.constructor
	 */
	public RelationshipEditDialog(Shell parentShell, String sourceName, String targetName) {
		super(parentShell);
		this.sourceName = sourceName;
		this.targetName = targetName;
	}

	/**
	 * コンストラクタ
	 * 
	 * @param parentShell
	 *            親
	 * @param sourceName
	 *            Fromのエンティティ名
	 * @param targetName
	 *            Toのエンティティ名
	 * @param sourceCardinaliry
	 *            Fromのカーディナリティ
	 * @param targetCardinality
	 *            Toのカーディナリティ
	 * @param sourceNoInstance
	 *            Fromの0のカーディナリティ有無
	 * @param targetNoInstance
	 *            Toの0のカーディナリティ有無
	 */
	public RelationshipEditDialog(Shell parentShell, String sourceName, String targetName,
			Cardinality sourceCardinaliry, Cardinality targetCardinality, boolean sourceNoInstance,
			boolean targetNoInstance) {
		this(parentShell, sourceName, targetName);
		this.sourceCardinality = sourceCardinaliry;
		this.targetCardinality = targetCardinality;
		this.sourceNoInstance = sourceNoInstance;
		this.targetNoInstance = targetNoInstance;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		getShell().setText(Messages.EditRelationship);
		Composite composite = new Composite(parent, SWT.NULL);
		composite.setLayout(new GridLayout(5, false));
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));
		Label label = new Label(composite, SWT.NULL);
		label.setText(sourceName);
		sourceCardinalityPanel = new CardinalitySettingPanel(composite, SWT.NULL);
		sourceCardinalityPanel
				.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		sourceCardinalityPanel.setCardinaliry(sourceCardinality);
		label_1 = new Label(composite, SWT.NULL);
		label_1.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		label_1.setText(":"); //$NON-NLS-1$
		targetCardinalityPanel = new CardinalitySettingPanel(composite, SWT.NULL);
		targetCardinalityPanel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		targetCardinalityPanel.setCardinaliry(targetCardinality);
		label_2 = new Label(composite, SWT.NULL);
		label_2.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label_2.setText(targetName);
		sourceCardinalityCheck = new Button(composite, SWT.CHECK);
		sourceCardinalityCheck.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		sourceCardinalityCheck.setText(NOT_MATCH);
		sourceCardinalityCheck.setSelection(this.sourceNoInstance);
		sourceCardinalityCheck.addSelectionListener(new SelectionAdapter() {
			/**
			 * 
			 * {@inheritDoc}
			 * 
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			@Override
			public void widgetSelected(SelectionEvent e) {
				Button bBut = (Button) e.widget;
				if (bBut.getSelection()) {
					sourceNoInstance = true;
				} else {
					sourceNoInstance = false;
				}
			}

		});
		new Label(composite, SWT.NONE);
		targetCardinalityCheck = new Button(composite, SWT.CHECK);
		targetCardinalityCheck.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 2, 1));
		targetCardinalityCheck.setText(NOT_MATCH);
		targetCardinalityCheck.setSelection(this.targetNoInstance);
		targetCardinalityCheck.addSelectionListener(new SelectionAdapter() {

			/**
			 * 
			 * {@inheritDoc}
			 * 
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			@Override
			public void widgetSelected(SelectionEvent e) {
				Button bBut = (Button) e.widget;
				if (bBut.getSelection()) {
					targetNoInstance = true;
				} else {
					targetNoInstance = false;
				}
			}

		});
		composite.pack();
		return composite;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#okPressed()
	 */
	@Override
	protected void okPressed() {
		this.sourceCardinality = sourceCardinalityPanel.getSelectedCardinality();
		this.targetCardinality = targetCardinalityPanel.getSelectedCardinality();
		super.okPressed();
	}

	public Cardinality getSourceCardinality() {
		return sourceCardinality;
	}

	public Cardinality getTargetCardinality() {
		return targetCardinality;
	}

	/**
	 * @return the sourceNoInstance
	 */
	public boolean isSourceNoInstance() {
		return sourceNoInstance;
	}

	/**
	 * @return the targetNoInstance
	 */
	public boolean isTargetNoInstance() {
		return targetNoInstance;
	}
}

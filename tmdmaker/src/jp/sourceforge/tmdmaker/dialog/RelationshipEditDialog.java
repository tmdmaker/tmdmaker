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
package jp.sourceforge.tmdmaker.dialog;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import jp.sourceforge.tmdmaker.Messages;
import jp.sourceforge.tmdmaker.model.Cardinality;

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
	private boolean sourceNoInstance, targetNoInstance;
	private String sourceName, targetName;
	private Combo sourceCardinalityCombo, targetCardinalityCombo;
	private Button sourceCardinalityCheck, targetCardinalityCheck;

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

	// /* (non-Javadoc)
	// * @see org.eclipse.jface.window.Window#constrainShellSize()
	// */
	// @Override
	// protected void constrainShellSize() {
	// Shell shell = getShell();
	// shell.pack();
	// shell.setSize(200, shell.getSize().y);
	// }

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
		sourceCardinalityCombo = new Combo(composite, SWT.READ_ONLY);
		sourceCardinalityCombo.add(Cardinality.ONE.getLabel());
		sourceCardinalityCombo.add(Cardinality.MANY.getLabel());
		sourceCardinalityCombo.select(sourceCardinalityCombo.indexOf(sourceCardinality.getLabel()));
		label = new Label(composite, SWT.NULL);
		label.setText(":"); //$NON-NLS-1$
		targetCardinalityCombo = new Combo(composite, SWT.READ_ONLY);
		targetCardinalityCombo.add(Cardinality.ONE.getLabel());
		targetCardinalityCombo.add(Cardinality.MANY.getLabel());
		targetCardinalityCombo.select(targetCardinalityCombo.indexOf(targetCardinality.getLabel()));
		label = new Label(composite, SWT.NULL);
		label.setText(targetName);

		label = new Label(composite, SWT.NULL);
		label.setText(NOT_MATCH);
		sourceCardinalityCheck = new Button(composite, SWT.CHECK);
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
		label = new Label(composite, SWT.NULL);
		label.setText(""); //$NON-NLS-1$
		label = new Label(composite, SWT.NULL);
		label.setText(NOT_MATCH);
		targetCardinalityCheck = new Button(composite, SWT.CHECK);
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
		this.sourceCardinality = getSelectedCardinality(sourceCardinalityCombo);
		this.targetCardinality = getSelectedCardinality(targetCardinalityCombo);
		super.okPressed();
	}

	private Cardinality getSelectedCardinality(Combo combo) {
		String selectedLabel = combo.getItem(combo.getSelectionIndex());
		if (selectedLabel.equals(Cardinality.ONE.getLabel())) {
			return Cardinality.ONE;
		} else {
			return Cardinality.MANY;
		}
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

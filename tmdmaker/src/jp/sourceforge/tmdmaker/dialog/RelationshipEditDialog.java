package jp.sourceforge.tmdmaker.dialog;

import jp.sourceforge.tmdmaker.model.Cardinality;

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

public class RelationshipEditDialog extends Dialog {
	private Cardinality sourceCardinality = Cardinality.ONE;
	private Cardinality targetCardinality = Cardinality.ONE;
	private boolean sourceNoInstance, targetNoInstance;
	private String sourceName, targetName;
	private Combo sourceCardinalityCombo, targetCardinalityCombo;
	private Button sourceCardinalityCheck, targetCardinalityCheck;

	public RelationshipEditDialog(Shell parentShell, String sourceName, String targetName) {
		super(parentShell);
		this.sourceName = sourceName;
		this.targetName = targetName;
	}

	public RelationshipEditDialog(Shell parentShell, String sourceName, String targetName, Cardinality sourceCardinaliry, Cardinality targetCardinality, boolean sourceNoInstance, boolean targetNoInstance) {
		this(parentShell, sourceName, targetName);
		this.sourceCardinality = sourceCardinaliry;
		this.targetCardinality = targetCardinality;
		this.sourceNoInstance = sourceNoInstance;
		this.targetNoInstance = targetNoInstance;
	}

//	/* (non-Javadoc)
//	 * @see org.eclipse.jface.window.Window#constrainShellSize()
//	 */
//	@Override
//	protected void constrainShellSize() {
//		Shell shell = getShell();
//		shell.pack();
//		shell.setSize(200, shell.getSize().y);
//	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		getShell().setText("Relationship編集");
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
		label.setText(":");
		targetCardinalityCombo = new Combo(composite, SWT.READ_ONLY);
		targetCardinalityCombo.add(Cardinality.ONE.getLabel());
		targetCardinalityCombo.add(Cardinality.MANY.getLabel());
		targetCardinalityCombo.select(targetCardinalityCombo.indexOf(targetCardinality.getLabel()));
		label = new Label(composite, SWT.NULL);
		label.setText(targetName);

		label = new Label(composite, SWT.NULL);
		label.setText("対応なし");
		sourceCardinalityCheck = new Button(composite, SWT.CHECK);
		sourceCardinalityCheck.setSelection(this.sourceNoInstance);
		sourceCardinalityCheck.addSelectionListener(new SelectionAdapter() {

			/* (non-Javadoc)
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
		label.setText("");
		label = new Label(composite, SWT.NULL);
		label.setText("対応なし");
		targetCardinalityCheck = new Button(composite, SWT.CHECK);
		targetCardinalityCheck.setSelection(this.targetNoInstance);
		targetCardinalityCheck.addSelectionListener(new SelectionAdapter() {

			/* (non-Javadoc)
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
		return composite;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#okPressed()
	 */
	@Override
	protected void okPressed() {
		this.sourceCardinality = getSelectedCardinality(sourceCardinalityCombo);
		this.targetCardinality = getSelectedCardinality(targetCardinalityCombo);;
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

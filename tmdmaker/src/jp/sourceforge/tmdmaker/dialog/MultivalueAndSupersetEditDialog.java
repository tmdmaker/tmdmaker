package jp.sourceforge.tmdmaker.dialog;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * 
 * @author nakaG
 * 
 */
public class MultivalueAndSupersetEditDialog extends Dialog {
	/** 名称入力欄 */
	private Text inputNameText;
	private String inputName;
	private String originalName;
	/**
	 * コンストラクタ
	 * 
	 * @param parentShell
	 *            親
	 */
	public MultivalueAndSupersetEditDialog(Shell parentShell, String originalName) {
		super(parentShell);
		this.originalName = originalName;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		getShell().setText("みなしスーパーセット編集");

		Composite composite = new Composite(parent, SWT.NULL);
		composite.setLayout(new GridLayout(2, false));
		GridData gridData = new GridData(GridData.FILL_BOTH);
		composite.setLayoutData(gridData);
		Label label = new Label(composite, SWT.NULL);
		label.setText("名称");
		inputNameText = new Text(composite, SWT.BORDER);
		gridData = new GridData(GridData.FILL_BOTH);
		gridData.widthHint = 100;
		inputNameText.setLayoutData(gridData);
		initializeValue();
		return composite;
	}
	private void initializeValue() {
		inputNameText.setText(originalName);
	}
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#okPressed()
	 */
	@Override
	protected void okPressed() {
		inputName = inputNameText.getText();
		super.okPressed();
	}

	public String getInputName() {
		return inputName;
	}
}

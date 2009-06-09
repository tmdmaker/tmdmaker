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
public class MultivalueOrEntityCreateDialog extends Dialog {
	/** 種別名入力欄 */
	private Text typeName;
	private String inputTypeName;
	
	/**
	 * コンストラクタ
	 * @param parentShell 親
	 */
	public MultivalueOrEntityCreateDialog(Shell parentShell) {
		super(parentShell);
		// TODO Auto-generated constructor stub
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		getShell().setText("MO作成");

		Composite composite = new Composite(parent, SWT.NULL);
		composite.setLayout(new GridLayout(2, false));
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));
		Label label = new Label(composite, SWT.NULL);
		label.setText("種別名");
		typeName = new Text(composite, SWT.BORDER);
		
	return composite;
	}
	
	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.jface.dialogs.Dialog#okPressed()
	 */
	@Override
	protected void okPressed() {
		inputTypeName = typeName.getText();
		super.okPressed();
	}

	public String getInputTypeName() {
		return inputTypeName;
	}
}

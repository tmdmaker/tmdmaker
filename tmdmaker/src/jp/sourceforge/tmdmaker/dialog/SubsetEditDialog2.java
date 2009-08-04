package jp.sourceforge.tmdmaker.dialog;

import java.util.List;

import jp.sourceforge.tmdmaker.model.Attribute;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

public class SubsetEditDialog2 extends Dialog {
	private SubsetSettingPanel panel;
	private List<Attribute> attributes;
	/**
	 * 
	 * @param parentShell 親
	 */
	public SubsetEditDialog2(Shell parentShell, List<Attribute> attributes) {
		super(parentShell);
		this.attributes = attributes;
		// TODO Auto-generated constructor stub
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite composite = new Composite(parent, SWT.NULL);
		panel = new SubsetSettingPanel(composite, SWT.NULL);
		panel.initializeValue(true, false, this.attributes, null, null);
		return composite;
	}

}

package jp.sourceforge.tmdmaker.dialog;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.widgets.Shell;

public class EntityCreateDialog1 extends Dialog {

	public EntityCreateDialog1(Shell parentShell) {
		super(parentShell);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		getShell().setText("エンティティ新規作成");

		Composite composite = new Composite(parent, SWT.NULL);
//		composite.setLayout(new FillLayout(SWT.VERTICAL));
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		composite.setLayout(layout);
		GridData gridData;
		
		composite.setSize(400,150);
		EntityNameAndTypeSettingPanel newPart = new EntityNameAndTypeSettingPanel(composite, SWT.NULL);
		newPart.setSize(400, 100);
		gridData = new GridData(GridData.FILL_BOTH);
		newPart.setLayoutData(gridData);
		ExpandBar expandBar = new ExpandBar(composite, SWT.V_SCROLL);
//		expandBar.setLayout(new FillLayout(SWT.HORIZONTAL));
//		expandBar.setSize(200, 350);
		ExpandItem item1 = new ExpandItem(expandBar, SWT.NULL);
		item1.setText("属性編集");
		item1.setExpanded(true);

//		Composite composite2 = new Composite(expandBar, SWT.NULL);
//		expandBar.setLayout(new FillLayout(SWT.VERTICAL));

		gridData = new GridData(GridData.FILL_BOTH);
		
		EntityAttributeSettingPanel attributePanel = new EntityAttributeSettingPanel(expandBar, SWT.NULL);
		expandBar.setLayoutData(gridData);
//		item1.setControl(composite2);
		item1.setControl(attributePanel);
		item1.setHeight(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT).y/2);
		
		return composite;
	}

	
}

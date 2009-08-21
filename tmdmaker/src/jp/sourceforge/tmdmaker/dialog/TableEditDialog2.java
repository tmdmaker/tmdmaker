package jp.sourceforge.tmdmaker.dialog;

import java.util.ArrayList;
import java.util.List;

import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.Attribute;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

/**
 * 表編集ダイアログ
 * 
 * @author nakaG
 * 
 */
public class TableEditDialog2 extends Dialog {
	/** 編集対象モデル */
	private AbstractEntityModel original;
	/** 編集用アトリビュートリスト */
	private List<EditAttribute> editAttributeList = new ArrayList<EditAttribute>();
	/** ダイアログタイトル */
	private String title;
	/** 表名設定用 */
	private TableNameSettingPanel panel1;
	/** アトリビュート設定用 */
	private AttributeSettingPanel panel2;

	/**
	 * コンストラクタ
	 * 
	 * @param parentShell
	 *            親
	 * @param title
	 *            ダイアログのタイトル
	 * @param original
	 *            編集対象モデル
	 */
	protected TableEditDialog2(Shell parentShell, String title,
			AbstractEntityModel original) {
		super(parentShell);
		this.title = title;
		this.original = original;
		for (Attribute a : this.original.getAttributes()) {
			editAttributeList.add(new EditAttribute(a));
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		getShell().setText(title);
		Composite composite = new Composite(parent, SWT.NULL);

		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 1;
		composite.setLayout(gridLayout);

		panel1 = new TableNameSettingPanel(composite, SWT.NULL);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		panel1.setLayoutData(gridData);

		gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalIndent = 5;
		Button notImplementCheck = new Button(composite, SWT.CHECK);
		notImplementCheck.setText("実装しない");
		notImplementCheck.setLayoutData(gridData);
		notImplementCheck.setSelection(original.isNotImplement());

		gridData = new GridData(GridData.FILL_HORIZONTAL);
		panel2 = new AttributeSettingPanel(composite, SWT.NULL);
		panel2.setLayoutData(gridData);
		panel2.setAttributeTableRow(editAttributeList);

		composite.pack();
		return composite;
	}

}

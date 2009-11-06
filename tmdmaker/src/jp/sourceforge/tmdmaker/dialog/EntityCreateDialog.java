package jp.sourceforge.tmdmaker.dialog;

import jp.sourceforge.tmdmaker.dialog.component.EntityNameAndTypeSettingPanel;
import jp.sourceforge.tmdmaker.model.EditAttribute;
import jp.sourceforge.tmdmaker.model.EntityType;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

/**
 * エンティティ新規作成ダイアログ
 * 
 * @author nakaG
 * 
 */
public class EntityCreateDialog extends Dialog {
	/** 個体指示子名称 */
	private String inputIdentifierName;
	/** エンティティ名称 */
	private String inputEntityName;
	/** 類別 */
	private EntityType inputEntityType = EntityType.RESOURCE;
	/** エンティティ名称・種類設定用パネル */
	private EntityNameAndTypeSettingPanel panel;

	/**
	 * コンストラクタ
	 * 
	 * @param parentShell
	 *            親
	 */
	public EntityCreateDialog(Shell parentShell) {
		super(parentShell);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		getShell().setText("エンティティ新規作成");

		Composite composite = new Composite(parent, SWT.NULL);

		panel = new EntityNameAndTypeSettingPanel(composite, SWT.NULL);
		panel.setEditIdentifier(new EditAttribute());
//		panel.setInitialFocus();
		
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
		this.inputIdentifierName = this.panel.getIdentifierName();
		this.inputEntityType = this.panel.getSelectedType();
		this.inputEntityName = this.panel.getEntityName();
		if (validate()) {
			super.okPressed();
		} else {

			return;
		}
	}

	/**
	 * ダイアログ検証
	 * 
	 * @return 必須事項が全て入力されている場合にtrueを返す
	 */
	private boolean validate() {
		return this.inputIdentifierName != null
				&& this.inputIdentifierName.length() > 0
				&& this.inputEntityName != null
				&& this.inputEntityName.length() > 0;
	}

	/**
	 * @return the inputIdentifierName
	 */
	public String getInputIdentifierName() {
		return inputIdentifierName;
	}

	/**
	 * @return the inputEntityType
	 */
	public EntityType getInputEntityType() {
		return inputEntityType;
	}

	/**
	 * @return the inputEntityName
	 */
	public String getInputEntityName() {
		return inputEntityName;
	}

}
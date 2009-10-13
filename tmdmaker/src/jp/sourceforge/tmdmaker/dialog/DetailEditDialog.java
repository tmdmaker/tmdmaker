package jp.sourceforge.tmdmaker.dialog;

import java.util.ArrayList;
import java.util.List;

import jp.sourceforge.tmdmaker.dialog.component.AttributeSettingPanel;
import jp.sourceforge.tmdmaker.dialog.component.DetailIdentifierSettingPanel;
import jp.sourceforge.tmdmaker.dialog.component.TableNameSettingPanel;
import jp.sourceforge.tmdmaker.model.Attribute;
import jp.sourceforge.tmdmaker.model.Detail;
import jp.sourceforge.tmdmaker.model.EditAttribute;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

/**
 * DTL表編集ダイアログ
 * 
 * @author nakaG
 * 
 */
public class DetailEditDialog extends Dialog {
	/** 編集対象モデル */
	private Detail original;
	/** 編集結果格納用 */
	private Detail editedValue;
	/** 編集用アトリビュートリスト */
	private List<EditAttribute> editAttributeList = new ArrayList<EditAttribute>();
	/** 実装可否設定用 */
	private Button notImplementCheck;
	/** 表名設定用 */
	private TableNameSettingPanel panel1;
	/** アトリビュート設定用 */
	private AttributeSettingPanel panel2;
	/** Detail個体指示子設定用 */
	private DetailIdentifierSettingPanel panel3;

	private List<Attribute> newAttributeOrder = new ArrayList<Attribute>();
	private List<Attribute> addAttributes = new ArrayList<Attribute>();
	private List<Attribute> editAttributes = new ArrayList<Attribute>();

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
	public DetailEditDialog(Shell parentShell, Detail original) {
		super(parentShell);
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
		getShell().setText("DTL表編集");
		Composite composite = new Composite(parent, SWT.NULL);

		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 1;
		composite.setLayout(gridLayout);

		panel1 = new TableNameSettingPanel(composite, SWT.NULL);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		panel1.setLayoutData(gridData);

		gridData = new GridData(GridData.FILL_HORIZONTAL);
		panel3 = new DetailIdentifierSettingPanel(composite, SWT.NULL);
		panel3.setLayoutData(gridData);
		
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalIndent = 5;
		notImplementCheck = new Button(composite, SWT.CHECK);
		notImplementCheck.setText("実装しない");
		notImplementCheck.setLayoutData(gridData);

		gridData = new GridData(GridData.FILL_HORIZONTAL);
		panel2 = new AttributeSettingPanel(composite, SWT.NULL);
		panel2.setLayoutData(gridData);

		composite.pack();

		initializeValue();

		return composite;
	}
	/**
	 * ダイアログへ初期値を設定する
	 */
	private void initializeValue() {
		panel1.setTableName(original.getName());

		notImplementCheck.setSelection(original.isNotImplement());

		panel2.setAttributeTableRow(editAttributeList);

		panel3.setIdentifierName(original.getDetailIdentifier().getName());
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.jface.dialogs.Dialog#okPressed()
	 */
	@Override
	protected void okPressed() {
		try {
			editedValue = original.getClass().newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		editedValue.setName(panel1.getTableName());
		editedValue.setDetailIdeitifierName(panel3.getIdentifierName());
		editedValue.setNotImplement(notImplementCheck.getSelection());
		createEditAttributeResult();
		
		super.okPressed();
	}
	private void createEditAttributeResult() {

		for (EditAttribute ea : editAttributeList) {
			Attribute originalAttribute = ea.getOriginalAttribute();
			if (originalAttribute == null) {
				originalAttribute = new Attribute(ea.getName());
				addAttributes.add(originalAttribute);
			} else {
				if (originalAttribute.getName().equals(ea.getName()) == false) {
					// AttributeEditCommand editCommand = new
					// AttributeEditCommand(original, ea.getName());
					// ccommand.add(editCommand);
					editAttributes.add(originalAttribute);
				}
			}
			newAttributeOrder.add(originalAttribute);
		}
//		deleteAttributes = panel2.getDeletedAttributeList();
		editedValue.setAttributes(newAttributeOrder);
	}

	/**
	 * @return the editAttributeList
	 */
	public List<EditAttribute> getEditAttributeList() {
		return editAttributeList;
	}

	/**
	 * @return the editedValue
	 */
	public Detail getEditedValue() {
		return editedValue;
	}

}

package jp.sourceforge.tmdmaker.dialog;

import java.util.ArrayList;
import java.util.List;

import jp.sourceforge.tmdmaker.model.Attribute;
import jp.sourceforge.tmdmaker.model.EntityType;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

public class EntityEditDialog2 extends Dialog {
	/** エンティティ名、個体指示子、エンティティ種類設定用 */
	private EntityNameAndTypeSettingPanel panel1;
	/** アトリビュート設定用 */
	private AttributeSettingPanel panel2;

	private String oldIdentifierName;
	private String oldEntityName;
	private EntityType oldEntityType;
	
	private String editIdentifierName;
	private String editEntityName;
	private EntityType editEntityType;
	private List<EditAttribute> editAttributeList = new ArrayList<EditAttribute>();
	/**
	 * コンストラクタ
	 * 
	 * @param parentShell 親
	 */
	public EntityEditDialog2(Shell parentShell, String oldIdentifierName, String oldEntityName, EntityType oldEntityType, boolean canEditEntityType, final List<Attribute> attributeList) {
		super(parentShell);
		this.oldIdentifierName = oldIdentifierName;
		this.oldEntityName = oldEntityName;
		this.oldEntityType = oldEntityType;
		
		for (Attribute a : attributeList) {
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
		getShell().setText("エンティティ編集");
		Composite composite = new Composite(parent, SWT.NULL);
		composite.setLayout(new FillLayout(SWT.VERTICAL));
		panel1 = new EntityNameAndTypeSettingPanel(composite, SWT.NULL);
		panel1.initializeValue(oldIdentifierName, oldEntityName, oldEntityType);
		
		panel2 = new AttributeSettingPanel(composite, SWT.NULL);
		panel2.initializeTableValue(editAttributeList);
		composite.pack();
		return composite;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.jface.dialogs.Dialog#okPressed()
	 */
	@Override
	protected void okPressed() {
		this.editIdentifierName = panel1.getIdentifierName();
		this.editEntityName = panel1.getEntityName();
		this.editEntityType = panel1.getSelectedType();

		super.okPressed();
	}

	/**
	 * @return the editIdentifierName
	 */
	public String getEditIdentifierName() {
		return editIdentifierName;
	}

	/**
	 * @return the editEntityName
	 */
	public String getEditEntityName() {
		return editEntityName;
	}

	/**
	 * @return the editEntityType
	 */
	public EntityType getEditEntityType() {
		return editEntityType;
	}

	/**
	 * @return the editAttributeList
	 */
	public List<EditAttribute> getEditAttributeList() {
		return editAttributeList;
	}
	public List<EditAttribute> getDeletedAttributeList() {
		return panel2.getDeletedAttributeList();
	}
}

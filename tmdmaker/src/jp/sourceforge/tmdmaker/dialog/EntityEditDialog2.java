package jp.sourceforge.tmdmaker.dialog;

import java.util.ArrayList;
import java.util.List;

import jp.sourceforge.tmdmaker.model.Attribute;
import jp.sourceforge.tmdmaker.model.Entity;
import jp.sourceforge.tmdmaker.model.EntityType;
import jp.sourceforge.tmdmaker.model.Identifier;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

/**
 * 
 * @author nakaG
 * 
 */
public class EntityEditDialog2 extends Dialog {
	/** エンティティ名、個体指示子、エンティティ種類設定用 */
	private EntityNameAndTypeSettingPanel panel1;
	/** アトリビュート設定用 */
	private AttributeSettingPanel panel2;
	/** 実装可否設定用 */
	private Button notImplementCheck;
	private String oldIdentifierName;
	private String oldEntityName;
	private EntityType oldEntityType;

	private String editIdentifierName;
	private String editEntityName;
	private EntityType editEntityType;
	private List<EditAttribute> editAttributeList = new ArrayList<EditAttribute>();
	private Entity original;
	/** 編集結果格納用 */
	private Entity editedValueEntity;
	private List<Attribute> newAttributeOrder = new ArrayList<Attribute>();
	private List<Attribute> addAttributes = new ArrayList<Attribute>();
	private List<Attribute> editAttributes = new ArrayList<Attribute>();
	private List<Attribute> deleteAttributes = new ArrayList<Attribute>();

	/**
	 * コンストラクタ
	 * 
	 * @param parentShell
	 *            親
	 */
	// public EntityEditDialog2(Shell parentShell, String oldIdentifierName,
	// String oldEntityName, EntityType oldEntityType, boolean
	// canEditEntityType, final List<Attribute> attributeList) {
	// super(parentShell);
	// this.oldIdentifierName = oldIdentifierName;
	// this.oldEntityName = oldEntityName;
	// this.oldEntityType = oldEntityType;
	//		
	// for (Attribute a : attributeList) {
	// editAttributeList.add(new EditAttribute(a));
	// }
	// }
	/**
	 * コンストラクタ
	 * 
	 * @param parentShell
	 *            親
	 * @param original
	 *            編集対象エンティティ
	 */
	public EntityEditDialog2(Shell parentShell, final Entity original) {
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
		getShell().setText("エンティティ編集");
		Composite composite = new Composite(parent, SWT.NULL);
		// composite.setLayout(new FillLayout(SWT.VERTICAL));
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 1;
		composite.setLayout(gridLayout);

		panel1 = new EntityNameAndTypeSettingPanel(composite, SWT.NULL);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		panel1.setLayoutData(gridData);

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

	private void initializeValue() {
		// panel1.initializeValue(oldIdentifierName, oldEntityName,
		// oldEntityType);
		panel1.setIdentifierNameText(original.getIdentifier().getName());
		panel1.setEntityNameText(original.getName());
		panel1.selectEntityTypeCombo(original.getEntityType());
		panel1.selectAutoCreateCheckBox(original.getIdentifier().getName(),
				original.getName());
		panel1.setEntityTypeComboEnabled(original.isEntityTypeEditable());

		notImplementCheck.setSelection(original.isNotImplement());

		panel2.setAttributeTableRow(editAttributeList);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#okPressed()
	 */
	@Override
	protected void okPressed() {
		// this.editIdentifierName = panel1.getIdentifierName();
		// this.editEntityName = panel1.getEntityName();
		// this.editEntityType = panel1.getSelectedType();
		this.editedValueEntity = new Entity();
		this.editedValueEntity.setIdentifier(new Identifier(panel1
				.getIdentifierName()));
		this.editedValueEntity.setName(panel1.getEntityName());
		this.editedValueEntity.setEntityType(panel1.getSelectedType());
		this.editedValueEntity
				.setNotImplement(notImplementCheck.getSelection());
		createEditAttributeResult();
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

	/**
	 * @return the editedValueEntity
	 */
	public Entity getEditedValueEntity() {
		return editedValueEntity;
	}

	/**
	 * @return the addAttributes
	 */
	public List<Attribute> getAddAttributes() {
		return addAttributes;
	}

	/**
	 * @return the editAttributes
	 */
	public List<Attribute> getEditAttributes() {
		return editAttributes;
	}

	/**
	 * @return the deleteAttributes
	 */
	public List<Attribute> getDeleteAttributes() {
		return deleteAttributes;
	}

	private void createEditAttributeResult() {
		// List<Attribute> newAttributeOrder = new ArrayList<Attribute>();
		// List<Attribute> addAttributes = new ArrayList<Attribute>();
		// List<Attribute> editAttributes = new ArrayList<Attribute>();

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
		deleteAttributes = panel2.getDeletedAttributeList();
		editedValueEntity.setAttributes(newAttributeOrder);
	}
}

/*
 * Copyright 2009-2010 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jp.sourceforge.tmdmaker.dialog;

import java.util.ArrayList;
import java.util.List;

import jp.sourceforge.tmdmaker.dialog.component.AttributeSettingPanel;
import jp.sourceforge.tmdmaker.dialog.component.EntityNameAndTypeSettingPanel;
import jp.sourceforge.tmdmaker.dialog.component.ImplementInfoSettingPanel;
import jp.sourceforge.tmdmaker.dialog.model.EditAttribute;
import jp.sourceforge.tmdmaker.model.Entity;
import jp.sourceforge.tmdmaker.model.IAttribute;
import jp.sourceforge.tmdmaker.model.Identifier;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

/**
 * エンティティ編集ダイアログ
 * 
 * @author nakaG
 * 
 */
public class EntityEditDialog extends Dialog {
	/** エンティティ名、個体指定子、エンティティ種類設定用 */
	private EntityNameAndTypeSettingPanel panel1;
	/** アトリビュート設定用 */
	private AttributeSettingPanel panel2;
	/** 実装可否設定用 */
	private ImplementInfoSettingPanel panel3;

	/** 編集用アトリビュート */
	private List<EditAttribute> editAttributeList = new ArrayList<EditAttribute>();
	/** 編集元エンティティ */
	private Entity original;
	/** 編集結果格納用 */
	private Entity editedValueEntity;
	private List<IAttribute> newAttributeOrder = new ArrayList<IAttribute>();
	private List<IAttribute> addAttributes = new ArrayList<IAttribute>();
	private List<IAttribute> editAttributes = new ArrayList<IAttribute>();
	private List<IAttribute> deleteAttributes = new ArrayList<IAttribute>();

	/**
	 * コンストラクタ
	 * 
	 * @param parentShell
	 *            親
	 */
	// public EntityEditDialog(Shell parentShell, String oldIdentifierName,
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
	public EntityEditDialog(Shell parentShell, final Entity original) {
		super(parentShell);
		this.original = original;
		for (IAttribute a : this.original.getAttributes()) {
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
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 1;
		composite.setLayout(gridLayout);

		panel1 = new EntityNameAndTypeSettingPanel(composite, SWT.NULL);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		panel1.setLayoutData(gridData);

		panel3 = new ImplementInfoSettingPanel(composite, SWT.NULL);
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		panel3.setLayoutData(gridData);

		gridData = new GridData(GridData.FILL_HORIZONTAL);
		panel2 = new AttributeSettingPanel(composite, SWT.NULL);
		panel2.setLayoutData(gridData);

		composite.pack();
		initializeValue();

		return composite;
	}

	private void initializeValue() {
		panel1.setEditIdentifier(new EditAttribute(original.getIdentifier()));
		panel1.setIdentifierNameText(original.getIdentifier().getName());
		panel1.setEntityNameText(original.getName());
		panel1.selectEntityTypeCombo(original.getEntityType());
		panel1.selectAutoCreateCheckBox(original.getIdentifier().getName(),
				original.getName());
		panel1.setEntityTypeComboEnabled(original.isEntityTypeEditable());

		panel3.initializeValue(original.isNotImplement(), original
				.getImplementName());
		panel2.setAttributeTableRow(editAttributeList);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#okPressed()
	 */
	@Override
	protected void okPressed() {
		this.editedValueEntity = new Entity();
		Identifier newIdentifier = new Identifier(panel1.getIdentifierName());
		EditAttribute editIdentifier = panel1.getEditIdentifier();
		editIdentifier.copyTo(newIdentifier);
		this.editedValueEntity.setIdentifier(newIdentifier);
		this.editedValueEntity.setName(panel1.getEntityName());
		this.editedValueEntity.setEntityType(panel1.getSelectedType());
		this.editedValueEntity.setNotImplement(panel3.isNotImplement());
		this.editedValueEntity.setImplementName(panel3.getImplementName());
		createEditAttributeResult();
		super.okPressed();
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
	public List<IAttribute> getAddAttributes() {
		return addAttributes;
	}

	/**
	 * @return the editAttributes
	 */
	public List<IAttribute> getEditAttributes() {
		return editAttributes;
	}

	/**
	 * @return the deleteAttributes
	 */
	public List<IAttribute> getDeleteAttributes() {
		return deleteAttributes;
	}

	private void createEditAttributeResult() {

		for (EditAttribute ea : editAttributeList) {
			IAttribute originalAttribute = ea.getOriginalAttribute();
			if (ea.isAdded()) {
				ea.copyToOriginal();
				addAttributes.add(ea.getOriginalAttribute());
			} else {
				if (ea.isNameChanged()) {
					editAttributes.add(originalAttribute);
				}
			}
			newAttributeOrder.add(originalAttribute);
		}
		deleteAttributes = panel2.getDeletedAttributeList();
		editedValueEntity.setAttributes(newAttributeOrder);
	}
}

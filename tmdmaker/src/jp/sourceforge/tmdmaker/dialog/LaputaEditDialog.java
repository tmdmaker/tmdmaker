/*
 * Copyright 2009-2011 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import jp.sourceforge.tmdmaker.dialog.component.AttributeSettingPanel;
import jp.sourceforge.tmdmaker.dialog.component.EntityNameAndTypeSettingPanel;
import jp.sourceforge.tmdmaker.dialog.model.EditAttribute;
import jp.sourceforge.tmdmaker.dialog.model.EditTable;
import jp.sourceforge.tmdmaker.model.Identifier;
import jp.sourceforge.tmdmaker.model.Laputa;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

/**
 * ラピュタ編集ダイアログ
 * 
 * @author nakaG
 * 
 */
public class LaputaEditDialog extends Dialog implements PropertyChangeListener {
	/** エンティティ名、個体指定子、エンティティ種類設定用 */
	private EntityNameAndTypeSettingPanel panel1;
	/** アトリビュート設定用 */
	private AttributeSettingPanel panel2;

	/** 編集元エンティティ */
	private Laputa original;
	private EditTable entity;
	/** 編集結果格納用 */
	private Laputa editedValueEntity;

	/**
	 * コンストラクタ
	 * 
	 * @param parentShell
	 *            親
	 * @param original
	 *            編集対象エンティティ
	 */
	public LaputaEditDialog(Shell parentShell, final Laputa original) {
		super(parentShell);
		this.original = original;
		entity = new EditTable(original);
		entity.addPropertyChangeListener(this);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals(EditTable.PROPERTY_ATTRIBUTES)) {
			panel2.updateAttributeTable();
		}
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#close()
	 */
	@Override
	public boolean close() {
		entity.removePropertyChangeListener(this);
		return super.close();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		getShell().setText("ラピュタ編集");

		Composite composite = new Composite(parent, SWT.NULL);
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 1;
		composite.setLayout(gridLayout);

		panel1 = new EntityNameAndTypeSettingPanel(composite, SWT.NULL);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		panel1.setLayoutData(gridData);

		gridData = new GridData(GridData.FILL_HORIZONTAL);
		panel2 = new AttributeSettingPanel(composite, SWT.NULL, entity);
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
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#okPressed()
	 */
	@Override
	protected void okPressed() {
		this.editedValueEntity = new Laputa();
		Identifier newIdentifier = new Identifier(panel1.getIdentifierName());
		EditAttribute editIdentifier = panel1.getEditIdentifier();
		editIdentifier.copyTo(newIdentifier);
		this.editedValueEntity.setIdentifier(newIdentifier);
		this.editedValueEntity.setName(panel1.getEntityName());
		this.editedValueEntity.setEntityType(panel1.getSelectedType());
		this.editedValueEntity.setAttributes(entity.getAttributesOrder());

		super.okPressed();
	}

	/**
	 * @return the editAttributeList
	 */
	public List<EditAttribute> getEditAttributeList() {
		return entity.getAttributes();
	}

	/**
	 * @return the editedValueEntity
	 */
	public Laputa getEditedValueEntity() {
		return editedValueEntity;
	}
}

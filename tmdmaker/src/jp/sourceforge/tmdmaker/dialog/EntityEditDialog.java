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
import jp.sourceforge.tmdmaker.dialog.component.EntityNameAndIdentifierNameAndTypeSettingPanel;
import jp.sourceforge.tmdmaker.dialog.component.ImplementInfoSettingPanel;
import jp.sourceforge.tmdmaker.dialog.model.EditAttribute;
import jp.sourceforge.tmdmaker.dialog.model.EditEntity;
import jp.sourceforge.tmdmaker.model.Entity;
import jp.sourceforge.tmdmaker.model.Identifier;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

/**
 * エンティティ編集ダイアログ
 * 
 * @author nakaG
 * 
 */
public class EntityEditDialog extends Dialog implements PropertyChangeListener {
	/** エンティティ名、個体指定子、エンティティ種類設定用 */
	private EntityNameAndIdentifierNameAndTypeSettingPanel panel1;
	/** アトリビュート設定用 */
	private AttributeSettingPanel panel2;
	/** 実装可否設定用 */
	private ImplementInfoSettingPanel panel3;

	/** 編集元エンティティ */
	private EditEntity entity;
	/** 編集結果格納用 */
	private Entity editedValueEntity;

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
		entity = new EditEntity(original);
		entity.addPropertyChangeListener(this);
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
	 * 
	 * {@inheritDoc}
	 * 
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals(EditEntity.PROPERTY_ATTRIBUTES)) {
			panel2.updateAttributeTable();
		} else if (evt.getPropertyName().equals(
				EditEntity.PROPERTY_UP_IDENTIFIER)) {
			panel1.updateValue();
			panel2.updateAttributeTable();
		}
		Button okButton = getButton(IDialogConstants.OK_ID);
		if (okButton != null) {
			okButton.setEnabled(entity.isValid());
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

		panel1 = new EntityNameAndIdentifierNameAndTypeSettingPanel(composite,
				SWT.NULL, entity);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		panel1.setLayoutData(gridData);

		panel3 = new ImplementInfoSettingPanel(composite, SWT.NULL);
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		panel3.setLayoutData(gridData);

		panel2 = new AttributeSettingPanel(composite, SWT.NULL, entity);
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		panel2.setLayoutData(gridData);

		composite.pack();
		initializeValue();

		return composite;
	}

	private void initializeValue() {
		// panel1.setEditIdentifier(new
		// EditAttribute(original.getIdentifier()));
		// panel1.setIdentifierNameText(original.getIdentifier().getName());
		// panel1.setEntityNameText(original.getName());
		// panel1.selectEntityTypeCombo(original.getEntityType());
		// panel1.selectAutoCreateCheckBox(original.getIdentifier().getName(),
		// original.getName());
		// panel1.setEntityTypeComboEnabled(original.isEntityTypeEditable());
		// TODO panel側で値の設定を出来るように修正予定
		panel3.initializeValue(entity.isNotImplement(),
				entity.getImplementName());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#okPressed()
	 */
	@Override
	protected void okPressed() {
		this.editedValueEntity = new Entity();
		Identifier newIdentifier = new Identifier();
		entity.getEditIdentifier().copyTo(newIdentifier);
		this.editedValueEntity.setIdentifier(newIdentifier);
		this.editedValueEntity.setName(entity.getName());
		this.editedValueEntity.setEntityType(entity.getType());
		this.editedValueEntity.setNotImplement(panel3.isNotImplement());
		this.editedValueEntity.setImplementName(panel3.getImplementName());
		this.editedValueEntity.setAttributes(entity.getAttributesOrder());
		this.editedValueEntity.setKeyModels(entity.getKeyModels());
		this.editedValueEntity.setImplementDerivationModels(entity.getImplementDerivationModels());
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
	public Entity getEditedValueEntity() {
		return editedValueEntity;
	}

}

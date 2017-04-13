/*
 * Copyright 2009-2016 TMD-Maker Project <http://tmdmaker.osdn.jp/>
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
package jp.sourceforge.tmdmaker.ui.dialogs;

import java.beans.PropertyChangeEvent;

import jp.sourceforge.tmdmaker.Messages;
import jp.sourceforge.tmdmaker.model.Entity;
import jp.sourceforge.tmdmaker.ui.dialogs.components.AttributeSettingPanel;
import jp.sourceforge.tmdmaker.ui.dialogs.components.EntityNameAndIdentifierNameAndTypeSettingPanel;
import jp.sourceforge.tmdmaker.ui.dialogs.components.ImplementInfoSettingPanel;
import jp.sourceforge.tmdmaker.ui.dialogs.models.EditEntity;

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
public class EntityEditDialog extends ModelEditDialog<Entity> {
	/** エンティティ名、個体指定子、エンティティ種類設定用 */
	private EntityNameAndIdentifierNameAndTypeSettingPanel panel1;
	/** アトリビュート設定用 */
	private AttributeSettingPanel panel2;
	/** 実装可否設定用 */
	private ImplementInfoSettingPanel panel3;

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
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals(EditEntity.PROPERTY_ATTRIBUTES)) {
			if (panel2 != null) {
				panel2.updateAttributeTable();
			}
		} else if (evt.getPropertyName().equals(EditEntity.PROPERTY_UP_IDENTIFIER)) {
			panel1.updateValue();
			panel2.updateAttributeTable();
		}
		// panel3.updateValue();
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
		getShell().setText(Messages.EditEntity);

		Composite composite = new Composite(parent, SWT.NULL);
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 1;
		composite.setLayout(gridLayout);

		panel1 = new EntityNameAndIdentifierNameAndTypeSettingPanel(composite, SWT.NULL,
				getEditModel());
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		panel1.setLayoutData(gridData);

		panel3 = new ImplementInfoSettingPanel(composite, SWT.NULL, getEditModel());
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		panel3.setLayoutData(gridData);

		panel2 = new AttributeSettingPanel(composite, SWT.NULL, getEditModel());
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		panel2.setLayoutData(gridData);

		composite.pack();

		return composite;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#createContents(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createContents(Composite parent) {
		Control contents = super.createContents(parent);
		panel2.updateAttributeTable();
		return contents;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#okPressed()
	 */
	@Override
	protected void okPressed() {
		this.editedValue = entity.createEditedModel();
		super.okPressed();
	}

	@Override
	protected EditEntity getEditModel() {
		return (EditEntity) entity;
	}

}

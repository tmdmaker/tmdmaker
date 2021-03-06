/*
 * Copyright 2009-2019 TMD-Maker Project <https://www.tmdmaker.org>
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
package org.tmdmaker.ui.dialogs;

import java.beans.PropertyChangeEvent;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.tmdmaker.core.model.VirtualEntity;
import org.tmdmaker.core.model.VirtualEntityType;
import org.tmdmaker.ui.Messages;
import org.tmdmaker.ui.dialogs.components.AttributeSettingPanel;
import org.tmdmaker.ui.dialogs.components.ImplementInfoSettingPanel;
import org.tmdmaker.ui.dialogs.components.TableNameSettingPanel;
import org.tmdmaker.ui.dialogs.model.EditTable;
import org.tmdmaker.ui.dialogs.model.EditVirtualEntity;

/**
 * みなしエンティティ編集ダイアログ
 * 
 * @author nakaG
 * 
 */
public class VirtualEntityEditDialog extends ModelEditDialog<VirtualEntity> { 
	/** 表名設定用 */
	private TableNameSettingPanel panel1;
	/** アトリビュート設定用 */
	private AttributeSettingPanel panel2;
	/** 実装可否設定用 */
	private ImplementInfoSettingPanel panel3;
	/** 種別設定用 */
	private Combo typeCombo;

	/**
	 * コンストラクタ
	 * 
	 * @param parentShell
	 *            親
	 * @param original
	 *            編集対象モデル
	 */
	public VirtualEntityEditDialog(Shell parentShell, VirtualEntity original) {
		super(parentShell);
		this.entity = new EditVirtualEntity(original);
		this.entity.addPropertyChangeListener(this);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals(EditTable.PROPERTY_ATTRIBUTES)) {
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
		getShell().setText(Messages.EditVirtualEntity);
		Composite composite = new Composite(parent, SWT.NULL);

		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		composite.setLayout(gridLayout);

		panel1 = new TableNameSettingPanel(composite, SWT.NULL, entity);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		panel1.setLayoutData(gridData);

		typeCombo = new Combo(composite, SWT.READ_ONLY);
		typeCombo.add(Messages.Normal);
		typeCombo.add(Messages.ResourceType);
		typeCombo.add(Messages.EventType);
		typeCombo.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				getEditModel().setVirtualEntityType(VirtualEntityType.values()[typeCombo
						.getSelectionIndex()]);
			}

		});

		panel3 = new ImplementInfoSettingPanel(composite, SWT.NULL, entity);
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		panel3.setLayoutData(gridData);

		gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		panel2 = new AttributeSettingPanel(composite, SWT.NULL, entity);
		panel2.setLayoutData(gridData);

		composite.pack();

		initializeTypeCombo();

		return composite;
	}

	/**
	 * ダイアログへ初期値を設定する
	 */
	private void initializeTypeCombo() {
		typeCombo.select(getEditModel().getVirtualEntityType().ordinal());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#okPressed()
	 */
	@Override
	protected void okPressed() {
		editedValue = entity.createEditedModel();

		super.okPressed();
	}
	
	@Override
	protected EditVirtualEntity getEditModel()
	{
		return (EditVirtualEntity)entity;
	}
}

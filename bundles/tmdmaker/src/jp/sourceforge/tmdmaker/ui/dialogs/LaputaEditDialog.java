/*
 * Copyright 2009-2017 TMD-Maker Project <http://tmdmaker.osdn.jp/>
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

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import jp.sourceforge.tmdmaker.Messages;
import jp.sourceforge.tmdmaker.model.Identifier;
import jp.sourceforge.tmdmaker.model.Laputa;
import jp.sourceforge.tmdmaker.ui.dialogs.components.AttributeSettingPanel;
import jp.sourceforge.tmdmaker.ui.dialogs.components.EntityEditSettingPanel;
import jp.sourceforge.tmdmaker.ui.dialogs.models.EditEntity;
import jp.sourceforge.tmdmaker.ui.dialogs.models.EditTable;

/**
 * ラピュタ編集ダイアログ
 * 
 * @author nakaG
 * 
 */
public class LaputaEditDialog extends ModelEditDialog<Laputa> {
	/** エンティティ名、個体指定子、エンティティ種類設定用 */
	private EntityEditSettingPanel panel1;
	/** アトリビュート設定用 */
	private AttributeSettingPanel panel2;

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
		if (evt.getPropertyName().equals(EditTable.PROPERTY_ATTRIBUTES)) {
			panel2.updateAttributeTable();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		getShell().setText(Messages.EditLaputa);

		Composite composite = new Composite(parent, SWT.NULL);
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 1;
		composite.setLayout(gridLayout);

		panel1 = new EntityEditSettingPanel(composite, SWT.NULL, getEditModel());
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		panel1.setLayoutData(gridData);

		gridData = new GridData(GridData.FILL_HORIZONTAL);
		panel2 = new AttributeSettingPanel(composite, SWT.NULL, entity);
		panel2.setLayoutData(gridData);

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
		this.editedValue = getEditModel().createEditedModel();
		Identifier newIdentifier = new Identifier();
		getEditModel().getEditIdentifier().copyTo(newIdentifier);
		this.editedValue.setEntityType(getEditModel().getType());
		this.editedValue.setIdentifier(newIdentifier);

		super.okPressed();
	}

	@Override
	protected EditEntity getEditModel() {
		return (EditEntity) entity;
	}
}

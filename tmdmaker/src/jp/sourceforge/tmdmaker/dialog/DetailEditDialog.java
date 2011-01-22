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
import jp.sourceforge.tmdmaker.dialog.component.DetailIdentifierSettingPanel;
import jp.sourceforge.tmdmaker.dialog.component.ImplementInfoSettingPanel;
import jp.sourceforge.tmdmaker.dialog.component.TableNameSettingPanel;
import jp.sourceforge.tmdmaker.dialog.model.EditAttribute;
import jp.sourceforge.tmdmaker.dialog.model.EditEntity;
import jp.sourceforge.tmdmaker.model.Detail;
import jp.sourceforge.tmdmaker.model.Identifier;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

/**
 * DTL表編集ダイアログ
 * 
 * @author nakaG
 * 
 */
public class DetailEditDialog extends Dialog implements PropertyChangeListener {
	/** 編集対象モデル */
	private Detail original;
	/** 編集結果格納用 */
	private Detail editedValue;
	private EditEntity entity;
	/** 表名設定用 */
	private TableNameSettingPanel panel1;
	/** アトリビュート設定用 */
	private AttributeSettingPanel panel2;
	/** Detail個体指定子設定用 */
	private DetailIdentifierSettingPanel panel3;
	/** 実装可否設定用 */
	private ImplementInfoSettingPanel panel4;

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
			panel2.updateAttributeTable();
		} else if (evt.getPropertyName().equals(EditEntity.PROPERTY_UP_IDENTIFIER)) {
			panel3.updateValue();
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
		getShell().setText("DTL表編集");
		Composite composite = new Composite(parent, SWT.NULL);

		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 1;
		composite.setLayout(gridLayout);

		panel1 = new TableNameSettingPanel(composite, SWT.NULL);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		panel1.setLayoutData(gridData);

		gridData = new GridData(GridData.FILL_HORIZONTAL);
		panel3 = new DetailIdentifierSettingPanel(composite, SWT.NULL, entity);
		panel3.setLayoutData(gridData);

		gridData = new GridData(GridData.FILL_HORIZONTAL);
		panel4 = new ImplementInfoSettingPanel(composite, SWT.NULL);
		panel4.setLayoutData(gridData);

		gridData = new GridData(GridData.FILL_HORIZONTAL);
		panel2 = new AttributeSettingPanel(composite, SWT.NULL, entity);
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

		// panel3.setEditIdentifier(new EditAttribute(original
		// .getDetailIdentifier()));
		// panel3.setIdentifierName(original.getDetailIdentifier().getName());

		panel4.initializeValue(original.isNotImplement(),
				original.getImplementName());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#okPressed()
	 */
	@Override
	protected void okPressed() {
		editedValue = new Detail();
		editedValue.setName(panel1.getTableName());
		Identifier newIdentifier = new Identifier();
		entity.getEditIdentifier().copyTo(newIdentifier);
		editedValue.setDetailIdentifier(newIdentifier);
		editedValue.setNotImplement(panel4.isNotImplement());
		editedValue.setImplementName(panel4.getImplementName());
		editedValue.setAttributes(entity.getAttributesOrder());
		editedValue.setKeyModels(entity.getKeyModels());
		editedValue.setImplementDerivationModels(entity.getImplementDerivationModels());

		super.okPressed();
	}

	/**
	 * @return the editAttributeList
	 */
	public List<EditAttribute> getEditAttributeList() {
		return entity.getAttributes();
	}

	/**
	 * @return the editedValue
	 */
	public Detail getEditedValue() {
		return editedValue;
	}

}

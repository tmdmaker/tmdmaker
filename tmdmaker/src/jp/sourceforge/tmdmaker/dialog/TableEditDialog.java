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
import jp.sourceforge.tmdmaker.dialog.component.ImplementInfoSettingPanel;
import jp.sourceforge.tmdmaker.dialog.component.TableNameSettingPanel;
import jp.sourceforge.tmdmaker.dialog.model.EditAttribute;
import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.Attribute;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

/**
 * 表編集ダイアログ
 * 
 * @author nakaG
 * 
 */
public class TableEditDialog extends Dialog {
	/** 編集対象モデル */
	private AbstractEntityModel original;
	/** 編集結果格納用 */
	private AbstractEntityModel editedValue;
	/** 編集用アトリビュートリスト */
	private List<EditAttribute> editAttributeList = new ArrayList<EditAttribute>();
	/** 実装可否設定用 */
//	private Button notImplementCheck;
	/** ダイアログタイトル */
	private String title;
	/** 表名設定用 */
	private TableNameSettingPanel panel1;
	/** アトリビュート設定用 */
	private AttributeSettingPanel panel2;
	/** 実装可否設定用 */
	private ImplementInfoSettingPanel panel3;

	private List<Attribute> newAttributeOrder = new ArrayList<Attribute>();
	private List<Attribute> addAttributes = new ArrayList<Attribute>();
	private List<Attribute> editAttributes = new ArrayList<Attribute>();
//	private List<Attribute> deleteAttributes = new ArrayList<Attribute>();

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
	public TableEditDialog(Shell parentShell, String title,
			AbstractEntityModel original) {
		super(parentShell);
		this.title = title;
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
		getShell().setText(title);
		Composite composite = new Composite(parent, SWT.NULL);

		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 1;
		composite.setLayout(gridLayout);

		panel1 = new TableNameSettingPanel(composite, SWT.NULL);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		panel1.setLayoutData(gridData);

//		gridData = new GridData(GridData.FILL_HORIZONTAL);
//		gridData.horizontalIndent = 5;
//		notImplementCheck = new Button(composite, SWT.CHECK);
//		notImplementCheck.setText("実装しない");
//		notImplementCheck.setLayoutData(gridData);

		gridData = new GridData(GridData.FILL_HORIZONTAL);
		panel3 = new ImplementInfoSettingPanel(composite, SWT.NULL);
		panel3.setLayoutData(gridData);
		
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

//		notImplementCheck.setSelection(original.isNotImplement());

		panel2.setAttributeTableRow(editAttributeList);

		panel3.initializeValue(original.isNotImplement(), original.getImplementName());
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
//		editedValue.setNotImplement(());
		editedValue.setNotImplement(panel3.isNotImplement());
		editedValue.setImplementName(panel3.getImplementName());
		createEditAttributeResult();
		
		super.okPressed();
	}
	private void createEditAttributeResult() {

		for (EditAttribute ea : editAttributeList) {
			Attribute originalAttribute = ea.getOriginalAttribute();
			if (ea.isAdded()) {
				ea.copyToOriginal();
				addAttributes.add(ea.getOriginalAttribute());
			} else {
				if (ea.isNameChanged()) {
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
	public AbstractEntityModel getEditedValue() {
		return editedValue;
	}

}

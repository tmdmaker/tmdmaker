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

import jp.sourceforge.tmdmaker.dialog.component.SubsetSettingPanel;
import jp.sourceforge.tmdmaker.dialog.model.EditSubsetEntity;
import jp.sourceforge.tmdmaker.model.IAttribute;
import jp.sourceforge.tmdmaker.model.SubsetEntity;
import jp.sourceforge.tmdmaker.model.SubsetType;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

/**
 * サブセット作成ダイアログ
 * 
 * @author nakaG
 * 
 */
public class SubsetCreateDialog extends Dialog {
	private SubsetSettingPanel panel;
	private SubsetType.SubsetTypeValue subsetType;
	private boolean exceptNull;
	private List<IAttribute> attributes;
	private List<EditSubsetEntity> subsetEntities = new ArrayList<EditSubsetEntity>();
	private IAttribute selectedAttribute;
	private SubsetType.SubsetTypeValue editedSubsetType;
	private IAttribute editedPartitionAttribute;
	private List<EditSubsetEntity> editedSubsetEntities;
	private List<EditSubsetEntity> deletedSubsetEntities;
	private boolean editedExceptNull;
	/**
	 * 
	 * @param parentShell 親
	 * @param subsetType サブセットの種類（同一/相違）
	 * @param exceptNull 形式的サブセットの判定
	 * @param list 区分コードにするアトリビュートの候補
	 * @param subsetEntities 既作成のサブセット
	 * @param selectedAttribute 既選択の区分コードアトリビュート
	 */
	public SubsetCreateDialog(Shell parentShell,
			SubsetType.SubsetTypeValue subsetType, boolean exceptNull,
			List<IAttribute> list, List<SubsetEntity> subsetEntities,
			IAttribute selectedAttribute) {
		super(parentShell);
		this.subsetType = subsetType;
		this.exceptNull = exceptNull;
		this.attributes = list;
		this.selectedAttribute = selectedAttribute;
		for (SubsetEntity se : subsetEntities) {
			this.subsetEntities.add(new EditSubsetEntity(se));
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		getShell().setText("サブセット編集");
		Composite composite = new Composite(parent, SWT.NULL);
		panel = new SubsetSettingPanel(composite, SWT.NULL);
		panel.initializeValue(this.subsetType
				.equals(SubsetType.SubsetTypeValue.SAME), this.exceptNull,
				this.attributes, this.subsetEntities, this.selectedAttribute);
		return composite;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#okPressed()
	 */
	@Override
	protected void okPressed() {
		editedSubsetType = createEditedSubsetType();
		int partitionSelectionIndex = panel
				.getSelectedPartitionAttributeIndex();
		if (partitionSelectionIndex != -1) {
			editedPartitionAttribute = this.attributes
					.get(partitionSelectionIndex);
		}
		editedSubsetEntities = panel.getSubsetEntityList();
		deletedSubsetEntities = panel.getDeletedSubsetEntityList();
		editedExceptNull = panel.isExceptNull();
		super.okPressed();
	}

	private SubsetType.SubsetTypeValue createEditedSubsetType() {
		if (panel.isSameTypeSelected()) {
			return SubsetType.SubsetTypeValue.SAME;
		} else {
			return SubsetType.SubsetTypeValue.DIFFERENT;
		}
	}

	/**
	 * @return the editedSubsetType
	 */
	public SubsetType.SubsetTypeValue getEditedSubsetType() {
		return editedSubsetType;
	}

	/**
	 * @return the editedPartitionAttribute
	 */
	public IAttribute getEditedPartitionAttribute() {
		return editedPartitionAttribute;
	}

	/**
	 * @return the editedSubsetEntities
	 */
	public List<EditSubsetEntity> getEditedSubsetEntities() {
		return editedSubsetEntities;
	}

	/**
	 * @return the deletedSubsetEntities
	 */
	public List<EditSubsetEntity> getDeletedSubsetEntities() {
		return deletedSubsetEntities;
	}

	/**
	 * @return the editedExceptNull
	 */
	public boolean isEditedExceptNull() {
		return editedExceptNull;
	}

}

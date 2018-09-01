/*
 * Copyright 2009-2018 TMD-Maker Project <https://tmdmaker.osdn.jp/>
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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import jp.sourceforge.tmdmaker.Messages;
import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.IAttribute;
import jp.sourceforge.tmdmaker.model.SubsetEntity;
import jp.sourceforge.tmdmaker.model.SubsetType;
import jp.sourceforge.tmdmaker.model.SubsetType.SubsetTypeValue;
import jp.sourceforge.tmdmaker.model.parts.ModelName;
import jp.sourceforge.tmdmaker.model.rule.ImplementRule;
import jp.sourceforge.tmdmaker.ui.dialogs.components.SubsetSettingPanel;
import jp.sourceforge.tmdmaker.ui.dialogs.models.EditSubsetEntity;
import jp.sourceforge.tmdmaker.ui.editor.gef3.commands.ImplementDerivationModelsDeleteCommand;
import jp.sourceforge.tmdmaker.ui.editor.gef3.commands.SubsetAddCommand;
import jp.sourceforge.tmdmaker.ui.editor.gef3.commands.SubsetDeleteCommand;
import jp.sourceforge.tmdmaker.ui.editor.gef3.commands.SubsetNameChangeCommand;
import jp.sourceforge.tmdmaker.ui.editor.gef3.commands.SubsetTypeChangeCommand;

/**
 * サブセット作成ダイアログ.
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

	private AbstractEntityModel model;
	private CompoundCommand ccommand;

	/**
	 * コンストラクタ.
	 *
	 * @param parentShell
	 *            親.
	 * @param subsetType
	 *            サブセットの種類（同一/相違）.
	 * @param model
	 *            スーパーセット.
	 */
	public SubsetCreateDialog(Shell parentShell, AbstractEntityModel model) {
		super(parentShell);
		if (model.subsets().hasSubset()) {
			SubsetType subsetType = model.subsets().subsetType();
			this.subsetType = subsetType.getSubsetType();
			this.exceptNull = subsetType.isExceptNull();
			this.selectedAttribute = subsetType.getPartitionAttribute();
			for (SubsetEntity se : subsetType.getSubsetList()) {
				this.subsetEntities.add(new EditSubsetEntity(se));
			}
		} else {
			this.subsetType = SubsetTypeValue.SAME;
			this.exceptNull = false;
		}
		this.attributes = model.getAttributes();
		this.model = model;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		getShell().setText(Messages.EditSubset);
		Composite composite = new Composite(parent, SWT.NULL);
		panel = new SubsetSettingPanel(composite, SWT.NULL);
		panel.initializeValue(this.subsetType.equals(SubsetType.SubsetTypeValue.SAME),
				this.exceptNull, this.attributes, this.subsetEntities, this.selectedAttribute);
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
		editedSubsetType = createEditedSubsetType();
		editedExceptNull = panel.isExceptNull();
		int partitionSelectionIndex = panel.getSelectedPartitionAttributeIndex();
		if (partitionSelectionIndex != -1) {
			editedPartitionAttribute = this.attributes.get(partitionSelectionIndex);
		}
		editedSubsetEntities = panel.getSubsetEntityList();
		deletedSubsetEntities = panel.getDeletedSubsetEntityList();

		ccommand = new CompoundCommand();
		addSubsetAddCommand();
		addSubsetTypeChangeCommand();
		addSubsetEntityRenameCommand();
		addSubsetDeleteCommand();
		super.okPressed();
	}

	public CompoundCommand getCcommand() {
		return ccommand;
	}

	private void addSubsetAddCommand() {
		ccommand.add(new SubsetAddCommand(this.model, getAddSubsetEntityNames()));
	}

	private List<ModelName> getAddSubsetEntityNames() {
		List<ModelName> subsetNames = new ArrayList<ModelName>();
		for (EditSubsetEntity s : editedSubsetEntities) {
			if (s.isAdded()) {
				subsetNames.add(new ModelName(s.getName()));
			}
		}
		return subsetNames;
	}

	private void addSubsetTypeChangeCommand() {
		ccommand.add(new SubsetTypeChangeCommand(this.model, editedSubsetType,
				editedPartitionAttribute, editedExceptNull));
	}

	private SubsetType.SubsetTypeValue createEditedSubsetType() {
		if (panel.isSameTypeSelected()) {
			return SubsetType.SubsetTypeValue.SAME;
		} else {
			return SubsetType.SubsetTypeValue.DIFFERENT;
		}
	}

	private void addSubsetEntityRenameCommand() {
		for (EditSubsetEntity s : this.editedSubsetEntities) {
			if (s.isNameChanged()) {
				this.ccommand.add(new SubsetNameChangeCommand(s.getOriginal(), s.getName()));
			}
		}
	}

	private void addSubsetDeleteCommand() {
		List<EditSubsetEntity> deletedList = new ArrayList<EditSubsetEntity>();
		for (EditSubsetEntity e : deletedSubsetEntities) {
			if (e.getOriginal() != null) {
				deletedList.add(e);
			}
		}
		AbstractEntityModel original = null;
		for (EditSubsetEntity e : deletedList) {
			SubsetEntity subset = e.getOriginal();
			if (original == null) {
				original = ImplementRule.findOriginalImplementModel(subset);
			}
			if (subset != null) {
				SubsetDeleteCommand command = new SubsetDeleteCommand(subset);
				ccommand.add(command);
				if (subset.isNotImplement()) {
					ccommand.add(new ImplementDerivationModelsDeleteCommand(subset, original));
				}
			}
		}
	}
}

/*
 * Copyright 2009-2015 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
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
import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.IAttribute;
import jp.sourceforge.tmdmaker.model.SubsetEntity;
import jp.sourceforge.tmdmaker.model.SubsetType;
import jp.sourceforge.tmdmaker.model.SubsetType.SubsetTypeValue;
import jp.sourceforge.tmdmaker.model.rule.ImplementRule;
import jp.sourceforge.tmdmaker.model.rule.SubsetRule;
import jp.sourceforge.tmdmaker.ui.command.Entity2SubsetTypeCreateCommand;
import jp.sourceforge.tmdmaker.ui.command.ImplementDerivationModelsDeleteCommand;
import jp.sourceforge.tmdmaker.ui.command.ModelConstraintChangeCommand;
import jp.sourceforge.tmdmaker.ui.command.SubsetCreateCommand;
import jp.sourceforge.tmdmaker.ui.command.SubsetDeleteCommand;
import jp.sourceforge.tmdmaker.ui.command.SubsetNameChangeCommand;
import jp.sourceforge.tmdmaker.ui.command.SubsetTypeChangeCommand;
import jp.sourceforge.tmdmaker.ui.command.SubsetTypeDeleteCommand;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

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
	private SubsetType orgSubsetType;
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
	public SubsetCreateDialog(Shell parentShell, SubsetType subsetType, AbstractEntityModel model) {
		super(parentShell);
		this.subsetType = subsetType.getSubsetType();
		this.exceptNull = subsetType.isExceptNull();
		this.attributes = model.getAttributes();
		this.selectedAttribute = subsetType.getPartitionAttribute();
		for (SubsetEntity se : subsetType.getSubsetList()) {
			this.subsetEntities.add(new EditSubsetEntity(se));
		}

		this.model = model;
		this.orgSubsetType = subsetType;
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
		panel.initializeValue(this.subsetType.equals(SubsetType.SubsetTypeValue.SAME),
				this.exceptNull, this.attributes, this.subsetEntities, this.selectedAttribute);
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
		int partitionSelectionIndex = panel.getSelectedPartitionAttributeIndex();
		if (partitionSelectionIndex != -1) {
			editedPartitionAttribute = this.attributes.get(partitionSelectionIndex);
		}
		editedSubsetEntities = panel.getSubsetEntityList();
		deletedSubsetEntities = panel.getDeletedSubsetEntityList();
		editedExceptNull = panel.isExceptNull();

		ccommand = new CompoundCommand();
		addSuitableSubsetTypeCommand(ccommand, model, orgSubsetType, getEditedSubsetType(),
				getEditedPartitionAttribute(), isEditedExceptNull());
		addSuitableSubsetEntityCommand(ccommand, model, orgSubsetType, getEditedSubsetEntities());
		addSubsetDeleteCommand(ccommand, model, orgSubsetType, getDeletedSubsetEntities());
		super.okPressed();
	}

	public CompoundCommand getCcommand() {
		return ccommand;
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
	private SubsetType.SubsetTypeValue getEditedSubsetType() {
		return editedSubsetType;
	}

	/**
	 * @return the editedPartitionAttribute
	 */
	private IAttribute getEditedPartitionAttribute() {
		return editedPartitionAttribute;
	}

	/**
	 * @return the editedSubsetEntities
	 */
	private List<EditSubsetEntity> getEditedSubsetEntities() {
		return editedSubsetEntities;
	}

	/**
	 * @return the deletedSubsetEntities
	 */
	private List<EditSubsetEntity> getDeletedSubsetEntities() {
		return deletedSubsetEntities;
	}

	/**
	 * @return the editedExceptNull
	 */
	private boolean isEditedExceptNull() {
		return editedExceptNull;
	}

	private int calcurateSubsetNameSize(List<EditSubsetEntity> editSubsets) {
		int nameLength = 0;
		for (EditSubsetEntity e : editSubsets) {
			nameLength = Math.max(nameLength, e.getName().length());
		}
		return nameLength;
	}

	private int calcurateSubsetWidth(List<EditSubsetEntity> editSubsets) {
		final int SPACE = 14;
		int subsetNameLength = calcurateSubsetNameSize(editSubsets);
		int reusedNameLength = model.calcurateMaxIdentifierRefSize();
		int charLength = Math.max(subsetNameLength, reusedNameLength) * SubsetRule.CHAR_SIZE;
		return charLength + SPACE;
	}

	private void addSuitableSubsetEntityCommand(CompoundCommand ccommand,
			AbstractEntityModel model, SubsetType subsetType, List<EditSubsetEntity> editSubsets) {
		int subsetwidth = calcurateSubsetWidth(editSubsets);
		int totalWidthHalf = editSubsets.size() * subsetwidth / 2;
		int subsetX = totalWidthHalf * -1;
		final int SUBSET_Y = 50;
		for (EditSubsetEntity e : editSubsets) {
			if (e.isAdded()) {
				SubsetEntity subset = SubsetRule.createSubsetEntity(model, e.getName());
				Command command = new SubsetCreateCommand(model, subsetType, subset);
				ccommand.add(command);

				command = new ModelConstraintChangeCommand(subset, subsetX, SUBSET_Y);
				ccommand.add(command);
			} else if (e.isNameChanged()) {
				SubsetEntity subsetEntity = e.getOriginal();
				SubsetNameChangeCommand command = new SubsetNameChangeCommand(subsetEntity,
						e.getName());
				ccommand.add(command);
			}
			subsetX += subsetwidth;
		}
	}

	private void addSubsetDeleteCommand(CompoundCommand ccommand, AbstractEntityModel model,
			SubsetType subsetType, List<EditSubsetEntity> deleteSubsets) {
		List<EditSubsetEntity> deletedList = new ArrayList<EditSubsetEntity>();
		for (EditSubsetEntity e : deleteSubsets) {
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
		if (deletedList.size() > 0) {
			SubsetTypeDeleteCommand command = new SubsetTypeDeleteCommand(model.getDiagram(),
					subsetType);
			ccommand.add(command);
		}
	}

	private void addSuitableSubsetTypeCommand(CompoundCommand ccommand, AbstractEntityModel model,
			SubsetType subsetType, SubsetTypeValue newSubsetType,
			IAttribute selectedPartitionAttribute, boolean newExceptNull) {
		if (subsetType.isNew()) {
			// entityとpartitionCodeModelの接続
			subsetType.setExceptNull(newExceptNull);
			subsetType.setSubsetType(newSubsetType);
			ccommand.add(new Entity2SubsetTypeCreateCommand(model, subsetType,
					selectedPartitionAttribute));
		} else {
			ccommand.add(new SubsetTypeChangeCommand(subsetType, newSubsetType,
					selectedPartitionAttribute, newExceptNull));
		}
	}

}

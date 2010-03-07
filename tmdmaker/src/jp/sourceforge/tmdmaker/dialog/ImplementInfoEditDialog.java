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
import java.util.Map;
import java.util.Map.Entry;

import jp.sourceforge.tmdmaker.dialog.component.ImplementInfoEditPanel;
import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.Attribute;
import jp.sourceforge.tmdmaker.model.Detail;
import jp.sourceforge.tmdmaker.model.EditAttribute;
import jp.sourceforge.tmdmaker.model.Entity;
import jp.sourceforge.tmdmaker.model.Identifier;
import jp.sourceforge.tmdmaker.model.IdentifierRef;
import jp.sourceforge.tmdmaker.model.ReusedIdentifier;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

/**
 * 実装情報編集Dialog
 * 
 * @author nakaG
 * 
 */
public class ImplementInfoEditDialog extends Dialog {
	/** 編集情報実装用 */
	private ImplementInfoEditPanel panel;
	/** 編集元モデル */
	private AbstractEntityModel model;
	/** 編集用アトリビュート */
	private List<EditAttribute> editAttributeList = new ArrayList<EditAttribute>();
	/** 編集結果格納用 */
	private AbstractEntityModel editedValueEntity;
	private List<EditAttribute> editedValueAttributes = new ArrayList<EditAttribute>();
	private List<EditAttribute> editedValueIdentifieres = new ArrayList<EditAttribute>();

	/**
	 * コンストラクタ
	 * 
	 * @param parentShell
	 *            親
	 * @param model
	 *            編集対象
	 */
	public ImplementInfoEditDialog(Shell parentShell, AbstractEntityModel model) {
		super(parentShell);
		this.model = model;

		if (model instanceof Entity) {
			editAttributeList.add(new EditAttribute(((Entity) model)
					.getIdentifier()));
		} else if (model instanceof Detail) {
			editAttributeList.add(new EditAttribute(((Detail) model)
					.getDetailIdentifier()));
		}
		// Re-usedをカラムとして追加
		Map<AbstractEntityModel, ReusedIdentifier> reused = model
				.getReusedIdentifieres();
		for (Entry<AbstractEntityModel, ReusedIdentifier> entry : reused
				.entrySet()) {
			for (IdentifierRef ref : entry.getValue().getIdentifires()) {
				editAttributeList.add(new EditAttribute(ref));
			}
		}
		for (Attribute a : model.getAttributes()) {
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
		getShell().setText("実装情報編集");
		Composite composite = new Composite(parent, SWT.NULL);
		panel = new ImplementInfoEditPanel(composite, SWT.NULL);
		panel.initializeValue(model, editAttributeList);

		return composite;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#okPressed()
	 */
	@Override
	protected void okPressed() {
		editedValueEntity = model.getCopy();
		editedValueEntity.setImplementName(panel.getImplementName());
		createEditAttributeResult();

		super.okPressed();
	}

	private void createEditAttributeResult() {
		for (EditAttribute ea : panel.getAttributes()) {
			if (ea.isEdited()) {
				System.out.println(ea);
				Attribute a = ea.getOriginalAttribute();
				if (a instanceof Identifier) {
					editedValueIdentifieres.add(ea);
				} else {
					editedValueAttributes.add(ea);
				}
			}
		}
	}

	/**
	 * @return the editedValueEntity
	 */
	public AbstractEntityModel getEditedValueEntity() {
		return editedValueEntity;
	}

	/**
	 * @return the editedValueAttributes
	 */
	public List<EditAttribute> getEditedValueAttributes() {
		return editedValueAttributes;
	}

	/**
	 * @return the editedValueIdentifieres
	 */
	public List<EditAttribute> getEditedValueIdentifieres() {
		return editedValueIdentifieres;
	}

}

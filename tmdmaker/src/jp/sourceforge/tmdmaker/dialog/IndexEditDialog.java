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

import jp.sourceforge.tmdmaker.dialog.component.IndexPanel;
import jp.sourceforge.tmdmaker.model.Attribute;
import jp.sourceforge.tmdmaker.model.EditImplementAttribute;
import jp.sourceforge.tmdmaker.model.EditKeyModel;
import jp.sourceforge.tmdmaker.model.KeyModel;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

/**
 * @author nakaG
 *
 */
public class IndexEditDialog extends Dialog {
	private IndexPanel panel;
	private EditKeyModel keyModel;
	private List<EditImplementAttribute> editAttributeList;
	private List<Attribute> attributes;
	/**
	 * @param parentShell
	 */
	public IndexEditDialog(Shell parentShell, List<EditImplementAttribute> editAttributeList) {
		super(parentShell);
//		getShell().setText("キー作成");
		this.editAttributeList = editAttributeList;
		this.keyModel = new EditKeyModel(new KeyModel());
		this.attributes = convert(editAttributeList);
		this.keyModel.setName(createKeyName(editAttributeList));
	}
	private String createKeyName(List<EditImplementAttribute> editAttributeList) {
		String modelName = null;
		int keyCount = 0;
		if (editAttributeList != null && editAttributeList.size() > 0) {
			EditImplementAttribute a = editAttributeList.get(0);
			modelName = a.getContainerModel().getName();
			keyCount = a.getKeyCount();
		} else {
			// この処理は行われないはず
			modelName = "";
		}
		return modelName + "_IDX" + (keyCount + 1);
	}
	public IndexEditDialog(Shell parentShell, EditKeyModel keyModel, List<EditImplementAttribute> editAttributeList) {
		super(parentShell);
//		getShell().setText("キー編集");
		this.editAttributeList = editAttributeList;
		this.keyModel = keyModel;
		this.attributes = convert(editAttributeList);
		this.attributes.removeAll(keyModel.getAttributes());
	}
	public List<Attribute> convert(List<EditImplementAttribute> sourceList) {
		List<Attribute> targetList = new ArrayList<Attribute>();
		
		for (EditImplementAttribute ea: sourceList) {
			targetList.add(ea.getOriginalAttribute());
		}
		return targetList;
	}
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		getShell().setText("キー編集");
		Composite composite = new Composite(parent,SWT.NULL);
		panel = new IndexPanel(composite, SWT.NULL);
		panel.initializeValue(keyModel, attributes);
		return composite;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#okPressed()
	 */
	@Override
	protected void okPressed() {
		keyModel.setName(panel.getIndexName());
		keyModel.setUnique(panel.isUnique());
		keyModel.setAttributes(panel.getSelectModels());
		super.okPressed();
	}
	/**
	 * @return the keyModel
	 */
	public EditKeyModel getKeyModel() {
		return keyModel;
	}
	
}

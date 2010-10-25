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
import jp.sourceforge.tmdmaker.dialog.model.EditImplementAttribute;
import jp.sourceforge.tmdmaker.model.IAttribute;
import jp.sourceforge.tmdmaker.model.KeyModel;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

/**
 * キー（インデックス）編集用ダイアログ
 * 
 * @author nakaG
 * 
 */
public class IndexEditDialog extends Dialog {
	private IndexPanel panel;
	private KeyModel keyModel;
	private List<IAttribute> attributes;

	/**
	 * キー追加時に使用するコンストラクタ
	 * 
	 * @param parentShell
	 *            親
	 * @param editAttributeList
	 *            編集用アトリビュートのリスト
	 */
	public IndexEditDialog(Shell parentShell, String implementEntityName,
			List<EditImplementAttribute> editAttributeList) {
		super(parentShell);
		// getShell().setText("キー作成");
		this.keyModel = new KeyModel();
		this.attributes = convert(editAttributeList);
		this.keyModel.setName(createKeyName(implementEntityName, editAttributeList));
	}

	private String createKeyName(String implementEntityName, List<EditImplementAttribute> editAttributeList) {
		String modelName = null;
		int keyCount = 0;
		if (editAttributeList != null && editAttributeList.size() > 0) {
			EditImplementAttribute a = editAttributeList.get(0);
			modelName = implementEntityName;
			keyCount = a.getKeyCount();
		} else {
			// この処理は行われないはず
			modelName = "";
		}
		return modelName + "_IDX" + (keyCount + 1);
	}

	/**
	 * キー修正時に使用するコンストラクタ
	 * 
	 * @param parentShell
	 *            親
	 * @param keyModel
	 *            キーモデル
	 * @param editAttributeList
	 *            編集用アトリビュートのリスト
	 */
	public IndexEditDialog(Shell parentShell, KeyModel keyModel,
			List<EditImplementAttribute> editAttributeList) {
		super(parentShell);
		// getShell().setText("キー編集");
		this.keyModel = keyModel;
		this.attributes = convert(editAttributeList);
		this.attributes.removeAll(keyModel.getAttributes());
	}

	private List<IAttribute> convert(List<EditImplementAttribute> sourceList) {
		List<IAttribute> targetList = new ArrayList<IAttribute>();

		for (EditImplementAttribute ea : sourceList) {
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
		Composite composite = new Composite(parent, SWT.NULL);
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
		keyModel.setMasterKey(panel.isMasterKey());
		super.okPressed();
	}

	/**
	 * 編集後のキーモデルを返す
	 * 
	 * @return the keyModel
	 */
	public KeyModel getKeyModel() {
		return keyModel;
	}

}

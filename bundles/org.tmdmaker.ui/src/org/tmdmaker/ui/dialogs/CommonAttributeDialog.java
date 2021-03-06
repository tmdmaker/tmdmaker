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
import java.beans.PropertyChangeListener;
import java.util.List;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.tmdmaker.core.model.IAttribute;
import org.tmdmaker.ui.Messages;
import org.tmdmaker.ui.dialogs.components.AttributeSettingPanel;
import org.tmdmaker.ui.dialogs.model.EditCommonAttribute;

/**
 * 共通属性編集ダイアログ
 * 
 * @author nakaG
 * 
 */
public class CommonAttributeDialog extends Dialog implements PropertyChangeListener {
	/** 編集用モデル */
	private EditCommonAttribute model;
	/** アトリビュート設定用 */
	private AttributeSettingPanel panel;

	/**
	 * コンストラクタ
	 * 
	 * @param parentShell
	 *            親
	 */
	public CommonAttributeDialog(Shell parentShell, List<IAttribute> commonAttributes) {
		super(parentShell);
		model = new EditCommonAttribute(commonAttributes);
		model.addPropertyChangeListener(this);
	}

	/**
	 * 
	 * @param evt
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		panel.updateAttributeTable();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		getShell().setText(Messages.CommonAttributeSettings);

		Composite composite = new Composite(parent, SWT.NULL);
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 1;
		composite.setLayout(gridLayout);

		panel = new AttributeSettingPanel(composite, SWT.NULL, model);

		composite.pack();
		return composite;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#close()
	 */
	@Override
	public boolean close() {
		model.removePropertyChangeListener(this);
		return super.close();
	}

	/**
	 * 編集後の共通属性のリストを取得する
	 * 
	 * @return
	 */
	public List<IAttribute> getEditedAttributes() {
		return model.getAttributesOrder();
	}
}

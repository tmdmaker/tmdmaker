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

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import jp.sourceforge.tmdmaker.Messages;
import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.ui.dialogs.components.EntityNameAndTypeSettingPanel;
import jp.sourceforge.tmdmaker.ui.dialogs.models.EntityCreation;

/**
 * エンティティ新規作成ダイアログ
 * 
 * @author nakaG
 * 
 */
public class EntityCreateDialog extends Dialog {
	/** エンティティ名称・種類設定用パネル */
	private EntityNameAndTypeSettingPanel panel;
	private EntityCreation entity;

	/**
	 * コンストラクタ
	 * 
	 * @param parentShell
	 *            親
	 */
	public EntityCreateDialog(Shell parentShell) {
		super(parentShell);
		entity = new EntityCreation();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		getShell().setText(Messages.CreateEntity);

		Composite composite = new Composite(parent, SWT.NULL);
		FillLayout fl_composite = new FillLayout(SWT.HORIZONTAL);
		fl_composite.marginWidth = 5;
		fl_composite.marginHeight = 5;
		composite.setLayout(fl_composite);

		panel = new EntityNameAndTypeSettingPanel(composite, SWT.NULL, entity);
		GridLayout gridLayout = (GridLayout) panel.getLayout();
		gridLayout.marginRight = 5;
		gridLayout.marginLeft = 5;

		composite.pack();
		return composite;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#okPressed()
	 */
	@Override
	protected void okPressed() {

		if (entity.isValid()) {
			super.okPressed();
		} else {
			return;
		}
	}

	public AbstractEntityModel getCreateModel() {
		return entity.getCreateModel();
	}
}
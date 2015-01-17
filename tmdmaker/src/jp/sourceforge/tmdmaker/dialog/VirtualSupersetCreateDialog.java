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
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import jp.sourceforge.tmdmaker.dialog.component.ModelSelectPanel;
import jp.sourceforge.tmdmaker.dialog.component.VirtualSupersetSettingPanel;
import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.Diagram;
import jp.sourceforge.tmdmaker.model.ModelElement;
import jp.sourceforge.tmdmaker.model.VirtualSuperset;
import jp.sourceforge.tmdmaker.model.VirtualSupersetType;
import jp.sourceforge.tmdmaker.model.rule.VirtualEntityRule;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * みなしスーパーセット作成ダイアログ
 * 
 * @author nakaG
 * 
 */
public class VirtualSupersetCreateDialog extends Dialog {
	private VirtualSupersetSettingPanel panel1;
	private ModelSelectPanel panel2;
	private VirtualSuperset superset;
	private VirtualSuperset editedValue;
	private VirtualSupersetType editedAggregator;
	private List<AbstractEntityModel> notSelection;
	private List<AbstractEntityModel> selection;
	private ModifyListener listener = new ModifyListener() {

		@Override
		public void modifyText(ModifyEvent e) {
			Text t = (Text) e.getSource();
			String name = t.getText();
			Button okButton = getButton(IDialogConstants.OK_ID);
			if (okButton != null) {
				okButton.setEnabled(name.length() != 0);
			}
		}
	};

	/**
	 * コンストラクタ
	 * 
	 * @param parentShell
	 *            親
	 * @param diagram
	 *            対象ダイアグラム
	 * @param superset
	 *            みなしスーパーセット
	 * @param selectedList
	 *            選択しているエンティティ系モデルのリスト
	 */
	public VirtualSupersetCreateDialog(Shell parentShell, Diagram diagram,
			VirtualSuperset superset, List<AbstractEntityModel> selectedList) {
		super(parentShell);
		this.superset = superset;
		selection = setupSelection(selectedList);

		notSelection = new ArrayList<AbstractEntityModel>();
		for (ModelElement m : diagram.getChildren()) {
			if (m instanceof AbstractEntityModel && !m.equals(superset)
					&& !selection.contains(m)) {
				notSelection.add((AbstractEntityModel) m);
			}
		}
	}

	private List<AbstractEntityModel> setupSelection(List<AbstractEntityModel> selectedList) {
		List<AbstractEntityModel> distinctList = new ArrayList<AbstractEntityModel>();
		// 重複排除
		Set<AbstractEntityModel> selectionTarget = new LinkedHashSet<AbstractEntityModel>();
		if (this.superset != null) {
			selectionTarget.addAll(this.superset.getVirtualSubsetList());
		}
		selectionTarget.addAll(selectedList);
		distinctList.addAll(selectionTarget);
		return distinctList;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		getShell().setText("スーパーセット編集");

		Composite composite = new Composite(parent, SWT.NULL);
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 1;
		composite.setLayout(gridLayout);

		panel1 = new VirtualSupersetSettingPanel(composite, SWT.NULL);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		panel1.setLayoutData(gridData);
		panel1.addNameModifyListener(listener);

		panel2 = new ModelSelectPanel(composite, SWT.NULL);
		gridData = new GridData(GridData.FILL_HORIZONTAL);

		panel2.setLayoutData(gridData);
		
		return composite;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#okPressed()
	 */
	@Override
	protected void okPressed() {
		editedValue = VirtualEntityRule.createVirtualSuperset(panel1
				.getVirtualSupersetName());
		// editedValue.setName(panel1.getVirtualSupersetName());
		editedAggregator = new VirtualSupersetType();
		editedAggregator.setApplyAttribute(panel1.isApplyAttributeSelected());

		super.okPressed();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#close()
	 */
	@Override
	public boolean close() {
		panel1.removeNameModifyListener(listener);
		return super.close();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#createButtonsForButtonBar(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		super.createButtonsForButtonBar(parent);
		Button okButton = getButton(IDialogConstants.OK_ID);
		okButton.setEnabled(false);
	}

	
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#createContents(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createContents(Composite parent) {
		Control c =  super.createContents(parent);
		panel1.initializeValue(superset);
		panel2.initializeValue(selection, notSelection);

		return c;
	}

	/**
	 * @return the editedValue
	 */
	public VirtualSuperset getEditedValue() {
		return editedValue;
	}

	/**
	 * @return the notSelection
	 */
	public List<AbstractEntityModel> getNotSelection() {
		return notSelection;
	}

	/**
	 * @return the selection
	 */
	public List<AbstractEntityModel> getSelection() {
		return selection;
	}

	/**
	 * @return the editedAggregator
	 */
	public VirtualSupersetType getEditedAggregator() {
		return editedAggregator;
	}

}

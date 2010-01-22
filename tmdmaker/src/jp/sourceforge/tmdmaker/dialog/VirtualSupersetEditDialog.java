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

import jp.sourceforge.tmdmaker.dialog.component.VirtualSupersetSettingPanel;
import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.Diagram;
import jp.sourceforge.tmdmaker.model.ModelElement;
import jp.sourceforge.tmdmaker.model.VirtualSuperset;
import jp.sourceforge.tmdmaker.model.VirtualSupersetAggregator;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

/**
 * みなしスーパーセット編集ダイアログ
 * 
 * @author nakaG
 * 
 */
public class VirtualSupersetEditDialog extends Dialog {
	private VirtualSupersetSettingPanel panel1;
	private Diagram diagram;
	private VirtualSuperset superset;
	private VirtualSuperset editedValue;
	private VirtualSupersetAggregator editedAggregator;
	private List<AbstractEntityModel> notSelection;
	private List<AbstractEntityModel> selection = new ArrayList<AbstractEntityModel>();

	/**
	 * コンストラクタ
	 * 
	 * @param parentShell 親
	 * @param diagram 対象ダイアグラム
	 * @param superset みなしスーパーセット
	 */
	public VirtualSupersetEditDialog(Shell parentShell, Diagram diagram,
			VirtualSuperset superset) {
		super(parentShell);
		this.diagram = diagram;
		this.superset = superset;
		if (this.superset != null) {
			selection.addAll(this.superset.getVirtualSubsetList());
		}
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
		panel1 = new VirtualSupersetSettingPanel(composite, SWT.NULL);
		notSelection = new ArrayList<AbstractEntityModel>();
		for (ModelElement m : diagram.getChildren()) {
			if (m instanceof AbstractEntityModel && !m.equals(superset)
					&& !selection.contains(m)) {
				notSelection.add((AbstractEntityModel) m);
			}
		}
		panel1.initializeValue(notSelection, selection, superset);
		return composite;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#okPressed()
	 */
	@Override
	protected void okPressed() {
		editedValue = new VirtualSuperset();
		editedValue.setName(panel1.getVirtualSupersetName());
		for (ModelElement m : selection) {
			System.out.println("selection:" + m.getName());
		}
		for (ModelElement m : notSelection) {
			System.out.println("notSelection:" + m.getName());
		}
		editedAggregator = new VirtualSupersetAggregator();
		editedAggregator.setApplyAttribute(panel1.isApplyAttributeSelected());
		
		super.okPressed();
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
	public VirtualSupersetAggregator getEditedAggregator() {
		return editedAggregator;
	}
	
}

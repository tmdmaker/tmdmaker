/*
 * Copyright 2009-2016 TMD-Maker Project <http://tmdmaker.osdn.jp/>
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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.sourceforge.tmdmaker.Messages;
import jp.sourceforge.tmdmaker.dialog.component.ImplementInfoEditPanel;
import jp.sourceforge.tmdmaker.dialog.component.IndexSettingPanel;
import jp.sourceforge.tmdmaker.dialog.component.ModelSelectPanel;
import jp.sourceforge.tmdmaker.dialog.component.SarogateKeyPanel;
import jp.sourceforge.tmdmaker.dialog.model.EditImplementAttribute;
import jp.sourceforge.tmdmaker.dialog.model.EditImplementEntity;
import jp.sourceforge.tmdmaker.dialog.model.EditSarogateKey;
import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.rule.ImplementRule;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

/**
 * 実装情報編集Dialog
 * 
 * @author nakaG
 * 
 */
public class ImplementInfoEditDialog extends Dialog {
	/** 編集情報実装用 */
	private ImplementInfoEditPanel panel1;
	/** 編集元モデル */
	private EditImplementEntity implementModel;
	private AbstractEntityModel model;
	/** 編集用アトリビュート */
	private List<EditImplementAttribute> editAttributeList = new ArrayList<EditImplementAttribute>();
	/** 編集結果格納用 */
	private AbstractEntityModel editedValueEntity;
	private List<EditImplementAttribute> editedValueAttributes = new ArrayList<EditImplementAttribute>();
	private List<EditImplementAttribute> editedValueIdentifieres = new ArrayList<EditImplementAttribute>();
	private Map<AbstractEntityModel, List<EditImplementAttribute>> otherModelAttributesMap = new HashMap<AbstractEntityModel, List<EditImplementAttribute>>();
	private ModelSelectPanel panel2;
	private IndexSettingPanel panel3;
	private SarogateKeyPanel panel4;

	private Button updateButton;

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
		this.implementModel = new EditImplementEntity(model);
		this.implementModel.addPropertyChangeListener(new ImplementInfoUpdateListener());
		editAttributeList = implementModel.getAttributes();
		otherModelAttributesMap = implementModel.getOtherModelAttributesMap();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		getShell().setText(Messages.EditImplementInformation);
		TabFolder tabFolder = new TabFolder(parent, SWT.NULL);
		// １つめのタブを作成
		TabItem item1 = new TabItem(tabFolder, SWT.NULL);
		item1.setText(Messages.TableDesign);

		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 1;
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);

		Composite composite = new Composite(tabFolder, SWT.NULL);
		composite.setLayout(gridLayout);
		panel1 = new ImplementInfoEditPanel(composite, SWT.NULL, implementModel);
		// panel1.initializeValue(model, editAttributeList);
		panel1.setLayoutData(gridData);

		gridData = new GridData(GridData.FILL_HORIZONTAL);
		panel2 = new ModelSelectPanel(composite, SWT.NULL);

		List<AbstractEntityModel> selectModels = model.getImplementDerivationModels();
		List<AbstractEntityModel> notSelectModels = ImplementRule.findNotImplementModel(model);
		notSelectModels.removeAll(selectModels);
		panel2.initializeValue(selectModels, notSelectModels);
		panel2.setLayoutData(gridData);

		item1.setControl(composite);

		updateButton = new Button(composite, SWT.NULL);
		updateButton.setText(Messages.ReflectAttribute);
		updateButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {

			/**
			 * {@inheritDoc}
			 * 
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			@Override
			public void widgetSelected(SelectionEvent e) {
				for (Map.Entry<AbstractEntityModel, List<EditImplementAttribute>> entry : otherModelAttributesMap
						.entrySet()) {
					System.out.println("remove all other model"); //$NON-NLS-1$
					editAttributeList.removeAll(entry.getValue());
				}
				for (AbstractEntityModel m : panel2.getSelectModels()) {
					System.out.println(m);
					System.out.println(m.getName());
					List<EditImplementAttribute> list = otherModelAttributesMap.get(m);
					System.out.println("add other model"); //$NON-NLS-1$
					if (list != null) {
						System.out.println("add other model attributes"); //$NON-NLS-1$
						editAttributeList.addAll(list);
					}
				}
				System.out.println("update"); //$NON-NLS-1$
				panel1.updateTable();
				// panel3.initializeValue(editAttributeList, editedKeyModels);
			}
		});

		// ２つめのタブを作成
		TabItem item2 = new TabItem(tabFolder, SWT.NULL);
		item2.setText(Messages.KeyDefinitions);
		gridLayout = new GridLayout();
		gridLayout.numColumns = 1;
		composite = new Composite(tabFolder, SWT.NULL);
		composite.setLayout(gridLayout);
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		panel3 = new IndexSettingPanel(composite, SWT.NULL, implementModel);
		panel3.setLayoutData(gridData);
		// panel3.initializeValue(editAttributeList, editedKeyModels);
		panel3.updateTable();
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		panel4 = new SarogateKeyPanel(composite, SWT.NULL, implementModel);
		panel4.setLayoutData(gridData);
		panel4.refreshVisual();
		item2.setControl(composite);

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
		editedValueEntity = model.getCopy();
		editedValueEntity.setImplementName(panel1.getImplementName());
		editedValueEntity.setImplementDerivationModels(panel2.getSelectModels());
		editedValueEntity.setKeyModels(implementModel.getKeyModels());
		// implementModel.getSarogateKey();
		// editedKeyModels.getSarogateKey().copyFrom(e);
		createEditAttributeResult();

		super.okPressed();
	}

	private void createEditAttributeResult() {
		for (EditImplementAttribute ea : implementModel.getAttributes()) {
			if (ea.isEdited()) {
				editedValueAttributes.add(ea);
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
	public List<EditImplementAttribute> getEditedValueAttributes() {
		return editedValueAttributes;
	}

	/**
	 * @return the editedValueIdentifieres
	 */
	public List<EditImplementAttribute> getEditedValueIdentifieres() {
		return editedValueIdentifieres;
	}

	public EditSarogateKey getEditedSarogateKey() {
		return implementModel.getSarogateKey();
	}

	/**
	 * サービス
	 *
	 */
	private class ImplementInfoUpdateListener implements PropertyChangeListener {

		/**
		 * {@inheritDoc}
		 * 
		 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
		 */
		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			if (evt.getPropertyName().equals(EditImplementEntity.PROPERTY_ATTRIBUTES)) {
				panel1.updateTable();
				panel3.updateTable();
				panel4.refreshVisual();
			} else if (evt.getPropertyName().equals(EditImplementEntity.PROPERTY_SAROGATE)) {
				panel3.updateTable();
				panel1.updateTable();
			} else if (evt.getPropertyName().equals(EditImplementEntity.PROPERTY_KEYMODELS)) {
				panel3.updateTable();
			}
		}
	}
}

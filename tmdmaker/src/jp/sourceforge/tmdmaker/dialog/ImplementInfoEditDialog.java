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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import jp.sourceforge.tmdmaker.dialog.component.ImplementInfoEditPanel;
import jp.sourceforge.tmdmaker.dialog.component.IndexSettingPanel;
import jp.sourceforge.tmdmaker.dialog.component.ModelSelectPanel;
import jp.sourceforge.tmdmaker.dialog.model.EditImplementAttribute;
import jp.sourceforge.tmdmaker.dialog.model.EditImplementEntity;
import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.Attribute;
import jp.sourceforge.tmdmaker.model.Detail;
import jp.sourceforge.tmdmaker.model.Entity;
import jp.sourceforge.tmdmaker.model.Identifier;
import jp.sourceforge.tmdmaker.model.IdentifierRef;
import jp.sourceforge.tmdmaker.model.KeyModel;
import jp.sourceforge.tmdmaker.model.KeyModels;
import jp.sourceforge.tmdmaker.model.ReusedIdentifier;
import jp.sourceforge.tmdmaker.model.rule.ImplementRule;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
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
	private KeyModels editedKeyModels = new KeyModels();
	private Map<AbstractEntityModel, List<EditImplementAttribute>> otherModelAttributesMap = new HashMap<AbstractEntityModel, List<EditImplementAttribute>>();
	private ModelSelectPanel panel2;
	private IndexSettingPanel panel3;

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

		if (model instanceof Entity) {
			editAttributeList.add(new EditImplementAttribute(model,((Entity) model)
					.getIdentifier()));
		} else if (model instanceof Detail) {
			editAttributeList.add(new EditImplementAttribute(model, ((Detail) model)
					.getDetailIdentifier()));
		}
		// Re-usedをカラムとして追加
		Map<AbstractEntityModel, ReusedIdentifier> reused = model
				.getReusedIdentifieres();
		for (Entry<AbstractEntityModel, ReusedIdentifier> entry : reused
				.entrySet()) {
			for (IdentifierRef ref : entry.getValue().getIdentifires()) {
				editAttributeList.add(new EditImplementAttribute(model, ref));
			}
		}
		// attributeをカラムとして追加
		for (Attribute a : model.getAttributes()) {
			editAttributeList.add(new EditImplementAttribute(model, a));
		}
		// 対象モデルを元とした実装しないモデル（サブセット、みなしエンティティ）のattributeを抽出
		for (AbstractEntityModel m : ImplementRule.findNotImplementModel(model)) {
			List<EditImplementAttribute> list = new ArrayList<EditImplementAttribute>();
			for (Attribute a : m.getAttributes()) {
				list.add(new EditImplementAttribute(m, a));
			}
			otherModelAttributesMap.put(m, list);			
		}
		// 対象モデルに戻して実装するモデルが保持するattributeを抽出
		if (model.getImplementDerivationModels() != null) {
			for (AbstractEntityModel m : model.getImplementDerivationModels()) {
				List<EditImplementAttribute> list = otherModelAttributesMap.get(m);
				if (list != null) {
					editAttributeList.addAll(list);
				}
			}
		}
		// 対象モデルのキーを抽出
		if (model.getKeyModels() != null) {
			for (KeyModel km : model.getKeyModels()) {
				editedKeyModels.add(km.getCopy());
			}
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
		TabFolder tabFolder = new TabFolder(parent, SWT.NULL);
		tabFolder.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				if (((TabItem)e.item).getText().equals("キー定義")) {
					System.out.println(e.data);
					panel3.updateTable();
				}
			}
			
		});
		// １つめのタブを作成
		TabItem item1 = new TabItem(tabFolder, SWT.NULL);
		item1.setText("テーブル設計");
		
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 1;
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);

		Composite composite = new Composite(tabFolder, SWT.NULL);
		composite.setLayout(gridLayout);
		panel1 = new ImplementInfoEditPanel(composite, SWT.NULL);
		panel1.initializeValue(model, editAttributeList);
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
		updateButton.setText("属性一覧へ反映");
		updateButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {

			/**
			 * {@inheritDoc}
			 * 
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			@Override
			public void widgetSelected(SelectionEvent e) {
				for (Map.Entry<AbstractEntityModel, List<EditImplementAttribute>> entry : otherModelAttributesMap.entrySet()) {
					System.out.println("remove all other model");
					editAttributeList.removeAll(entry.getValue());
				}
				for (AbstractEntityModel m : panel2.getSelectModels()) {
					System.out.println(m);
					System.out.println(m.getName());
					List<EditImplementAttribute> list = otherModelAttributesMap.get(m);
					System.out.println("add other model");
					if (list != null) {
						System.out.println("add other model attributes");
						editAttributeList.addAll(list);
					}
				}
				System.out.println("update");
				panel1.initializeValue(model, editAttributeList);
				panel3.initializeValue(editAttributeList, editedKeyModels);
			}
		});

		// ２つめのタブを作成
		TabItem item2 = new TabItem(tabFolder, SWT.NULL);
		item2.setText("キー定義");
		gridLayout = new GridLayout();
		gridLayout.numColumns = 1;
		composite =	new Composite(tabFolder, SWT.NULL);
		composite.setLayout(gridLayout);
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		panel3 = new IndexSettingPanel(composite, SWT.NULL);
		panel3.setLayoutData(gridData);
		panel3.initializeValue(editAttributeList, editedKeyModels);
		item2.setControl(composite);
		
		
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
		editedValueEntity.setKeyModels(editedKeyModels);
		createEditAttributeResult();

		super.okPressed();
	}
	
	private void createEditAttributeResult() {
		for (EditImplementAttribute ea : panel1.getAttributes()) {
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
	public List<EditImplementAttribute> getEditedValueAttributes() {
		return editedValueAttributes;
	}

	/**
	 * @return the editedValueIdentifieres
	 */
	public List<EditImplementAttribute> getEditedValueIdentifieres() {
		return editedValueIdentifieres;
	}

}

/*
 * Copyright 2009-2012 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
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
package jp.sourceforge.tmdmaker.ui.preferences.appearance;

import java.util.ArrayList;
import java.util.List;

import jp.sourceforge.tmdmaker.TMDPlugin;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.ColorFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

/**
 * 外観設定ページ
 * 
 * @author nakaG
 * 
 */
public class AppearancePreferencePage extends FieldEditorPreferencePage
		implements IWorkbenchPreferencePage {

	private Composite colorFields;
	private BooleanFieldEditor editor;
	private List<ColorFieldEditor> colorFieldEditors = new ArrayList<ColorFieldEditor>();

	/**
	 * @param title
	 */
	public AppearancePreferencePage() {
		super(GRID);
		setPreferenceStore(TMDPlugin.getDefault().getPreferenceStore());
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	@Override
	public void init(IWorkbench workbench) {
		// TODO Auto-generated method stub
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.preference.FieldEditorPreferencePage#initialize()
	 */
	@Override
	protected void initialize() {
		super.initialize();
		setColorFieldsEnabled(getPreferenceStore().getBoolean(
				AppearancePreferenceConstants.P_ENTITY_COLOR_ENABLED));
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.preference.FieldEditorPreferencePage#createFieldEditors()
	 */
	@Override
	protected void createFieldEditors() {
		Composite parent = getFieldEditorParent();
		Group colorGroup = new Group(parent, SWT.NONE);
		colorGroup.setText("色設定");
		colorGroup.setLayout(new GridLayout(1, true));
		GridData colorGd = new GridData(GridData.FILL_HORIZONTAL);
		colorGd.horizontalSpan = 2;
		colorGroup.setLayoutData(colorGd);

		editor = new BooleanFieldEditor(
				AppearancePreferenceConstants.P_ENTITY_COLOR_ENABLED,
				"モデルの色設定を有効にする :", colorGroup) {

			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * org.eclipse.jface.preference.BooleanFieldEditor#valueChanged(
			 * boolean, boolean)
			 */
			@Override
			protected void valueChanged(boolean oldValue, boolean newValue) {
				setColorFieldsEnabled(newValue);
				super.valueChanged(oldValue, newValue);
			}

		};

		addField(editor);
		colorFields = new Composite(colorGroup, SWT.NONE);
		new Label(colorFields, SWT.NONE);
		// colorFields.setLayout(new GridLayout(3, true));
		// GridData colorFontGd = new GridData(GridData.FILL_BOTH);
		// colorFontGd.horizontalSpan = 3;
		// colorFields.setLayoutData(colorFontGd);

		Label l = new Label(colorFields, SWT.NONE);
		l.setText("背景色");
		// l = new Label(colorFields, SWT.NONE);
		// l.setText("フォント");
		// new Label(colorFields, SWT.NONE);

		colorFieldEditors.add(new ColorFieldEditor(
				AppearancePreferenceConstants.P_RESOURCE_ENTITY_COLOR,
				"リソース :", colorFields));

		colorFieldEditors.add(new ColorFieldEditor(
				AppearancePreferenceConstants.P_EVENT_ENTITY_COLOR, "イベント :",
				colorFields));

		colorFieldEditors.add(new ColorFieldEditor(
				AppearancePreferenceConstants.P_COMBINATION_TABLE_COLOR,
				"対照表 :", colorFields));
		colorFieldEditors.add(new ColorFieldEditor(
				AppearancePreferenceConstants.P_MAPPING_LIST_COLOR, "対応表 :",
				colorFields));
		colorFieldEditors.add(new ColorFieldEditor(
				AppearancePreferenceConstants.P_RECURSIVE_TABLE_COLOR, "再帰表 :",
				colorFields));
		colorFieldEditors.add(new ColorFieldEditor(
				AppearancePreferenceConstants.P_RESOURCE_SUBSET_COLOR,
				"サブセット（リソース） :", colorFields));
		colorFieldEditors.add(new ColorFieldEditor(
				AppearancePreferenceConstants.P_EVENT_SUBSET_COLOR,
				"サブセット（イベント） :", colorFields));
		colorFieldEditors.add(new ColorFieldEditor(
				AppearancePreferenceConstants.P_MULTIVALUE_OR_COLOR, "多値のOR :",
				colorFields));
		colorFieldEditors.add(new ColorFieldEditor(
				AppearancePreferenceConstants.P_VIRTUAL_ENTITY_COLOR,
				"みなしエンティティ :", colorFields));
		colorFieldEditors.add(new ColorFieldEditor(
				AppearancePreferenceConstants.P_RESOURCE_VIRTUAL_ENTITY_COLOR,
				"みなしエンティティ（リソース） :", colorFields));
		colorFieldEditors.add(new ColorFieldEditor(
				AppearancePreferenceConstants.P_EVENT_VIRTUAL_ENTITY_COLOR,
				"みなしエンティティ（イベント） :", colorFields));
		colorFieldEditors.add(new ColorFieldEditor(
				AppearancePreferenceConstants.P_SUPERSET_COLOR, "みなしスーパーセット:",
				colorFields));
		colorFieldEditors.add(new ColorFieldEditor(
				AppearancePreferenceConstants.P_LAPUTA_COLOR, "ラピュタ:",
				colorFields));
		for (ColorFieldEditor e : colorFieldEditors) {
			addField(e);
		}
	}

	private void setColorFieldsEnabled(boolean enabled) {
		for (ColorFieldEditor e : colorFieldEditors) {
			e.setEnabled(enabled, colorFields);
		}

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.preference.FieldEditorPreferencePage#performDefaults()
	 */
	@Override
	protected void performDefaults() {
		super.performDefaults();
		setColorFieldsEnabled(editor.getBooleanValue());
	}

}

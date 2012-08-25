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
	private List<ColorFieldEditor> fontFieldEditors = new ArrayList<ColorFieldEditor>();

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
		Label l = new Label(colorFields, SWT.NONE);
		l.setText("背景色");

		for (ModelAppearance a : ModelAppearance.values()) {
			colorFieldEditors.add(new ColorFieldEditor(a
					.getBackgroundColorPropertyName(), a.getLabel(),
					colorFields));
		}

		for (ColorFieldEditor e : colorFieldEditors) {
			addField(e);
		}
		new Label(colorFields, SWT.NONE);
		l = new Label(colorFields, SWT.NONE);
		l.setText("罫線・フォント");

		for (ModelAppearance a : ModelAppearance.values()) {
			fontFieldEditors.add(new ColorFieldEditor(a
					.getFontColorPropertyName(), a.getLabel(), colorFields));
		}

		for (ColorFieldEditor e : fontFieldEditors) {
			addField(e);
		}

	}

	private void setColorFieldsEnabled(boolean enabled) {
		for (ColorFieldEditor e : colorFieldEditors) {
			e.setEnabled(enabled, colorFields);
		}
		for (ColorFieldEditor e : fontFieldEditors) {
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

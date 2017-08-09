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
package jp.sourceforge.tmdmaker.ui.preferences.appearance;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.ColorFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import jp.sourceforge.tmdmaker.Messages;
import jp.sourceforge.tmdmaker.TMDPlugin;

/**
 * 外観設定ページ
 * 
 * @author nakaG
 * 
 */
public class AppearancePreferencePage extends FieldEditorPreferencePage implements
		IWorkbenchPreferencePage {

	private Composite colorFields;
	private BooleanFieldEditor editor;
	private List<ColorFieldEditor> colorFieldEditors = new ArrayList<ColorFieldEditor>();
	private List<ColorFieldEditor> fontFieldEditors = new ArrayList<ColorFieldEditor>();

	/**
	 * コンストラクタ
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
		colorGroup.setText(Messages.ColorSettings);
		colorGroup.setLayout(new GridLayout(1, true));

		editor = new BooleanFieldEditor(AppearancePreferenceConstants.P_ENTITY_COLOR_ENABLED,
				Messages.EnableColorSetting, colorGroup);

		addField(editor);
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

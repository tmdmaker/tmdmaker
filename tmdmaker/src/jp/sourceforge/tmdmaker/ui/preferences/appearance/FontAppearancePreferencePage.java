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

import org.eclipse.jface.preference.ColorFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import jp.sourceforge.tmdmaker.Messages;
import jp.sourceforge.tmdmaker.TMDPlugin;

/**
 * フォント外観設定ページ
 * 
 * @author nakaG
 * 
 */
public class FontAppearancePreferencePage extends FieldEditorPreferencePage
		implements IWorkbenchPreferencePage {

	private Composite colorFields;
	private List<ColorFieldEditor> fontFieldEditors = new ArrayList<ColorFieldEditor>();
	private IPropertyChangeListener listener = new IPropertyChangeListener() {
		@Override
		public void propertyChange(PropertyChangeEvent event) {
			if (event.getProperty().equals(AppearancePreferenceConstants.P_ENTITY_COLOR_ENABLED)) {
				Boolean colorEnabled = (Boolean) event.getNewValue();
				setColorFieldsEnabled(colorEnabled);
			}
		}
	};

	/**
	 * コンストラクタ
	 */
	public FontAppearancePreferencePage() {
		super(GRID);
		setPreferenceStore(TMDPlugin.getDefault().getPreferenceStore());
		getPreferenceStore().addPropertyChangeListener(listener);
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
		setColorFieldsEnabled(getPreferenceStore()
				.getBoolean(AppearancePreferenceConstants.P_ENTITY_COLOR_ENABLED));
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
		colorGroup.setText(Messages.FontAppearancePreferencePage_0);
		colorGroup.setLayout(new GridLayout(1, true));
		
		colorFields = new Composite(colorGroup, SWT.NONE);
		new Label(colorFields, SWT.NONE);
		Label l = new Label(colorFields, SWT.NONE);
		l.setText(Messages.FontAppearancePreferencePage_1);

		for (ModelAppearance a : ModelAppearance.values()) {
			fontFieldEditors.add(
					new ColorFieldEditor(a.getFontColorPropertyName(), a.getLabel(), colorFields));
		}

		for (ColorFieldEditor e : fontFieldEditors) {
			addField(e);
		}

	}

	private void setColorFieldsEnabled(boolean enabled) {
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
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.jface.preference.FieldEditorPreferencePage#dispose()
	 */
	@Override
	public void dispose() {
		getPreferenceStore().removePropertyChangeListener(listener);
		super.dispose();
	}
}

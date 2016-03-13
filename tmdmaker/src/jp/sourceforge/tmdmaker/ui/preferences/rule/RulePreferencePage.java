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
package jp.sourceforge.tmdmaker.ui.preferences.rule;

import jp.sourceforge.tmdmaker.Messages;
import jp.sourceforge.tmdmaker.TMDPlugin;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

/**
 * ルール設定ページ
 * 
 * @author nakaG
 * 
 */
public class RulePreferencePage extends FieldEditorPreferencePage
		implements IWorkbenchPreferencePage {

	/**
	 * 
	 */
	public RulePreferencePage() {
		super(GRID);
		setPreferenceStore(TMDPlugin.getDefault().getPreferenceStore());
		setDescription(""); //$NON-NLS-1$
	}

	/**
	 * @param title
	 * @param image
	 * @param style
	 */
	public RulePreferencePage(String title, ImageDescriptor image, int style) {
		super(title, image, style);
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
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.preference.FieldEditorPreferencePage#createFieldEditors()
	 */
	@Override
	protected void createFieldEditors() {
		addField(new StringFieldEditor(RulePreferenceConstants.P_IDENTIFIER_SUFFIXES,
				Messages.RulePreferencePage_0, getFieldEditorParent()));
		addField(new StringFieldEditor(RulePreferenceConstants.P_REPORT_SUFFIXES,
				Messages.RulePreferencePage_1, getFieldEditorParent()));
		BooleanFieldEditor forenKeyEnabledEdior = new BooleanFieldEditor(
				RulePreferenceConstants.P_FOREIGN_KEY_ENABLED, Messages.RulePreferencePage_2,
				getFieldEditorParent());
		addField(forenKeyEnabledEdior);
	}
}

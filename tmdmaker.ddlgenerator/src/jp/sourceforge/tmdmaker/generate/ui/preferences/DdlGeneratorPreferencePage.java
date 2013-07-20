/*
 * Copyright 2009-2013 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
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
package jp.sourceforge.tmdmaker.generate.ui.preferences;

import jp.sourceforge.tmdmaker.TMDPlugin;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

/**
 * DDLGeneratorの設定ページ
 * 
 * @author nakaG
 * 
 */
public class DdlGeneratorPreferencePage extends FieldEditorPreferencePage
		implements IWorkbenchPreferencePage {

	public DdlGeneratorPreferencePage() {
		super(GRID);
		setPreferenceStore(TMDPlugin.getDefault().getPreferenceStore());
	}

	@Override
	public void init(IWorkbench workbench) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void createFieldEditors() {
		BooleanFieldEditor forenKeyEnabledEdior = new BooleanFieldEditor(
				DdlPreferenceConstants.P_FOREIGN_KEY_ENABLED, "外部参照制約を出力する",
				getFieldEditorParent());
		addField(forenKeyEnabledEdior);
	}

}

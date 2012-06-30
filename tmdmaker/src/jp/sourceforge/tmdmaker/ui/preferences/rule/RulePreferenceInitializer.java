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
package jp.sourceforge.tmdmaker.ui.preferences.rule;

import jp.sourceforge.tmdmaker.TMDPlugin;
import jp.sourceforge.tmdmaker.model.rule.EntityRecognitionRule;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

/**
 * ルール初期化
 * 
 * @author nakaG
 * 
 */
public class RulePreferenceInitializer extends AbstractPreferenceInitializer {

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
	 */
	@Override
	public void initializeDefaultPreferences() {
		IPreferenceStore store = TMDPlugin.getDefault().getPreferenceStore();
		store.setDefault(RulePreferenceConstants.P_IDENTIFIER_SUFFIXES,
				EntityRecognitionRule.getInstance()
						.getIdentifierSuffixesString());
		store.setDefault(RulePreferenceConstants.P_REPORT_SUFFIXES,
				EntityRecognitionRule.getInstance().getReportSuffixesString());
	}
}

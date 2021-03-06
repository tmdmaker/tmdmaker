/*
 * Copyright 2009-2019 TMD-Maker Project <https://www.tmdmaker.org>
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
package org.tmdmaker.ui.preferences.rule;

import java.util.List;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;
import org.tmdmaker.core.Configuration;
import org.tmdmaker.core.model.rule.ImplementRule;
import org.tmdmaker.ui.Activator;

/**
 * ルール初期化
 * 
 * @author nakaG
 * 
 */
public class RulePreferenceInitializer extends AbstractPreferenceInitializer {
	/** 設定値の区切り文字 */
	public static final String PREFERENCE_SEPARATOR = ",";

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
	 */
	@Override
	public void initializeDefaultPreferences() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		store.setDefault(RulePreferenceConstants.P_IDENTIFIER_SUFFIXES, 
				list2string(Configuration.getDefault().getIdentifierSuffixes()));

		store.setDefault(RulePreferenceConstants.P_REPORT_SUFFIXES, 
				list2string(Configuration.getDefault().getReportSuffixes()));
		store.setDefault(RulePreferenceConstants.P_FOREIGN_KEY_ENABLED,
				ImplementRule.isForeignKeyEnabled());
	}

	private String list2string(List<String> list) {
		StringBuilder builder = new StringBuilder();
		boolean added = false;
		for (String s : list) {
			if (added) {
				builder.append(PREFERENCE_SEPARATOR);
			}
			builder.append(s);
			added = true;
		}
		return builder.toString();
	}
}

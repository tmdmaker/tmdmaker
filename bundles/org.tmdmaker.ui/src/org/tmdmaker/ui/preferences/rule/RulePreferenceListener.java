/*
 * Copyright 2009-2018 TMD-Maker Project <https://tmdmaker.osdn.jp/>
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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.tmdmaker.core.Configuration;
import org.tmdmaker.core.model.rule.ImplementRule;
import org.tmdmaker.ui.preferences.IPreferenceListener;

/**
 * ルール設定リスナー
 * 
 * @author nakaG
 * 
 */
public class RulePreferenceListener implements IPreferenceListener {

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.util.IPropertyChangeListener#propertyChange(org.eclipse.jface.util.PropertyChangeEvent)
	 */
	@Override
	public void propertyChange(PropertyChangeEvent event) {
		if (event.getProperty().equals(RulePreferenceConstants.P_IDENTIFIER_SUFFIXES)) {
			Configuration.getDefault()
					.setIdentifierSuffixes(string2list((String) event.getNewValue()));
		} else if (event.getProperty().equals(RulePreferenceConstants.P_REPORT_SUFFIXES)) {
			Configuration.getDefault().setReportSuffixes(string2list((String) event.getNewValue()));
		} else if (event.getProperty().equals(RulePreferenceConstants.P_FOREIGN_KEY_ENABLED)) {
			ImplementRule.setForeignKeyEnabled((Boolean) event.getNewValue());
		}
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.tmdmaker.ui.preferences.IPreferenceListener#preferenceStart(org.eclipse.jface.preference.IPreferenceStore)
	 */
	@Override
	public void preferenceStart(IPreferenceStore store) {
		Configuration.getDefault().setIdentifierSuffixes(
				string2list(store.getString(RulePreferenceConstants.P_IDENTIFIER_SUFFIXES)));
		Configuration.getDefault().setReportSuffixes(
				string2list(store.getString(RulePreferenceConstants.P_REPORT_SUFFIXES)));
	}

	private List<String> string2list(String string) {
		List<String> list = new ArrayList<String>();
		for (String s : string.split(RulePreferenceInitializer.PREFERENCE_SEPARATOR)) {
			list.add(s);
		}
		return list;
	}
}

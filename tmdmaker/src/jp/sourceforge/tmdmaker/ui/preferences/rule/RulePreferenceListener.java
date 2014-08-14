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

import jp.sourceforge.tmdmaker.model.rule.EntityRecognitionRule;
import jp.sourceforge.tmdmaker.model.rule.ImplementRule;
import jp.sourceforge.tmdmaker.ui.preferences.IPreferenceListener;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.PropertyChangeEvent;

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
			EntityRecognitionRule.getInstance().setIdentifierSuffixesString(
					(String) event.getNewValue());
		} else if (event.getProperty().equals(RulePreferenceConstants.P_REPORT_SUFFIXES)) {
			EntityRecognitionRule.getInstance().setReportSuffixesString(
					(String) event.getNewValue());
		} else if (event.getProperty().equals(RulePreferenceConstants.P_FOREIGN_KEY_ENABLED)) {
			ImplementRule.setForeignKeyEnabled((Boolean) event.getNewValue());
		}
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.ui.preferences.IPreferenceListener#preferenceStart(org.eclipse.jface.preference.IPreferenceStore)
	 */
	@Override
	public void preferenceStart(IPreferenceStore store) {
		EntityRecognitionRule rule = EntityRecognitionRule.getInstance();
		rule.setIdentifierSuffixesString(store
				.getString(RulePreferenceConstants.P_IDENTIFIER_SUFFIXES));
		rule.setReportSuffixesString(store.getString(RulePreferenceConstants.P_REPORT_SUFFIXES));
	}

}

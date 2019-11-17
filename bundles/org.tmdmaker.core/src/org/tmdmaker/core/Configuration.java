/*
 * Copyright 2009-2019 TMD-Maker Project <https://tmdmaker.osdn.jp/>
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
package org.tmdmaker.core;

import java.util.ArrayList;
import java.util.List;

/**
 * TMD-Makerの設定を保持するクラス.
 * 
 * @author nakag
 *
 */
public class Configuration {

	private static final String DEFAULT_RESOURCE_NAME_FORMAT = "%1$s名称";
	private static final String DEFAULT_EVENT_DATE_FORMAT = "%1$s日";
	/** 個体指定子サフィックスのデフォルト値 */
	private static final String[] DEFAULT_IDENTIFIER_SUFFIXES = { "ID", "ＩＤ", "id", "ｉｄ", "コード",
			"CD", "cd", "番号", "No", "NO", "no", "NUM", "Num", "num" };
	/** 帳票名サフィックスのデフォルト値 */
	private static final String[] DEFAULT_REPORT_SUFFIXES = { "伝票", "報告書", "書", "レポート" };

	private List<String> identifierSuffixes = new ArrayList<String>();
	private List<String> reportSuffixes = new ArrayList<String>();
	private String resourceNameFormat;
	private String eventDateFormat;

	private static Configuration config;

	private void initDefault() {
		array2list(DEFAULT_IDENTIFIER_SUFFIXES, identifierSuffixes);
		array2list(DEFAULT_REPORT_SUFFIXES, reportSuffixes);

		resourceNameFormat = DEFAULT_RESOURCE_NAME_FORMAT;
		eventDateFormat = DEFAULT_EVENT_DATE_FORMAT;
	}

	private void array2list(String[] arrays, List<String> list) {
		for (String s : arrays) {
			list.add(s);
		}
	}

	public static Configuration getDefault() {
		if (config == null) {
			config = new Configuration();
			config.initDefault();
		}
		return config;
	}

	public String getResourceAttributeFormat() {
		return resourceNameFormat;
	}

	public String getEventAttributeFormat() {
		return eventDateFormat;
	}

	public List<String> getIdentifierSuffixes() {
		return identifierSuffixes;
	}

	public List<String> getReportSuffixes() {
		return reportSuffixes;
	}

	public void setIdentifierSuffixes(List<String> identifierSuffixes) {
		this.identifierSuffixes = identifierSuffixes;
	}

	public void setReportSuffixes(List<String> reportSuffixes) {
		this.reportSuffixes = reportSuffixes;
	}

}

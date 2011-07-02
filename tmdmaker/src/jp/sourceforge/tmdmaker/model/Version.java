/*
 * Copyright 2009-2011 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
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
package jp.sourceforge.tmdmaker.model;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * ダイアグラムを作成したエディタのバージョンを表すクラス
 * 
 * @author nakaG
 * 
 */
public class Version {
	/** バージョン番号(major.minor.service.qualifier) */
	private String value;

	private int majorVersion;
	private int minorVersion;
	private int serviceNo;
	private String qualifier;

	/**
	 * コンストラクタ
	 * 
	 * @param value
	 *            バージョン番号
	 */
	public Version(String value) {
		this.value = value;
		setupVersionNo();
	}

	private void setupVersionNo() {
		if (value != null) {
			String[] values = split(value);
			if (values.length == 4) {
				majorVersion = Integer.parseInt(values[0]);
				minorVersion = Integer.parseInt(values[1]);
				serviceNo = Integer.parseInt(values[2]);
				qualifier = values[3];
			}
		}
	}
	private String[] split(String value) {
		StringTokenizer token = new StringTokenizer(value, ".");
		List<String> list = new ArrayList<String>();
		while (token.hasMoreTokens()) {
			list.add(token.nextToken());
		}
		String[] arrays = new String[list.size()];
		return list.toArray(arrays);
	}
	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	public int getMajorVersion() {
		return majorVersion;
	}

	public int getMinorVersion() {
		return minorVersion;
	}

	public int getServiceNo() {
		return serviceNo;
	}

	public String getBuildNo() {
		return qualifier;
	}

}

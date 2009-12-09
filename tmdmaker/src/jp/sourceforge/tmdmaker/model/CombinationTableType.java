/*
 * Copyright 2009 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
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

/**
 * 対照表種類定数
 * 
 * @author nakaG
 * 
 */
public enum CombinationTableType {
	/** L-真 */
	L_TRUTH("L"),
	/** F-真 */
	F_TRUTH("F");
	
	/** 表示用 */
	private String label;
	
	/**
	 * コンストラクタ
	 * @param label 対照表種類の表示名
	 */
	private CombinationTableType(String label) {
		this.label = label;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}
}

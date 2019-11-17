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
package org.tmdmaker.core.model;

/**
 * リレーションシップのカーディナリティ
 * 
 * @author nakaG
 * 
 */
public enum Cardinality {
	/** 1 */
	ONE("1"),
	/** N */
	MANY("N"),
	/** 0あり */
	ZERO("0");
	/** カーディナリティの表示内容 */
	private String label;

	/**
	 * コンストラクタ
	 * 
	 * @param label
	 *            カーディナリティの表示内容
	 */
	Cardinality(String label) {
		this.label = label;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

}

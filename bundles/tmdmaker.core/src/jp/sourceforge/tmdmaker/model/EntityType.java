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
package jp.sourceforge.tmdmaker.model;

/**
 * エンティティ種類定数
 * 
 * @author nakaG
 * 
 */
public enum EntityType {
	/** リソース */
	RESOURCE("リソース","R"),
	/** イベント */
	EVENT("イベント", "E"),
	/** 多値のOR */
	MO("多値のOR", "MO"),
	/** 多値のAND */
	MA("多値のAND", "MA"),
	/** みなしエンティティ */
	VE("みなしエンティティ", "VE"),
	LAPUTA("ラピュタ", ""),
	TURBO("ターボファイル", "TB");
	
	/** ダイアグラム表示用 */
	private String label;
	
	/** 種類名 */
	private String typeName;
	/**
	 * コンストラクタ
	 * @param label エンティティ種類の表示名
	 */
	EntityType(String typeName,String label) {
		this.typeName = typeName;
		this.label = label;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @return the typeName
	 */
	public String getTypeName() {
		return typeName;
	}
	
}

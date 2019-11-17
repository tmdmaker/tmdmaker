/*
 * Copyright 2009-2019 TMD-Maker Project <https://www.tmdmaker.org/>
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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * キーを管理するモデル
 * 
 * @author nakaG
 * 
 */
public class KeyModel implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final String PROPERTY_UNIQUE = "p_unique";

	/** インデックス名 */
	private String name;
	/** ユニーク 制約有無*/
	private boolean unique;
	/** マスターキー判定 */
	private boolean masterKey;
	/** キーのアトリビュート */
	private List<IAttribute> attributes = new ArrayList<IAttribute>();
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @return the unique
	 */
	public boolean isUnique() {
		return unique;
	}
	/**
	 * @param unique the unique to set
	 */
	public void setUnique(boolean unique) {
		this.unique = unique;
		if (!this.unique) {
			setMasterKey(false);
		}
	}
	/**
	 * @return the attributes
	 */
	public List<IAttribute> getAttributes() {
		return attributes;
	}
	/**
	 * @param attributes the attributes to set
	 */
	public void setAttributes(List<IAttribute> attributes) {
		this.attributes = attributes;
	}
	/**
	 * @param masterKey the masterKey to set
	 */
	public void setMasterKey(boolean masterKey) {
		this.masterKey = masterKey;
	}
	/**
	 * @return the masterKey
	 */
	public boolean isMasterKey() {
		return masterKey;
	}
	public boolean contains(IAttribute attribute) {
		return attributes.contains(attribute);
	}
	public int indexOf(IAttribute attribute) {
		return attributes.indexOf(attribute);
	}
	public KeyModel getCopy() {
		KeyModel model = new KeyModel();
		model.setAttributes(new ArrayList<IAttribute>(attributes));
		model.setMasterKey(isMasterKey());
		model.setUnique(isUnique());
		model.setName(getName());
		return model;
	}
}

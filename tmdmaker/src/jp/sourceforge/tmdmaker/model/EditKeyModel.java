/*
 * Copyright 2009-2010 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
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

import jp.sourceforge.tmdmaker.model.util.ModelEditUtils;

/**
 * 編集用キーモデル
 * 
 * @author nakaG
 * 
 */
public class EditKeyModel {
	/** 編集元キーモデル */
	private KeyModel original;
	/** 編集有無 */
	private boolean edited = false;
	/** インデックス名 */
	private String name;
	/** ユニーク 制約有無 */
	private boolean unique;
	/** キーのアトリビュート */
	private List<Attribute> attributes = new ArrayList<Attribute>();

	/**
	 * コンストラクタ
	 * 
	 * @param original
	 *            編集元
	 */
	public EditKeyModel(KeyModel original) {
		this.original = original;
		this.name = ModelEditUtils.toBlankStringIfNull(original.getName());
		this.unique = original.isUnique();
		this.attributes.addAll(original.getAttributes());
	}
	public EditKeyModel(EditKeyModel to) {
		this.original = to.getOriginal();
		this.name = to.getName();
		this.unique = to.isUnique();
		this.attributes.addAll(to.getAttributes());
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
		setEdited(true);
	}

	/**
	 * @param unique
	 *            the unique to set
	 */
	public void setUnique(boolean unique) {
		this.unique = unique;
		setEdited(true);
	}

	public void addAttribute(Attribute attribute) {
		attributes.add(attribute);
		setEdited(true);
	}

	public void addAttribute(int index, Attribute attribute) {
		attributes.add(index, attribute);
		setEdited(true);
	}

	public void removeAttribute(Attribute attribute) {
		attributes.remove(attribute);
		setEdited(true);
	}

	private void setEdited(boolean edited) {
		this.edited = edited;
	}

	/**
	 * @return the original
	 */
	public KeyModel getOriginal() {
		return original;
	}

	/**
	 * @return the edited
	 */
	public boolean isEdited() {
		return edited;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the unique
	 */
	public boolean isUnique() {
		return unique;
	}

	/**
	 * @return the attributes
	 */
	public List<Attribute> getAttributes() {
		return attributes;
	}
	
	/**
	 * @param attributes the attributes to set
	 */
	public void setAttributes(List<Attribute> attributes) {
		this.attributes = attributes;
	}

	public boolean contains(Attribute attribute) {
		return attributes.contains(attribute);
	}
	public int indexOf(Attribute attribute) {
		return attributes.indexOf(attribute);
	}
	public KeyModel createEditedValue() {
		KeyModel edited = new KeyModel();
		edited.setName(name);
		edited.setUnique(unique);
		edited.setAttributes(attributes);
		return edited;
	}
}

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
package jp.sourceforge.tmdmaker.dialog.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.IAttribute;

/**
 * 表の編集用
 * 
 * @author nakaG
 * 
 */
public class EditTable {

	/** プロパティ変更通知用 */
	private PropertyChangeSupport listeners = new PropertyChangeSupport(this);
	public static final String PROPERTY_NAME = "_edit_property_name";
	public static final String PROPERTY_ATTRIBUTES = "_edit_property_attributes";

	/** 編集対象のエンティティ */
	protected AbstractEntityModel entity;
	/** 名称 */
	protected String name;
	/** 物理実装しない */
	protected boolean notImplement = false;
	/** 実装名 */
	protected String implementName = "";

	/** 編集対象のアトリビュート */
	protected List<EditAttribute> attributes = new ArrayList<EditAttribute>();
	private List<EditAttribute> newAttributes = new ArrayList<EditAttribute>();
	private List<EditAttribute> deleteAttributes = new ArrayList<EditAttribute>();

	/**
	 * コンストラクタ
	 * 
	 * @param model
	 *            編集対象のエンティティ
	 */
	public EditTable(AbstractEntityModel model) {
		this.entity = model;
		this.name = model.getName();
		this.notImplement = model.isNotImplement();
		this.implementName = model.getImplementName();
		for (IAttribute a : this.entity.getAttributes()) {
			attributes.add(new EditAttribute(a));
		}
	}

	/**
	 * プロパティ変更通知先追加
	 * 
	 * @param listener
	 *            プロパティ変更通知先
	 */
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		listeners.addPropertyChangeListener(listener);
	}

	/**
	 * プロパティ変更通知
	 * 
	 * @param propName
	 *            変更したプロパティの名称
	 * @param oldValue
	 *            変更前の値
	 * @param newValue
	 *            変更後の値
	 */
	public void firePropertyChange(String propName, Object oldValue,
			Object newValue) {
		listeners.firePropertyChange(propName, oldValue, newValue);
	}

	/**
	 * プロパティ変更通知先削除
	 * 
	 * @param listener
	 *            プロパティ変更通知先
	 */
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		listeners.removePropertyChangeListener(listener);
	}

	/**
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * 
	 * @param name
	 *            the name
	 */
	public void setName(String name) {
		String oldValue = this.name;
		this.name = name;
		firePropertyChange(PROPERTY_NAME, oldValue, name);
	}

	/**
	 * アトリビュートの最大添え字を返す
	 * 
	 * @return size - 1 の添え字
	 */
	public int getMaxAttributeIndex() {
		return attributes.size() - 1;
	}

	/**
	 * 編集用のアトリビュートを追加する
	 */
	public void addAttribute() {
		EditAttribute ea = new EditAttribute();
		ea.setName("アトリビュート" + String.valueOf(attributes.size() + 1));
		attributes.add(ea);
		newAttributes.add(ea);
		firePropertyChange(PROPERTY_ATTRIBUTES, null, ea);
	}

	/**
	 * 編集用のアトリビュートの順番を上げる
	 * 
	 * @param index
	 *            対象アトリビュートの添え字
	 */
	public void upAttribute(int index) {
		int newIndex = index - 1;
		EditAttribute attribute = attributes.remove(index);
		attributes.add(newIndex, attribute);
		firePropertyChange(PROPERTY_ATTRIBUTES, index, newIndex);
	}

	/**
	 * 編集用のアトリビュートの順番を下げる
	 * 
	 * @param index
	 *            対象アトリビュートの添え字
	 */
	public void downAttribute(int index) {
		int newIndex = index + 1;
		EditAttribute attribute = attributes.remove(index);
		attributes.add(newIndex, attribute);
		firePropertyChange(PROPERTY_ATTRIBUTES, index, newIndex);
	}

	/**
	 * 編集用のアトリビュートの値を変更する
	 * 
	 * @param index
	 *            対象アトリビュートの添え字
	 * @param attribute
	 *            変更値
	 */
	public void editAttribute(int index, EditAttribute attribute) {
		attributes.remove(index);
		attributes.add(index, attribute);
		firePropertyChange(PROPERTY_ATTRIBUTES, null, attribute);
	}

	/**
	 * 編集用のアトリビュートを削除する
	 * 
	 * @param index
	 *            対象アトリビュートの添え字
	 */
	public void deleteAttribute(int index) {
		EditAttribute deleted = attributes.remove(index);
		deleteAttributes.add(deleted);
		firePropertyChange(PROPERTY_ATTRIBUTES, deleted, null);
	}

	/**
	 * 編集用のアトリビュートを個体指定子に昇格させる。
	 * 
	 * @param index
	 *            対象アトリビュートの添え字
	 */
	public void uptoIdentifier(int index) {
		// 表は個体指定子を持たないため未実装
		// サブクラス化して実装する
	}

	/**
	 * 
	 * @return the attributes
	 */
	public List<EditAttribute> getAttributes() {
		return attributes;
	}

	/**
	 * 指定した編集用のアトリビュートを取得する
	 * 
	 * @param index
	 *            　対象アトリビュートの添え字
	 * @return　対象アトリビュート
	 */
	public EditAttribute getEditAttribute(int index) {
		return attributes.get(index);
	}

	/**
	 * 編集後のアトリビュートの順序を取得する
	 * 
	 * @return 新しい並び順のアトリビュートの配列
	 */
	public List<IAttribute> getAttributesOrder() {
		List<IAttribute> order = new ArrayList<IAttribute>();
		for (EditAttribute ea : attributes) {
			order.add(ea.getOriginalAttribute());
		}
		return order;
	}

	/**
	 * 
	 * @return the notImplement
	 */
	public boolean isNotImplement() {
		return notImplement;
	}

	/**
	 * 
	 * @param notImplement
	 *            the notImplement
	 */
	public void setNotImplement(boolean notImplement) {
		this.notImplement = notImplement;
	}

	/**
	 * 
	 * @return the implementName
	 */
	public String getImplementName() {
		return implementName;
	}

	/**
	 * 
	 * @param implementName
	 *            the implementName
	 */
	public void setImplementName(String implementName) {
		this.implementName = implementName;
	}

	/**
	 * アトリビュートを個体指定子と入替可能か？
	 * 
	 * @return 入替可能な場合にtrueを返す
	 */
	public boolean canUpToIdentifier() {
		return false;
	}
}
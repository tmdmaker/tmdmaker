/*
 * Copyright 2009-2019 TMD-Maker Project <https://www.tmdmaker.org>
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

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;

import org.tmdmaker.core.model.constraint.Constraint;

/**
 * ノードとなるモデル
 * 
 * @author nakaG
 */
@SuppressWarnings("serial")
public class ModelElement implements Serializable, IAcceptor {
	/** 名称プロパティ定数 */
	public static final String PROPERTY_NAME = "_property_name";
	/** 説明プロパティ定数 */
	public static final String PROPERTY_DESCRIPTION = "_property_description";
	/** 領域プロパティ定数 */
	public static final String PROPERTY_CONSTRAINT = "_property_constraint";
	/** オブジェクトの名称 */
	private String name;
	/** オブジェクトの説明 */
	private String description;
	/** オブジェクトの領域 */
	private Constraint constraint;
	/** プロパティ変更通知用 */
	private PropertyChangeSupport listeners = new PropertyChangeSupport(this);

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
	public void firePropertyChange(String propName, Object oldValue, Object newValue) {
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
		firePropertyChange(PROPERTY_NAME, null, name);
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
		firePropertyChange(PROPERTY_DESCRIPTION, null, description);
	}

	/**
	 * @return the constraint
	 */
	public Constraint getConstraint() {
		return constraint;
	}

	/**
	 * @param constraint
	 *            the constraint to set
	 */
	public void setConstraint(Constraint constraint) {
		Constraint oldValue = this.constraint;
		this.constraint = constraint;
		firePropertyChange(PROPERTY_CONSTRAINT, oldValue, constraint);
	}

	/**
	 * 
	 * {@inheritDoc}
	 *
	 * @see org.tmdmaker.core.model.IAcceptor#accept(org.tmdmaker.core.model.IVisitor)
	 */
	public void accept(IVisitor visitor) {
		visitor.visit(this);
	}

	/**
	 * 位置を移動する.
	 * 
	 * @param x
	 *            x軸
	 * @param y
	 *            y軸
	 */
	public void move(int x, int y) {
		setConstraint(getConstraint().newPosition(x, y));
	}

	/**
	 * サブセットを作成可能か？
	 *
	 * @return サブセットを作成可能な場合はtrueを返す
	 */
	public boolean canCreateSubset() {
		return false;
	}

	/**
	 * 多値のORを作成可能か？
	 *
	 * @return 多値のORを作成可能な場合はtrueを返す
	 */
	public boolean canCreateMultivalueOr() {
		return false;
	}

	/**
	 * 多値のANDを作成可能か？
	 * 
	 * @return 多値のANDを作成可能な場合はtrueを返す
	 */
	public boolean canCreateMultivalueAnd() {
		return false;
	}

	/**
	 * みなしエンティティを作成可能か？
	 *
	 * @return みなしエンティティを作成可能な場合はtrueを返す
	 */
	public boolean canCreateVirtualEntity() {
		return false;
	}

	/**
	 * 初期位置か？
	 * 
	 * @return 座標が新規作成時の初期位置の場合はtrueを返す
	 */
	public boolean isInitialPosition() {
		return getConstraint().isInitialPosition();
	}
}

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

import java.util.ArrayList;
import java.util.List;

/**
 * TMのダイアグラムを表すクラス
 * 
 * @author nakaG
 * 
 */
@SuppressWarnings("serial")
public class Diagram extends ModelElement {
	/** ダイアグラムを作成したプラグインのバージョン */
	private String version;

	/** 子モデル */
	private List<ModelElement> children = new ArrayList<ModelElement>();
	/** 子モデルプロパティ定数 */
	public static final String PROPERTY_CHILDREN = "_property_children";

	/**
	 * 子モデル追加
	 * 
	 * @param child
	 *            エンティティまたは表
	 */
	public void addChild(AbstractEntityModel child) {
		if (!children.contains(child)) {
			children.add(child);
			child.setDiagram(this);
			firePropertyChange(PROPERTY_CHILDREN, null, child);
		}
	}

	/**
	 * 子モデル追加
	 * 
	 * @param child
	 *            エンティティや表以外の子モデル
	 */
	public void addChild(ConnectableElement child) {
		if (!children.contains(child)) {
			children.add(child);
			firePropertyChange(PROPERTY_CHILDREN, null, child);
		}
	}

	/**
	 * 子モデル削除
	 * 
	 * @param child
	 *            エンティティや表
	 */
	public void removeChild(AbstractEntityModel child) {
		child.setDiagram(null);
		children.remove(child);
		firePropertyChange(PROPERTY_CHILDREN, child, null);
	}

	/**
	 * 子モデル削除
	 * 
	 * @param child
	 *            エンティティや表以外の子モデル
	 */
	public void removeChild(ConnectableElement child) {
		children.remove(child);
		firePropertyChange(PROPERTY_CHILDREN, child, null);
	}

	/**
	 * @return the children
	 */
	public List<ModelElement> getChildren() {
		return children;
	}

	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @param version
	 *            the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * エンティティ系モデルとして利用可能なモデルのリストを取得する。
	 * 
	 * @return AbstractEntityModelのリスト
	 */
	public List<AbstractEntityModel> findEntityModel() {
		List<AbstractEntityModel> entities = new ArrayList<AbstractEntityModel>(
				getChildren().size());

		for (ModelElement m : getChildren()) {
			if (m instanceof AbstractEntityModel) {
				entities.add((AbstractEntityModel) m);
			}
		}
		return entities;
	}

}

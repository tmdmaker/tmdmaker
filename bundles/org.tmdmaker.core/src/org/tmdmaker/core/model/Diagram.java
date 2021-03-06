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

	/** 使用するデータベース名 */
	private String databaseName;

	/** 子モデル */
	private List<ModelElement> children = new ArrayList<ModelElement>();
	/** 子モデルプロパティ定数 */
	public static final String PROPERTY_CHILDREN = "_property_children";

	/** 全モデル共通のアトリビュート（実装時のみ） */
	private List<IAttribute> commonAttributes = new ArrayList<IAttribute>();

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
	 * @return the databaseName
	 */
	public String getDatabaseName() {
		return databaseName;
	}

	/**
	 * @param databaseName
	 *            the databaseName to set
	 */
	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	/**
	 * 共通アトリビュートを取得する
	 * 
	 * @return 共通アトリビュート
	 */
	public List<IAttribute> getCommonAttributes() {
		return commonAttributes;
	}

	/**
	 * 共通アトリビュートを設定する
	 * 
	 * @param commonAttributes
	 *            共通アトリビュート
	 */
	public void setCommonAttributes(List<IAttribute> commonAttributes) {
		this.commonAttributes = commonAttributes;
	}

	/**
	 * 
	 * {@inheritDoc}
	 *
	 * @see org.tmdmaker.core.model.ModelElement#accept(org.tmdmaker.core.model.IVisitor)
	 */
	@Override
	public void accept(IVisitor visitor) {
		visitor.visit(this);
	}

	/**
	 * Diagramのモデルを検索するクエリを返す.
	 * 
	 * @return
	 */
	public DiagramQuery query() {
		return new DiagramQuery(this);
	}
	
	public boolean contains(ModelElement m) {
		return this.children.contains(m);
	}
}

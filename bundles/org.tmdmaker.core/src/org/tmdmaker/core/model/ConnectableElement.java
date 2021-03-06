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
 * コネクションと接続可能なモデルの基底クラス
 * 
 * @author nakaG
 * 
 */
@SuppressWarnings("serial")
public abstract class ConnectableElement extends ModelElement {

	/** 接続元プロパティ定数 */
	public static final String P_SOURCE_CONNECTION = "p_source_connection";
	/** 接続先プロパティ定数 */
	public static final String P_TARGET_CONNECTION = "p_target_connection";
	/**
	 * 自分が接続元になっているコネクションリスト
	 */
	private List<AbstractConnectionModel> sourceConnections = new ArrayList<AbstractConnectionModel>();
	/**
	 * 自分が接続先になっているコネクションリスト
	 */
	private List<AbstractConnectionModel> targetConnections = new ArrayList<AbstractConnectionModel>();

	/**
	 * コンストラクタ
	 */
	public ConnectableElement() {
		super();
	}

	public void addSourceConnection(AbstractConnectionModel connx) {
		sourceConnections.add(connx);
		firePropertyChange(P_SOURCE_CONNECTION, null, connx);
	}

	public void addTargetConnection(AbstractConnectionModel connx) {
		targetConnections.add(connx);
		firePropertyChange(P_TARGET_CONNECTION, null, connx);
	}

	public List<AbstractConnectionModel> getModelSourceConnections() {
		return sourceConnections;
	}

	public List<AbstractConnectionModel> getModelTargetConnections() {
		return targetConnections;
	}

	public void removeSourceConnection(Object connx) {
		sourceConnections.remove(connx);
		firePropertyChange(P_SOURCE_CONNECTION, connx, null);
	}

	public void removeTargetConnection(Object connx) {
		targetConnections.remove(connx);
		firePropertyChange(P_TARGET_CONNECTION, connx, null);
	}

	/**
	 * リレーションシップがあるか？
	 * 
	 * @return 他のモデルとのリレーションシップがある場合はtrueを返す.
	 */
	public boolean hasRelationship() {
		return !this.sourceConnections.isEmpty() || !this.targetConnections.isEmpty();
	}
}

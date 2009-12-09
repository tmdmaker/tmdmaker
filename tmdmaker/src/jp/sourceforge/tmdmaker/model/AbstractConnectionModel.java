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
 * モデルを接続するコネクションの基底クラス
 * 
 * @author nakaG
 * 
 */
@SuppressWarnings("serial")
public abstract class AbstractConnectionModel extends ConnectableElement {
	/** 接続先モデル */
	private ConnectableElement source;
	/** 接続先モデル */
	private ConnectableElement target;
	/** 接続元多重度プロパティ定数 */
	public static final String PROPERTY_SOURCE_CARDINALITY = "_property_source_cardinality";
	/** 接続先多重度プロパティ定数 */
	public static final String PROPERTY_TARGET_CARDINALITY = "_property_target_cardinality";
	/** 接続プロパティ定数 */
	public static final String PROPERTY_CONNECTION = "_property_connection";

	/**
	 * コネクション接続
	 */
	public void connect() {
		attachSource();
		attachTarget();
	}

	/**
	 * コネクション切断
	 */
	public void disconnect() {
		detachSource();
		detachTarget();
	}

	/**
	 * 接続元モデルと接続する
	 */
	public void attachSource() {
		if (!source.getModelSourceConnections().contains(this)) {
			source.addSourceConnection(this);
		}
	}

	/**
	 * 接続先モデルと接続する
	 */
	public void attachTarget() {
		if (!target.getModelTargetConnections().contains(this)) {
			target.addTargetConnection(this);
		}
	}

	/**
	 * 接続元モデルと切断する
	 */
	public void detachSource() {
		if (source != null) {
			source.removeSourceConnection(this);
		}
	}

	/**
	 * 接続先モデル切断する
	 */
	public void detachTarget() {
		if (target != null) {
			target.removeTargetConnection(this);
		}
	}

	/**
	 * @return the source
	 */
	public ConnectableElement getSource() {
		return source;
	}

	/**
	 * @param source
	 *            the source to set
	 */
	public void setSource(ConnectableElement source) {
		this.source = source;
	}

	/**
	 * @return the target
	 */
	public ConnectableElement getTarget() {
		return target;
	}

	/**
	 * @param target
	 *            the target to set
	 */
	public void setTarget(ConnectableElement target) {
		this.target = target;
	}

	/**
	 * 削除可能か判定する
	 * 
	 * @return 削除可能な場合にtrueを返す
	 */
	public abstract boolean isDeletable();

}

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

import org.tmdmaker.core.model.constraint.AnchorConstraint;

/**
 * モデルを接続するコネクションの基底クラス
 * 
 * @author nakaG
 * 
 */
@SuppressWarnings("serial")
public abstract class AbstractConnectionModel extends ConnectableElement {

	/** 接続先モデル */
	protected ConnectableElement source;
	/** 接続先モデル */
	protected ConnectableElement target;
	/** Source側接続 */
	protected AnchorConstraint sourceAnchorConstraint = new AnchorConstraint();
	/** Target側接続 */
	protected AnchorConstraint targetAnchorConstraint = new AnchorConstraint();
	@Deprecated
	public int sourceXp = -1;
	@Deprecated
	public int sourceYp = -1;
	@Deprecated
	public int targetXp = -1;
	@Deprecated
	public int targetYp = -1;

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
	 * エンティティ系モデルが移動した際の再描画用
	 */
	public void fireParentMoved() {
		firePropertyChange(PROPERTY_CONNECTION, null, null);
	}

	/**
	 * 削除可能か判定する
	 * 
	 * @return 削除可能な場合にtrueを返す
	 */
	public abstract boolean isDeletable();

	/**
	 * 接続元モデル名を取得する
	 * 
	 * @return 接続元モデル名
	 */
	public abstract String getSourceName();

	/**
	 * 接続先モデル名を取得する
	 * 
	 * @return 接続先モデル名
	 */
	public abstract String getTargetName();

	public void setSourceAnchorConstraint(AnchorConstraint sourceAnchorConstraint) {
		AnchorConstraint oldSourceAnchorConstraint = this.sourceAnchorConstraint;
		this.sourceAnchorConstraint = sourceAnchorConstraint;
		firePropertyChange(PROPERTY_CONNECTION, oldSourceAnchorConstraint,
				this.sourceAnchorConstraint);
	}

	public AnchorConstraint getSourceAnchorConstraint() {
		return sourceAnchorConstraint;
	}

	public void setTargetAnchorConstraint(AnchorConstraint targetAnchorConstraint) {
		AnchorConstraint oldTargetAnchorConstraint = this.targetAnchorConstraint;
		this.targetAnchorConstraint = targetAnchorConstraint;
		firePropertyChange(PROPERTY_CONNECTION, oldTargetAnchorConstraint,
				this.targetAnchorConstraint);
	}

	public AnchorConstraint getTargetAnchorConstraint() {
		return targetAnchorConstraint;
	}

	/**
	 * 接続済みか？
	 * 
	 * @return 接続済みの場合にtrueを返す。
	 */
	public boolean isConnected() {
		return source != null && source.getModelSourceConnections().contains(this) && target != null
				&& target.getModelTargetConnections().contains(this);
	}
}

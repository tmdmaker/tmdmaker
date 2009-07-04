package jp.sourceforge.tmdmaker.model;

import java.util.ArrayList;
import java.util.List;

/**
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
}

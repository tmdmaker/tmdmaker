package jp.sourceforge.tmdmaker.model;

/**
 * 
 * @author nakaG
 * 
 * @param <T>
 */
@SuppressWarnings("serial")
public abstract class AbstractConnectionModel<T extends ConnectableElement>
		extends ConnectableElement {
	/** 接続先モデル */
	protected T source;
	/** 接続先モデル */
	protected T target;
	/**  */
	public static final String PROPERTY_SOURCE_CARDINALITY = "_property_source_cardinality";
	public static final String PROPERTY_TARGET_CARDINALITY = "_property_target_cardinality";
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

	public void attachSource() {
		if (!source.getModelSourceConnections().contains(this)) {
			System.out.println("attachSource():source="
					+ source.getClass().toString());
			source.addSourceConnection(this);
		}
	}

	public void attachTarget() {
		if (!target.getModelTargetConnections().contains(this)) {
			System.out.println("attachTarget():target="
					+ target.getClass().toString());
			target.addTargetConnection(this);
		}
	}

	public void detachSource() {
		if (source != null) {
			System.out.println("detachSource()");
			source.removeSourceConnection(this);
		}
	}

	public void detachTarget() {
		if (target != null) {
			System.out.println("detachTarget()");
			target.removeTargetConnection(this);
		}
	}

	public T getSource() {
		return source;
	}

	public void setSource(T source) {
		this.source = source;
	}

	public T getTarget() {
		return target;
	}

	public void setTarget(T target) {
		this.target = target;
	}

}

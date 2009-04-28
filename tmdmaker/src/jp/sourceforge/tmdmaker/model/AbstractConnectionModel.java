package jp.sourceforge.tmdmaker.model;
/**
 * 
 * @author nakaG
 *
 * @param <T>
 */
public abstract class AbstractConnectionModel<T extends ConnectableElement> extends ConnectableElement {
	/**
	 * 
	 */
	protected T source, target;
	public static final String P_SOURCE_CARDINALITY = "p_source_cardinality";
	public static final String P_TARGET_CARDINALITY = "p_target_cardinality";

	public void connect() {
		attachSource();
		attachTarget();
	}
	public void disConnect() {
		detachSource();
		detachTarget();
	}
	public void attachSource() {
		if (!source.getModelSourceConnections().contains(this)){
			System.out.println("attachSource():source=" + source.getClass().toString());
			source.addSourceConnection(this);
		}
	}
	
	public void attachTarget() {
		if (!target.getModelTargetConnections().contains(this)){
			System.out.println("attachTarget():target=" + target.getClass().toString());
			target.addTargetConnection(this);
		}
	}
	
	public void detachSource() {
		if(source!=null){
			System.out.println("detachSource()");
			source.removeSourceConnection(this);
		}
	}
	
	public void detachTarget() {
		if(target!=null){
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

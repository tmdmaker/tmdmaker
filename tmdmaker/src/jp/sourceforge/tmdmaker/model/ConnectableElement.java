package jp.sourceforge.tmdmaker.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author nakaG
 *
 */
public abstract class ConnectableElement extends ModelElement {

	public static final String P_SOURCE_CONNECTION = "p_source_connection";
	public static final String P_TARGET_CONNECTION = "p_target_connection";
	private List<AbstractConnectionModel> sourceConnections = new ArrayList<AbstractConnectionModel>();
	private List<AbstractConnectionModel> targetConnections = new ArrayList<AbstractConnectionModel>();

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

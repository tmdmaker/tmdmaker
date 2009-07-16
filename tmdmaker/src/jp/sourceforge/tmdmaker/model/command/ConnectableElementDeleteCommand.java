package jp.sourceforge.tmdmaker.model.command;

import java.util.ArrayList;
import java.util.List;

import jp.sourceforge.tmdmaker.model.AbstractConnectionModel;
import jp.sourceforge.tmdmaker.model.Diagram;

import org.eclipse.gef.commands.Command;

public abstract class ConnectableElementDeleteCommand extends Command {

	protected Diagram diagram;
	protected List<AbstractConnectionModel> sourceConnections = new ArrayList<AbstractConnectionModel>();
	protected List<AbstractConnectionModel> targetConnections = new ArrayList<AbstractConnectionModel>();

	public ConnectableElementDeleteCommand() {
		super();
	}

	public ConnectableElementDeleteCommand(String label) {
		super(label);
	}

	public void setDiagram(Object diagram) {
		this.diagram = (Diagram) diagram;
	}

	protected void detachConnections(List<AbstractConnectionModel> connections) {
		for (AbstractConnectionModel model : connections) {
			model.disconnect();
//			model.detachSource();
//			model.detachTarget();
		}
	}

	protected void attathConnections(List<AbstractConnectionModel> connections) {
		for (AbstractConnectionModel model : connections) {
			model.connect();
//			model.attachSource();
//			model.attachTarget();
		}
	}

}
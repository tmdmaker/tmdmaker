package jp.sourceforge.tmdmaker.model;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class VirtualSuperset extends AbstractEntityModel {

	@Override
	public ReusedIdentifier createReusedIdentifier() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isDeletable() {
		return true;
	}

	@Override
	public boolean isEntityTypeEditable() {
		// TODO Auto-generated method stub
		return false;
	}

	public List<AbstractEntityModel> getVirtualSubsetList() {
		List<AbstractEntityModel> results = new ArrayList<AbstractEntityModel>();
		for (AbstractConnectionModel con : getVirtualSupersetAggregator().getModelSourceConnections()) {
			results.add((AbstractEntityModel) con.getTarget());
		}
		return results;
	}
	public List<AbstractConnectionModel> getVirtualSubsetRelationshipList() {
		List<AbstractConnectionModel> results = new ArrayList<AbstractConnectionModel>();
		for (AbstractConnectionModel con : getVirtualSupersetAggregator().getModelSourceConnections()) {
			results.add(con);
		}
		return results;
	}
	public VirtualSupersetAggregator getVirtualSupersetAggregator() {
		return (VirtualSupersetAggregator) getModelSourceConnections().get(0).getTarget();
	}
}

package jp.sourceforge.tmdmaker.model;

import org.eclipse.draw2d.geometry.Rectangle;

/**
 * 
 * @author nakaG
 *
 */
public class Event2EventRelationship extends Relationship {
	/**
	 * 対応表
	 */
	private MappingList table;
	/** 接続しているか */
	private boolean connected = false;
	
	/**
	 * 対応表とのコネクション
	 */
	private RelatedRelationship mappingListConnection;

	public Event2EventRelationship(AbstractEntityModel source,
			AbstractEntityModel target) {
		this.source = source;
		this.target = target;
		this.setCenterMard(true);
	}

	/* (non-Javadoc)
	 * @see tm.tmdiagram.tmdeditor.model.Relationship#setSourceCardinality(java.lang.String)
	 */
	@Override
	public void setSourceCardinality(String sourceCardinality) {
		String oldValue = getSourceCardinality();
		super.setSourceCardinality(sourceCardinality);
		if (connected && !oldValue.equals(sourceCardinality)) {
			createRelationship();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see tm.tmdiagram.tmdeditor.model.AbstractConnectionModel#connect()
	 */
	@Override
	public void connect() {
		super.connect();
		connected = true;
		createRelationship();
	}

	private void createRelationship() {
		if (getSourceCardinality().equals("N")) {
			removeTargetRelationship();
			createMappingList();
		} else {
			removeMappingList();
			createTargetRelationship();
		}
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see tm.tmdiagram.tmdeditor.model.AbstractConnectionModel#disConnect()
	 */
	@Override
	public void disConnect() {
		if (getSourceCardinality().equals("N")) {
			removeMappingList();
		} else {
			removeTargetRelationship();
		}
		super.disConnect();
		connected = false;
	}

	private void createTargetRelationship() {
		target.addReuseKey(source);
	}
	private void removeTargetRelationship() {
		target.removeReuseKey(source);
	}
	private void createMappingList() {
		if (table == null) {
			table = new MappingList();
		}
		AbstractEntityModel sourceEntity = this.source;
		Rectangle constraint = sourceEntity.getConstraint().getTranslated(100,
				100);
		table.setConstraint(constraint);
		Diagram diagram = sourceEntity.getDiagram();
		diagram.addChild(table);
		table.setDiagram(diagram);
		table.setName(source.getName() + "." + target.getName() + "." + "対応表");
		table.addReuseKey(source);
		table.addReuseKey(target);

		mappingListConnection = new RelatedRelationship();
		mappingListConnection.setSource(this);
		mappingListConnection.setTarget(table);
		mappingListConnection.connect();
	}

	private void removeMappingList() {
		if (mappingListConnection != null) {
			mappingListConnection.disConnect();
		}
		if (table != null) {
			table.removeReuseKey(source);
			table.removeReuseKey(target);
			AbstractEntityModel sourceEntity = this.source;
			sourceEntity.getDiagram().removeChild(table);
		}
	}
}

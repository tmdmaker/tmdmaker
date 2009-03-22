package jp.sourceforge.tmdmaker.model;

import jp.sourceforge.tmdmaker.model.AbstractEntityModel.EntityType;

import org.eclipse.draw2d.geometry.Rectangle;

/**
 * 
 * @author nakaG
 *
 */
public class Resource2ResourceRelationship extends Relationship {

	/**
	 * 対照表
	 */
	private CombinationTable table;

	/**
	 * 対照表とのコネクション
	 */
	private RelatedRelationship combinationTableConnection;

	public Resource2ResourceRelationship(AbstractEntityModel source,
			AbstractEntityModel target) {
		this.source = source;
		this.target = target;
		this.table = new CombinationTable();
		Rectangle constraint = source.getConstraint().getTranslated(100, 100);
		this.table.setConstraint(constraint);
		this.table.setEntityType(EntityType.R);
		this.table.setConstraint(constraint);
		this.table.setName(source.getName().replace(
				CombinationTable.COMBINATION_TABLE_SUFFIX, "")
				+ "."
				+ target.getName().replace(
						CombinationTable.COMBINATION_TABLE_SUFFIX, "")
				+ CombinationTable.COMBINATION_TABLE_SUFFIX);
		this.combinationTableConnection = new RelatedRelationship();
		this.combinationTableConnection.setSource(this);
		this.combinationTableConnection.setTarget(this.table);
		this.setCenterMard(true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see tm.tmdiagram.tmdeditor.model.AbstractConnectionModel#connect()
	 */
	@Override
	public void connect() {
		super.connect();
		((AbstractEntityModel) getSource()).getDiagram().addChild(this.table);
		this.combinationTableConnection.connect();
		this.table.addReuseKey((AbstractEntityModel) getSource());
		this.table.addReuseKey((AbstractEntityModel) getTarget());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see tm.tmdiagram.tmdeditor.model.AbstractConnectionModel#disConnect()
	 */
	@Override
	public void disConnect() {
		this.table.removeReuseKey((AbstractEntityModel) getSource());
		this.table.removeReuseKey((AbstractEntityModel) getTarget());
		this.combinationTableConnection.disConnect();
		((AbstractEntityModel) getSource()).getDiagram()
				.removeChild(this.table);
		super.disConnect();
	}
}

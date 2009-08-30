package jp.sourceforge.tmdmaker.model;

import org.eclipse.draw2d.geometry.Rectangle;

/**
 * 
 * @author nakaG
 *
 */
@SuppressWarnings("serial")
public class Resource2ResourceRelationship extends AbstractRelationship {

	/**
	 * 対照表
	 */
	private CombinationTable table;

	/**
	 * 対照表とのコネクション
	 */
	private RelatedRelationship combinationTableConnection;

	/**
	 * コンストラクタ
	 * @param source ソース
	 * @param target ターゲット
	 */
	public Resource2ResourceRelationship(AbstractEntityModel source,
			AbstractEntityModel target) {
		setSource(source);
		setTarget(target);
		this.table = new CombinationTable();
		Rectangle constraint = source.getConstraint().getTranslated(100, 100);
		this.table.setConstraint(constraint);
		this.table.setEntityType(EntityType.RESOURCE);
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
		this.setCenterMark(true);
	}

	/**
	 * 
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.model.AbstractConnectionModel#connect()
	 */
	@Override
	public void connect() {
		super.connect();
		((AbstractEntityModel) getSource()).getDiagram().addChild(this.table);
		this.combinationTableConnection.connect();
		this.table.addReuseKey((AbstractEntityModel) getSource());
		this.table.addReuseKey((AbstractEntityModel) getTarget());
	}

	/**
	 * 
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.model.AbstractConnectionModel#disconnect()
	 */
	@Override
	public void disconnect() {
		this.table.removeReuseKey((AbstractEntityModel) getSource());
		this.table.removeReuseKey((AbstractEntityModel) getTarget());
		this.combinationTableConnection.disconnect();
		((AbstractEntityModel) getSource()).getDiagram()
				.removeChild(this.table);
		super.disconnect();
	}

	/**
	 * 
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.model.AbstractRelationship#canDeletable()
	 */
	@Override
	public boolean canDeletable() {
		return table.isDeletable();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.model.AbstractRelationship#notifyReUseKeyChanged()
	 */
	@Override
	public void notifyReUseKeyChanged() {
//		table.firePropertyChange(AbstractEntityModel.PROPERTY_REUSEKEY, null, null);
		table.notifyReUseKeyChange(this);
	}
}

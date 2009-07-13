package jp.sourceforge.tmdmaker.model;

import jp.sourceforge.tmdmaker.editpart.CenterAnchor;

import org.eclipse.draw2d.geometry.Rectangle;

/**
 * 
 * @author nakaG
 * 
 */
@SuppressWarnings("serial")
public class Event2EventRelationship extends AbstractRelationship {
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

	/**
	 * コンストラクタ
	 * 
	 * @param source
	 *            ソース
	 * @param target
	 *            ターゲット
	 */
	public Event2EventRelationship(AbstractEntityModel source,
			AbstractEntityModel target) {
		setSource(source);
		setTarget(target);
		this.setCenterMark(true);
	}

	/**
	 * 
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.model.AbstractRelationship#setSourceCardinality(java.lang.String)
	 */
	@Override
	public void setSourceCardinality(Cardinality sourceCardinality) {
		Cardinality oldValue = getSourceCardinality();
		super.setSourceCardinality(sourceCardinality);
		if (sourceCardinality.equals(Cardinality.MANY)) {
			setCenterMark(true);
		} else {
			setCenterMark(false);
		}
		if (connected && !oldValue.equals(sourceCardinality)) {
			createRelationship();
		}
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
		connected = true;
		createRelationship();
	}

	/**
	 * リレーションシップを作成する。
	 * <ul>
	 * <li>ソースのカーディナリティがNの場合は対応表を作成する</li>
	 * <li>ソースのカーディナリティがN以外の場合はターゲットにキーを移送する</li>
	 * </ul>
	 */
	private void createRelationship() {
		if (getSourceCardinality().equals(Cardinality.MANY)) {
			removeTargetRelationship();
			createMappingList();
		} else {
			removeMappingList();
			createTargetRelationship();
		}
	}

	/**
	 * 
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.model.AbstractConnectionModel#disconnect()
	 */
	@Override
	public void disconnect() {
		if (getSourceCardinality().equals(Cardinality.MANY)) {
			removeMappingList();
		} else {
			removeTargetRelationship();
		}
		super.disconnect();
		connected = false;
	}
	/**
	 * ターゲットにキーを移送する
	 */
	private void createTargetRelationship() {
		setCenterMark(false);
		getTarget().addReuseKey(getSource());
	}
	/**
	 * ターゲットからキーを削除する
	 */
	private void removeTargetRelationship() {
		getTarget().removeReuseKey(getSource());
	}
	/**
	 * 対応表を作成する
	 */
	private void createMappingList() {
		if (table == null) {
			table = new MappingList();
		}
		setCenterMark(true);
		
		AbstractEntityModel sourceEntity = getSource();
		AbstractEntityModel targetEntity = getTarget();
		Rectangle constraint = sourceEntity.getConstraint().getTranslated(100,
				100);
		table.setConstraint(constraint);
		Diagram diagram = sourceEntity.getDiagram();
		diagram.addChild(table);
		// table.setDiagram(diagram);
		table.setName(sourceEntity.getName() + "." + targetEntity.getName() + "." + "対応表");
		table.addReuseKey(sourceEntity);
		table.addReuseKey(targetEntity);

		mappingListConnection = new RelatedRelationship();
		mappingListConnection.setSource(this);
		mappingListConnection.setTarget(table);
		mappingListConnection.connect();
	}
	/**
	 * 対応表を削除する。undo()を考慮して実際はコネクションを切ってキーを削除するだけで表は残している
	 */
	private void removeMappingList() {
		if (mappingListConnection != null) {
			setCenterMark(false);
			mappingListConnection.disconnect();
		}
		if (table != null) {
			AbstractEntityModel sourceEntity = getSource();
			table.removeReuseKey(sourceEntity);
			table.removeReuseKey(getTarget());
			sourceEntity.getDiagram().removeChild(table);
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.model.AbstractRelationship#canDeletable()
	 */
	@Override
	public boolean canDeletable() {
		return true;
	}

	/**
	 * 
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.model.ReUseKeysChangeListener#awareReUseKeysChanged()
	 */
	@Override
	public void awareReUseKeysChanged() {
		if (getSourceCardinality().equals(Cardinality.MANY)) {
			table.firePropertyChange(AbstractEntityModel.PROPERTY_REUSEKEY, null, null);
		} else {
			getTarget().firePropertyChange(AbstractEntityModel.PROPERTY_REUSEKEY, null, null);
		}
	}
}

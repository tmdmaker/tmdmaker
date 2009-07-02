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
		this.source = source;
		this.target = target;
		this.setCenterMark(true);
	}

	/**
	 * 
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.model.AbstractRelationship#setSourceCardinality(java.lang.String)
	 */
	@Override
	public void setSourceCardinality(String sourceCardinality) {
		String oldValue = getSourceCardinality();
		super.setSourceCardinality(sourceCardinality);
		if (sourceCardinality.equals("N")) {
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
		if (getSourceCardinality().equals("N")) {
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
		if (getSourceCardinality().equals("N")) {
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
		target.addReuseKey(source);
	}
	/**
	 * ターゲットからキーを削除する
	 */
	private void removeTargetRelationship() {
		target.removeReuseKey(source);
	}
	/**
	 * 対応表を作成する
	 */
	private void createMappingList() {
		if (table == null) {
			table = new MappingList();
		}
		setCenterMark(true);
		
		AbstractEntityModel sourceEntity = this.source;
		Rectangle constraint = sourceEntity.getConstraint().getTranslated(100,
				100);
		table.setConstraint(constraint);
		Diagram diagram = sourceEntity.getDiagram();
		diagram.addChild(table);
		// table.setDiagram(diagram);
		table.setName(source.getName() + "." + target.getName() + "." + "対応表");
		table.addReuseKey(source);
		table.addReuseKey(target);

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
			table.removeReuseKey(source);
			table.removeReuseKey(target);
			AbstractEntityModel sourceEntity = this.source;
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
}

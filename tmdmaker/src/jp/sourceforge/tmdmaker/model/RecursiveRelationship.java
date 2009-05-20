package jp.sourceforge.tmdmaker.model;

import org.eclipse.draw2d.geometry.Rectangle;

/**
 * 
 * @author nakaG
 *
 */
public class RecursiveRelationship extends Relationship {
	/** 再帰表 */
	private RecursiveTable table;
	/** 親 */
	private Diagram diagram;

	/**
	 * コンストラクタ
	 * @param source 再帰元エンティティ
	 */
	public RecursiveRelationship(AbstractEntityModel source) {
		this.source = source;
		// this.connection = new RecursiveMarkConnection();
		this.table = new RecursiveTable();
		Rectangle constraint = source.getConstraint().getTranslated(100, 0);
		this.table.setConstraint(constraint);
		this.target = table;
		this.diagram = this.source.getDiagram();
		// this.connection.setSource(this);
		// this.connection.setTarget(source);
		// 再帰表の類別は元エンティティを引き継ぐ
		table.setEntityType(source.getEntityType());
		table.setConstraint(source.getConstraint().getTranslated(100, 0));
		table.setName(source.getName() + "." + source.getName() + "." + "再帰表");

	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see
	 * tm.tmdiagram.tmdeditor.model.AbstractConnectionModel#setTarget(tm.tmdiagram
	 * .tmdeditor.model.ConnectableElement)
	 */
	@Override
	public void setTarget(AbstractEntityModel target) {
		// source == targetのため設定しない
		// super.setTarget(target);
	}

	/**
	 * {@inheritDoc}
	 *  
	 * @see tm.tmdiagram.tmdeditor.model.AbstractConnectionModel#connect()
	 */
	@Override
	public void connect() {
		diagram.addChild(table);
		table.setDiagram(diagram);
		if (!source.getModelSourceConnections().contains(this)) {
			System.out.println("source=" + source.getClass().toString());
			source.addSourceConnection(this);
		}
		table.addTargetConnection(this);
		attachSource();
		attachTarget();
		table.addReuseKey((AbstractEntityModel) source);

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.AbstractConnectionModel#disConnect()
	 */
	@Override
	public void disConnect() {
		source.removeSourceConnection(this);
		table.removeTargetConnection(this);
		table.setDiagram(null);
		diagram.removeChild(table);
	}
}

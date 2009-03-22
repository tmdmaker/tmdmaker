package jp.sourceforge.tmdmaker.model;

import org.eclipse.draw2d.geometry.Rectangle;

public class RecursiveRelationship extends Relationship {
//	private RecursiveMarkConnection connection;
	private RecursiveTable table;

	public RecursiveRelationship(AbstractEntityModel source) {
		this.source = source;
//		this.connection = new RecursiveMarkConnection();
		this.table = new RecursiveTable();
		Rectangle constraint = source.getConstraint().getTranslated(100, 0);
		this.table.setConstraint(constraint);
		this.target = table;
//		this.connection.setSource(this);
//		this.connection.setTarget(source);
		// 再帰表の類別は元エンティティを引き継ぐ
		table.setEntityType(source.getEntityType());
		table.setConstraint(source.getConstraint().getTranslated(100, 0));
		table.setName(source.getName() + "." + source.getName() + "." + "再帰表");

	}
	
	/* (non-Javadoc)
	 * @see tm.tmdiagram.tmdeditor.model.AbstractConnectionModel#setTarget(tm.tmdiagram.tmdeditor.model.ConnectableElement)
	 */
	@Override
	public void setTarget(AbstractEntityModel target) {
		// source == targetのため設定しない
//		super.setTarget(target);
	}

	/* (non-Javadoc)
	 * @see tm.tmdiagram.tmdeditor.model.AbstractConnectionModel#connect()
	 */
	@Override
	public void connect() {
		((AbstractEntityModel)this.source).getDiagram().addChild(table);
		if (!source.getModelSourceConnections().contains(this)){
			System.out.println("source=" + source.getClass().toString());
			source.addSourceConnection(this);
		}
		table.addTargetConnection(this);
		attachSource();
		attachTarget();
		table.addReuseKey((AbstractEntityModel)source);
		
	}

	/* (non-Javadoc)
	 * @see tm.tmdiagram.tmdeditor.model.AbstractConnectionModel#detachSource()
	 */
	@Override
	public void detachSource() {
		source.removeSourceConnection(this);
		table.removeTargetConnection(this);
		((AbstractEntityModel)this.source).getDiagram().removeChild(table);
	}
}

package jp.sourceforge.tmdmaker.model;

import org.eclipse.draw2d.geometry.Rectangle;

public class MultivalueOrRelationship extends Relationship {
	private MultivalueOrEntity table;
//	/** 接続しているか */
//	private boolean connected = false;

	public MultivalueOrRelationship(AbstractEntityModel source, MultivalueOrEntity target) {
		this.source = source;
		this.table = target;
		this.target = target;
	}

	@Override
	public void attachTarget() {
		super.attachTarget();
		((AbstractEntityModel)getTarget()).addReuseKey((AbstractEntityModel)getSource());
	}
	@Override
	public void detachTarget() {
		((AbstractEntityModel)getTarget()).removeReuseKey((AbstractEntityModel)getSource());
		super.detachTarget();
	}

	
	/**
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.model.AbstractConnectionModel#connect()
	 */
	@Override
	public void connect() {
		Diagram diagram = source.getDiagram();
		diagram.addChild(target);
		target.setDiagram(diagram);
		super.connect();
	}
//
//	private void createMultivalueOrEntity() {
//		if (table == null) {
//			table = new MultivalueOrEntity();
//		}
//		AbstractEntityModel sourceEntity = this.source;
//		Rectangle constraint = sourceEntity.getConstraint().getTranslated(100,
//				100);
//		table.setConstraint(constraint);
//		Diagram diagram = sourceEntity.getDiagram();
//		diagram.addChild(table);
//		table.setDiagram(diagram);
//		table.setName(source.getName() + "." + "種別");
//	}
	/**
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.model.AbstractConnectionModel#disConnect()
	 */
	@Override
	public void disConnect() {
		super.disConnect();
		source.getDiagram().removeChild(table);
		target.setDiagram(null);
	}
	
}

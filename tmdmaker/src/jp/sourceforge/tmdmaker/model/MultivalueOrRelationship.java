package jp.sourceforge.tmdmaker.model;

/**
 * 
 * @author nakaG
 *
 */
@SuppressWarnings("serial")
public class MultivalueOrRelationship extends TransfarReuseKeysToTargetRelationship {
	/** MO */
	private MultivalueOrEntity table;
//	/** 接続しているか */
//	private boolean connected = false;

	/**
	 * コンストラクタ
	 * @param source MOの元エンティティ
	 * @param target MO
	 */
	public MultivalueOrRelationship(AbstractEntityModel source, MultivalueOrEntity target) {
		setSource(source);
		setTarget(target);
		this.table = target;
	}

//	@Override
//	public void attachTarget() {
//		super.attachTarget();
//		((AbstractEntityModel)getTarget()).addReuseKey((AbstractEntityModel)getSource());
//	}
//	@Override
//	public void detachTarget() {
//		((AbstractEntityModel)getTarget()).removeReuseKey((AbstractEntityModel)getSource());
//		super.detachTarget();
//	}

	
	/**
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.model.AbstractConnectionModel#connect()
	 */
	@Override
	public void connect() {
		Diagram diagram = getSource().getDiagram();
		diagram.addChild(getTarget());
//		target.setDiagram(diagram);
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
	 * @see jp.sourceforge.tmdmaker.model.AbstractConnectionModel#disconnect()
	 */
	@Override
	public void disconnect() {
		super.disconnect();
		getSource().getDiagram().removeChild(table);
//		target.setDiagram(null);
	}

	
}

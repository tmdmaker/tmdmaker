package jp.sourceforge.tmdmaker.model;
/**
 * 
 * @author nakaG
 *
 */
@SuppressWarnings("serial")
public class Entity2VirtualEntityRelationship extends
		TransfarReuseKeysToTargetRelationship {
	/** みなしエンティティ */
	private VirtualEntity ve;

	/**
	 * コンストラクタ
	 * @param source みなしエンティティ作成対象
	 */
	public Entity2VirtualEntityRelationship(AbstractEntityModel source) {
		setSource(source);
		ve = new VirtualEntity();
		ve.setName(source.getName() + ".VE" + source.getModelSourceConnections().size());
		ve.setConstraint(source.getConstraint().getTranslated(100, 0));

		setTarget(ve);
		
	}
	/**
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.model.TransfarReuseKeysToTargetRelationship#attachTarget()
	 */
	@Override
	public void attachTarget() {
		getSource().getDiagram().addChild(ve);
		super.attachTarget();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.model.TransfarReuseKeysToTargetRelationship#detachTarget()
	 */
	@Override
	public void detachTarget() {
		super.detachTarget();
		ve.getDiagram().removeChild(ve);
	}

	
}

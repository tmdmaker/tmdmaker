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
	public Entity2VirtualEntityRelationship(AbstractEntityModel source, String virtualEntityName) {
		setSource(source);
		ve = new VirtualEntity();
		ve.setName(virtualEntityName);
		ve.setConstraint(source.getConstraint().getTranslated(100, 0));
		ve.setOriginalReusedIdentifier(source.createReusedIdentifier());
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
	/**
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.model.TransfarReuseKeysToTargetRelationship#isDeletable()
	 */
	@Override
	public boolean isDeletable() {
		return getTarget().isDeletable();
	}

	
}

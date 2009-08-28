package jp.sourceforge.tmdmaker.model;
/**
 * 
 * @author nakaG
 *
 */
@SuppressWarnings("serial")
public class Entity2VirtualEntityRelationship extends
		TransfarReuseKeysToTargetRelationship {

	/**
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.model.TransfarReuseKeysToTargetRelationship#detachTarget()
	 */
	@Override
	public void detachTarget() {
		VirtualEntity ve = (VirtualEntity) getTarget();
		super.detachTarget();
		ve.getDiagram().removeChild(ve);
	}

	
}

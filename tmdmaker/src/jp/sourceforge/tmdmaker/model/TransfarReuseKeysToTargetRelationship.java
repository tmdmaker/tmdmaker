package jp.sourceforge.tmdmaker.model;

/**
 * ソースのReuseKeyをターゲットへ移送するリレーションシップ
 * 
 * @author nakaG
 * 
 */
@SuppressWarnings("serial")
public class TransfarReuseKeysToTargetRelationship extends AbstractRelationship {
	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.AbstractConnectionModel#attachTarget()
	 */
	@Override
	public void attachTarget() {
		super.attachTarget();
		((AbstractEntityModel) getTarget())
				.addReusedIdentifier((AbstractEntityModel) getSource());
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.AbstractConnectionModel#detachTarget()
	 */
	@Override
	public void detachTarget() {
		((AbstractEntityModel) getTarget())
				.removeReusedIdentifier((AbstractEntityModel) getSource());
		super.detachTarget();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.AbstractRelationship#canDeletable()
	 */
	@Override
	public boolean canDeletable() {
//		return getTarget().isDeletable();
		return true;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.model.AbstractRelationship#identifierChanged()
	 */
	@Override
	public void identifierChanged() {
//		getTarget().firePropertyChange(AbstractEntityModel.PROPERTY_REUSED, null, null);
		getTarget().fireIdentifierChanged(this);
	}
}

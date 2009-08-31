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
				.addReuseKey((AbstractEntityModel) getSource());
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
				.removeReuseKey((AbstractEntityModel) getSource());
		super.detachTarget();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.AbstractRelationship#canDeletable()
	 */
	@Override
	public boolean canDeletable() {
		return getTarget().isDeletable();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.model.AbstractRelationship#identifierChanged()
	 */
	@Override
	public void identifierChanged() {
//		getTarget().firePropertyChange(AbstractEntityModel.PROPERTY_REUSEKEY, null, null);
		getTarget().fireIdentifierChanged(this);
	}
}

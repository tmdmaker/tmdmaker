package jp.sourceforge.tmdmaker.model;

/**
 * 
 * @author nakaG
 *
 */
@SuppressWarnings("serial")
public class Superset2AggregateRelationship extends AbstractConnectionModel implements ReUseKeysChangeListener {

	/**
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.model.ReUseKeysChangeListener#awareReUseKeysChanged()
	 */
	@Override
	public void awareReUseKeysChanged() {
		for (AbstractConnectionModel con : getTarget().getModelTargetConnections()) {
			if (con instanceof ReUseKeysChangeListener) {
				((ReUseKeysChangeListener) con).awareReUseKeysChanged();
			}
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.model.AbstractConnectionModel#canDeletable()
	 */
	@Override
	public boolean canDeletable() {
		// TODO Auto-generated method stub
		return true;
	}

}

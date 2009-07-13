package jp.sourceforge.tmdmaker.model;

/**
 * 
 * @author nakaG
 *
 */
@SuppressWarnings("serial")
public class Superset2AggregateRelationship extends AbstractConnectionModel<ConnectableElement> implements ReUseKeysChangeListener {

	/**
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.model.ReUseKeysChangeListener#awareReUseKeysChanged()
	 */
	@Override
	public void awareReUseKeysChanged() {
		for (AbstractConnectionModel<?> con : getTarget().getModelTargetConnections()) {
			if (con instanceof ReUseKeysChangeListener) {
				((ReUseKeysChangeListener) con).awareReUseKeysChanged();
			}
		}
	}

}

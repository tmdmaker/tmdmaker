package jp.sourceforge.tmdmaker.model;

/**
 * 
 * @author nakaG
 *
 */
@SuppressWarnings("serial")
public class Superset2AggregateRelationship extends AbstractConnectionModel implements ReUseKeyChangeListener {

	/**
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.model.ReUseKeyChangeListener#notifyReUseKeyChanged()
	 */
	@Override
	public void notifyReUseKeyChanged() {
		for (AbstractConnectionModel con : getTarget().getModelTargetConnections()) {
			if (con instanceof ReUseKeyChangeListener) {
				((ReUseKeyChangeListener) con).notifyReUseKeyChanged();
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

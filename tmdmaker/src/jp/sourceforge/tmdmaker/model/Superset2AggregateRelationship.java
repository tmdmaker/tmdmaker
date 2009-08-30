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
	 * @see jp.sourceforge.tmdmaker.model.ReUseKeyChangeListener#reUseKeyChanged()
	 */
	@Override
	public void reUseKeyChanged() {
		for (AbstractConnectionModel con : getTarget().getModelTargetConnections()) {
			if (con instanceof ReUseKeyChangeListener) {
				((ReUseKeyChangeListener) con).reUseKeyChanged();
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

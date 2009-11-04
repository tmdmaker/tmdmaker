package jp.sourceforge.tmdmaker.model;

/**
 * 
 * @author nakaG
 *
 */
@SuppressWarnings("serial")
public class Superset2AggregateRelationship extends AbstractConnectionModel implements IdentifierChangeListener {

	/**
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.model.IdentifierChangeListener#identifierChanged()
	 */
	@Override
	public void identifierChanged() {
		for (AbstractConnectionModel con : getTarget().getModelTargetConnections()) {
			if (con instanceof IdentifierChangeListener) {
				((IdentifierChangeListener) con).identifierChanged();
			}
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.model.AbstractConnectionModel#isDeletable()
	 */
	@Override
	public boolean isDeletable() {
		// TODO Auto-generated method stub
		return true;
	}

}

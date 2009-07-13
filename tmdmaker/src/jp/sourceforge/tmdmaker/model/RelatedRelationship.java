package jp.sourceforge.tmdmaker.model;

/**
 * 
 * @author nakaG
 *
 */
@SuppressWarnings("serial")
public class RelatedRelationship extends AbstractConnectionModel<ConnectableElement> implements ReUseKeysChangeListener {

	/**
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.model.ReUseKeysChangeListener#awareReUseKeysChanged()
	 */
	@Override
	public void awareReUseKeysChanged() {
		getTarget().firePropertyChange(AbstractEntityModel.PROPERTY_REUSEKEY, null, null);
	}

}

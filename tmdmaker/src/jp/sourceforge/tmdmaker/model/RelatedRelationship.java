package jp.sourceforge.tmdmaker.model;

/**
 * 
 * @author nakaG
 *
 */
@SuppressWarnings("serial")
public class RelatedRelationship extends AbstractConnectionModel<ConnectableElement> {

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
//	public class RelatedRelationship extends AbstractConnectionModel<ConnectableElement> implements ReUseKeysChangeListener {

	/**
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.model.ReUseKeysChangeListener#awareReUseKeysChanged()
	 */
//	@Override
//	public void awareReUseKeysChanged() {
//		getTarget().firePropertyChange(AbstractEntityModel.PROPERTY_REUSEKEY, null, null);
//	}

}

package jp.sourceforge.tmdmaker.model;
/**
 * 
 * @author nakaG
 *
 */
@SuppressWarnings("serial")
public class Entity2SubsetTypeRelationship extends AbstractConnectionModel<ConnectableElement> implements ReUseKeysChangeListener {
	/** 区分コードプロパティ定数 */
	public static final String PROPERTY_PARTITION = "_property_partition";
	/** 区分コードの属性 */
	private Attribute partitionAttribute;

	/**
	 * @return the partitionAttributeName
	 */
	public Attribute getPartitionAttribute() {
		return partitionAttribute;
	}

	/**
	 * @param partitionAttribute the partitionAttributeName to set
	 */
	public void setPartitionAttribute(Attribute partitionAttribute) {
		Attribute oldValue = this.partitionAttribute;
		this.partitionAttribute = partitionAttribute;
		firePropertyChange(PROPERTY_PARTITION, oldValue, partitionAttribute);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.model.ReUseKeysChangeListener#awareReUseKeysChanged()
	 */
	@Override
	public void awareReUseKeysChanged() {
		for (AbstractConnectionModel<?> con : getTarget().getModelSourceConnections()) {
			if (con instanceof ReUseKeysChangeListener) {
				((ReUseKeysChangeListener) con).awareReUseKeysChanged();
			}
		}
	}	
}

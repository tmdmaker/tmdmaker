package jp.sourceforge.tmdmaker.model;
/**
 * 
 * @author nakaG
 *
 */
@SuppressWarnings("serial")
public class Entity2SubsetTypeRelationship extends AbstractConnectionModel<ConnectableElement> {
	public static final String PROPERTY_PARTITION = "_property_partition";
	private Attribute partitionAttribute;

	/**
	 * @return the partitionAttributeName
	 */
	public Attribute getPartitionAttribute() {
		return partitionAttribute;
	}

	/**
	 * @param partitionAttributeName the partitionAttributeName to set
	 */
	public void setPartitionAttribute(Attribute partitionAttribute) {
		Attribute oldValue = this.partitionAttribute;
		this.partitionAttribute = partitionAttribute;
		firePropertyChange(PROPERTY_PARTITION, oldValue, partitionAttribute);
	}
	
}

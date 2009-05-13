package jp.sourceforge.tmdmaker.model;

public class Entity2SubsetRelationship extends AbstractConnectionModel<ConnectableElement> {
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

package jp.sourceforge.tmdmaker.model;

/**
 * 
 * @author nakaG
 *
 */
public class Entity extends AbstractEntityModel {
	private String physicalName = "";
	private String description = "";
	private Identifier identifier = null;
	/**
	 * @return the physicalName
	 */
	public String getPhysicalName() {
		return physicalName;
	}
	/**
	 * @param physicalName the physicalName to set
	 */
	public void setPhysicalName(String physicalName) {
		this.physicalName = physicalName;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the identifier
	 */
	public Identifier getIdentifier() {
		return identifier;
	}
	/**
	 * @param identifier the identifier to set
	 */
	public void setIdentifier(Identifier identifier) {
		this.identifier = identifier;
	}
	@Override
	public ReuseKey getMyReuseKey() {
		return new ReuseKey(this.identifier);
	}
}

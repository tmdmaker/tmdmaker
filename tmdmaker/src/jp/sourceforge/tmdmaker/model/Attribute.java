package jp.sourceforge.tmdmaker.model;

/**
 * 
 * @author nakaG
 *
 */
public class Attribute extends ModelElement {
	private String physicalName = "";
	private String description = "";
	private String type = "";
	private String size;
	private int scale;

	public Attribute() {
	}
	public Attribute(String name) {
		setName(name);
	}
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
	 * @return the size
	 */
	public String getSize() {
		return size;
	}
	/**
	 * @param size the size to set
	 */
	public void setSize(String size) {
		this.size = size;
	}
	/**
	 * @return the scale
	 */
	public int getScale() {
		return scale;
	}
	/**
	 * @param scale the scale to set
	 */
	public void setScale(int scale) {
		this.scale = scale;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

}

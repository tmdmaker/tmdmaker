package jp.sourceforge.tmdmaker.model;


public class EditSubsetEntity {
	private SubsetEntity original;
	private String name;
	
	public EditSubsetEntity() {
	}
	public EditSubsetEntity(SubsetEntity subsetEntity) {
		this.original = subsetEntity;
		this.name = subsetEntity.getName();
	}
	public boolean isAdded() {
		return original == null;
	}
	public boolean isNameChanged() {
		return original != null && !original.getName().equals(name);
	}
	/**
	 * @return the subsetEntity
	 */
	public SubsetEntity getOriginal() {
		return original;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
}
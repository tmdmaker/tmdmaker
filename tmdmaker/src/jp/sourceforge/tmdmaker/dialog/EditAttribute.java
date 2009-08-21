package jp.sourceforge.tmdmaker.dialog;

import jp.sourceforge.tmdmaker.model.Attribute;

/**
 * 
 * @author nakaG
 *
 */
public class EditAttribute {
	/** 編集元アトリビュート */
	private Attribute originalAttribute;
	/** 編集用名称 */
	private String name;
	/** 編集有無 */
	private boolean edited = false;

	/**
	 * コンストラクタ
	 */
	public EditAttribute() {
		
	}
	/**
	 * コンストラクタ
	 * 
	 * @param original
	 *            編集元アトリビュート
	 */
	public EditAttribute(Attribute original) {
		this.originalAttribute = original;
		this.name = original.getName();
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
		setEdited(true);
	}

	/**
	 * @return the originalAttribute
	 */
	public Attribute getOriginalAttribute() {
		return originalAttribute;
	}
	/**
	 * @return the edited
	 */
	public boolean isEdited() {
		return edited;
	}
	/**
	 * @param edited the edited to set
	 */
	public void setEdited(boolean edited) {
		this.edited = edited;
	}
	public boolean isAdded() {
		return originalAttribute == null;
	}
}

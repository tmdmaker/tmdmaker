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
	}

	/**
	 * @return the originalAttribute
	 */
	public Attribute getOriginalAttribute() {
		return originalAttribute;
	}
	
}

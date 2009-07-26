package jp.sourceforge.tmdmaker.model;

/**
 * エンティティ種類定数
 * 
 * @author nakaG
 * 
 */
public enum EntityType {
	/** リソース */
	RESOURCE("R"),
	/** イベント */
	EVENT("E"),
	/** 多値のOR */
	MO("MO");
	
	/** 表示用 */
	private String label;
	
	/**
	 * コンストラクタ
	 */
	private EntityType(String label) {
		this.label = label;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}
	
}

package jp.sourceforge.tmdmaker.model;

/**
 * 対照表種類定数
 * 
 * @author nakaG
 * 
 */
public enum CombinationTableType {
	/** L-真 */
	L_TRUTH("L"),
	/** F-真 */
	F_TRUTH("F");
	
	/** 表示用 */
	private String label;
	
	/**
	 * コンストラクタ
	 * @param label 対照表種類の表示名
	 */
	private CombinationTableType(String label) {
		this.label = label;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}
	
}

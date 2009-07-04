package jp.sourceforge.tmdmaker.model;
/**
 * リレーションシップのカーディナリティ
 * @author nakaG
 *
 */
public enum Cardinality {
	/** 1 */
	ONE("1"),
	/** N */
	MANY("N"),
	/** 0あり */
	ZERO("0");
	/** カーディナリティの表示内容 */
	private String label;
	/**
	 * コンストラクタ
	 * @param label カーディナリティの表示内容
	 */
	private Cardinality(String label) {
		this.label = label;
	}
	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}
	
}

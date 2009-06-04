package jp.sourceforge.tmdmaker.model;
/**
 * 
 * @author nakaG
 *
 */
@SuppressWarnings("serial")
public class ReUseKey extends Identifier {
	/** 元の個体指示子 */
	private Identifier original;

	/**
	 * @return the original
	 */
	public Identifier getOriginal() {
		return original;
	}

	/**
	 * @param original the original to set
	 */
	public void setOriginal(Identifier original) {
		this.original = original;
	}
	
}

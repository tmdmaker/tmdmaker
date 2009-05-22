package jp.sourceforge.tmdmaker.model;

/**
 * 
 * @author nakaG
 * 
 */
public class SubsetEntity extends AbstractEntityModel {

	private AbstractEntityModel original;

	private ReuseKey originalReuseKey;
	
	/**
	 * @return the originalReuseKey
	 */
	public ReuseKey getOriginalReuseKey() {
		return originalReuseKey;
	}

	/**
	 * @param originalReuseKey the originalReuseKey to set
	 */
	public void setOriginalReuseKey(ReuseKey originalReuseKey) {
		this.originalReuseKey = originalReuseKey;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.AbstractEntityModel#getMyReuseKey()
	 */
	@Override
	public ReuseKey getMyReuseKey() {
		return originalReuseKey;
	}

	/**
	 * @param original
	 *            the original to set
	 */
	public void setOriginal(AbstractEntityModel original) {
		this.original = original;
	}

	/**
	 * @return the original
	 */
	public AbstractEntityModel getOriginal() {
		return original;
	}
}

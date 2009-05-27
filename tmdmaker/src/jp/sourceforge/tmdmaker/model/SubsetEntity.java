package jp.sourceforge.tmdmaker.model;

/**
 * サブセットモデル
 * @author nakaG
 * 
 */
@SuppressWarnings("serial")
public class SubsetEntity extends AbstractEntityModel {
	/** サブセットの親のRe-usedキー */
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
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.model.AbstractEntityModel#canEntityTypeEditable()
	 */
	@Override
	public boolean canEntityTypeEditable() {
		return false;
	}
	
}

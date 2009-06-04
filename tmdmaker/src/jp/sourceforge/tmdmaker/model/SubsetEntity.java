package jp.sourceforge.tmdmaker.model;

/**
 * サブセットモデル
 * @author nakaG
 * 
 */
@SuppressWarnings("serial")
public class SubsetEntity extends AbstractEntityModel {
	/** サブセットの親のRe-usedキー */
	private ReUseKeys originalReuseKey;
	
	/**
	 * @return the originalReuseKey
	 */
	public ReUseKeys getOriginalReuseKey() {
		return originalReuseKey;
	}

	/**
	 * @param originalReuseKey the originalReuseKey to set
	 */
	public void setOriginalReuseKey(ReUseKeys originalReuseKey) {
		this.originalReuseKey = originalReuseKey;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.AbstractEntityModel#getMyReuseKey()
	 */
	@Override
	public ReUseKeys getMyReuseKey() {
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

package jp.sourceforge.tmdmaker.model;

/**
 * サブセットモデル
 * 
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
	 * @param originalReuseKey
	 *            the originalReuseKey to set
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
		System.out.println(getClass() + "#getMyReuseKey()");
		System.out.println("value = [" + originalReuseKey + "]");

		ReUseKeys returnValue = new ReUseKeys();
		returnValue.addAll(this.originalReuseKey.getIdentifires());

		return returnValue;
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

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.AbstractEntityModel#canDeletable()
	 */
	@Override
	public boolean canDeletable() {
		return getModelTargetConnections().size() == 1
				&& getModelSourceConnections().size() == 0;
	}

}

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
	private ReUseKey originalReuseKey;

	/**
	 * @return the originalReuseKey
	 */
	public ReUseKey getOriginalReuseKey() {
		return originalReuseKey;
	}

	/**
	 * @param originalReuseKey
	 *            the originalReuseKey to set
	 */
	public void setOriginalReuseKey(ReUseKey originalReuseKey) {
		this.originalReuseKey = originalReuseKey;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.AbstractEntityModel#getMyReuseKey()
	 */
	@Override
	public ReUseKey getMyReuseKey() {
		System.out.println(getClass() + "#getMyReuseKey()");
		System.out.println("value = [" + originalReuseKey + "]");

		ReUseKey returnValue = new ReUseKey();
		returnValue.addAll(this.originalReuseKey.getIdentifires());

		return returnValue;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.AbstractEntityModel#isEntityTypeEditable()
	 */
	@Override
	public boolean isEntityTypeEditable() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.AbstractEntityModel#isDeletable()
	 */
	@Override
	public boolean isDeletable() {
		return getModelTargetConnections().size() == 1
				&& getModelSourceConnections().size() == 0;
	}

}

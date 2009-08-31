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
	private ReusedIdentifier originalReuseKey;

	/**
	 * @return the originalReuseKey
	 */
	public ReusedIdentifier getOriginalReuseKey() {
		return originalReuseKey;
	}

	/**
	 * @param originalReuseKey
	 *            the originalReuseKey to set
	 */
	public void setOriginalReuseKey(ReusedIdentifier originalReuseKey) {
		this.originalReuseKey = originalReuseKey;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.AbstractEntityModel#getMyReuseKey()
	 */
	@Override
	public ReusedIdentifier getMyReuseKey() {
		System.out.println(getClass() + "#getMyReuseKey()");
		System.out.println("value = [" + originalReuseKey + "]");

		ReusedIdentifier returnValue = new ReusedIdentifier();
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

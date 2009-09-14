package jp.sourceforge.tmdmaker.model;

/**
 * 
 * @author nakaG
 * 
 */
@SuppressWarnings("serial")
public class Detail extends AbstractEntityModel {
	/** HDRモデルのRe-used */
	private ReusedIdentifier originalReusedIdentifier;

	/** DTLの個体指示子 */
	private Identifier detailIdentifier = new Identifier();
	/**
	 * DTLの個体指示子名を設定する
	 * @param name
	 */
	public void setDetailIdeitifierName(String name) {
		String oldValue = detailIdentifier.getName();
		detailIdentifier.setName(name);
		firePropertyChange(PROPERTY_IDENTIFIER, oldValue, name);
	}
	
	/**
	 * @return the detailIdentifier
	 */
	public Identifier getDetailIdentifier() {
		return detailIdentifier;
	}

	/**
	 * @return the originalReusedIdentifier
	 */
	public ReusedIdentifier getOriginalReusedIdentifier() {
		return originalReusedIdentifier;
	}

	/**
	 * @param originalReusedIdentifier
	 *            the originalReusedIdentifier to set
	 */
	public void setOriginalReusedIdentifier(
			ReusedIdentifier originalReusedIdentifier) {
		this.originalReusedIdentifier = originalReusedIdentifier;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.AbstractEntityModel#isEntityTypeEditable()
	 */
	@Override
	public boolean isEntityTypeEditable() {
		return false;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.AbstractEntityModel#createReusedIdentifier()
	 */
	@Override
	public ReusedIdentifier createReusedIdentifier() {
		ReusedIdentifier returnValue = new ReusedIdentifier();
		returnValue.addAll(this.originalReusedIdentifier.getIdentifires());
		returnValue.addIdentifier(detailIdentifier);
		return returnValue;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.AbstractEntityModel#isDeletable()
	 */
	@Override
	public boolean isDeletable() {
		return getModelSourceConnections().size() == 1 && getModelTargetConnections().size() == 1;
	}
}

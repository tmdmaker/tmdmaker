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

	/** DTLの個体指定子 */
	private Identifier detailIdentifier = new Identifier();
	/**
	 * DTLの個体指定子名を設定する
	 * @param name
	 */
	public void setDetailIdentifierName(String name) {
		String oldValue = detailIdentifier.getName();
		detailIdentifier.setName(name);
		if (oldValue == null || !oldValue.equals(name)) {
			firePropertyChange(PROPERTY_IDENTIFIER, oldValue, name);
			fireIdentifierChanged(null);
		}		
	}
	
	/**
	 * @return the detailIdentifier
	 */
	public Identifier getDetailIdentifier() {
		return detailIdentifier;
	}
	
	/**
	 * @param detailIdentifier the detailIdentifier to set
	 */
	public void setDetailIdentifier(Identifier detailIdentifier) {
		Identifier oldValue = this.detailIdentifier;
		this.detailIdentifier = detailIdentifier;
		firePropertyChange(PROPERTY_IDENTIFIER, oldValue, detailIdentifier);
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

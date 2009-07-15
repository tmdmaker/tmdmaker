package jp.sourceforge.tmdmaker.model;

/**
 * 
 * @author nakaG
 * 
 */
@SuppressWarnings("serial")
public class Entity extends AbstractEntityModel {
	/** 個体指示子プロパティ定数 */
	public static final String PROPERTY_IDENTIFIER = "_property_identifier";
	/** 物理名 */
	private String physicalName = "";
	/** 摘要 */
	private String description = "";
	/** 個体指示子 */
	private Identifier identifier = null;

	/**
	 * @return the physicalName
	 */
	public String getPhysicalName() {
		return physicalName;
	}

	/**
	 * @param physicalName
	 *            the physicalName to set
	 */
	public void setPhysicalName(String physicalName) {
		this.physicalName = physicalName;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the identifier
	 */
	public Identifier getIdentifier() {
		return identifier;
	}

	/**
	 * @param identifier
	 *            the identifier to set
	 */
	public void setIdentifier(Identifier identifier) {
		Identifier oldValue = this.identifier;
		this.identifier = identifier;
		firePropertyChange(PROPERTY_IDENTIFIER, oldValue, identifier);
	}
	/**
	 * 
	 * @param identifierName 個体指示子名称
	 */
	public void setIdentifierName(String identifierName) {
		String oldValue = this.identifier.getName();
		this.identifier.setName(identifierName);
		if (!oldValue.equals(identifierName)) {
			firePropertyChange(PROPERTY_IDENTIFIER, oldValue, identifier);
			notifyReUseKeyChange(null);
		}
	}
	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.AbstractEntityModel#getMyReuseKey()
	 */
	@Override
	public ReUseKeys getMyReuseKey() {
		return new ReUseKeys(this.identifier);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.AbstractEntityModel#canEntityTypeEditable()
	 */
	@Override
	public boolean canEntityTypeEditable() {
		return getModelSourceConnections().size() == 0
				&& getModelTargetConnections().size() == 0;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.model.AbstractEntityModel#canDeletable()
	 */
	@Override
	public boolean canDeletable() {
		return getModelSourceConnections().size() == 0
		&& getModelTargetConnections().size() == 0;
	}
	
}

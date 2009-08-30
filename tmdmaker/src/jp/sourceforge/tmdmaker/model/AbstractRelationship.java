package jp.sourceforge.tmdmaker.model;


/**
 * 
 * @author nakaG
 *
 */
@SuppressWarnings("serial")
public abstract class AbstractRelationship extends AbstractConnectionModel implements ReUseKeyChangeListener {
	/** 接続元とのカーディナリティ */
	private Cardinality sourceCardinality = Cardinality.ONE;
	/** 接続先とのカーディナリティ */
	private Cardinality targetCardinality = Cardinality.ONE;
	private boolean sourceNoInstance = false;
	private boolean targetNoInstance = false;
	private boolean centerMark = false;
	
	/**
	 * @return the sourceCardinality
	 */
	public Cardinality getSourceCardinality() {
		return sourceCardinality;
	}

	/**
	 * @param sourceCardinality the sourceCardinality to set
	 */
	public void setSourceCardinality(Cardinality sourceCardinality) {
		Cardinality oldValue = this.sourceCardinality;
		this.sourceCardinality = sourceCardinality;
		firePropertyChange(PROPERTY_SOURCE_CARDINALITY, oldValue, sourceCardinality);
	}

	/**
	 * @return the targetCardinality
	 */
	public Cardinality getTargetCardinality() {
		return targetCardinality;
	}

	/**
	 * @param targetCardinality the targetCardinality to set
	 */
	public void setTargetCardinality(Cardinality targetCardinality) {
		Cardinality oldValue = this.targetCardinality;
		this.targetCardinality = targetCardinality;
		firePropertyChange(PROPERTY_TARGET_CARDINALITY, oldValue, targetCardinality);
	}

	/**
	 * @return the sourceNoInstance
	 */
	public boolean isSourceNoInstance() {
		return sourceNoInstance;
	}

	/**
	 * @param sourceNoInstance the sourceNoInstance to set
	 */
	public void setSourceNoInstance(boolean sourceNoInstance) {
		boolean oldValue = this.sourceNoInstance;
		this.sourceNoInstance = sourceNoInstance;
		firePropertyChange(PROPERTY_SOURCE_CARDINALITY, oldValue, sourceNoInstance);
	}

	/**
	 * @return the targetNoInstance
	 */
	public boolean isTargetNoInstance() {
		return targetNoInstance;
	}

	/**
	 * @param targetNoInstance the targetNoInstance to set
	 */
	public void setTargetNoInstance(boolean targetNoInstance) {
		boolean oldValue = this.targetNoInstance;
		this.targetNoInstance = targetNoInstance;
		firePropertyChange(PROPERTY_TARGET_CARDINALITY, oldValue, targetNoInstance);
	}

	/**
	 * @return the centerMark
	 */
	public boolean isCenterMark() {
		return centerMark;
	}

	/**
	 * @param centerMark the centerMark to set
	 */
	public void setCenterMark(boolean centerMark) {
		boolean oldValue = this.centerMark;
		this.centerMark = centerMark;
		firePropertyChange(PROPERTY_CONNECTION, oldValue, centerMark);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.model.ReUseKeyChangeListener#notifyReUseKeyChanged()
	 */
	@Override
	public void notifyReUseKeyChanged() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.model.AbstractConnectionModel#canDeletable()
	 */
	@Override
	public boolean canDeletable() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public AbstractEntityModel getSource() {
		return (AbstractEntityModel) super.getSource();
	}
	public AbstractEntityModel getTarget() {
		return (AbstractEntityModel) super.getTarget();
	}
	public void setSource(AbstractEntityModel source) {
		super.setSource(source);
	}
	public void setTarget(AbstractEntityModel target) {
		super.setTarget(target);
	}
}

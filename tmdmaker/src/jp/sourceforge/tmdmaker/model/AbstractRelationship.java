package jp.sourceforge.tmdmaker.model;

/**
 * 
 * @author nakaG
 *
 */
@SuppressWarnings("serial")
public abstract class AbstractRelationship extends AbstractConnectionModel<AbstractEntityModel> {
	private String sourceCardinality = "1";
	private String targetCardinality = "1";
	private boolean sourceNoInstance = false;
	private boolean targetNoInstance = false;
	private boolean centerMark = false;
	
	/**
	 * @return the sourceCardinality
	 */
	public String getSourceCardinality() {
		return sourceCardinality;
	}

	/**
	 * @param sourceCardinality the sourceCardinality to set
	 */
	public void setSourceCardinality(String sourceCardinality) {
		String oldValue = this.sourceCardinality;
		this.sourceCardinality = sourceCardinality;
		firePropertyChange(P_SOURCE_CARDINALITY, oldValue, sourceCardinality);
	}

	/**
	 * @return the targetCardinality
	 */
	public String getTargetCardinality() {
		return targetCardinality;
	}

	/**
	 * @param targetCardinality the targetCardinality to set
	 */
	public void setTargetCardinality(String targetCardinality) {
		String oldValue = this.targetCardinality;
		this.targetCardinality = targetCardinality;
		firePropertyChange(P_TARGET_CARDINALITY, oldValue, targetCardinality);
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
		firePropertyChange(P_SOURCE_CARDINALITY, oldValue, sourceNoInstance);
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
		firePropertyChange(P_TARGET_CARDINALITY, oldValue, targetNoInstance);
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
	
	public abstract boolean canDeletable();
}

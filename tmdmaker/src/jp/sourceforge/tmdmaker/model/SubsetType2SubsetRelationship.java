package jp.sourceforge.tmdmaker.model;
/**
 * 
 * @author nakaG
 *
 */
@SuppressWarnings("serial")
public class SubsetType2SubsetRelationship extends RelatedRelationship
		implements ReUseKeyChangeListener {
	/**
	 * 
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.model.ReUseKeyChangeListener#reUseKeyChanged()
	 */
	@Override
	public void reUseKeyChanged() {
		((AbstractEntityModel) getTarget()).fireReUseKeyChange(this);
	}
}

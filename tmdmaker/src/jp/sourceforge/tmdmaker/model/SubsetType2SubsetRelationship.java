package jp.sourceforge.tmdmaker.model;
/**
 * 
 * @author nakaG
 *
 */
@SuppressWarnings("serial")
public class SubsetType2SubsetRelationship extends RelatedRelationship
		implements ReUseKeyChangeListener {

	@Override
	public void notifyReUseKeyChanged() {
		((AbstractEntityModel) getTarget()).notifyReUseKeyChange(this);
	}
}

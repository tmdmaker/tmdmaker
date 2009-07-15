package jp.sourceforge.tmdmaker.model;

public class SubsetType2SubsetRelationship extends RelatedRelationship
		implements ReUseKeysChangeListener {

	@Override
	public void awareReUseKeysChanged() {
		((AbstractEntityModel) getTarget()).notifyReUseKeyChange(this);
	}
}

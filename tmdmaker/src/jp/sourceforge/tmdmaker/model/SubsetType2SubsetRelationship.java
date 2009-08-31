package jp.sourceforge.tmdmaker.model;
/**
 * 
 * @author nakaG
 *
 */
@SuppressWarnings("serial")
public class SubsetType2SubsetRelationship extends RelatedRelationship
		implements IdentifierChangeListener {
	/**
	 * 
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.model.IdentifierChangeListener#identifierChanged()
	 */
	@Override
	public void identifierChanged() {
		((AbstractEntityModel) getTarget()).fireIdentifierChanged(this);
	}
}

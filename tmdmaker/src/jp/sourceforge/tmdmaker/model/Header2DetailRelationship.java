package jp.sourceforge.tmdmaker.model;

/**
 * 
 * @author nakaG
 *
 */
@SuppressWarnings("serial")
public class Header2DetailRelationship extends TransfarReuseKeysToTargetRelationship {
	
	/**
	 * コンストラクタ
	 * 
	 */
	public Header2DetailRelationship() {
		
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.model.TransfarReuseKeysToTargetRelationship#awareReUseKeysChanged()
	 */
	@Override
	public void awareReUseKeysChanged() {
		super.awareReUseKeysChanged();
		AbstractConnectionModel m =getTarget().getModelSourceConnections().get(0);
		System.out.println("Source = " + m.getSource().getClass());
		System.out.println("Source = " + m.getSource().getName());
		System.out.println("Target = " + m.getTarget().getClass());
		System.out.println("Target = " + m.getTarget().getName());
		MultivalueAndAggregator aggregator = (MultivalueAndAggregator) m.getTarget();
		AbstractConnectionModel t =aggregator.getModelTargetConnections().get(0);
		System.out.println("Source = " + t.getSource().getClass());
		System.out.println("Source = " + t.getSource().getName());
		System.out.println("Target = " + t.getTarget().getClass());
		System.out.println("Target = " + t.getTarget().getName());
		t.getSource().firePropertyChange(AbstractEntityModel.PROPERTY_REUSEKEY, null, null);
		
	}
	
}

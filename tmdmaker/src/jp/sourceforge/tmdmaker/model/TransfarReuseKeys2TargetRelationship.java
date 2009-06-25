package jp.sourceforge.tmdmaker.model;

/**
 * ソースのReuseKeyをターゲットへ移送するリレーションシップ
 * 
 * @author nakaG
 * 
 */
@SuppressWarnings("serial")
public class TransfarReuseKeys2TargetRelationship extends Relationship {
	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.AbstractConnectionModel#attachTarget()
	 */
	@Override
	public void attachTarget() {
		super.attachTarget();
		((AbstractEntityModel) getTarget())
				.addReuseKey((AbstractEntityModel) getSource());
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.AbstractConnectionModel#detachTarget()
	 */
	@Override
	public void detachTarget() {
		((AbstractEntityModel) getTarget())
				.removeReuseKey((AbstractEntityModel) getSource());
		super.detachTarget();
	}

}

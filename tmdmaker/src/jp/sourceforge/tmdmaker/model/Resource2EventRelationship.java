package jp.sourceforge.tmdmaker.model;

/**
 * 
 * @author nakaG
 *
 */
public class Resource2EventRelationship extends Relationship {

	/* (non-Javadoc)
	 * @see tm.tmdiagram.tmdeditor.model.AbstractConnectionModel#attachTarget()
	 */
	@Override
	public void attachTarget() {
		super.attachTarget();
		((AbstractEntityModel)getTarget()).addReuseKey((AbstractEntityModel)getSource());
	}

	/* (non-Javadoc)
	 * @see tm.tmdiagram.tmdeditor.model.AbstractConnectionModel#detachTarget()
	 */
	@Override
	public void detachTarget() {
		((AbstractEntityModel)getTarget()).removeReuseKey((AbstractEntityModel)getSource());
		super.detachTarget();
	}

}

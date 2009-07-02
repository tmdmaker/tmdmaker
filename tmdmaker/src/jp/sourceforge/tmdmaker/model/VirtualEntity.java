package jp.sourceforge.tmdmaker.model;

public class VirtualEntity extends AbstractEntityModel {

	@Override
	public boolean canEntityTypeEditable() {
		return false;
	}

	@Override
	public ReUseKeys getMyReuseKey() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see jp.sourceforge.tmdmaker.model.AbstractEntityModel#canDeletable()
	 */
	@Override
	public boolean canDeletable() {
		// TODO Auto-generated method stub
		return false;
	}

}

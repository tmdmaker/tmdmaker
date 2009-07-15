package jp.sourceforge.tmdmaker.model;

import java.util.Map;

/**
 * 
 * @author nakaG
 * 
 */
@SuppressWarnings("serial")
public class CombinationTable extends AbstractEntityModel {
	/** 対照表名のサフィックス */
	public static final String COMBINATION_TABLE_SUFFIX = ".対照表";

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.AbstractEntityModel#getMyReuseKey()
	 */
	@Override
	public ReUseKeys getMyReuseKey() {
		ReUseKeys returnValue = new ReUseKeys();
		for (Map.Entry<AbstractEntityModel, ReUseKeys> rk : this.reuseKeys
				.entrySet()) {
			returnValue.addAll(rk.getValue().getIdentifires());
		}
		return returnValue;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.AbstractEntityModel#canEntityTypeEditable()
	 */
	@Override
	public boolean canEntityTypeEditable() {
		// return getModelTargetConnections().size() == 1
		// && getModelSourceConnections().size() == 0;
		return false;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.AbstractEntityModel#canDeletable()
	 */
	@Override
	public boolean canDeletable() {
		return getModelSourceConnections().size() == 0
				&& getModelTargetConnections().size() == 1;
	}
	/**
	 * 
	 */
	public void notifyReUseKeyChange(AbstractConnectionModel<?> callConnection) {
		firePropertyChange(AbstractEntityModel.PROPERTY_REUSEKEY, null, null);
		for (AbstractConnectionModel<?> con : getModelTargetConnections()) {

			if (con instanceof ReUseKeysChangeListener && con != callConnection ) {
				((ReUseKeysChangeListener) con).awareReUseKeysChanged();
			}
		}
		for (AbstractConnectionModel<?> con : getModelSourceConnections()) {
			if (con instanceof ReUseKeysChangeListener && con != callConnection ) {
				((ReUseKeysChangeListener) con).awareReUseKeysChanged();
			}
		}

	}
}
